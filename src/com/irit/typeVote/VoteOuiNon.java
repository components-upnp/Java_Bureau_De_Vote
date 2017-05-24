package com.irit.typeVote;

import java.beans.PropertyChangeListener;

/**
 * Created by mkostiuk on 24/05/2017.
 */
public class VoteOuiNon implements Vote {
    @Override
    public PropertyChangeListener getListenerMasterService() {
        return null;
    }

    @Override
    public PropertyChangeListener getListenerVoteService() {
        return null;
    }
}
