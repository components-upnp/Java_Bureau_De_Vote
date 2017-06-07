package com.irit.typeVote;

import java.beans.PropertyChangeListener;

/**
 * Created by mkostiuk on 24/05/2017.
 */
public interface Vote {

    public PropertyChangeListener getListenerMasterService();

    public PropertyChangeListener getListenerVoteService();

}
