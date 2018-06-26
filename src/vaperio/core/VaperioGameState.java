package vaperio.core;

import ggi.core.AbstractGameState;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VaperioGameState implements AbstractGameState {
    private static final float playerBulletWidth = 1f;
    private static final float playerBulletHeight = 1f;
    private static final float ralphBulletWidth = 1f;
    private static final float ralphBulletHeight = 1f;

    private int juice;
    private int ralphHits;
    private int frameCount;

    public Spaceship spaceship;
    public Marge marge;
    public List<Ralph> ralphs;
    public List<Bullet> playerBullets = new ArrayList<>();
    public List<Bullet> ralphBullets = new ArrayList<>();
    public RalphManager ralphManager;
    public boolean isNether;

    private VaperioParams gameParams;

    public VaperioGameState(VaperioParams gameParams){
        this.marge = new Marge(gameParams, new FloatPoint(6f, -6.6f));
        this.spaceship = new Spaceship(gameParams, new FloatPoint(-2f, 0f), this);
        this.ralphs = new ArrayList<>();
        this.juice = gameParams.playerStartingHealth;
        this.isNether = false;
        this.gameParams = gameParams;
        this.ralphManager = new RalphManager(gameParams);
        this.ralphHits = 0;
    }

    public VaperioGameState(VaperioGameState old){
        this.juice = old.juice;
        this.ralphHits = old.ralphHits;
        this.frameCount = old.frameCount;

        this.spaceship = old.spaceship.clone();
        this.spaceship.setGameState(this);

        this.marge = old.marge.clone();
        this.ralphs = old.ralphs.stream()
                .map(Ralph::clone)
                .collect(Collectors.toList());
        this.ralphs.forEach(ralph -> ralph.setGameState(this));

        this.playerBullets = old.playerBullets.stream()
                .map(Bullet::clone)
                .collect(Collectors.toList());
        this.ralphBullets = old.ralphBullets.stream()
                .map(Bullet::clone)
                .collect(Collectors.toList());
        this.isNether = old.isNether;
        this.ralphManager = old.ralphManager.clone();
        this.gameParams = old.gameParams;
    }

    @Override
    public AbstractGameState copy() {
        return new VaperioGameState(this);
    }

    @Override
    public AbstractGameState next(int[] actions) {
        spaceship.next(actions[0]);
        netherSwitch(actions[0]);
        marge.next(spaceship.getPosition());
        ralphs.forEach(Ralph::next);
        checkCollisions();
        Ralph newRalph = ralphManager.next(this);
        if(newRalph != null){
            ralphs.add(newRalph);
        }
        frameCount++;
        return this;
    }

    private void netherSwitch(int action){
        if(action == 10) {
            isNether = !isNether;
        }
    }

    private void checkCollisions(){
        playerBullets = playerBullets.stream()
                .map(Bullet::move)
                .filter(this::checkPlayerBulletCollision)
                .collect(Collectors.toList());

        ralphBullets = ralphBullets.stream()
                .map(Bullet::move)
                .filter(this::checkRalphBulletCollision)
                .collect(Collectors.toList());
        if(spaceship.collideWithMarge(marge)) {
            juice -= gameParams.margeDamage;
        }
    }

    private boolean checkRalphBulletCollision(Bullet bullet) {
        if(bullet.getPosition().x < VaperioParams.minXCoordinate) {
            return false;
        } else if (spaceship.checkCollision(bullet)) {
            juice -= gameParams.ralphDamage;
            return false;
        }
        return true;
    }

    private boolean checkPlayerBulletCollision(Bullet bullet) {
        if(bullet.getPosition().x > VaperioParams.maxXCoordinate) {
            return false;
        }
        for(Ralph ralph : ralphs){
            if(ralph.getIsNether() == bullet.getIsNether() && ralph.checkCollision(bullet)) {
                boolean ralphDied = ralph.applyDamage(gameParams.playerDamage);
                ralphHits++;
                if(ralphDied){
                    ralphs.remove(ralph);
                    ralphManager.enemyKilled();
                }
                return false;
            }
        }
        return true;
    }

    public void shootSpaceshipBullet(FloatPoint position){
        FloatPoint velocity = new FloatPoint(gameParams.playerBulletSpeed, 0f);
        velocity.scale(1f / VaperioParams.frameRate);
        Bullet spaceshipBullet = new Bullet(position, velocity, isNether , playerBulletWidth, playerBulletHeight);
        playerBullets.add(spaceshipBullet);
    }

    public void shootRalphBullet(FloatPoint position, boolean isNetherRalph){
        FloatPoint velocity = new FloatPoint(-gameParams.enemyBulletSpeed, 0f);
        velocity.scale(1f / VaperioParams.frameRate);
        Bullet ralphBullet = new Bullet(position, velocity, isNetherRalph , ralphBulletWidth, ralphBulletHeight);
        ralphBullets.add(ralphBullet);
    }

    @Override
    public int nActions() {
        return 11;
    }

    @Override
    public double getScore() {
        return juice + ralphHits * 10;
    }

    @Override
    public boolean isTerminal() {
        return juice <= 0 || frameCount >= gameParams.maxFrames;
    }

    public void setGameParams(VaperioParams vaperioParams) {
        this.gameParams = vaperioParams;
    }

    public int getFrameCount() {
        return frameCount;
    }
}
