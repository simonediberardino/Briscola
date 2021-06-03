package Main;

import Engine.*;
import Entity.*;
import GUI.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.*;

public class Game {
    public static final Integer nGiocatori = 2, nCarte = 3;
    public static final String[] semi = {"bastoni", "denara", "spade", "coppe"};
    public static boolean carteScoperte = false;
    public static Integer scoreLimit = 3;
    public static Carta briscola;

    protected static Giocatore[] giocatori = new Giocatore[nGiocatori];
    protected static ArrayList<Carta> mazzo = new ArrayList<>();
    protected static Giocatore giocante, ultimoVincitore;
    protected static boolean canPlay = true, lastManche = false, terminata = false;
    protected static Short endEvent;

    protected static JFrame f;
    protected static JPanel p, pMain, pSouth, pGiocoC, pGiocoR, pGiocoL, pGiocoUser, pGiocoUserL, pGiocoUserR, pGiocoCPU, pGiocoCPUL, pGiocoCPUR;
    protected static AnteprimaCarte anteprimaCarte = new AnteprimaCarte();
    protected static Scoreboard scoreboard;

    public static void main(String args[]) {
        init();
    }

    static void init() {
        keyListener();
        GUI.creaInterfaccia();
        GUI.aggiornaFrame();
    }

    static void keyListener(){
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new OnKeyPressed());
    }
}
