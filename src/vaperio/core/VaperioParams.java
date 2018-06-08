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
    public int ralphSpawnRate;
    public int ralphShootRate;

    // Marge
    public int margeDamage;
    public float margeGroundSpeed = 300;
    public float margeSpikeSpeed = 8000;
    public float margeReturnSpeed = 3000;
    public float margeDistanceThreshold = 100;
    public float margeSpikeHeight = 7000;
    public int margeSpikeSustainFrames = 60 * 2;

}
