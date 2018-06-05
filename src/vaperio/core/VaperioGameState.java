package vaperio.core;

import ggi.core.AbstractGameState;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VaperioGameState implements AbstractGameState {
    private int juice;
    private List<Ralph> ralphs;
    private Point margePosition;
    private Point shipPosition;
    private Point shipVelocity;
    private boolean isNether;


    public VaperioGameState(){
        this.margePosition = new Point();
        this.shipPosition = new Point();
        this.shipVelocity = new Point();
        this.ralphs = new ArrayList<Ralph>();
        this.juice = 60;
        this.isNether = false;
    }

    public VaperioGameState(Point margePosition, Point shipPosition, Point shipVelocity, List<Ralph> ralphs, int juice, boolean isNether){
        this.margePosition = margePosition;
        this.shipPosition = shipPosition;
        this.shipVelocity = shipVelocity;
        this.ralphs = ralphs;
        this.juice = juice;
        this.isNether = isNether;
    }

    @Override
    public AbstractGameState copy() {
        Point newMargePosition = new Point(margePosition.x, margePosition.y);
        Point newShipPosition = new Point(shipPosition.x, shipPosition.y);
        Point newShipVelocity = new Point(shipVelocity.x, shipPosition.y);
        List<Ralph> newRalphs = ralphs.stream()
                .map(Ralph::clone)
                .collect(Collectors.toList());
        return new VaperioGameState(newMargePosition, newShipPosition, newShipVelocity, newRalphs, this.juice, this.isNether);
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
}
