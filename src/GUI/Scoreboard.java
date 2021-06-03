package GUI;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class Scoreboard extends JPanel {
    /* LABEL CONTENENTE I PUNTEGGI DEI GIOCATORI; */
    public JLabel score[] = new JLabel[2];

    /* TITOLO DELLA SCOREBOARD; */
    private JLabel titolo;

    /* SOTTOPANNELLO DELLA SCOREBOARD; */
    private JPanel p2;

    public Scoreboard(){
        super();
        this.setPreferredSize(new Dimension(250, 100));
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
        this.p2.setLayout(new GridLayout(1,2));
        add(this.p2);
    }

    void creaTitolo(){
        this.titolo = new JLabel("SCORE BOARD");
        this.titolo.setFont(new Font("Tahoma", 1, 18));
        this.titolo.setHorizontalAlignment(SwingConstants.CENTER);
        this.titolo.setBorder(new MatteBorder(0, 0, 2, 0, Color.black));
        add(this.titolo);
    }

    void creaLabel(){
        for(int i = 0; i < score.length; i++){
            this.score[i] = new JLabel("In attesa...");
            this.score[i].setFont(new Font("Tahoma", 1, 15));
            this.score[i].setHorizontalAlignment(SwingConstants.CENTER);
            this.score[i].setBorder(new MatteBorder(0, 1, 0, 1, Color.black));

            this.p2.add(this.score[i]);
        }
    }

    public void setTitle(String string){
        titolo.setText(string);
    }

    public String getTitle(){
        return titolo.getText();
    }
}
