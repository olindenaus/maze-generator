package com.olindena.entity;

import java.util.*;

public class Graph {
    private int height;
    private int width;
    private int startCell;
    private int endCell;
    private List<Cell> cells = new ArrayList<>();

    public Graph(int size) {
        this.height = size;
        this.width = size;
        initGraph();
    }

    public Graph(int width, int height) {
        this.width = width;
        this.height = height;
        initGraph();
    }

    private void initGraph() {
        generateCells();
        linkNeighbors();
        setStartAndEnd();
    }

    private void generateCells() {
        int counter = 1;
        for (int h = 0; h < this.height; h++) {
            for (int w = 0; w < this.width; w++) {
                cells.add(new Cell.Builder()
                        .withId(counter)
                        .withHeightPosition(h)
                        .withWidthPosition(w).build());
                counter++;
            }
        }
    }

    private Cell getCell(int width, int height) {
        return cells.stream().filter(cell -> cell.getWidthPosition() == width)
                .filter(cell -> cell.getHeightPosition() == height).findFirst().get();
    }

    private void linkNeighbors() {
        Cell current;
        for (int h = 0; h < this.height; h++) {
            for (int w = 0; w < this.width; w++) {
                HashSet<Side> neighborsToAdd = new HashSet<>() {{
                    add(Side.TOP);
                    add(Side.RIGHT);
                    add(Side.BOT);
                    add(Side.LEFT);
                }};
                current = getCell(w, h);
                if (h == 0)
                    neighborsToAdd.remove(Side.BOT);
                if (w == 0)
                    neighborsToAdd.remove(Side.LEFT);
                if (h == this.height - 1)
                    neighborsToAdd.remove(Side.TOP);
                if (w == this.width - 1)
                    neighborsToAdd.remove(Side.RIGHT);
                addNeighbors(current, neighborsToAdd);
            }
        }
    }

    private void addNeighbors(Cell cell, HashSet<Side> neighborsToAdd) {
        for (Side side:neighborsToAdd) {
            int width = cell.getWidthPosition();
            int height = cell.getHeightPosition();
            switch (side) {
                case TOP -> height = cell.getHeightPosition() + 1;
                case RIGHT -> width = cell.getWidthPosition() + 1;
                case BOT -> height = cell.getHeightPosition() - 1;
                case LEFT -> width = cell.getWidthPosition() - 1;
            }
            Cell neighbor = getCell(width, height);
            cell.addNeighbor(side, neighbor);
        }
    }

    private void setStartAndEnd() {
        Random r = new Random();
        startCell = r.nextInt(cells.size());
        int end = r.nextInt(cells.size());
        while(end==startCell) {
            end = r.nextInt(cells.size());
        }
        endCell = end;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public Cell getStartCell() {
        return cells.get(startCell);
    }

    public int getStartCellId() {
        return startCell;
    }

    public int getEndCellId() {
        return endCell;
    }
}
