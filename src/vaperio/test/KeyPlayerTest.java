package vaperio.test;

import vaperio.core.VaperioGameState;
import vaperio.core.VaperioParams;
import vaperio.view.VaperioView;

import vaperio.controllers.KeyController;
import utilities.JEasyFrame;

public class KeyPlayerTest {
    public static void main(String[] args) throws Exception {
        VaperioParams params = new VaperioParams();

        VaperioGameState gameState = new VaperioGameState(params);
        VaperioView view = new VaperioView().setGameState(gameState).setParams(params);
        String title = "Key Player Test";
        JEasyFrame frame = new JEasyFrame(view, title);
        KeyController player = new KeyController();
        frame.addKeyListener(player);
        //ViewUtil.waitUntilReady(view);

        while (!gameState.isTerminal()) {
            // get the action from the player, update the game state, and show a view
            int action = player.getAction(gameState.copy(), 0);
            // recall the action array is needed for generality for n-player games
            int[] actions = new int[]{action};
            gameState.next(actions);
            VaperioGameState viewState = (VaperioGameState) gameState.copy();
            view.setGameState(viewState).repaint();
            Thread.sleep(40);
        }
    }
}
