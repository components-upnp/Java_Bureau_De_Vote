package com.irit.upnp;

import org.fourthline.cling.binding.annotations.*;

import java.beans.PropertyChangeSupport;

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

    @UpnpStateVariable(defaultValue = "false")
    private boolean isAppaire = false;

    @UpnpStateVariable
    private String clef = "1234";

    @UpnpStateVariable
    private String typeCommande;

    @UpnpStateVariable
    private String udnMaster;

    @UpnpAction(name = "Appairage")
    public void appairage(@UpnpInputArgument(name = "Clef")String inClef, @UpnpInputArgument(name = "UdnMaster") String udn ) {
        if ((inClef == clef) && !isAppaire) {
            isAppaire = true;
            udnMaster = udn;
            getPropertyChangeSupport().firePropertyChange("appairage", false, true);
        }
    }

    @UpnpAction(name = "Commande")
    public void commande(@UpnpInputArgument(name = "TypeCommande") String c, @UpnpInputArgument(name = "UdnMaster") String udn) {
        typeCommande = c;
        if (isAppaire && (udn == udnMaster))
            getPropertyChangeSupport().firePropertyChange("commande", null, c);
    }
}
