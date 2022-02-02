public class Main {

    public static void main(String[] args) {
        Main m = new Main();
        m.playGame();
    }

    private void playGame() {
        GameLogic logic = new GameLogic();
        InputProcessor inputProcessor = new InputProcessor(logic);
        while (true) {
            System.out.println(inputProcessor.getNextAction());
            logic.botNextAction();
        }
    }
}
