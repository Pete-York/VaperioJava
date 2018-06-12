package vaperio.view;

import caveswing.view.CaveView;
import vaperio.core.VaperioParams;
import vaperio.core.VaperioGameState;
import spinbattle.util.DrawUtil;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class VaperioView extends JComponent {
    VaperioParams params;
    VaperioGameState gameState;
    Color bg = Color.PINK;
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

    public void paintComponent(Graphics go) {
        Graphics2D g = (Graphics2D) go;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.fillRect(0, 0, getWidth(), getHeight());
/*
        double xScroll = -gameState.spaceship.s.x + scrollWidth/2;
        if (scrollView) {
            g.translate(xScroll, 0);
        }
*/
        paintStars(g);
/*
        paintAnchors(g);
        paintAvatar(g);


        if (scrollView) {
            g.translate(-xScroll, 0);
        }
*/
        // have to paint the score last so that it is not obscured by any game objects
        paintScore(g);
        nPaints++;
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
