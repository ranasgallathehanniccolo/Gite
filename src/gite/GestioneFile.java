/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gite;
import java.io.RandomAccessFile;
import java.io.IOException;
import java.io.*;
import java.util.*;
/**
 *
 * @author ranasgalla.niccolo
 */
public class GestioneFile {

    private static final String FILE_STUDENTI = "studenti.dat";
    private static final String FILE_GITE = "gite.dat";
    private static final String FILE_ISCRIZIONI = "iscrizioni.dat";
    
    public GestioneFile() {
        creaSeNonEsiste(FILE_STUDENTI);
        creaSeNonEsiste(FILE_GITE);
        creaSeNonEsiste(FILE_ISCRIZIONI);
    }
    private static final int DIM_RECORD = ;
    private void creaSeNonEsiste(String nomeFile) {
        File f = new File(nomeFile);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                System.out.println("Impossibile creare " + nomeFile);
            }
        }
    }
    private String aggiustaLunghezzaStringa(String s) {
        String aggiustata = s;
        if (s.length() < 20) {
            for (int i = 0; i < (20 - s.length()); i++) {
                aggiustata += "*";
            }
            return aggiustata;
        } else if (s.length() > 20) {
            aggiustata = s.substring(0, 19);
            return aggiustata;
        }
        return s;
    }
    public void AggiungiRecord(){
        try {
            RandomAccessFile file = new RandomAccessFile("elenco.dat", "rw");
            //calcolo la dimensione del file per capire quanti record ci sono. 
            //Questo serve perché ogni nuovo record vine aggiunto in fondo
            int nRecord = (int) (file.length() / DIM_RECORD);
            //con il metodo seek ci si sposta all'interno del file alla posizione desiderata
            file.seek(nRecord * DIM_RECORD);
            //leggo il nome dalla text field e ne aggiusto la lunghezza perché deve essere per forza 20
            String nome = aggiustaLunghezzaStringa(txtNome.getText().trim());
            //leggo il cognome dalla text field e ne aggiusto la lunghezza perché deve essere per forza 20
            String cognome = aggiustaLunghezzaStringa(txtCognome.getText());
            int eta = Integer.parseInt(txtEta.getText());
            //scrittura su file
            System.out.println(" " + nome);
            file.writeChars(nome);
            file.writeChars(cognome);
            file.writeInt(eta);

            file.close();

        } catch (FileNotFoundException ex) {
            System.out.println("File non trovato");
        } catch (IOException e) {
            System.out.println("Problema in lettura-scrittura file");
        }
    }
}
