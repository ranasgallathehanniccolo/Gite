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
public class GestioneFileStudenti {
    private static final String FILE_STUDENTI = "studenti.dat";
    // 3 campi da 20 char, ogni char = 2 byte → 3 * 20 * 2 = 120 byte
    public static final int DIM_RECORD = 120;

    public GestioneFileStudenti() {
        creaSeNonEsiste(FILE_STUDENTI);
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
     
     public int aggiungiStudente(Studente s) {
        int id = -1;
        try (RandomAccessFile file = new RandomAccessFile(FILE_STUDENTI, "rw")) {
            id = (int) (file.length() / DIM_RECORD);
            file.seek(id * DIM_RECORD);
            file.writeChars(aggiustaLunghezza(s.getNome().trim()));
            file.writeChars(aggiustaLunghezza(s.getCognome().trim()));
            file.writeChars(aggiustaLunghezza(s.getClasse().trim()));
        } catch (IOException e) {
            System.out.println("Errore scrittura studente: " + e.getMessage());
        }
        return id;
    }
     
      public Studente[] leggiTuttiStudenti() {
        try (RandomAccessFile file = new RandomAccessFile(FILE_STUDENTI, "r")) {
            int nRecord = (int) (file.length() / DIM_RECORD);
            Studente[] studenti = new Studente[nRecord];
            for (int i = 0; i < nRecord; i++) {
                file.seek(i * DIM_RECORD);
                String nome = leggiStringa(file, 20).replace("*", "").trim();
                String cognome = leggiStringa(file, 20).replace("*", "").trim();
                String classe = leggiStringa(file, 20).replace("*", "").trim();
                studenti[i] = new Studente(nome, cognome, classe, i);
            }
            return studenti;
        } catch (IOException e) {
            System.out.println("Errore lettura studenti: " + e.getMessage());
            return new Studente[0];
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
