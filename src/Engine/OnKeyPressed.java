package Engine;

import Main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class OnKeyPressed extends Game implements KeyEventDispatcher {
    private static boolean dialogOpen = false;

    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        if(dialogOpen)
            return false;

        switch(e.getKeyCode()){
            case KeyEvent.VK_ENTER: next(); break;
            case KeyEvent.VK_ESCAPE: menu(); break;
        }

        return false;
    }

    void next(){
        if(!Main.Game.terminata)
            return;

        switch(Game.endEvent){
            case 0:
                Engine.iniziaRound();
                break;
            case 1:
                Engine.iniziaPartita();
                break;
        }
    }

    static void menu(){

    }
}
