package com.olindena;

import com.olindena.entity.Cell;
import com.olindena.entity.Graph;
import com.olindena.entity.Side;

import java.awt.*;

public class DisplayGraph extends Canvas {
    private Graph graph;
    private int cellSize;
    private int frameSize;

    public DisplayGraph(Graph g, int cellSize, int frameSize) {
        this.graph = g;
        this.cellSize = cellSize;
        this.frameSize = frameSize;
    }

    public void paint(Graphics g) {
        g.drawRect(0, 0, frameSize, frameSize);
        for (Cell c : graph.getCells()) {
            int x = c.getWidthPosition() * cellSize;
            int y = c.getHeightPosition() * cellSize;
            if (c.getId() == graph.getStartCellId()) {
                g.setColor(Color.CYAN);
                g.fillRect(x + cellSize, getFrameYCord(y + cellSize), cellSize, cellSize);
            } else if (c.getId() == graph.getEndCellId()) {
                g.setColor(Color.CYAN);
                g.fillRect(x + cellSize, getFrameYCord(y + cellSize), cellSize, cellSize);
            }
            g.setColor(Color.BLACK);
            for (Side wall : c.getWalls()) {
                int newY = getFrameYCord(y + cellSize);
                if (wall == Side.BOT)
                    g.drawLine(x, getFrameYCord(y), x + cellSize, getFrameYCord(y));
                if (wall == Side.TOP)
                    g.drawLine(x, newY, x + cellSize, newY);
                if (wall == Side.LEFT)
                    g.drawLine(x, getFrameYCord(y), x, newY);
                if (wall == Side.RIGHT)
                    g.drawLine(x + cellSize, getFrameYCord(y), x + cellSize, newY);
            }
        }
    }

    private int getFrameYCord(int y) {
        return this.frameSize - y;
    }
}
