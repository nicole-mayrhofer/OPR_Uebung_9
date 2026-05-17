package com.example.opr_uebung9;

public enum TheSnake {
    X, O, NONE;

    public TheSnake snake() {
        if (this == X) return O;
        if (this == O) return X;
        return NONE;
    }
}
