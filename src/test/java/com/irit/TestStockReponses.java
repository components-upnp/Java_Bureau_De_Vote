package com.irit;

import com.irit.reponses.StockReponses;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by mkostiuk on 12/05/2017.
 */
public class TestStockReponses extends TestCase {

    private StockReponses sr;


    @Before
    public void setUp() {
        sr = new StockReponses(2);
    }

    @Test
    public void testNbQuestions() {
        assertEquals(2, sr.getNbQuestions());
    }

    @Test
    public void testAddReponseOk() {
        sr.addReponse(1);
        assertEquals(1, sr.getReponses().get(1).intValue());
    }

    @Test
    public void testGetNbReponses0() {
        assertEquals(0, sr.getNbVotes());
    }

    @Test
    public void testGetNbReponses5() {
        sr.addReponse(1);
        sr.addReponse(2);
        sr.addReponse(1);
        sr.addReponse(2);
        sr.addReponse(1);
        assertEquals(5, sr.getNbVotes());
    }

    @Test
    public void testAddResponse0()  {
        boolean passed = false;
        try {
            sr.addReponse(0);
        } catch (NullPointerException ex) {
            passed = true;
        }
        assertTrue(passed);
    }
}
