public class guessMovie {
    public static void main (String [] args) throws Exception {
        Game game = new Game();
        while (!(game.endGame())) {
            game.checkLetter();
            game.messages();
        }
    }
}