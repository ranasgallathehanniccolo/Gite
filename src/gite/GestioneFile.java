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
}
