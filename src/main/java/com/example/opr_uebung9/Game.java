package com.example.opr_uebung9;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;

public class Game {

    public static final int SIZE = 3;  // spielfeldgrösse

    private final TheSnake[][] board = new TheSnake[SIZE][SIZE];

    // Wrapper um einen Wert auf ein Listener setzen
    private final ObjectProperty<TheSnake>           currentTheSnake = new SimpleObjectProperty<>(TheSnake.X);        // immer Spieler X beginng
    private final ReadOnlyObjectWrapper<TheSnake>    winner        = new ReadOnlyObjectWrapper<>(TheSnake.NONE);
    private final ReadOnlyObjectWrapper<GameState> state         = new ReadOnlyObjectWrapper<>(GameState.RUNNING);

    public Game() {
        reset();
    }

    public void reset() {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                board[r][c] = TheSnake.NONE;
            }
        }
        currentTheSnake.set(TheSnake.X);
        winner.set(TheSnake.NONE);
        state.set(GameState.RUNNING);
    }

    public boolean play(int row, int col) {
        if (state.get() != GameState.RUNNING)  return false;
        if (board[row][col] != TheSnake.NONE)    return false;

        board[row][col] = currentTheSnake.get();

        if (hasWon(currentTheSnake.get())) {
            winner.set(currentTheSnake.get());
            state.set(GameState.WON);
        } else if (isBoardFull()) {
            state.set(GameState.DRAW);
        } else {
            currentTheSnake.set(currentTheSnake.get().snake());
        }
        return true;
    }

    public TheSnake at(int row, int col) {
        return board[row][col];
    }

    ///  OBJECT WRAPPER --- wickelt "TheSnake" in object und ich kann den add listener drauf setzen
    public ObjectProperty<TheSnake>            currentTheSnakeProperty() { return currentTheSnake; }
    public ReadOnlyObjectProperty<TheSnake>    winnerProperty()        { return winner.getReadOnlyProperty(); }
    public ReadOnlyObjectProperty<GameState> stateProperty()        { return state.getReadOnlyProperty(); }

    private boolean isBoardFull() {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (board[r][c] == TheSnake.NONE) return false;
            }
        }
        return true;
    }

    private boolean hasWon(TheSnake p) {
        for (int i = 0; i < SIZE; i++) {
            if (board[i][0] == p && board[i][1] == p && board[i][2] == p) return true;
            if (board[0][i] == p && board[1][i] == p && board[2][i] == p) return true;
        }
        if (board[0][0] == p && board[1][1] == p && board[2][2] == p) return true;
        if (board[0][2] == p && board[1][1] == p && board[2][0] == p) return true;
        return false;
    }
}
