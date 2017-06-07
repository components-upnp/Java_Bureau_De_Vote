package com.irit.upnp;

import com.irit.reponses.GenerateurXML;
import com.irit.reponses.StockReponses;
import com.irit.typeVote.Vote;
import org.fourthline.cling.UpnpService;
import org.fourthline.cling.UpnpServiceImpl;
import org.fourthline.cling.binding.LocalServiceBindingException;
import org.fourthline.cling.binding.annotations.AnnotationLocalServiceBinder;
import org.fourthline.cling.model.DefaultServiceManager;
import org.fourthline.cling.model.meta.*;
import org.fourthline.cling.model.types.DeviceType;
import org.fourthline.cling.model.types.UDADeviceType;
import org.fourthline.cling.model.types.UDN;
import org.w3c.dom.Document;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;

/**
 * Created by mkostiuk on 02/05/2017.
 */
public class PollingStationServer implements Runnable {

    private LocalService<VoteService> voteService;
    private LocalService<MasterCommandService> commandeProfesseurService;
    private LocalService<ReportService> rapportService;
    private LocalService<SendQuestionService> questionService;

    private StockReponses stockReponses;
    private State state;

    private String question;

    private Vote typeVote;

    private enum State {
        INIT, SOUMISE;
    }

    public void run() {
        try {

            final UpnpService upnpService = new UpnpServiceImpl();

            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    upnpService.shutdown();
                }
            });

            // Add the bound local device to the registry
            upnpService.getRegistry().addDevice(
                    createDevice()
            );

            state = State.INIT;

            voteService.getManager().getImplementation()
                    .getPropertyChangeSupport().addPropertyChangeListener(
                    new PropertyChangeListener() {
                        public void propertyChange(PropertyChangeEvent evt) {
                            String stEvt = evt.getPropertyName().toString();

                            switch(state) {
                                case INIT:
                                    if (stEvt == "inscription") {
                                        System.out.println("Incription d'un élève!!!");
                                    }
                                    break;
                                case SOUMISE:
                                    if (stEvt == "commande") {
                                        System.out.println("Commande d'un élève reçue!!!");
                                        stockReponses.addReponse(((Integer)evt.getNewValue()));
                                    }
                                    break;
                            }
                        }
                    }
            );

            commandeProfesseurService.getManager().getImplementation()
                    .getPropertyChangeSupport().addPropertyChangeListener(
                    new PropertyChangeListener() {
                        public void propertyChange(PropertyChangeEvent evt) {
                            if (evt.getPropertyName().equals("question")) {
                                switch(state) {
                                    case INIT:
                                        HashMap<String,String> arg = (HashMap<String, String>) evt.getNewValue();
                                        question = arg.get("Question");
                                        stockReponses = new StockReponses(Integer.valueOf(arg.get("NbQuestion")));
                                        break;
                                    case SOUMISE:
                                        //Forbiden
                                        break;

                                }

                            } else
                            if (evt.getPropertyName().equals("commande")) {
                                if (evt.getNewValue().equals("CENTRE")) {
                                    switch(state) {
                                        case INIT:
                                            voteService.getManager().getImplementation().setState();
                                            state = State.SOUMISE;
                                            questionService.getManager().getImplementation()
                                                    .notifier(question);
                                            questionService.getManager().getImplementation()
                                                    .notifier("");
                                            break;
                                        case SOUMISE:
                                            state = State.INIT;
                                            Document res = null;
                                            try {
                                                res = new GenerateurXML().getDocXml(stockReponses.getReponses(),
                                                        stockReponses.getNbQuestions());
                                                DOMSource source = new DOMSource(res);
                                                StringWriter writer = new StringWriter();
                                                StreamResult result = new StreamResult(writer);

                                                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                                                Transformer transformer = transformerFactory.newTransformer();
                                                transformer.transform(source, result);
                                                rapportService.getManager().getImplementation().transmettreRapport(writer.toString());
                                                rapportService.getManager().getImplementation().transmettreRapport("");

                                                voteService.getManager().getImplementation()
                                                        .reinit();


                                            } catch (ParserConfigurationException e) {
                                                e.printStackTrace();
                                            } catch (TransformerConfigurationException e) {
                                                e.printStackTrace();
                                            } catch (TransformerException e) {
                                                e.printStackTrace();
                                            }

                                            break;

                                    }
                                }

                            }

                        }
                    }
            );

            while (true) {

            }


        } catch (Exception ex) {
            System.err.println("Exception occured: " + ex);
            ex.printStackTrace(System.err);
            System.exit(1);
        }
    }

    public LocalDevice createDevice()
            throws LocalServiceBindingException, IOException, org.fourthline.cling.model.ValidationException {

        DeviceIdentity identity =
                new DeviceIdentity(
                        UDN.uniqueSystemIdentifier("Polling Station")
                );

        DeviceType type =
                new UDADeviceType("Vote", 1);

        DeviceDetails details =
                new DeviceDetails(
                        "Polling Station",					// Friendly Name
                        new ManufacturerDetails(
                                "UPS-IRIT",								// Manufacturer
                                ""),								// Manufacturer URL
                        new ModelDetails(
                                "PollingStation",						// Model Name
                                "Bureau de vote logiciel, permet de soumettre une question aux utilisateurs",	// Model Description
                                "v1" 								// Model Number
                        )
                );
        voteService =
                new AnnotationLocalServiceBinder().read(VoteService.class);
        voteService.setManager(
                new DefaultServiceManager(voteService, VoteService.class)
        );
        commandeProfesseurService =
                new AnnotationLocalServiceBinder().read(MasterCommandService.class);
        commandeProfesseurService.setManager(
                new DefaultServiceManager(commandeProfesseurService,MasterCommandService.class)
        );
        rapportService =
                new AnnotationLocalServiceBinder().read(ReportService.class);
        rapportService.setManager(
                new DefaultServiceManager<ReportService>(rapportService, ReportService.class)
        );
        questionService =
                new AnnotationLocalServiceBinder().read(SendQuestionService.class);
        questionService.setManager(
                new DefaultServiceManager<SendQuestionService>(questionService,SendQuestionService.class)
        );




        //new Fenetre(voteService,commandeProfesseurService,rapportService,questionService).setVisible(true);

        return new LocalDevice(
                identity, type, details,
                new LocalService[] {voteService,commandeProfesseurService,rapportService,questionService}
        );
    }
}
