package com.irit;

import com.irit.reponses.GenerateurXML;
import com.irit.reponses.StockReponses;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

/**
 * Created by mkostiuk on 12/05/2017.
 */
public class TestGenerateurXml extends TestCase {

    private String xml;

    @Before
    public void setUp() throws ParserConfigurationException, TransformerException {
        StockReponses sr = new StockReponses(3);
        sr.addReponse(1);
        sr.addReponse(1);
        sr.addReponse(2);

        GenerateurXML gen = new GenerateurXML();

        Document doc = gen.getDocXml(sr.getReponses(), sr.getNbQuestions());

        DOMSource source = new DOMSource(doc);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.transform(source, result);

        xml = writer.toString();
    }

    @Test
    public void testXmlOk() {
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Reponses><NombreQuestion>3</NombreQuestion><Question><Numero>0</Numero><NbReponse>2</NbReponse></Question><Question><Numero>1</Numero><NbReponse>1</NbReponse></Question><Question><Numero>2</Numero><NbReponse>0</NbReponse></Question></Reponses>",
                xml);
    }

}
