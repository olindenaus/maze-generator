package com.olindena.entity;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Cell {
    private int id;
    private int widthPosition;
    private int heightPosition;
    private boolean visited;
    private HashMap<Side, Cell> neighbors = new HashMap<>();
    private HashMap<Side, Boolean> walls = new HashMap<>() {
        {
            put(Side.BOT, true);
            put(Side.LEFT, true);
            put(Side.TOP, true);
            put(Side.RIGHT, true);
        }
    };

    public Cell(Builder builder) {
        this.id = builder.id;
        this.heightPosition = builder.heightPosition;
        this.widthPosition = builder.widthPosition;
    }

    public int getId() {
        return id;
    }

    public int getWidthPosition() {
        return widthPosition;
    }

    public int getHeightPosition() {
        return heightPosition;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean hasUnvisitedNeighbors() {
        return neighbors.entrySet().stream().anyMatch(a -> !a.getValue().visited);
    }

    public boolean addNeighbor(Side side, Cell cell) {
        if(neighbors.containsKey(side))
            return false;
        neighbors.put(side, cell);
        return true;
    }

    public Map.Entry<Side, Cell> getRandomUnvisited() {
        return neighbors.entrySet().stream()
                .filter(neighbor -> !neighbor.getValue().visited)
                .sorted((o1, o2) -> ThreadLocalRandom.current().nextInt(-1,2))
                .findAny().orElseGet(null);
    }

    public void deleteWall(Side side) {
        walls.put(side, false);
    }

    public List<Side> getWalls() {
        return walls.entrySet().stream()
                .filter(Map.Entry::getValue)
                .map(Map.Entry::getKey).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "Cell-"+id+":" +
                "(" + widthPosition +
                "," + heightPosition +
                ')';
    }

    public static class Builder {
        private int id;
        private int widthPosition;
        private int heightPosition;


        public Builder withId(int id) {
            this.id = id;
            return this;
        }

        public Builder withWidthPosition(int widthPos) {
            this.widthPosition = widthPos;
            return this;
        }

        public Builder withHeightPosition(int heightPost) {
            this.heightPosition = heightPost;
            return this;
        }

        public Cell build() {
            return new Cell(this);
        }
    }
}
