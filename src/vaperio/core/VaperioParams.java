package vaperio.core;

public class VaperioParams {
    // Spaceship Movement
    public int spaceshipDrag;
    public int spaceshipthrust;
    public int lagDuration;

    // Health
    public int playerStartingHealth;
    public int ralphHealth;

    // Damage
    public int playerDamage;
    public int invulnerableTime;

    // Other

    //Ralp
    public int ralphDamage;
    public int ralphSpawnRate;
    public int ralphShootRate;

    // Marge
    public int margeDamage;
    public int margeGroundSpeed = 300;
    public int margeSpikeSpeed = 8000;
    public int margeReturnSpeed = 3000;
    public int margeDistanceThreshold = 100;
    public int margeSpikeHeight = 7000;
    public int margeSpikeSustainFrames = 60 * 2;
}
