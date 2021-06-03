package GUI;

import java.io.IOException;

public class CartaVuota extends Carta {
    public CartaVuota() {
        super(false);
        visible();
    }

    public void visible(){
        this.setVisible(true);
    }

    public void invisible(){
        this.setVisible(false);
    }
}
