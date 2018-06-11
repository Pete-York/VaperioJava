package vaperio.core;

public class VaperioParams {
    public static final int frameRate = 30;
    public static final float minXCoordinate = -12.5f;
    public static final float maxXCoordinate = 12.5f;
    public static final float minYCoordinate = -9.3f;
    public static final float maxYCoordinate = 9.3f;

    // Spaceship Movement
    public float minSpaceshipDrag = 0.5f;
    public float spaceshipDragFactor = 1f;
    public float spaceshipDragExponent = 2f;
    public float spaceshipThrust = 7f;
    public int lagDuration = 20;

    // Health
    public int playerStartingHealth = 60;
    public int ralphHealth = 40;

    // Player Damage
    public int playerDamage = 10;
    public int spaceshipShootRate = 15;

    // Bullets
    public float playerBulletSpeed = 5f;
    public float enemyBulletSpeed = 5f;

    //Ralph
    public int ralphDamage = 10;
    public int ralphSpawnTime = 75;
    public int ralphShootRate = 57;
    public float ralphMinXSpawn = -2.5f;
    public float ralphMaxXSpawn = -3.5f;
    public float ralphMinYSpawn = -2f;
    public float ralphMaxYSpawn = 2f;

    // Marge
    public int margeDamage;
    public float margeGroundSpeed = 0.3f;
    public float margeSpikeSpeed = 8f;
    public float margeReturnSpeed = 3f;
    public float margeDistanceThreshold = 0.1f;
    public float margeSpikeHeight = 7f;
    public int margeSpikeSustainFrames = frameRate * 2;

}
