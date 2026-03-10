/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gite;
import java.io.RandomAccessFile;
import java.io.*;
/**
 *
 * @author ranasgalla.niccolo
 */
public class GestioneFileGite {
    private static final String FILE_GITA        = "gita.dat";
    private static final String FILE_ISCRIZIONI  = "iscrizioni.dat";

    // gita.dat: 20 char * 2 byte = 40 byte per record
    public static final int DIM_RECORD_GITA = 40;
    // iscrizioni.dat: 2 int * 4 byte = 8 byte per record
    public static final int DIM_RECORD_ISCRIZIONE = 8;

    public GestioneFileGite() {
        creaSeNonEsiste(FILE_GITA);
        creaSeNonEsiste(FILE_ISCRIZIONI);
        inizializzaGitePredefinite();
    }
    
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
    
    private String aggiustaLunghezza(String s) {
        if (s.length() > 20) return s.substring(0, 20);
        StringBuilder sb = new StringBuilder(s);
        while (sb.length() < 20) sb.append('*');
        return sb.toString();
    }
    
    private void inizializzaGitePredefinite() {
        try (RandomAccessFile file = new RandomAccessFile(FILE_GITA, "rw")) {
            if (file.length() == 0) {
                String[] gitePredefinite = {
                    "Roma",
                    "Firenze",
                    "Venezia",
                    "Milano",
                    "Napoli"
                };
                for (String nomeGita : gitePredefinite) {
                    file.writeChars(aggiustaLunghezza(nomeGita));
                }
                System.out.println("Gite predefinite inizializzate.");
            }
        } catch (IOException e) {
            System.out.println("Errore inizializzazione gite: " + e.getMessage());
        }
    }
    
    public Gita[] leggiTutteGite() {
        try (RandomAccessFile file = new RandomAccessFile(FILE_GITA, "r")) {
            int nRecord = (int) (file.length() / DIM_RECORD_GITA);
            Gita[] gite = new Gita[nRecord];
            for (int i = 0; i < nRecord; i++) {
                file.seek(i * DIM_RECORD_GITA);
                String nome = leggiStringa(file, 20).replace("*", "").trim();
                gite[i] = new Gita(nome, i);
            }
            return gite;
        } catch (IOException e) {
            System.out.println("Errore lettura gite: " + e.getMessage());
            return new Gita[0];
        }
    }
    
     public void aggiungiIscrizione(int idStudente, int idGita) {
        try (RandomAccessFile file = new RandomAccessFile(FILE_ISCRIZIONI, "rw")) {
            int nRecord = (int) (file.length() / DIM_RECORD_ISCRIZIONE);
            file.seek(nRecord * DIM_RECORD_ISCRIZIONE);
            file.writeInt(idStudente);
            file.writeInt(idGita);
            System.out.println("Iscrizione salvata: studente " + idStudente + " → gita " + idGita);
        } catch (IOException e) {
            System.out.println("Errore scrittura iscrizione: " + e.getMessage());
        }
    }
     
     public int[][] leggiTutteIscrizioni() {
        try (RandomAccessFile file = new RandomAccessFile(FILE_ISCRIZIONI, "r")) {
            int nRecord = (int) (file.length() / DIM_RECORD_ISCRIZIONE);
            int[][] iscrizioni = new int[nRecord][2];
            for (int i = 0; i < nRecord; i++) {
                file.seek(i * DIM_RECORD_ISCRIZIONE);
                iscrizioni[i][0] = file.readInt(); // idStudente
                iscrizioni[i][1] = file.readInt(); // idGita
            }
            return iscrizioni;
        } catch (IOException e) {
            System.out.println("Errore lettura iscrizioni: " + e.getMessage());
            return new int[0][0];
        }
    }
    private String leggiStringa(RandomAccessFile file, int nChar) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nChar; i++) {
            sb.append(file.readChar());
        }
        return sb.toString();
    }
}
