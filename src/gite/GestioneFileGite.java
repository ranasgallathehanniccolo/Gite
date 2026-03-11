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

    // nomi dei due file che usiamo
    private static final String FILE_GITA = "gita.dat";
    private static final String FILE_ISCRIZIONI = "iscrizioni.dat";

    // ogni gita occupa 40 byte nel file
    // ha solo un campo nome di 20 caratteri e ogni carattere pesa 2 byte quindi 20 x 2 = 40
    public static final int DIM_RECORD_GITA = 40;

    // ogni iscrizione occupa 8 byte nel file
    // ha solo 2 numeri interi (id studente e id gita) e ogni int pesa 4 byte quindi 2 x 4 = 8
    public static final int DIM_RECORD_ISCRIZIONE = 8;

    // quando creiamo l'oggetto ci assicuriamo che i file esistano
    // e carichiamo le gite di default se il file è vuoto
    public GestioneFileGite() {
        creaSeNonEsiste(FILE_GITA);
        creaSeNonEsiste(FILE_ISCRIZIONI);
        inizializzaGitePredefinite();
    }

    // crea il file sul disco se non c'è già
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

    // porta la stringa a esattamente 20 caratteri
    // se è troppo lunga la taglia, se è troppo corta aggiunge asterischi in fondo
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

    // scrive le 5 gite di default nel file solo se il file è ancora vuoto
    // se il file ha già dei dati dentro non fa nulla
    private void inizializzaGitePredefinite() {
        try (RandomAccessFile file = new RandomAccessFile(FILE_GITA, "rw")) {
            if (file.length() == 0) {
                String[] gitePredefinite = {
                    "Roma",
                    "Firenze",
                    "Venezia",
                    "Milano",
                    "Fiesole"
                };
                // scrive ogni gita nel file adattandola a 20 caratteri
                for (String nomeGita : gitePredefinite) {
                    file.writeChars(aggiustaLunghezza(nomeGita));
                }
                System.out.println("Gite predefinite inizializzate.");
            }
        } catch (IOException e) {
            System.out.println("Errore inizializzazione gite: " + e.getMessage());
        }
    }

    // legge tutte le gite dal file e le restituisce come array
    public Gita[] leggiTutteGite() {
        try (RandomAccessFile file = new RandomAccessFile(FILE_GITA, "r")) {

            // calcola quante gite ci sono nel file
            int nRecord = (int) (file.length() / DIM_RECORD_GITA);
            Gita[] gite = new Gita[nRecord];

            for (int i = 0; i < nRecord; i++) {
                file.seek(i * DIM_RECORD_GITA); // salta all'inizio dell'i-esima gita
                String nome = leggiStringa(file, 20).replace("*", "").trim(); // legge il nome e rimuove gli asterischi
                gite[i] = new Gita(nome, i); // i è l'id della gita
            }
            return gite;

        } catch (IOException e) {
            System.out.println("Errore lettura gite: " + e.getMessage());
            return new Gita[0]; // se qualcosa va storto restituisce un array vuoto
        }
    }

    // salva una nuova iscrizione in fondo al file
    // riceve l'id dello studente e l'id della gita a cui si iscrive
    public void aggiungiIscrizione(int idStudente, int idGita) {
        try (RandomAccessFile file = new RandomAccessFile(FILE_ISCRIZIONI, "rw")) {
            int nRecord = (int) (file.length() / DIM_RECORD_ISCRIZIONE); // quante iscrizioni ci sono già
            file.seek(nRecord * DIM_RECORD_ISCRIZIONE); // va in fondo al file
            file.writeInt(idStudente); // scrive l'id dello studente
            file.writeInt(idGita);    // scrive l'id della gita
            System.out.println("Iscrizione salvata: studente " + idStudente + " → gita " + idGita);
        } catch (IOException e) {
            System.out.println("Errore scrittura iscrizione: " + e.getMessage());
        }
    }

    // legge tutte le iscrizioni dal file e le restituisce come matrice
    // ogni riga ha due valori, il primo è l'id studente e il secondo è l'id gita
    public int[][] leggiTutteIscrizioni() {
        try (RandomAccessFile file = new RandomAccessFile(FILE_ISCRIZIONI, "r")) {

            int nRecord = (int) (file.length() / DIM_RECORD_ISCRIZIONE); // quante iscrizioni ci sono
            int[][] iscrizioni = new int[nRecord][2];

            for (int i = 0; i < nRecord; i++) {
                file.seek(i * DIM_RECORD_ISCRIZIONE); // salta all'i-esima iscrizione
                iscrizioni[i][0] = file.readInt(); // legge l'id dello studente
                iscrizioni[i][1] = file.readInt(); // legge l'id della gita
            }
            return iscrizioni;

        } catch (IOException e) {
            System.out.println("Errore lettura iscrizioni: " + e.getMessage());
            return new int[0][0]; // se qualcosa va storto restituisce una matrice vuota
        }
    }

    // metodo di supporto che legge nChar caratteri dal file e li restituisce come stringa
    private String leggiStringa(RandomAccessFile file, int nChar) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nChar; i++) {
            sb.append(file.readChar()); // ogni readChar legge 2 byte e li trasforma in un carattere
        }
        return sb.toString();
    }
}
