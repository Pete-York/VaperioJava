package vaperio.core;

import ggi.core.AbstractGameState;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VaperioGameState implements AbstractGameState {
    private int juice;
    private int ralphsBullied;
    private int frameCount;

    private Spaceship spaceship;
    private Marge marge;
    private List<Ralph> ralphs;
    private List<Bullet> playerBullets = new ArrayList<>();
    private List<Bullet> ralphBullets = new ArrayList<>();
    private boolean isNether;

    private VaperioParams gameParams;

    public VaperioGameState(VaperioParams gameParams){
        this.marge = new Marge(gameParams, new FloatPoint(0f, 0f));
        this.spaceship = new Spaceship(gameParams, new FloatPoint(0f, 0f));
        this.ralphs = new ArrayList<>();
        this.juice = gameParams.playerStartingHealth;
        this.isNether = false;
        this.gameParams = gameParams;
    }

    public VaperioGameState(VaperioGameState vaperioGameState){
        this.juice = vaperioGameState.juice;
        this.ralphsBullied = vaperioGameState.ralphsBullied;
        this.frameCount = vaperioGameState.frameCount;
        
    }

    @Override
    public AbstractGameState copy() {
        return null;
    }

    @Override
    public AbstractGameState next(int[] actions) {
        spaceship.next(actions[0]);
        marge.next(spaceship.getPosition());
        checkCollisions();
        frameCount++;
        return null;
    }

    private void checkCollisions(){
        ralphBullets = ralphBullets.stream()
                .filter(this::checkRalphBulletCollision)
                .collect(Collectors.toList());

        if(spaceship.collideWithMarge(marge)) {
            juice -= gameParams.margeDamage;
        }

        playerBullets = playerBullets.stream()
                .filter(this::checkPlayerBulletCollision)
                .collect(Collectors.toList());
    }

    private boolean checkRalphBulletCollision(Bullet bullet) {
        if(spaceship.checkCollision(bullet)) {
            ralphBullets.remove(bullet);
            juice -= gameParams.ralphDamage;
            return false;
        }
        return true;
    }

    private boolean checkPlayerBulletCollision(Bullet bullet) {
        for(Ralph ralph : ralphs){
            if(ralph.getIsNether() == bullet.getIsNether() && ralph.checkCollision(bullet)) {
                boolean ralphDied = ralph.applyDamage(gameParams.playerDamage);
                if(ralphDied){
                    ralphs.remove(ralph);
                    ralphsBullied ++;
                }
                return false;
            }
        }
        return true;
    }

    @Override
    public int nActions() {
        return 32;
    }

    @Override
    public double getScore() {
        return juice + ralphsBullied * 20 - frameCount / 60;
    }

    @Override
    public boolean isTerminal() {
        return juice <= 0;
    }

    public void setGameParams(VaperioParams vaperioParams) {
        this.gameParams = vaperioParams;
    }
}
