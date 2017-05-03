package com.irit.reponses;

import java.util.HashMap;

/**
 * Created by mkostiuk on 03/05/2017.
 */
public class StockReponses {

    private HashMap<Integer, Integer> reponses;
    private int nbQuestions;

    public StockReponses(int nb) {
        reponses = new HashMap<Integer, Integer>();
        nbQuestions = nb;

        for (int i = 0; i < nbQuestions; i++)
            reponses.put(i,0);
    }

    public void addReponse(int num) {
        num--;
        int nbCourant = reponses.get(num);
        nbCourant++;
        System.out.println(num + nbCourant);
        reponses.put(num, nbCourant);
    }

    public HashMap<Integer,Integer> getReponses() {
        return reponses;
    }

    public int getNbQuestions() {
        return nbQuestions;
    }
}
