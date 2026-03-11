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

    // Nome del file dove salviamo i dati
    private static final String FILE_STUDENTI = "studenti.dat";

    // Ogni studente occupa 120 byte nel file:
    // ha 3 campi (nome, cognome, classe), ognuno è 20 caratteri,
    // e ogni carattere Java pesa 2 byte → 3 × 20 × 2 = 120
    public static final int DIM_RECORD = 120;

    // Costruttore, quando creiamo l'oggetto, assicuriamo che il file esista
    public GestioneFileStudenti() {
        creaSeNonEsiste(FILE_STUDENTI);
    }

    // Crea il file se non esiste già sul disco
    private void creaSeNonEsiste(String nomeFile) {
        File f = new File(nomeFile);
        if (!f.exists()) {          // se il file non c'è...
            try {
                f.createNewFile(); // ...lo crea vuoto
            } catch (IOException e) {
                System.out.println("Impossibile creare " + nomeFile);
            }
        }
    }

    // Adatta una stringa in modo che sia 20 caratteri:
    // se è troppo lunga, la taglia a 20
    // se è troppo corta, aggiunge asterischi '*' alla fine come riempimento
    private String aggiustaLunghezza(String s) {
        if (s.length() > 20) {
            return s.substring(0, 20);
        }
        StringBuilder sb = new StringBuilder(s);
        while (sb.length() < 20) {
            sb.append('*');
        }
        return sb.toString();
    }

    // Aggiunge uno studente in fondo al file e restituisce il suo ID
    public int aggiungiStudente(Studente s) {
        int id = -1;
        try (RandomAccessFile file = new RandomAccessFile(FILE_STUDENTI, "rw")) {

            // L'ID è la posizione del nuovo record
            // dividiamo la dimensione totale del file per 120 quanti studenti ci sono già
            id = (int) (file.length() / DIM_RECORD);

            // Spostiamo il "cursore" alla fine del file, dove scriveremo il nuovo studente
            file.seek(id * DIM_RECORD);

            // Scriviamo i 3 campi, ognuno adattato a 20 caratteri
            file.writeChars(aggiustaLunghezza(s.getNome().trim()));
            file.writeChars(aggiustaLunghezza(s.getCognome().trim()));
            file.writeChars(aggiustaLunghezza(s.getClasse().trim()));

        } catch (IOException e) {
            System.out.println("Errore scrittura studente: " + e.getMessage());
        }
        return id; // restituisce l'ID assegnato allo studente
    }

    // Legge tutti gli studenti dal file e li restituisce come array
    public Studente[] leggiTuttiStudenti() {
        try (RandomAccessFile file = new RandomAccessFile(FILE_STUDENTI, "r")) {

            // Calcoliamo quanti studenti ci sono nel file
            int nRecord = (int) (file.length() / DIM_RECORD);
            Studente[] studenti = new Studente[nRecord];

            // Leggiamo uno per uno ogni studente
            for (int i = 0; i < nRecord; i++) {
                file.seek(i * DIM_RECORD); // spostiamo il cursore all'inizio dell'i-esimo record

                // Leggiamo i 3 campi e rimuoviamo gli asterischi di riempimento
                String nome = leggiStringa(file, 20).replace("*", "").trim();
                String cognome = leggiStringa(file, 20).replace("*", "").trim();
                String classe = leggiStringa(file, 20).replace("*", "").trim();

                studenti[i] = new Studente(nome, cognome, classe, i); // i = ID dello studente
            }
            return studenti;

        } catch (IOException e) {
            System.out.println("Errore lettura studenti: " + e.getMessage());
            return new Studente[0]; // in caso di errore, restituisce un array vuoto
        }
    }

    // Metodo di supporto legge esattamente nChar caratteri dal file
    // e li restituisce come stringa
    private String leggiStringa(RandomAccessFile file, int nChar) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nChar; i++) {
            sb.append(file.readChar()); // legge un carattere alla volta (2 byte)
        }
        return sb.toString();
    }
}