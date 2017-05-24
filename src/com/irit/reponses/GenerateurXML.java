package com.irit.reponses;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.HashMap;

/**
 * Created by mkostiuk on 03/05/2017.
 */
public class GenerateurXML {

    public Document getDocXml(HashMap<Integer,Integer> reponses, Integer nb) throws ParserConfigurationException {

        String namespace = "";
        Document doc;
        DocumentBuilderFactory db = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;

        builder = db.newDocumentBuilder();
        doc = builder.newDocument();
        Element root = doc.createElementNS(namespace, "Reponses");
        doc.appendChild(root);

        Element nbQuestion = doc.createElementNS(namespace, "NombreQuestion");
        root.appendChild(nbQuestion);
        nbQuestion.appendChild(doc.createTextNode(nb.toString()));

        Element question;
        Element nbReponse;
        Element numero;

        for (Integer i : reponses.keySet()) {
            question = doc.createElementNS(namespace,  "Question");
            root.appendChild(question);
            numero = doc.createElementNS(namespace,"Numero");
            question.appendChild(numero);
            numero.appendChild(doc.createTextNode(i.toString()));
            nbReponse = doc.createElementNS(namespace, "NbReponse");
            question.appendChild(nbReponse);
            nbReponse.appendChild(doc.createTextNode(reponses.get(i).toString()));
        }

        return doc;
    }

}
