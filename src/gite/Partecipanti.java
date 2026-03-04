package gite;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.*;
/**
 *
 * @author ranasgalla.niccolo
 */
public class Partecipanti {
    private ArrayList<Studente> iscritti;

    public Partecipanti(ArrayList<Studente> iscrtitti) {
        this.iscritti = new ArrayList<>();
    }

    public void AggiungiStudnete(Studente s){
        iscritti.add(s);
    }
    
}
