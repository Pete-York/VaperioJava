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
    private Point shipPosition;
    private Point shipVelocity;
    private boolean isNether;
    private VaperioParams gameParams;

    public VaperioGameState(){
        this.marge = new Marge();
        this.shipPosition = new Point();
        this.shipVelocity = new Point();
        this.ralphs = new ArrayList<Ralph>();
        this.juice = 60;
        this.isNether = false;
    }

    public VaperioGameState(Marge marge, Point shipPosition, Point shipVelocity, List<Ralph> ralphs, int juice, boolean isNether){
        this.marge = marge;
        this.shipPosition = shipPosition;
        this.shipVelocity = shipVelocity;
        this.ralphs = ralphs;
        this.juice = juice;
        this.isNether = isNether;
    }

    @Override
    public AbstractGameState copy() {
        Marge newMarge = marge.clone();
        Point newShipPosition = new Point(shipPosition.x, shipPosition.y);
        Point newShipVelocity = new Point(shipVelocity.x, shipPosition.y);
        List<Ralph> newRalphs = ralphs.stream()
                .map(Ralph::clone)
                .collect(Collectors.toList());
        return new VaperioGameState(newMarge, newShipPosition, newShipVelocity, newRalphs, this.juice, this.isNether);
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
