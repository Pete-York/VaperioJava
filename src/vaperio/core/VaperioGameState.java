package vaperio.core;

import ggi.core.AbstractGameState;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VaperioGameState implements AbstractGameState {
    private int juice;
    private Spaceship spaceship;
    private Marge marge;
    private List<Ralph> ralphs;
    private List<Bullet> playerBullets = new ArrayList<Bullet>();
    private List<Bullet> ralphBullets = new ArrayList<Bullet>();
    private boolean isNether;

    private VaperioParams gameParams;

    public VaperioGameState(VaperioParams gameParams){
        this.marge = new Marge(gameParams, new FloatPoint(0f, 0f));
        this.spaceship = new Spaceship(gameParams, new FloatPoint(0f, 0f));
        this.ralphs = new ArrayList<Ralph>();
        this.juice = 60;
        this.isNether = false;
        this.gameParams = gameParams;
    }

    public VaperioGameState(Marge marge, Spaceship spaceship, List<Ralph> ralphs, int juice, boolean isNether){
        this.marge = marge;
        this.spaceship = spaceship;
        this.ralphs = ralphs;
        this.juice = juice;
        this.isNether = isNether;
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
        return null;
    }

    private void checkCollisions(){
        ralphBullets = ralphBullets.stream()
                .filter(bullet -> checkRalphBulletCollision(bullet))
                .collect(Collectors.toList());

        if(spaceship.collideWithMarge(marge)) {
            juice -= gameParams.margeDamage;
        }

        playerBullets = playerBullets.stream()
                .filter(bullet -> checkPlayerBulletCollision(bullet))
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
            if(ralph.checkCollision(bullet)) {
                boolean ralphDied = ralph.applyDamage(gameParams.playerDamage);
                if(ralphDied) ralphs.remove(ralph);
                return false;
            }
        }
        return true;
    }

    @Override
    public int nActions() {
        return 0;
    }

    @Override
    public double getScore() {
        return 0;
    }

    @Override
    public boolean isTerminal() {
        return false;
    }

    public void setGameParams(VaperioParams vaperioParams) {
        this.gameParams = vaperioParams;
    }
}
