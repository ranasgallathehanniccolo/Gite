/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gite;
import java.util.*;
/**
 *
 * @author ranasgalla.niccolo
 */
public class GiteDisponibili {
    private ArrayList<Gita> listaGita; 

    public GiteDisponibili(ArrayList<Gita> listaGita) {
        this.listaGita = new ArrayList<>();
    }
    public void AggiungiGita(Gita g){
        listaGita.add(g);
    }
}
