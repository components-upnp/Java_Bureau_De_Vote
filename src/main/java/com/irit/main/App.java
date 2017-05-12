package com.irit.main;

import com.irit.reponses.GenerateurXML;
import com.irit.reponses.StockReponses;
import com.irit.upnp.BureauDeVoteServer;
import org.w3c.dom.Document;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws ParserConfigurationException, TransformerException {
        new Thread(new BureauDeVoteServer()).run();
        /*StockReponses sr = new StockReponses(3);
        sr.addReponse(1);
        sr.addReponse(1);
        sr.addReponse(2);

        GenerateurXML g = new GenerateurXML();
        Document res = g.getDocXml(sr.getReponses(),sr.getNbQuestions());

        DOMSource source = new DOMSource(res);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.transform(source, result);

        System.out.println(writer.toString());*/
    }
}
