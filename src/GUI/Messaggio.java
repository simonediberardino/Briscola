package GUI;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class Messaggio extends JPanel {
    private JLabel titolo;
    private JLabel sottotitolo;
    private String stringTit, stringSot;

    /* SOTTOPANNELLO DELLA SCOREBOARD; */
    private static JPanel p2;

    public Messaggio(String titolo, String sottotitolo){
        super();
        this.stringTit = titolo;
        this.stringSot = sottotitolo;
        this.setPreferredSize(new Dimension(400, 100));
        this.creaP1();
        this.creaTitolo();
        this.creaP2();
        this.creaLabel();
    }

    void creaP1(){
        this.setLayout(new GridLayout(2,1));
        this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        this.setBackground(Color.white);
    }

    void creaP2(){
        this.p2 = new JPanel();
        add(this.p2);
    }

    void creaTitolo(){
        this.titolo = new JLabel(this.stringTit);
        this.titolo.setFont(new Font("Tahoma", 1, 18));
        this.titolo.setHorizontalAlignment(SwingConstants.CENTER);
        this.titolo.setBorder(new MatteBorder(0, 0, 2, 0, Color.black));
        add(this.titolo);
    }

    void creaLabel(){
        this.sottotitolo = new JLabel(this.stringSot);
        this.sottotitolo.setFont(new Font("Tahoma", 1, 18));
        this.sottotitolo.setHorizontalAlignment(SwingConstants.CENTER);
        this.sottotitolo.setBorder(new MatteBorder(0, 1, 0, 1, Color.black));
        this.p2.add(this.sottotitolo);
    }

    public void setTitle(String string){
        titolo.setText(string);
    }

    public String getTitle(){
        return titolo.getText();
    }
}
