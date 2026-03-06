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
    private static final String FILE_GITA = "gita.dat";
    private static final String FILE_ISCRIZIONI = "iscrizioni.dat";
    
    public GestioneFile() {
        creaSeNonEsiste(FILE_STUDENTI);
        creaSeNonEsiste(FILE_GITA);
        creaSeNonEsiste(FILE_ISCRIZIONI);
    }
    private static final int DIM_RECORD = 70; // nome 30 cognome 30 classe 10
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
    public void AggiungiRecordStudente(Studente s){
        try {
            RandomAccessFile file = new RandomAccessFile("studenti.dat", "rw");
            int nRecord = (int) (file.length() / DIM_RECORD);   
            file.seek(nRecord * DIM_RECORD);
            String nome = aggiustaLunghezzaStringa(s.getNome().trim());
            String cognome = aggiustaLunghezzaStringa(s.getCognome().trim());
            String classe = aggiustaLunghezzaStringa(s.getClasse().trim());
            System.out.println(" " + nome);
            file.writeChars(nome);
            file.writeChars(cognome);
            file.writeChars(classe);
            file.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File non trovato");
        } catch (IOException e) {
            System.out.println("Problema in lettura-scrittura file");
        }
    }
    public void AggiungiRecordGita(Gita g){
        try {
            RandomAccessFile file = new RandomAccessFile("gita.dat", "rw");
            int nRecord = (int) (file.length() / DIM_RECORD);   
            file.seek(nRecord * DIM_RECORD);
            String nomeGita = aggiustaLunghezzaStringa(g.getNome().trim());
            System.out.println(" " + nomeGita);
            file.writeChars(nomeGita);
            file.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File non trovato");
        } catch (IOException e) {
            System.out.println("Problema in lettura-scrittura file");
        }
    }
}
