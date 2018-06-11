package vaperio.core;

public class VaperioParams {
    // Spaceship Movement
    public float spaceshipDrag;
    public float spaceshipthrust;
    public int lagDuration;

    // Health
    public int playerStartingHealth;
    public int ralphHealth;

    // Player Damage
    public int playerDamage;
    public int spaceshipShootRate;

    // Bullets
    public float playerBulletSpeed;
    public float enemyBulletSpeed;

    //Ralph
    public int ralphDamage;
    public int ralphSpawnTime;
    public int ralphShootRate;
    public float ralphMinXSpawn = 2.5f;
    public float ralphMaxXSpawn = 3.5f;
    public float ralphMinYSpawn = -2f;
    public float rakphMaxYSpawn = 2f;

    // Marge
    public int margeDamage;
    public float margeGroundSpeed = 300;
    public float margeSpikeSpeed = 8000;
    public float margeReturnSpeed = 3000;
    public float margeDistanceThreshold = 100;
    public float margeSpikeHeight = 7000;
    public int margeSpikeSustainFrames = 60 * 2;

    public static final int frameRate = 30;

}
