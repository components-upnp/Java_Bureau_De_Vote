package com.irit.upnp;

import org.fourthline.cling.binding.annotations.UpnpService;
import org.fourthline.cling.binding.annotations.UpnpServiceId;
import org.fourthline.cling.binding.annotations.UpnpServiceType;
import org.fourthline.cling.binding.annotations.UpnpStateVariable;

import java.beans.PropertyChangeSupport;

/**
 * Created by mkostiuk on 16/05/2017.
 */
@UpnpService(
        serviceId = @UpnpServiceId(value = "QuestionService"),
        serviceType = @UpnpServiceType("QuestionService")
)
public class QuestionService {

    private PropertyChangeSupport propertyChangeSupport;

    public QuestionService() {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public PropertyChangeSupport getPropertyChangeSupport() {
        return propertyChangeSupport;
    }

    @UpnpStateVariable
    private String question = "";

    public void notifier(String q) {
        question = q;
        getPropertyChangeSupport().firePropertyChange("Question", null, question);
    }

}
