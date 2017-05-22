package com.irit.upnp;

import org.fourthline.cling.binding.annotations.UpnpService;
import org.fourthline.cling.binding.annotations.UpnpServiceId;
import org.fourthline.cling.binding.annotations.UpnpServiceType;
import org.fourthline.cling.binding.annotations.UpnpStateVariable;

import java.beans.PropertyChangeSupport;

/**
 * Created by mkostiuk on 02/05/2017.
 */

@UpnpService(
        serviceType = @UpnpServiceType("ReportService"),
        serviceId = @UpnpServiceId(value = "ReportService")
)
public class ReportService {

    private final PropertyChangeSupport propertyChangeSupport;

    public ReportService() {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public PropertyChangeSupport getPropertyChangeSupport() {
        return propertyChangeSupport;
    }

    @UpnpStateVariable
    private String reponses = "";

    public void transmettreRapport(String r) {
        reponses = r;
        propertyChangeSupport.firePropertyChange("Reponses", null, reponses);
    }
}
