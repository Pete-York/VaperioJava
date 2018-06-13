package vaperio.test;

import agents.evo.EvoAgent;
import evodef.DefaultMutator;
import evodef.EvoAlg;
import ga.SimpleRMHC;
import ggi.core.SimplePlayerInterface;
import vaperio.core.VaperioGameState;
import vaperio.core.VaperioParams;

import java.awt.*;

public class EvolutionaryAgentPerformanceTest {

    static boolean showEvolution = false;

    public static void main(String[] args) throws Exception {

        int[] nEvalss = {10, 20, 30, 40};
        int[] seqLengths = {100, 200, 300, 400, 1000};
        VaperioParams params = new VaperioParams();

        params.maxFrames = 15000;
        // todo: how does changing the  parameter settings affect AI agent performance?
        // todo: Can you settings that make it really tough for the AI?
        int nTrials = 20;
        for(int nEvals : nEvalss) {
            for(int seqLength : seqLengths) {
                double totalScore = 0;
                for (int i = 0; i < nTrials; i++) {
                    SimplePlayerInterface player = getEvoAgent(nEvals, seqLength);
                    VaperioGameState gameState = new VaperioGameState(params);

                    while (!gameState.isTerminal()) {
                        // get the action from the player, update the game state, and show a view
                        int action = player.getAction(gameState.copy(), 0);
                        // recall the action array is needed for generality for n-player games
                        int[] actions = new int[]{action};
                        VaperioGameState viewState = (VaperioGameState) gameState.copy();
                        gameState.next(actions);
                        //Thread.sleep(40);
                    }
                    totalScore += gameState.getScore();
                }
                System.out.println("nEvals - " + nEvals);
                System.out.println("seqLength - " + seqLength);
                double meanScore = totalScore / nTrials;
                System.out.println("mean score  - " + meanScore);
                System.out.println("nTrials - " + nTrials);
            }
        }
    }

    public static SimplePlayerInterface getEvoAgent(int nEvals, int seqLength) {
        //
        // todo Add in the code to make this
        int nResamples = 1;

        DefaultMutator mutator = new DefaultMutator(null);
        // setting to true may give best performance
        mutator.totalRandomChaosMutation = true;
        mutator.flipAtLeastOneValue = true;
        mutator.pointProb = 100;

        SimpleRMHC simpleRMHC = new SimpleRMHC();
        simpleRMHC.setSamplingRate(nResamples);
        simpleRMHC.setMutator(mutator);

        EvoAlg evoAlg = simpleRMHC;

        // evoAlg = new SlidingMeanEDA();
        EvoAgent evoAgent = new EvoAgent().setEvoAlg(evoAlg, nEvals).setSequenceLength(seqLength);
        evoAgent.setDimension(new Dimension(800, 400));
        evoAgent.setUseShiftBuffer(true);

        if (showEvolution)
            evoAgent.setVisual();

        return evoAgent;
    }
}