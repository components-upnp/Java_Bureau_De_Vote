package com.irit.upnp;

import com.irit.reponses.LecteurXml;
import org.fourthline.cling.binding.annotations.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mkostiuk on 02/05/2017.
 */
@UpnpService(
        serviceId = @UpnpServiceId("MasterCommandService"),
        serviceType = @UpnpServiceType(value = "MasterCommandService")
)
public class MasterCommandService {

    private final PropertyChangeSupport propertyChangeSupport;

    public MasterCommandService() {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public PropertyChangeSupport getPropertyChangeSupport() {
        return propertyChangeSupport;
    }



    @UpnpStateVariable
    private String clef = "1234";

    @UpnpStateVariable(sendEvents = false)
    String commande = "";

    @UpnpStateVariable(sendEvents = false)
    private String question = "";

    @UpnpStateVariable(sendEvents = false)
    private String nbQuestion = "0";


    @UpnpAction(name = "SetCommande", out = @UpnpOutputArgument(name = "Commande"))
    public String commande(@UpnpInputArgument(name = "Commande") String c) throws IOException, SAXException, ParserConfigurationException {
        LecteurXml l = new LecteurXml(c);
        commande = l.getCommande();
        if (clef.equals(l.getClef())) {
            getPropertyChangeSupport().firePropertyChange("commande", null, commande);
        }
        return null;
    }

    public String getClef() {
        return clef;
    }

    @UpnpAction(name = "SetQuestion")
    public void setQuestion(@UpnpInputArgument(name = "Question") String q) throws IOException, SAXException, ParserConfigurationException {
        LecteurXml l = new LecteurXml(q);
        question = l.getQuestion();
        System.out.println(l.getClef());
        if (clef.equals(l.getClef())) {
            nbQuestion = l.getNbQuestion();
            Map<String, String> arg = new HashMap<String, String>();
            arg.put("NbQuestion",nbQuestion);
            arg.put("Question", question);
            System.out.println("Question recue : " + question);
            propertyChangeSupport.firePropertyChange("question", "", arg);
        }
    }


}
