package com.irit;

import com.irit.upnp.BureauDeVoteServer;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by mkostiuk on 12/05/2017.
 */
public class TestVoteController extends TestCase {

    Susbcription sub;
    Thread app;

    @Before
    public void setUp() {
        app = new Thread(new BureauDeVoteServer());
        app.run();
        pause(2000);
        sub = new Susbcription("VoteController");
        sub.run();
        pause(3000);
    }

    @After
    public void after() {
        app.interrupt();
    }

    @Test
    public void testIsConnected() {
        assertTrue(sub.isDeviceConnecte());
    }

    //Permet de mettre l'exécution en pause, afin d'avoir le temps de recevoir les évènements
    public static void pause(long ms){
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }
}
