package vaperio.core;

public class RalphManager {
    private final float xSpawn = 0f;
    private final float minYSpawn = 0f;
    private final float maxYSpawn = 1f;

    private int framesSinceStart = 0;

    private int spawnTime;
    private int framesSinceLastSpawn = 0;

    private VaperioParams gameParams;
    private LevelState levelState = LevelState.FIRST_RALPH;

    public RalphManager(VaperioParams gameParams){
        this.spawnTime = gameParams.ralphSpawnTime;
        this.gameParams = gameParams;

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
            return framesSinceLastSpawn > spawnTime;
        }
        int framesSinceLastSpawnSquared = framesSinceLastSpawn * framesSinceLastSpawn;
        int toBeat = (int) (Math.random() * 100);
        framesSinceLastSpawnSquared += (int) (framesSinceStart * 0.1f);
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
        float ySpawn = (float) (Math.random() * (maxYSpawn - minYSpawn)) + minYSpawn;
        return new FloatPoint (xSpawn, ySpawn);
    }
}
