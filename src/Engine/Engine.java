package Engine;

import Main.Game;
import Entity.*;
import GUI.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Collections;

public class Engine extends Game implements ActionListener {
    public static void inizializza() throws InterruptedException {
        creaGiocatori();
        iniziaPartita();
    }

    public static void iniziaPartita() {
        reset();
        iniziaRound();
    }

    static void iniziaRound() {
        Giocatore toccaA = trovaVincitore();

        if(toccaA == null)
            toccaA = getRandomPlayer();

        inizia();
        pulisciTavolo();
        creaMazzo();
        estraiBriscola();
        distribuisciCarte();
        prossimoTurno(toccaA);
    }

    static void inizia(){
        Game.canPlay = true;
        terminata = false;
    }

    static void creaMazzo() {
        lastManche = false;
        mazzo.clear();

        for(String seme : semi) {
            for (Integer i = 1; i <= 10; i++)
                mazzo.add(new Carta(i, seme));
        }

        Collections.shuffle(mazzo);
    }

    static void reset(){
        for(Giocatore p : giocatori){
            p.svuotaMazzo();
            p.azzeraPunteggio();
        }
    }

    static void creaGiocatori(){
        for(int i = 0; i < giocatori.length; i++){
            boolean CPU = i == 0 ? false : true;
            giocatori[i] = CPU == false ? new Giocatore("Giocatore", scoreboard.score[i]) : new CPU("CPU", scoreboard.score[i]);
        }
    }

    static void estraiBriscola(){
        briscola = mazzo.get(0);
        briscola.disabilita();
        mazzo.remove(briscola);
        pGiocoR.add(briscola);
        pGiocoR.add(anteprimaCarte);
        anteprimaCarte.setVisible(true);
    }

    static void distribuisciCarte() {
        for(Giocatore p : giocatori){
            p.svuotaMazzo();

            while(p.carte.size() < Game.nCarte)
                p.pesca();
        }
    }

    static void prossimoTurno(Giocatore p){
        if(p == null)
            p = getRandomPlayer();

        giocante = p;
        p.toccaA();
    }

    static void terminaManche(Giocatore vincitore) throws IOException, InterruptedException {
        Game.canPlay = false;

        Thread.sleep(1750);

        vincitore.mancheVinta();
        pulisciPianoGioco();

        for(Giocatore p : giocatori)
            if(Game.mazzo.size() > 0 || !lastManche)
                p.pesca();

        Game.canPlay = true;

        if(isTerminata()){
            termina();
        }else{
            prossimoTurno(ultimoVincitore);
        }
    }

    static void termina(){
        Giocatore vincitore = trovaVincitore();

        Game.canPlay = false;
        terminata = true;

        for(Giocatore p : giocatori)
            p.mostraMazzo();

        if(vincitore != null){
            vincitore.aggiornaPunteggio();

            if(vincitore.getScore() == Game.scoreLimit){
                terminaPartita(vincitore);
            }else{
                terminaRound(vincitore);
            }
        }else{
            terminaRound(null);
        }
    }

    static void terminaPartita(Giocatore vincitore){
        Game.endEvent = 1;
        pGiocoC.add(new Messaggio(vincitore.getNome() + " ha vinto! ("+vincitore.conta()+")", "Premi INVIO per un'altra partita!"));
        GUI.aggiornaFrame();
    }

    static void terminaRound(Giocatore vincitore){
        Game.endEvent = 0;
        String titolo = vincitore == null ? "Pareggio!" : vincitore.getNome() + " ha vinto il round (" + vincitore.conta() + ")!";
        pGiocoC.add(new Messaggio(titolo, "Premi INVIO per il prossimo round!"));
        GUI.aggiornaFrame();
    }

    static Giocatore trovaVincitore(){
        Integer score_1 = giocatori[0].conta();
        Integer score_2 = giocatori[1].conta();

        if(score_1 > score_2)
            return giocatori[0];
        else if(score_2 > score_1)
            return giocatori[1];
        else
            return null;
    }

    static void pulisciTavolo(){
        pulisciPianoGioco();
        pulisciTavoloGiocatori();
        pulisciMazzo();
    }

    static void pulisciPianoGioco(){
        for(Component component: pGiocoC.getComponents())
            pGiocoC.remove(component);
    }

    static void pulisciTavoloGiocatori(){
        for(Giocatore p : giocatori){
            p.pTavolo.removeAll();
            p.pMazzo.removeAll();
        }
    }

    static void pulisciMazzo(){
        for(Component component : pGiocoR.getComponents())
            pGiocoR.remove(component);
    }

    static Giocatore getRandomPlayer(){
        return giocatori[(int) (Math.random() * giocatori.length)];
    }

    static Giocatore getOtherPlayer(Giocatore current){
        for(Giocatore p : giocatori)
            if(p != current)
                return p;
        return null;
    }

    static Carta getOtherCarta(Carta current){
        for(Component component: pGiocoC.getComponents()){
            Carta carta = (Carta) component;

            if(carta != current)
                return carta;
        }

        return null;
    }

    static Integer getCarteATerra(){
        return pGiocoUser.getComponentCount() + pGiocoCPU.getComponentCount();
    }

    static boolean isTerminata(){
        return getCarteATerra() == 0;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Carta carta = (Carta) e.getSource();

        if(carta.getPortatore() != giocante)
            return;

        if(!Game.canPlay)
            return;

        try{
            giocante.lancia(carta);
        }catch(IOException ioException) {
            ioException.printStackTrace();
        }

        Giocatore vincente = null;

        vincente = doLogic(carta, getOtherCarta(carta));

        if(vincente == null) {
            prossimoTurno(getOtherPlayer(giocante));
        }else{
            Giocatore finalVincente = vincente;
            Thread thread = new Thread(() -> {
                try{
                    terminaManche(finalVincente);
                }catch (IOException | InterruptedException err) {
                    err.printStackTrace();
                }
            });

            thread.start();
        }
    }

    static Giocatore doLogic(Carta last, Carta first) {
        if(last != null && first == null)
            return null;

        Carta[] carte = {first, last};
        Carta comanda = first;

        for(Carta carta : carte){
            if(carta.isBriscola())
                comanda = carta;
        }

        Carta c_vincente;

        if(first.getSeme() == last.getSeme()){
            c_vincente = getMax(carte);
        }else{
            c_vincente = comanda;
        }

        c_vincente.setBorderPainted(true);
        return c_vincente.getPortatore();
    }

    public static Carta getMax(Carta[] array) {
        Carta max = array[0];

        for(int i = 1; i < array.length; i++){
            if(array[i].getValore() > max.getValore())
                max = array[i];
            else if(array[i].getValore() == max.getValore()){
                if(array[i].getNumero() > max.getNumero()){
                    max = array[i];
                }
            }
        }

        return max;
    }

    public static Carta getMin(Carta[] array) {
        Carta min = array[0];

        for(int i = 1; i < array.length; i++){
            if(array[i].getValore() < min.getValore())
                min = array[i];
            else if(array[i].getValore() == min.getValore()){
                if(array[i].getNumero() < min.getNumero()){
                    min = array[i];
                }
            }
        }

        return min;
    }
}
