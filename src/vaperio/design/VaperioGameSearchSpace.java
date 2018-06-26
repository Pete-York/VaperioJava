package vaperio.design;

import agents.dummy.RandomAgent;
import agents.evo.EvoAgent;
import caveswing.core.CaveGameFactory;
import evodef.*;
import ga.SimpleRMHC;
import ggi.core.GameRunnerTwoPlayer;
import ggi.tests.SpeedTest;
import math.Vector2d;
import ntuple.params.*;
import utilities.ElapsedTimer;
import vaperio.core.VaperioFactory;
import vaperio.core.VaperioParams;

public class VaperioGameSearchSpace implements AnnotatedFitnessSpace {

    public static void main(String[] args) {
        ElapsedTimer timer = new ElapsedTimer();
        VaperioGameSearchSpace searchSpace = new VaperioGameSearchSpace();
        searchSpace.verbose = true;
        int[] point = SearchSpaceUtil.randomPoint(searchSpace);

        // or choose a specific one:

        point = new int[]{1, 2, 0, 0, 0, 0};

        System.out.println(Report.report(searchSpace, point));
        System.out.println();
        System.out.println("Size: " + SearchSpaceUtil.size(searchSpace));
        System.out.println("Value: " + searchSpace.evaluate(point));
        System.out.println(timer);
    }

    boolean verbose = false;

    public Param[] getParams() {
        return new Param[]{
                new FloatParam().setArray(thrust).setName("spaceship thrust"),
                new FloatParam().setArray(dragFactor).setName("drag factor"),
                new FloatParam().setArray(dragExponent).setName("drag exponent"),
                new FloatParam().setArray(minDrag).setName("min drag"),
                //new FloatParam().setArray(playerBulletSpeed).setName("play bullet speed"),
                //new FloatParam().setArray(ralphBulletSpeed).setName("ralph bullet speed"),
        };
    }

    float[] thrust = {5f, 7f, 9f};
    float[] dragFactor = {0.5f, 1f};
    float[] dragExponent = {1.5f, 2f};
    float[] minDrag = {0.25f, 0.5f};

    //float[] playerBulletSpeed = {4f, 5f, 6f};
    //float[] ralphBulletSpeed = {4f, 5f, 6f};

    int[] nValues = new int[]{thrust.length, dragFactor.length,
            dragExponent.length, minDrag.length};
    int nDims = nValues.length;

    static int thrustIndex = 0;
    static int dragFactorIndex = 1;
    static int dragExponentIndex = 2;
    static int minDragIndex = 3;
    static int playerBulletSpeedIndex = 4;
    static int ralphBulletSpeedIndex = 5;

    int nGames = 1000;
    int maxSteps = 5000;

    public EvolutionLogger logger;

    public VaperioGameSearchSpace() {
        this.logger = new EvolutionLogger();
    }

    @Override
    public int nDims() {
        return nDims;
    }

    @Override
    public int nValues(int i) {
        return nValues[i];
    }

    // int innerEvals = 2000;

    // int nEvals = 0;
    @Override
    public void reset() {
        // nEvals = 0;
        logger.reset();
    }

    @Override
    public double evaluate(int[] x) {

        // bundle extract the selected params from the solution vector
        // and inject in to the game design params

        GameRunnerTwoPlayer gameRunner = new GameRunnerTwoPlayer();

        // set up the params
        VaperioParams params = new VaperioParams();

        params.spaceshipThrust = thrust[x[thrustIndex]];
        params.spaceshipDragFactor = dragFactor[x[dragFactorIndex]];

        params.spaceshipDragExponent = dragExponent[x[dragExponentIndex]];
        params.minSpaceshipDrag = minDrag[x[minDragIndex]];
        //params.playerBulletSpeed = params.height * playerBulletSpeed[x[playerBulletSpeedIndex]];
        //params.enemyBulletSpeed = ralphBulletSpeed[x[ralphBulletSpeedIndex]];

        // using a Game Factory enables the tester to start with a fresh copy of the
        // game each time
        VaperioFactory gameFactory = new VaperioFactory(params);
        SpeedTest speedTest = new SpeedTest().setGameFactory(gameFactory);


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
        int nEvals = 20;
        int seqLength = 300;
        EvoAgent evoAgent = new EvoAgent().setEvoAlg(evoAlg, nEvals).setSequenceLength(seqLength);
        speedTest.setPlayer(evoAgent);

        speedTest.playGames(nGames, maxSteps);

        if (verbose) {
            System.out.println(speedTest.gameScores);
        }

        double value = speedTest.gameScores.mean();

        speedTest = new SpeedTest().setGameFactory(gameFactory);
        nEvals = 20;
        seqLength = 100;
        evoAgent = new EvoAgent().setEvoAlg(evoAlg, nEvals).setSequenceLength(seqLength);
        speedTest.setPlayer(evoAgent);

        speedTest.playGames(nGames, maxSteps);
        value -= speedTest.gameScores.mean();
        value = Math.abs(value);


        // return this for now, and see what we get
        logger.log(value, x, false);
        return value;
    }

    @Override
    public boolean optimalFound() {
        return false;
    }

    @Override
    public SearchSpace searchSpace() {
        return this;
    }

    @Override
    public int nEvals() {
        return logger.nEvals();
    }

    @Override
    public EvolutionLogger logger() {
        return logger;
    }

    @Override
    public Double optimalIfKnown() {
        return null;
    }
}
