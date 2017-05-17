package com.irit;

import com.irit.upnp.BureauDeVoteServer;
import junit.framework.TestCase;
import org.fourthline.cling.model.action.ActionArgumentValue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by mkostiuk on 12/05/2017.
 */
public class TestVoteController extends TestCase {

    Subscription sub;
    Thread app;

    @Before
    public void setUp() {
        app = new Thread(new BureauDeVoteServer());
        app.run();
        pause(2000);
        sub = new Subscription("VoteService");
        sub.run();
        pause(3000);
    }

    @After
    public void after() {
        app.interrupt();
    }



    //Vérifie que l'identifiant de l'élève soit bien ajouté à la liste
    @Test
    public void testInscriptionOk() {
        ArrayList<Object> res = sub.executeAction("Inscription",
                "Udn",
                "TEST");
        pause(2000);
        ActionArgumentValue r = (ActionArgumentValue) res.get(0);
        assertEquals("uuid:TEST", r.toString());
    }



    //Vérifie que le service retourne null après une inscription invalide
    @Test
    public void testDejaInscrit() {
        ArrayList<Object> res = sub.executeAction("Inscription",
                "Udn",
                "TEST");
        pause(2000);
        res = sub.executeAction("Inscription",
                "Udn",
                "TEST");
        pause(2000);

        ActionArgumentValue r = (ActionArgumentValue) res.get(0);
        assertEquals("", r.toString());
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
