package com.irit.main;

import com.irit.upnp.PollingStationServer;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws ParserConfigurationException, TransformerException {
        new Thread(new PollingStationServer()).run();
    }
}
