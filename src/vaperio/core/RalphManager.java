package vaperio.core;

public class RalphManager {
    private float minXSpawn;
    private float maxXSpawn;
    private float minYSpawn;
    private float maxYSpawn;

    private int framesSinceStart = 0;

    private int spawnTime;
    private int framesSinceLastSpawn = 0;

    private VaperioParams gameParams;
    private LevelState levelState = LevelState.FIRST_RALPH;

    public RalphManager(VaperioParams gameParams){
        this.spawnTime = gameParams.ralphSpawnTime;
        this.gameParams = gameParams;

        this.minXSpawn = gameParams.ralphMinXSpawn;
        this.maxXSpawn = gameParams.ralphMaxXSpawn;
        this.minYSpawn = gameParams.ralphMinYSpawn;
        this.maxYSpawn = gameParams.rakphMaxYSpawn;
    }

    public void next(VaperioGameState gameState) {
        framesSinceLastSpawn++;
        framesSinceStart++;
        if(shouldSpawn()) {
            spawn(gameState);
        }
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
        } else if (levelState == LevelState.FIRST_NETHER) {
            levelState = LevelState.NORMAL_LEVEL;
        }
    }

    private boolean shouldSpawn() {
        if(levelState == LevelState.FIRST_NETHER || levelState == LevelState.FIRST_RALPH) {
            return framesSinceLastSpawn > spawnTime * 6;
        }
        int framesSinceLastSpawnSquared = framesSinceLastSpawn * framesSinceLastSpawn;
        int toBeat = (int) (Math.random() * 100);
        framesSinceLastSpawnSquared += framesSinceStart / (VaperioParams.frameRate * 10);
        return framesSinceLastSpawnSquared > toBeat;
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
}
