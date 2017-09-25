import Controllers.ConsollController;
import Game.Game;
import view.ConsollUserIMPL;

public class main {
    public static void main(String[] args) {
        ConsollController consollController = new ConsollController();
        ConsollUserIMPL consollUserIMPL = new ConsollUserIMPL();
        consollController.addGameListener(consollUserIMPL);
        consollUserIMPL.addListener(consollController);
        consollUserIMPL.gameOptions();

    }
}
