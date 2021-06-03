package Entity;

import Engine.*;
import GUI.*;

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

        // LA CARTA PIU' BASSA DEL MAZZO;
        Carta minAssoluta = Engine.getMin(nonBriscole());

        if(sulTavolo.length == 0)
            return minAssoluta;

        Carta cartaSulTavolo = (Carta) sulTavolo[0];

        // ARRAY CONTENENTE LE CARTE DEL MAZZO CHE SUPERANO QUELLA AVVERSARIA;
        Carta[] superano = getSuperano((Carta) sulTavolo[0]);

        // LA CARTA PIU' BASSA DEL MAZZO (TRA QUELLE CHE SUPERANO LA CARTA AVVERSARIA), ESCLUDENDO LE BRISCOLE SE PRESENTI;
        Carta minSupera = Engine.getMin(nonBriscole(superano));

        // SE NEL MAZZO NON SI HA NESSUNA CARTA CHE SUPERA QUELLA AVVERSARIA, GIOCA LA PIU' BASSA DISPONIBILE;
        if(superano.length == 0)
            return minAssoluta;

        // L'AVVERSARIO HA GIOCATO UN "LISCIO";
        if(cartaSulTavolo.getValore() == 0){
            /*
                SE SI POSSEDE UNA CARTA BRISCOLA CON VALORE 0 CHE SUPERA LA CARTA AVVERSARIA
                LA SI GIOCA SOLO NEL CASO IN CUI NON SI ABBIA UN LISCIO DA POTER LANCIARE,
                ALTRIMENTI SI GIOCA IL LISCIO;

                EX:
                BRISCOLA BASTONI;
                L'UTENTE GIOCA IL 6 DI DENARA;
                L'AVVERSARIO HA IL 6 DI BRISCOLA E IL 5 DI DENARA: GIOCHERA' IL 5 DI DENARA
                LASCIANDO LA MANO ALL'AVVERSARIO PIUTTOSTO DI SPRECARE UNA BRISCOLA;
                NEL CASO IN CUI L'AVVERSARIO NON AVESSE AVUTO UN LISCIO DA POTER LANCIARE,
                AVREBBE UTILIZZATO LA BRISCOLA AGGIUDICANDOSI LA MANO PIUTTOSTO DI REGALARE PUNTI ALL'UTENTE;
            */
            if(minSupera.isBriscola() && minSupera.getValore() == 0) {
                if(minAssoluta.getValore() == 0) {
                    return minAssoluta;
                }else{
                    return minSupera;
                }
            }else if(!minSupera.isBriscola()) {
                return minSupera;
            }else{
                return minAssoluta;
            }
        }else{
            // GIOCA LA PIU' BASSA CARTA NEL MAZZO CHE BATTE QUELLA AVVERSARIA, SE ESISTE;
            return minSupera;
        }
    }

    // SE NON SI PASSA NESSUN PARAMETRO AL METODO, ALLORA SI CONSIDERA IL MAZZO DEL GIOCATORE;
    public Carta[] nonBriscole(){
        return nonBriscole(carte.toArray(new Carta[0]));
    }

    // METODO CHE RESTITUISCE LE CARTE NON BRISCOLE PRESENTI NELL'ARRAY PASSATO, SE NON SONO PRESENTI RESTITUISCE L'ARRAY STESSO;
    public Carta[] nonBriscole(Carta[] carte){
        ArrayList<Carta> nonBriscole = new ArrayList<>();

        for(Carta carta : carte){
            if(!carta.isBriscola())
                nonBriscole.add(carta);
        }

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

        for(Carta carta : mazzo){
            if(carta.getSeme() == daSuperare.getSeme()){
                if(carta.getValore() > daSuperare.getValore()){
                    maggiori.add(carta);
                    continue;
                }else if(carta.getValore() == daSuperare.getValore()){
                    if(carta.getNumero() > daSuperare.getNumero()){
                        maggiori.add(carta);
                        continue;
                    }
                }
            }else if(carta.isBriscola()){
                maggiori.add(carta);
            }
        }

        if(maggiori.size() == 0)
            return carte.toArray(new Carta[0]);
        else
            return maggiori.toArray(new Carta[0]);
    }
}

