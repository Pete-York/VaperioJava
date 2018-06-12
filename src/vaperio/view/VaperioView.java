package vaperio.view;

import caveswing.view.CaveView;
import math.Vector2d;
import vaperio.core.*;
import spinbattle.util.DrawUtil;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class VaperioView extends JComponent {
    VaperioParams params;
    VaperioGameState gameState;
    Color bg = Color.PINK;
    Color spaceShipColor = Color.gray;
    Color margeColor = Color.blue;
    Color ralphColor = Color.yellow;

    int nStars = 200;
    int rad = 10;
    public int nPaints = 0;

    int scoreFontSize = 16;
    DrawUtil scoreDraw = new DrawUtil().setColor(Color.white).setFontSize(scoreFontSize);

    public VaperioView setGameState(VaperioGameState gameState) {
        this.gameState = gameState;
        return this;
    }

    public VaperioView setParams(VaperioParams params) {
        this.params = params;
        setStars();
        return this;
    }

    private void setStars() {
        for (int i=0; i<nStars; i++) {
            stars.add(new Star());
        }
    }

    public String getTitle() {
        return gameState.getFrameCount() + " : " + nPaints;
    }

    public Dimension getPreferredSize() {
        return new Dimension(params.width,params.height);
    }



    public void paintComponent(Graphics go) {
        Graphics2D g = (Graphics2D) go;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(bg);
        g.fillRect(0, 0, getWidth(), getHeight());
/*
        double xScroll = -gameState.spaceship.s.x + scrollWidth/2;
        if (scrollView) {
            g.translate(xScroll, 0);
        }
*/
        paintStars(g);
        paintSpaceShip(g);
        paintMarge(g);
        paintRalph(g);
        paintSpaceShipBullets(g);
        paintRalphBullets(g);
/*

        if (scrollView) {
            g.translate(-xScroll, 0);
        }
*/
        // have to paint the score last so that it is not obscured by any game objects
        paintScore(g);
        nPaints++;
    }

    //paint sprites

    private void paintSpaceShip(Graphics2D g) {
        g.setColor(spaceShipColor);
        paintCollideable(g, gameState.spaceship);
    }

    private void paintMarge(Graphics2D g) {
        g.setColor(margeColor);
        paintCollideable(g, gameState.marge);
    }

    private void paintRalph(Graphics2D g) {
        g.setColor(ralphColor);
        for(Ralph r : gameState.ralphs) {
            paintCollideable(g, r);
        }
    }

    private void paintSpaceShipBullets(Graphics2D g) {
        g.setColor(spaceShipColor);
        for( Bullet sb : gameState.playerBullets) {
            paintCollideable(g, sb);
        }
    }

    private void paintRalphBullets(Graphics2D g) {
        g.setColor(ralphColor);
        for( Bullet rb : gameState.ralphBullets) {
            paintCollideable(g, rb);
        }
    }

    private void paintCollideable(Graphics2D g, Collideable collideable) {
        g.setColor(g.getColor());
        FloatPoint s = collideable.getPosition().clone();
        s.scale(VaperioParams.scaleFactor);
        FloatPoint toCentre = new FloatPoint(VaperioParams.maxXCoordinate * VaperioParams.scaleFactor, VaperioParams.maxYCoordinate * VaperioParams.scaleFactor);
        s.add(toCentre);
        int xOrigin = (int) s.x - (int) (collideable.getWidth() / 2 * VaperioParams.scaleFactor);
        int yOrigin = (int) (VaperioParams.maxYCoordinate * 2 * VaperioParams.scaleFactor) - (int) s.y - (int) (collideable.getWidth() / 2 * VaperioParams.scaleFactor);
        int width = (int) (collideable.getWidth() * VaperioParams.scaleFactor);
        int height = (int) (collideable.getHeight() * VaperioParams.scaleFactor);
        g.fillRect(xOrigin, yOrigin, width ,height );
    }



    //paint score

    private void paintScore(Graphics2D g) {
        g.setColor(Color.white);
        int score = (int) gameState.getScore();
        String message = String.format("%d", score);
        scoreDraw.centreString(g, message, getWidth()/2, scoreFontSize);
    }







    //draw stars

    ArrayList<Star> stars = new ArrayList();

    private void paintStars(Graphics2D g) {
        for (Star star : stars) star.draw(g);
    }


    class Star {
        int x,y;
        double inc;
        double shine = params.getRandom().nextDouble();
        Star() {
            x = params.getRandom().nextInt(params.width);
            y = params.getRandom().nextInt(params.height);
            inc = 0.1 * (params.getRandom().nextDouble() + 1);
        }
        void draw(Graphics2D g) {
            shine += inc;
            float bright = (float) (1 + Math.sin(shine)) / 2;
            Color grey = new Color(bright, 1-bright, bright);
            g.setColor(grey);
            g.fillRect(x, y, 2, 2);
        }
    }


}
