package com.irit.main;

import com.irit.upnp.BureauDeVoteServer;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
       new Thread(new BureauDeVoteServer()).run();
    }
}
