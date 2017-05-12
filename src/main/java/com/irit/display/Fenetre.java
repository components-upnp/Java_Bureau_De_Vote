/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irit.display;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.irit.reponses.GenerateurXML;
import com.irit.reponses.StockReponses;
import com.irit.upnp.RapportController;
import org.fourthline.cling.model.meta.LocalService;
import com.irit.upnp.CommandeProfesseurController;
import com.irit.upnp.VoteController;
import org.w3c.dom.Document;

/**
 *
 * @author mkostiuk
 */
public class Fenetre extends javax.swing.JFrame {
    
    private enum State {
        INIT, SOUMISE;
    }
    
    private LocalService<VoteController> voteService;
    private LocalService<CommandeProfesseurController> commandeProfesseurService;
    private LocalService<RapportController> rapportService;
    private State state;

    private StockReponses stockReponses;

    public void activate(JButton ... buttons) {
        for (JButton b : buttons)
            b.setEnabled(true);
    }
    
    public void deactivate(JButton ... buttons) {
        for (JButton b : buttons)
            b.setEnabled(false);
    }
    
    public void init(LocalService<VoteController> vc, LocalService<CommandeProfesseurController> cpc, LocalService<RapportController> rc) {
        voteService = vc;
        commandeProfesseurService = cpc;
        rapportService = rc;
        state = State.INIT;
        activate(soumettreButton);
        deactivate(terminerbutton);

        voteService.getManager().getImplementation()
                .getPropertyChangeSupport().addPropertyChangeListener(
                new PropertyChangeListener() {
                    public void propertyChange(PropertyChangeEvent evt) {
                        String stEvt = evt.getPropertyName().toString();

                        switch(state) {
                            case INIT:
                                if (stEvt == "inscription") {
                                    System.out.println("Incription d'un élève!!!");
                                }
                                break;
                            case SOUMISE:
                                if (stEvt == "commande") {
                                    System.out.println("Commande d'un élève reçue!!!");
                                    stockReponses.addReponse(((Integer)evt.getNewValue())-1);
                                }
                                break;
                        }
                    }
                }
        );
    }
    /**
     * Creates new form Fenetre
     */
    public Fenetre(LocalService<VoteController> vc, LocalService<CommandeProfesseurController> cpc, LocalService<RapportController> rc) {
        initComponents();
        init(vc,cpc, rc);
    }

  
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        soumettreButton = new javax.swing.JButton();
        terminerbutton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextPane1.setText("Question test:\n\nCombien font  1+1???\n\n1) 4000\n2) 2\n3) 11");
        jScrollPane1.setViewportView(jTextPane1);

        soumettreButton.setText("Soumettre");
        soumettreButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                soumettreButtonActionPerformed(evt);
            }
        });

        terminerbutton.setText("Terminer");
        terminerbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    terminerbuttonActionPerformed(evt);
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (TransformerException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(soumettreButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(terminerbutton))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(soumettreButton)
                    .addComponent(terminerbutton))
                .addContainerGap(110, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void soumettreButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_soumettreButtonActionPerformed
        switch(state) {
            case INIT:
                activate(terminerbutton);
                deactivate(soumettreButton);

                String nb = JOptionPane.showInputDialog("Entrer le nombre de reponses possible:");

                stockReponses = new StockReponses(Integer.valueOf(nb));
                voteService.getManager().getImplementation().notifier(jTextPane1.getText());
                voteService.getManager().getImplementation().setState();
                state = State.SOUMISE;
                break;
            case SOUMISE:
                //Interdit
                break;
        }
    }//GEN-LAST:event_soumettreButtonActionPerformed

    private void terminerbuttonActionPerformed(java.awt.event.ActionEvent evt)
            throws ParserConfigurationException,
            IOException,
            TransformerException {
        //GEN-FIRST:event_terminerbuttonActionPerformed

        switch(state) {
            case INIT:
                //Interdit
                break;
            case SOUMISE:
                activate(soumettreButton);
                deactivate(terminerbutton);
                state = State.INIT;
                Document res = new GenerateurXML().getDocXml(stockReponses.getReponses(),
                        stockReponses.getNbQuestions());


                DOMSource source = new DOMSource(res);
                StringWriter writer = new StringWriter();
                StreamResult result = new StreamResult(writer);

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.transform(source, result);


                rapportService.getManager().getImplementation().transmettreRapport(writer.toString());

                break;
        }
    }//GEN-LAST:event_terminerbuttonActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JButton soumettreButton;
    private javax.swing.JButton terminerbutton;
    // End of variables declaration//GEN-END:variables
}
