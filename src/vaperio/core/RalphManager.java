package vaperio.core;

public class RalphManager implements Cloneable {
    private float minXSpawn;
    private float maxXSpawn;
    private float minYSpawn;
    private float maxYSpawn;

    private int framesSinceStart = 0;

    private int spawnTime;
    private int framesSinceLastSpawn = 0;

    private VaperioParams gameParams;
    private LevelState levelState = LevelState.FIRST_RALPH;
    private boolean ralphSpawned = false;

    public RalphManager(VaperioParams gameParams){
        this.spawnTime = gameParams.ralphSpawnTime;
        this.gameParams = gameParams;

        this.minXSpawn = gameParams.ralphMinXSpawn;
        this.maxXSpawn = gameParams.ralphMaxXSpawn;
        this.minYSpawn = gameParams.ralphMinYSpawn;
        this.maxYSpawn = gameParams.ralphMaxYSpawn;
    }

    public RalphManager(RalphManager old) {
        this.spawnTime = old.spawnTime;
        this.gameParams = old.gameParams;

        this.framesSinceStart = old.framesSinceStart;
        this.framesSinceLastSpawn = old.framesSinceLastSpawn;
        this.levelState = old.levelState;

        this.minXSpawn = old.minXSpawn;
        this.maxXSpawn = old.maxXSpawn;
        this.minYSpawn = old.minYSpawn;
        this.maxYSpawn = old.maxYSpawn;

    }

    public Ralph next(VaperioGameState gameState) {
        framesSinceLastSpawn++;
        framesSinceStart++;
        if(shouldSpawn()) {
            return spawn(gameState);
        }
        return null;
    }


    public Ralph spawn(VaperioGameState gameState) {
        return spawn(shouldBeNether(), gameState);
    }

    private Ralph spawn(boolean isNether, VaperioGameState gameState) {
        framesSinceLastSpawn = 0;
        return new Ralph(gameParams, generatePosition(), isNether, gameState);
    }

    public void enemyKilled() {
        if(levelState == LevelState.FIRST_RALPH) {
            levelState = LevelState.FIRST_NETHER;
            framesSinceLastSpawn = 0;
            ralphSpawned = false;
        } else if (levelState == LevelState.FIRST_NETHER) {
            levelState = LevelState.NORMAL_LEVEL;
        }
    }

    private boolean shouldSpawn() {
        if(levelState == LevelState.FIRST_NETHER || levelState == LevelState.FIRST_RALPH) {
            if(framesSinceLastSpawn > spawnTime * 6 && !ralphSpawned){
                ralphSpawned = true;
                return true;
            } else {
                return false;
            }
        }
        float timeSinceLastSpawn = framesSinceLastSpawn / VaperioParams.frameRate;
        float timeSinceLastSpawnSquared = timeSinceLastSpawn * timeSinceLastSpawn;
        int toBeat = (int) (Math.random() * 100);
        timeSinceLastSpawnSquared += (framesSinceStart / VaperioParams.frameRate) * 0.1f;
        return timeSinceLastSpawnSquared > toBeat;
    }

    private boolean shouldBeNether() {
        switch (levelState){
            case FIRST_RALPH:
                return false;
            case FIRST_NETHER:
                return true;
            case NORMAL_LEVEL:
                return(int) (Math.random() * 1) == 1;
            default:
                return false;
        }
    }

    private FloatPoint generatePosition(){
        float xSpawn = (float) (Math.random() * (maxXSpawn - minXSpawn)) + minXSpawn;
        float ySpawn = (float) (Math.random() * (maxYSpawn - minYSpawn)) + minYSpawn;
        return new FloatPoint (xSpawn, ySpawn);
    }

    @Override
    public RalphManager clone(){
        return new RalphManager(this);
    }
}
