package com.olindena.entity;

public enum Side {
    TOP,
    RIGHT,
    BOT,
    LEFT;

    public Side getOpposite() {
        switch (this) {
            case TOP:
                return BOT;
            case RIGHT:
                return LEFT;
            case BOT:
                return TOP;
            case LEFT:
                return RIGHT;
        }
        return this;
    }
}
