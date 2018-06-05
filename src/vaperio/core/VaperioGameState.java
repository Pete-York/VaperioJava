package vaperio.core;

import ggi.core.AbstractGameState;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VaperioGameState implements AbstractGameState {
    private int juice;
    private List<Ralph> ralphs;
    private Marge marge;
    private Spaceship spaceship;
    private boolean isNether;
    private VaperioParams gameParams;

    public VaperioGameState(VaperioParams gameParams){
        this.marge = new Marge(gameParams);
        this.spaceship = new Spaceship(gameParams);
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
        return null;
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
