package com.olindena;

import com.olindena.entity.Cell;
import com.olindena.entity.Graph;
import com.olindena.entity.Side;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Stack;

public class Main {

    public static void main(String[] args) {
        Graph graph = new Graph(80);
        generateMaze(graph);
    }

    private static void generateMaze(Graph graph) {
        Stack<Cell> stack = new Stack<>();
        Cell start = graph.getStartCell();
        start.setVisited(true);
        stack.push(start);
        generateIteratively(stack);
        draw(graph, 8);
    }

    private static void generateIteratively(Stack<Cell> stack) {
        Cell current;
        while(!stack.empty()) {
            current = stack.pop();
            if(current.hasUnvisitedNeighbors()) {
                stack.push(current);
                Map.Entry<Side, Cell> chosen = current.getRandomUnvisited();
                chosen.getValue().deleteWall(chosen.getKey().getOpposite());
                current.deleteWall(chosen.getKey());
                chosen.getValue().setVisited(true);
                stack.push(chosen.getValue());
            }
        }
    }

    private static void draw(Graph graph, int cellSize) {
        JFrame frame = new JFrame();
        int widthSize = cellSize*graph.getWidth();
        int heightSize = cellSize*graph.getHeight();
        frame.setSize(widthSize, heightSize);
        DisplayGraph dg = new DisplayGraph(graph, cellSize, widthSize);
        frame.add(dg);
        frame.setVisible(true);
        save(frame);
    }
    private static void save(JFrame dPanel)
    {
        BufferedImage bImg = new BufferedImage(dPanel.getWidth(), dPanel.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D cg = bImg.createGraphics();
        dPanel.paintAll(cg);
        try {
            if (ImageIO.write(bImg, "png", new File("./output_image.png")))
            {
                System.out.println("-- saved");
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
