package com.irit;

import com.irit.upnp.BureauDeVoteServer;
import junit.framework.TestCase;
import org.fourthline.cling.model.action.ActionArgumentValue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by mkostiuk on 17/05/2017.
 */
public class TestMasterControl extends TestCase {

    Subscription sub;
    Thread app;

    @Before
    public void setUp() {
        app = new Thread(new BureauDeVoteServer());
        app.run();
        pause(2000);
        sub = new Subscription("MasterCommandService");
        sub.run();
        pause(3000);
    }

    @After
    public void stop() {
        app.interrupt();
    }

    @Test
    public void testInscriptionClefIncorrecte() {
        pause(2000);
        ArrayList<Object> res = sub.executeAction("SetCommande",
                "Commande",
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><MasterRemoteControl xmlns=\"/\"><Key>2234</Key><UDN>uuid:TEST</UDN><Commande>CommandeTest</Commande></MasterRemoteControl>"
                );
        pause(2000);
        ActionArgumentValue r = (ActionArgumentValue) res.get(0);
        assertEquals("",r.toString());
    }

    @Test
    public void testCommandeClefValide() {
        pause(2000);
        ArrayList<Object> res = sub.executeAction("SetCommande",
                "Commande",
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><MasterRemoteControl xmlns=\"/\"><Key>1234</Key><UDN>uuid:TEST</UDN><Commande>CommandeTest</Commande></MasterRemoteControl>"
        );
        pause(2000);
        ActionArgumentValue r = (ActionArgumentValue) res.get(0);
        assertEquals("CommandeTest",r.toString());
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
