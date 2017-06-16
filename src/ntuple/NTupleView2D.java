package ntuple;

import bandits.BanditEA;
import math.BanditEquations;
import math.Vector2d;
import ropegame.RopeGameState;
import utilities.StatSummary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

/**
 * Created by sml on 22/05/2017.
 */

public class NTupleView2D extends JComponent {


    static int cellSize = 100;
    double fill = 0.9;

    NTupleBanditEA banditEA;

    public NTupleView2D(NTupleBanditEA banditEA, int m) {
        this.banditEA = banditEA;
        this.m = m;
    }

    NTuple xn, yn, xyn;
    int m;


//    public NTupleView2D(NTuple xn, NTuple yn, NTuple xyn, int m) {
//        this.xn = xn;
//        this.yn = yn;
//        this.xyn = xyn;
//        this.m = m;
//    }

    public Dimension getPreferredSize() {
        return new Dimension(cellSize * (m + 1), cellSize * (m + 1));
    }

    static Color bg = Color.black;

    public void paintComponent(Graphics gx) {
        setupNTuples();
        Graphics2D g = (Graphics2D) gx;
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setColor(bg);
        g.fillRect(0, 0, getWidth(), getHeight());

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                int[] p = new int[]{i, j};
                StatSummary ss = xyn.getStats(p);
                drawCell(g, ss, i, j);
            }
        }
    }

    private void setupNTuples() {
        System.out.println(banditEA.nTupleSystem);
        try {
            xn = banditEA.nTupleSystem.tuples.get(0);
            yn = banditEA.nTupleSystem.tuples.get(1);
            xyn = banditEA.nTupleSystem.tuples.get(2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void drawCell(Graphics2D g, StatSummary ss, int i, int j) {
        AffineTransform affineTransform = g.getTransform();
        g.translate(i * cellSize, j * cellSize);
        double offset = cellSize * (1 - fill) / 2;
        g.translate(offset, offset);
        g.scale(fill, fill);
        g.setColor(Color.YELLOW);
        Path2D.Double path = new Path2D.Double();
        path.append(new Rectangle2D.Double(0, 0, cellSize, cellSize), false);
        g.fill(path);
        if (ss != null) {
            float v = (float) BanditEquations.sigmoid(ss.mean()); //  Math.max(0, Math.min((float) ss.mean(), 1));
            g.setColor(Color.getHSBColor(v, 1, 1));
            g.fill(path);
            String statString = String.format("%d: %.2f", ss.n(), ss.mean());
            centreString(g, cellSize / 2, cellSize / 2, statString, 16);
            String ucbString = ucbString(ss);
            centreString(g, cellSize/2, cellSize * 0.8f, ucbString, 14);
        } else {
            System.out.println("Null " + ss);
            g.setColor(Color.gray);
            g.fill(path);
        }
        String cell = String.format("[%d,%d]", i, j);
        centreString(g, cellSize / 2, cellSize / 10, cell, 12);
        g.setTransform(affineTransform);
    }

    private void centreString(Graphics2D g, float x, float y, String str, int fontSize) {
        // Font font = Font.getFont(Font.MONOSPACED);

        Font font = new Font("Lucida Console", Font.PLAIN, fontSize);
        FontMetrics fm = g.getFontMetrics(font);

        int textWidth = fm.stringWidth(str);
        int textHeigth = fm.getHeight();
        int yOff = fm.getDescent();

        g.setFont(font);
        g.setColor(Color.black);
        // g.scale(3,3);
        g.drawString(str, x - textWidth / 2, y + yOff);
    }

    private String ucbString(StatSummary ss) {
        return String.format("ucb: %.2f", BanditEquations.UCB(ss.mean(), ss.n(), banditEA.evaluator.nEvals()));
    }

}
