package Entity;

import Engine.*;
import GUI.*;
import Main.Game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CPU extends Giocatore {
    public CPU(String nome, JLabel label){
        super(nome, label, true);
        this.pTavolo = pGiocoCPU;
        this.pMazzo = pGiocoCPUL;
    }

    public Carta pesca() {
        Carta carta = super.pesca();

        if(carta == null)
            return null;

        carta.nascondi();
        return carta;
    }

    @Override
    public void toccaA(){
        scegli().doClick();
    }

    // METODO CHE RESTITUISCE LA MIGLIOR CARTA DA GIOCARE;
    public Carta scegli(){
        Component[] sulTavolo = pGiocoC.getComponents();

        Carta[] mieCarte = this.carte.toArray(new Carta[0]);

        // LA CARTA CON PUNTEGGIO PIU' BASSO;
        Carta minPunti = Engine.getMin(mieCarte);
        
        // LA CARTA CON PUNTEGGIO PIU' BASSO, ESCLUDENDO LE BRISCOLE;
        Carta minBriscEsc = Engine.getMin(nonBriscole());

        // LA PRIMA MOSSA SPETTA AL PC;
        if(sulTavolo.length == 0){
            /**
            *  Nota: minBriscEsc esclude le briscole mentre ciò non accade invece per minPunti, per cui minBriscEsc viene
            *  giocata solo nel caso in cui valga zero oppure nel caso in cui abbia un valore maggiore di 0 ma nel mazzo
            *  non ci sia nessun'altra carta (briscole comprese) che valga zero.
            *  Questo serve ad evitare che il giocatore alla prima mano giochi un carico nonostante abbia nel mazzo una
            *  briscola con valore 0;
            **/
            if(minBriscEsc.getValore() > 0){
                if(minPunti.getValore() == 0)
                    return minPunti;
                else
                    return minBriscEsc;
            }else{
                return minBriscEsc;
            }
        }

        Carta cartaSulTavolo = (Carta) sulTavolo[0];

        // ARRAY CONTENENTE LE CARTE DEL MAZZO CHE SUPERANO QUELLA AVVERSARIA;
        Carta[] superano = getSuperano((Carta) sulTavolo[0]);
        
        // LA CARTA PIU' BASSA DEL MAZZO (TRA QUELLE CHE SUPERANO LA CARTA AVVERSARIA), ESCLUDENDO LE BRISCOLE SE PRESENTI;
        Carta minSupera = Engine.getMin(nonBriscole(superano));

        // LA CARTA PIU' ALTA DEL MAZZO (TRA QUELLE CHE SUPERANO LA CARTA AVVERSARIA), ESCLUDENDO LE BRISCOLE SE PRESENTI;
        Carta maxSupera = Engine.getMax(nonBriscole(superano));

        // LA CARTA PIU' ALTA DEL MAZZO, SENZA ESCLUDERE LE BRISCOLE;
        Carta maxSuperaBrisc = Engine.getMax(superano);

        /** Se nessuna delle carte supera quella avversaria */
        if(superano.length == 0){
            /**
            * Se, escludendo le briscole, la carta più bassa del proprio mazzo ha valore maggiore di zero, la lancia
            * solo nel caso in cui non abbia una briscola con valore 0 da poter giocare;
            **/
            if(minBriscEsc.getValore() > 0){
                if(minPunti.getValore() == 0){
                    return minPunti;
                }else{
                    return minBriscEsc;
                }
            }else{
                return minBriscEsc;
            }
        }

        /**
        * Se la somma della carta massima e la carta avveraria più il punteggio attuale
        * garantisce la vittoria al giocatore, gioca la stessa (solo nel caso in cui la carta stessa
        * supera quella avversaria);
        */
        if(maxSuperaBrisc.supera(cartaSulTavolo)){
            Integer sommaCarte = maxSuperaBrisc.getValore() + cartaSulTavolo.getValore();

            if(sommaCarte + this.punteggio > Game.maxPunti / 2)
                return maxSuperaBrisc;
        }

        // L'AVVERSARIO HA GIOCATO UN "LISCIO";
        if(cartaSulTavolo.getValore() == 0){
            /**
                Se si possede una carta briscola con valore 0 che supera la carta avversaria
                la si gioca solo nel caso in cui non si abbia un liscio da poter lanciare,
                altrimenti si gioca il liscio;
            */
            if(minSupera.isBriscola() && minSupera.getValore() == 0){
                if(minBriscEsc.getValore() == 0) {
                    return minBriscEsc;
                }else{
                    return minSupera;
                }
            }else if(!maxSupera.isBriscola()){
                return maxSupera;
            }else{
                return minBriscEsc;
            }
        }else{
            /**
             * Nota: maxSupera è il risultato di un metodo a cui viene applicato il metodo "nonBriscole", ciò significa
             * che se maxSupera è una briscola vuol dire che il mazzo è formato da sole briscole. In altre parole, nonBriscole
             * restituisce il mazzo originale se il mazzo è formato da sole briscole, per cui se maxSupera è briscola allora
             * il mazzo è formato da sole briscole e perciò l'avversario giocherà la più bassa tra queste; Se invece maxSupera non
             * è una briscola, allora l'avversario giocherà quest'ultima in modo da ottenere più punti possibili con una sola presa.
            **/
            if(maxSupera.isBriscola())
                return minSupera;
            else
                return maxSupera;
        }
    }

    // SE NON SI PASSA NESSUN PARAMETRO AL METODO, ALLORA SI CONSIDERA IL MAZZO DEL GIOCATORE;
    public Carta[] nonBriscole(){
        return nonBriscole(carte.toArray(new Carta[0]));
    }

    // METODO CHE RESTITUISCE LE CARTE NON BRISCOLE PRESENTI NELL'ARRAY PASSATO, SE NON SONO PRESENTI RESTITUISCE L'ARRAY STESSO;
    public Carta[] nonBriscole(Carta[] carte){
        ArrayList<Carta> nonBriscole = new ArrayList<>();

        for(Carta carta : carte)
            if(!carta.isBriscola())
                nonBriscole.add(carta);

        if(nonBriscole.size() == 0)
            return carte;
        else
            return nonBriscole.toArray(new Carta[0]);
    }

    public Carta[] getSuperano(Carta daSuperare){
        return getSuperano(daSuperare, carte.toArray(new Carta[0]));
    }

    // METODO CHE RESTITUISCE UN ARRAY DI "CARTA" CONTENENTE LE CARTE CHE SONO PIU' ALTE DELLA CARTA PASSATA;
    public Carta[] getSuperano(Carta daSuperare, Carta[] mazzo){
        ArrayList<Carta> maggiori = new ArrayList<>();

        for(Carta carta : mazzo)
            if(carta.supera(daSuperare))
                maggiori.add(carta);

        return maggiori.toArray(new Carta[0]);
    }
}

