package com.irit.upnp;

import com.irit.reponses.LecteurXml;
import org.fourthline.cling.binding.annotations.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by mkostiuk on 02/05/2017.
 */

@UpnpService(
        serviceType = @UpnpServiceType("VoteController"),
        serviceId = @UpnpServiceId(value = "VoteController")
)
public class VoteController {
    private final PropertyChangeSupport propertyChangeSupport;

    public VoteController() {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public PropertyChangeSupport getPropertyChangeSupport() {
        return propertyChangeSupport;
    }

    @UpnpStateVariable(defaultValue = "false")
    private boolean state = false;

    @UpnpStateVariable(defaultValue = "")
    private String commande = "";

    @UpnpStateVariable(defaultValue = "")
    private String udn = "";

    @UpnpStateVariable
    private String question = "";

    private ArrayList<String> listeUdnEleves = new ArrayList<String>();

    @UpnpAction
    public void setState() {
        boolean oldStateValue = state;
        state = !state;

        getPropertyChangeSupport().firePropertyChange("state", oldStateValue, state);
    }

    @UpnpAction
    public boolean getState() {
        return state;
    }

    @UpnpAction(name = "Inscription")
    public void inscritpion(@UpnpInputArgument(name = "Udn") String udn) {
        udn = "uuid:"+udn;
        if (!listeUdnEleves.contains(udn) && !state) {
            listeUdnEleves.add(udn);
            System.out.println("Nouvel UDN: " +udn);
            getPropertyChangeSupport().firePropertyChange("inscription", null, udn);
        }
    }

    @UpnpAction(name = "Commande")
    public void commande(@UpnpInputArgument(name = "Commande") String com) throws IOException, SAXException, ParserConfigurationException {

        commande = com;
        LecteurXml l = new LecteurXml(commande);
        String u = l.getUdn();
        String c = l.getCommande();

        System.out.println(u);
        System.out.println(c);

        if (state && listeUdnEleves.contains(u)) {
            getPropertyChangeSupport().firePropertyChange("commande", null, Integer.valueOf(c));
        }
    }

    public void notifier(String q) {
        question = q;
        getPropertyChangeSupport().firePropertyChange("Question", null, question);
    }
}


