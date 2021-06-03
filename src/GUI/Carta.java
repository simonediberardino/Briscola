package GUI;

import Main.*;
import Engine.*;
import Entity.*;

import javax.swing.*;
import java.awt.*;

public class Carta extends JButton {
    protected Integer valore;
    protected Integer numero;
    protected String seme;
    protected boolean enabled = true;
    protected Giocatore portatore;

    public Carta(boolean disabled) {
        super();
        this.enabled = false;
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setContentAreaFilled(false);
        this.nascondi();
    }

    public Carta(Integer numero, String seme) {
        super();
        this.numero = numero;
        this.seme = seme;
        this.valore = calcolaValore(numero);

        this.setIcon(getImage());
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setContentAreaFilled(false);
        this.setBorder(BorderFactory.createLineBorder(Color.cyan));
        this.addActionListener(new Engine());
    }

    public void abilita(){
        enabled = true;
    }

    public void disabilita(){
        enabled = false;
    }

    public boolean isEnabled(){
        return enabled;
    }

    public Integer getNumero() {
        return numero;
    }

    public Integer getValore() {
        return valore;
    }

    public String getSeme() {
        return seme;
    }

    public Giocatore getPortatore(){
        return portatore;
    }

    public void setPortatore(Giocatore portatore){
        this.portatore = portatore;
    }

    private Integer calcolaValore(Integer numero){
        switch(numero){
            case 1: return 11;
            case 3: return 10;
            case 10: return 4;
            case 9: return 3;
            case 8: return 2;
            default: return 0;
        }
    }

    public boolean isBriscola(){
        return this.getSeme() == Game.briscola.getSeme();
    }

    public void mostra() {
        this.setIcon(getImage());
    }

    public void nascondi() {
        if(Game.carteScoperte && this.getPortatore() != null)
            return;

        String imagePath = "/resources/vuoto.png";
        this.setIcon(new ImageIcon(getClass().getResource(imagePath)));
    }

    public ImageIcon getImage() {
        String imagePath = "/resources/"+this.numero+"_"+this.seme+".png";
        return new ImageIcon(getClass().getResource(imagePath));
    }
}
