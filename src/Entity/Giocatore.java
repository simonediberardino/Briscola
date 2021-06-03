package Entity;

import Main.Game;
import GUI.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Giocatore extends Game {
    // ArrayList contenente le carte che il giocatore ha in mano;
    public ArrayList<Carta> carte;

    // ArrayList contenente le carte prese;
    protected ArrayList<Carta> prese;

    // Nome del giocatore;
    protected String nome;

    // Round vinti del giocatore;
    protected Integer score = 0;

    // Se il giocatore è controllato dalla CPU o no;
    protected boolean CPU;

    // La carta rappresentante il mazzo delle carte ottenute;
    protected Carta mazzo;

    // Pannello contenente le carte che il giocatore ha in mano;
    public JPanel pTavolo;

    // Pannello contenente le carte prese;
    public JPanel pMazzo;

    // Label della scoreboard appartenente al giocatore;
    protected JLabel label;

    public Giocatore(String nome, JLabel label){
        this(nome, label, false);
        this.pTavolo = pGiocoUser;
        this.pMazzo = pGiocoUserR;
    }

    protected Giocatore(String nome, JLabel label, boolean CPU){
        this.nome = nome;
        this.CPU = CPU;
        this.label = label;
        this.carte = new ArrayList<>();
        this.prese = new ArrayList<>();
        this.aggiornaLabel();
    }

    public String getNome() {
        return nome;
    }

    public Integer getScore() {
        return score;
    }

    public void mostraMazzo(){
        final Integer daMostrare = 5;

        ArrayList<Carta> carte = prese;
        Collections.sort(carte, ordinaCarte);
        Collections.reverse(carte);

        if(carte.size() <= daMostrare)
            this.pMazzo.remove(this.mazzo);

        for(int i = 0; i < daMostrare && i < carte.size(); i++)
            this.pTavolo.add(carte.get(i));
    }

    public static Comparator<Carta> ordinaCarte = Comparator.comparingInt(c -> c.getValore());

    public void svuotaMazzo(){
        this.carte.clear();
        this.prese.clear();
        this.mazzo = null;
    }

    public void azzeraPunteggio(){
        this.score = 0;
        this.aggiornaLabel();
    }

    public void aggiornaPunteggio(){
        this.score++;
        this.aggiornaLabel();
    }

    public void aggiornaLabel(){
        this.label.setText(this.nome + ": " + this.score);
    }

    public Carta pesca(){
        if(Game.mazzo.size() == 0){
            if(!lastManche){
                anteprimaCarte.setVisible(false);
                lastManche = true;
                pGiocoR.remove(briscola);
                return pesca(briscola);
            }
        }else{
            return pesca(Game.mazzo.get(0));
        }

        return null;
    }

    public Carta pesca(Carta carta) {
        carta.setPortatore(this);
        carta.abilita();

        this.carte.add(carta);
        this.pTavolo.add(carta);
        Game.mazzo.remove(carta);
        GUI.aggiornaFrame();

        return carta;
    }

    public void mancheVinta() {
        if(this.mazzo == null){
            this.mazzo = new CartaVuota();
            this.pMazzo.add(this.mazzo);
        }

        for(Component c : pGiocoC.getComponents()) {
            prese.add((Carta) c);
            ((Carta) c).setBorderPainted(false);
        }

        ultimoVincitore = this;
        GUI.aggiornaFrame();
    }

    public void lancia(Carta carta) throws IOException {
        carta.setBorderPainted(false);
        carta.disabilita();
        carta.mostra();
        this.carte.remove(carta);
        this.pTavolo.remove(carta);
        GUI.pGiocoC.add(carta);
        GUI.aggiornaFrame();
    }

    public Integer conta(){
        Integer punteggio = 0;

        for(Carta carta : prese)
            punteggio += carta.getValore();

        return punteggio;
    }

    public void toccaA(){}
}

