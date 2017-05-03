package com.irit.upnp;

import org.fourthline.cling.binding.annotations.*;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    public void inscritpion(@UpnpInputArgument(name = "Udn")String udn) {
        if (!listeUdnEleves.contains(udn) && !state) {
            listeUdnEleves.add(udn);
            getPropertyChangeSupport().firePropertyChange("inscription", null, udn);
        }
    }

    @UpnpAction(name = "Commande")
    public void commande(@UpnpInputArgument(name = "Commande") String c, @UpnpInputArgument(name = "Udn") String udn) {
        if (state && listeUdnEleves.contains(udn)) {
            getPropertyChangeSupport().firePropertyChange("commande", null, Integer.valueOf(c));
        }
    }

    public void notifier(String q) {
        question = q;
        getPropertyChangeSupport().firePropertyChange("Question", null, question);
    }
}
