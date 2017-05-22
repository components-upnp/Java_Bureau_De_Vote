package com.irit.reponses;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.StringReader;

/**
 * Created by mkostiuk on 04/05/2017.
 */
public class LecteurXml {

    private String udn;
    private String clef;
    private String commande;
    private String question;
    private String nbQuestion;

    public LecteurXml(String xml) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser sp = spf.newSAXParser();

        DefaultHandler handler = new DefaultHandler() {

            boolean isUdn = false;
            boolean isCommande = false;
            boolean isClef = false;
            boolean isNbQuestion = false;
            boolean isQuestion = false;

            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) {
                if (qName.equalsIgnoreCase("UDN"))
                    isUdn = true;
                if (qName.equalsIgnoreCase("Commande"))
                    isCommande = true;
                if (qName.equalsIgnoreCase("Key"))
                    isClef = true;
                if(qName.equalsIgnoreCase("NbQuestion"))
                    isNbQuestion = true;
                if (qName.equalsIgnoreCase("Question"))
                    isQuestion = true;
            }

            @Override
            public void characters(char ch[], int start, int length) {
                if (isCommande) {
                    isCommande = false;
                    commande = new String(ch, start, length);
                    System.err.println(commande);
                }
                if (isUdn) {
                    isUdn = false;
                    udn = new String(ch, start, length);
                }
                if (isClef) {
                    isClef = false;
                    clef = new String(ch,start, length);
                }
                if (isQuestion) {
                    isQuestion = false;
                    question = new String(ch, start, length);
                }
                if (isNbQuestion) {
                    isNbQuestion = false;
                    nbQuestion = new String(ch, start, length);
                }
            }
        };
        sp.parse(new InputSource(new StringReader(xml)), handler);
    }

    public String getUdn() {
        return udn;
    }

    public String getCommande() {
        return commande;
    }

    public String getClef() {return clef;}

    public String getNbQuestion() {
        return nbQuestion;
    }

    public String getQuestion() {
        return question;
    }
}
