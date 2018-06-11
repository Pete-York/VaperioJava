package vaperio.core;

public class VaperioParams {
    public static final int frameRate = 30;

    // Spaceship Movement
    public float spaceshipDrag;
    public float spaceshipThrust;
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
    public float ralphMinXSpawn = 2.5f;
    public float ralphMaxXSpawn = 3.5f;
    public float ralphMinYSpawn = -2f;
    public float rakphMaxYSpawn = 2f;

    // Marge
    public int margeDamage;
    public float margeGroundSpeed = 0.3f;
    public float margeSpikeSpeed = 8f;
    public float margeReturnSpeed = 3f;
    public float margeDistanceThreshold = 0.1f;
    public float margeSpikeHeight = 7f;
    public int margeSpikeSustainFrames = frameRate * 2;

}
