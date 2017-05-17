package com.irit.upnp;

import com.irit.reponses.LecteurXmlVote;
import org.fourthline.cling.binding.annotations.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.beans.PropertyChangeSupport;
import java.io.IOException;

/**
 * Created by mkostiuk on 02/05/2017.
 */
@UpnpService(
        serviceId = @UpnpServiceId("MasterCommandService"),
        serviceType = @UpnpServiceType(value = "MasterCommandService")
)
public class CommandeProfesseurController {

    private final PropertyChangeSupport propertyChangeSupport;

    public CommandeProfesseurController() {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public PropertyChangeSupport getPropertyChangeSupport() {
        return propertyChangeSupport;
    }



    @UpnpStateVariable
    private String clef = "1234";

    @UpnpStateVariable(sendEvents = false)
    String commande = "";


    @UpnpAction(name = "SetCommande", out = @UpnpOutputArgument(name = "Commande"))
    public String commande(@UpnpInputArgument(name = "Commande") String c) throws IOException, SAXException, ParserConfigurationException {
        LecteurXmlVote l = new LecteurXmlVote(c);
        commande = l.getCommande();
        if (clef == l.getClef()) {
            getPropertyChangeSupport().firePropertyChange("commande", null, commande);
        }
        return null;
    }

    public String getClef() {
        return clef;
    }
}
