package GUI;

import Main.Game;
import Engine.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class GUI extends Game {
    private static JMenuBar menuBar;
    private static JMenu pauseMenu, options;
    private static JMenuItem resetOpt, returnOpt, exitOpt;
    private static JCheckBoxMenuItem carteScoperteOpt;

    public static void creaInterfaccia(){
        creaFrame();
        creaMenu();
        creaP();
        creaPMain();
        creaPSouth();
        menuIniziale();
        setSfondo();
        aggiornaFrame();
    }

    static void creaP(){
        p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BorderLayout());
        p.setBorder(new EmptyBorder(30, 0, 15, 0));
        f.add(p);
    }

    static void creaPSouth(){
        pSouth = new JPanel();
        pSouth.setOpaque(false);

        JLabel crediti = new JLabel("Credits: Simone Di Berardino @ 2021");
        crediti.setFont(new Font("Calibri", Font.BOLD, 18));
        crediti.setForeground(Color.lightGray);

        pSouth.add(crediti);
        p.add(pSouth, "South");
    }

    static void menuIniziale() {
        pMain.setLayout(new GridBagLayout());
        abilitaMenu(false);

        CButton inizia = new CButton("Gioca a Briscola", 200, 70, 20, true);
        inizia.addActionListener(e -> {
            pMain.removeAll();
            creaMenuPartita();

            try{
                Engine.inizializza();
            }catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        });

        CButton quit = new CButton("Chiudi il gioco", 200, 70, 20, true);
        quit.addActionListener(e -> chiudiGioco());

        String imagePath = "/resources/logo.png";
        JLabel logoLabel = new JLabel(new ImageIcon(GUI.class.getResource(imagePath)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);

        pMain.add(inizia, gbc);
        pMain.add(quit, gbc);
        pMain.add(logoLabel, gbc);

        aggiornaFrame();
    }

    static void creaMenuPartita(){
        changeLayout();
        abilitaMenu(true);
        creaPCPUL();
        creaPCPUC();
        creaPCPUR();
        creaPGiocoL();
        creaPGiocoC();
        creaPGiocoR();
        creaPUserR();
        creaPUserC();
        creaPUserL();
    }

    static void creaFrame(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenSize.setSize(screenSize.getWidth(), screenSize.getHeight());

        f = new JFrame("Briscola!");
        f.setSize(screenSize);
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.setUndecorated(true);
        f.setVisible(true);
    }

    static void creaMenu(){
        menuBar = new JMenuBar();
        pauseMenu = new JMenu("Menu di pausa");
        options = new JMenu("Opzioni");

        resetOpt = new JMenuItem("Riavvia la partita");
        resetOpt.addActionListener((event) -> riavviaPartita());
        pauseMenu.add(resetOpt);

        returnOpt = new JMenuItem("Torna al menu principale");
        returnOpt.addActionListener((event) -> menuPrincipale());
        pauseMenu.add(returnOpt);

        exitOpt = new JMenuItem("Torna al desktop");
        exitOpt.addActionListener((event) -> chiudiGioco());
        pauseMenu.add(exitOpt);

        carteScoperteOpt = new JCheckBoxMenuItem("Carte scoperte");
        carteScoperteOpt.setState(carteScoperte);

        carteScoperteOpt.addItemListener(e -> toggleCarteScoperte());
        options.add(carteScoperteOpt);

        menuBar.add(pauseMenu);
        menuBar.add(options);
        f.setJMenuBar(menuBar);
    }

    static void creaPMain(){
        pMain = new JPanel();
        pMain.setOpaque(false);
        p.add(pMain, "Center");
    }

    static void creaPCPUL(){
        pGiocoCPUL = new JPanel();
        pGiocoCPUL.setOpaque(false);
        pMain.add(pGiocoCPUL);
    }

    static void creaPCPUC(){
        pGiocoCPU = new JPanel();
        pGiocoCPU.setOpaque(false);
        pMain.add(pGiocoCPU);
    }

    static void creaPCPUR(){
        pGiocoCPUR = new JPanel();
        pGiocoCPUR.setOpaque(false);
        pMain.add(pGiocoCPUR);
    }

    static void creaPGiocoR(){
        pGiocoR = new JPanel();
        pGiocoR.setOpaque(false);
        pMain.add(pGiocoR);
    }

    static void creaPGiocoC(){
        pGiocoC = new JPanel();
        pGiocoC.setOpaque(false);
        pMain.add(pGiocoC);
    }

    static void creaPGiocoL(){
        pGiocoL = new JPanel();
        pGiocoL.setOpaque(false);
        scoreboard = new Scoreboard();
        pGiocoL.add(scoreboard);
        pMain.add(pGiocoL);
    }

    static void creaPUserL(){
        pGiocoUserL = new JPanel();
        pGiocoUserL.setOpaque(false);
        pMain.add(pGiocoUserL);
    }

    static void creaPUserC(){
        pGiocoUser = new JPanel();
        pGiocoUser.setOpaque(false);
        pMain.add(pGiocoUser);
    }

    static void creaPUserR(){
        pGiocoUserR = new JPanel();
        pGiocoUserR.setOpaque(false);
        pMain.add(pGiocoUserR);
    }

    static void abilitaMenu(boolean flag){
        resetOpt.setEnabled(flag);
        returnOpt.setEnabled(flag);
    }

    static void changeLayout(){
        pMain.setLayout(new GridLayout(3,3));
    }

    static void setSfondo(){
        f.add(new Background("/resources/sfondo.jpg"));
    }

    public static void aggiornaFrame(){
        f.revalidate();
        f.repaint();
    }

    static void riavviaPartita(){
        int risposta = JOptionPane.showConfirmDialog(null, "Vuoi davvero riavviare la partita?");
        if(risposta == JOptionPane.YES_OPTION) {
            Engine.iniziaPartita();
        }
    }

    static void menuPrincipale(){
        int risposta = JOptionPane.showConfirmDialog(null, "Vuoi davvero tornare al menu principale?");
        if(risposta == JOptionPane.YES_OPTION) {
            pMain.removeAll();
            aggiornaFrame();
            new Thread(() -> menuIniziale()).start();
        }
    }

    static void chiudiGioco(){
        int risposta = JOptionPane.showConfirmDialog(null, "Vuoi davvero chiudere il gioco?");
        if(risposta == JOptionPane.YES_OPTION) {
            System.exit(1);
        }
    }

    static void toggleCarteScoperte() {
        Game.carteScoperte = !Game.carteScoperte;

        if(pGiocoCPU == null)
            return;

        if(terminata)
            return;

        Component[] carteCPU = pGiocoCPU.getComponents();

        if(carteScoperte){
            for(var carta : carteCPU){
                ((Carta) carta).mostra();
            }
        }else{
            for(var carta : carteCPU){
                ((Carta) carta).nascondi();
            }
        }
    }
}
