package vaperio.test;

import agents.evo.EvoAgent;
import vaperio.core.VaperioGameState;
import vaperio.core.VaperioParams;
import evodef.DefaultMutator;
import evodef.EvoAlg;
import ga.SimpleRMHC;
import ggi.core.SimplePlayerInterface;
import utilities.JEasyFrame;
import vaperio.view.VaperioView;

import java.awt.*;

public class EvoAgentVisTest {

    static boolean showEvolution = true;

    public static void main(String[] args) throws Exception {
        SimplePlayerInterface player = getEvoAgent();

        VaperioParams params = new VaperioParams();


        // todo: how does changing the parameter settings affect AI agent performance?
        // todo: Can you settings that make it really tough for the AI?
        params.ralphMaxYSpawn = 0f;
        params.ralphMinYSpawn = 0f;

        VaperioGameState gameState = new VaperioGameState(params);
        VaperioView view = new VaperioView().setGameState(gameState).setParams(params);
        String title = "Evo Agent Visual Test";

        while (!gameState.isTerminal()) {
            // get the action from the player, update the game state, and show a view
            int action = player.getAction(gameState.copy(), 0);
            // recall the action array is needed for generality for n-player games
            int[] actions = new int[]{action};
            VaperioGameState viewState = (VaperioGameState) gameState.copy();
            view.setGameState(viewState).repaint();
            gameState.next(actions);
            Thread.sleep(40);
        }

    }

    public static SimplePlayerInterface getEvoAgent() {
        //
        // todo Add in the code to make this
        int nResamples = 1;

        DefaultMutator mutator = new DefaultMutator(null);
        // setting to true may give best performance
        mutator.totalRandomChaosMutation = true;
        mutator.flipAtLeastOneValue = true;
        mutator.pointProb = 5;

        SimpleRMHC simpleRMHC = new SimpleRMHC();
        simpleRMHC.setSamplingRate(nResamples);
        simpleRMHC.setMutator(mutator);

        EvoAlg evoAlg = simpleRMHC;

        // evoAlg = new SlidingMeanEDA();

        int nEvals = 30;
        int seqLength = 1000;
        EvoAgent evoAgent = new EvoAgent().setEvoAlg(evoAlg, nEvals).setSequenceLength(seqLength);
        evoAgent.setDimension(new Dimension(800, 400));
        evoAgent.setUseShiftBuffer(true);

        if (showEvolution)
            evoAgent.setVisual();

        return evoAgent;
    }
}
