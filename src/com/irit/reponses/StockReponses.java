package com.irit.reponses;

import java.util.HashMap;

/**
 * Created by mkostiuk on 03/05/2017.
 */
public class StockReponses {

    private HashMap<Integer, Integer> reponses;
    private int nbQuestions;
    private int nbVotes;

    public StockReponses(int nb) {
        reponses = new HashMap<>();
        nbQuestions = nb;

        nbVotes = 0;

        for (int i = 0; i < nbQuestions; i++)
            reponses.put(i,0);
    }

    public void addReponse(int num) {
        nbVotes++;
        int nbCourant = reponses.get(num);
        nbCourant++;
        System.out.println(num + nbCourant);
        reponses.put(num, nbCourant);
    }

    public HashMap<Integer,Integer> getReponses() {
        return reponses;
    }

    public int getVoteReponse(int num) {
        return reponses.get(num);
    }

    public int getNbQuestions() {
        return nbQuestions;
    }

    public int getNbVotes() {
        return nbVotes;
    }
}
