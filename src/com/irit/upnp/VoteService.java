package com.irit.upnp;

import com.irit.reponses.LecteurXml;
import org.fourthline.cling.binding.annotations.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by mkostiuk on 02/05/2017.
 */

@UpnpService(
        serviceType = @UpnpServiceType("VoteService"),
        serviceId = @UpnpServiceId(value = "VoteService")
)
public class VoteService {
    private final PropertyChangeSupport propertyChangeSupport;

    public VoteService() {
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

    private HashMap<String, Boolean> listeUdnEleves = new HashMap<>();

    @UpnpAction
    public void setState() {
        boolean oldStateValue = state;
        state = !state;

        getPropertyChangeSupport().firePropertyChange("state", oldStateValue, state);
    }

    @UpnpAction(out = @UpnpOutputArgument(name = "State"))
    public boolean getState() {
        return state;
    }


    @UpnpAction(name = "SetCommande")
    public void commande(@UpnpInputArgument(name = "Commande") String com) throws IOException, SAXException, ParserConfigurationException {

        String c = com;
        LecteurXml l = new LecteurXml(c);
        String u = l.getUdn();
        commande = l.getCommande();

        System.out.println(u);
        System.out.println(commande);

        if (commande.equals("DROITE"))
            commande = "1";
        if (commande.equals("GAUCHE"))
            commande = "0";
        if (commande.equals("HAUT"))
            commande = "2";
        if (commande.equals("BAS"))
            commande = "3";
        if (commande.equals("CENTRE"))
            commande = "4";

        if (state && listeUdnEleves.containsKey(u)) {
            if (!listeUdnEleves.get(u)) {
                getPropertyChangeSupport().firePropertyChange("commande", null, Integer.valueOf(commande));
                listeUdnEleves.remove(u);
                listeUdnEleves.put(u,true);
            }
        }

        if (!listeUdnEleves.containsKey(u) && !state) {
            listeUdnEleves.put(u, false);
            System.out.println("Nouvel UDN: " +u);
            getPropertyChangeSupport().firePropertyChange("inscription", null, u);

        }

    }

    public void reinit() {
        for (String s : listeUdnEleves.keySet()) {
            listeUdnEleves.replace(s, true, false);
        }
    }

}


