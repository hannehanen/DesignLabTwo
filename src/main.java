import Factories.DeckFactory;
import Game.Game;

public class main {
    public static void main(String[] args) {
        Game game = new Game();
        //kanske en facade som startas istället för nytt game? som viewn pratar med.. eller ännu bättre en controller?
        ConsollUserIMPL view = new ConsollUserIMPL();
        view.addListener(game);
        game.addGameListener(view);
        view.playCardGame();

        //DeckFactory factory = new DeckFactory();
        //game.setDeck(factory.getOrdinaryDeck());
        //game.getDeck().shuffleDeck();
        //game.playGame();

    }
}
