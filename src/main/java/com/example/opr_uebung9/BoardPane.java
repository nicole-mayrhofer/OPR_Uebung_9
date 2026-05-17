package com.example.opr_uebung9;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class BoardPane extends GridPane {       // grid pane klasse für den spiele-raster

    private final Game       game;      // konstruktur beinhaltet ein neues game (aus der game logik)
    private final Button[][] cells = new Button[Game.SIZE][Game.SIZE];  // in jeder zelle ist ein button

    ///------ KONSTRUKTOR ------------------------------------------------------

    public BoardPane(Game game) {
        this.game = game;
        getStyleClass().add("board");

        // iterieren über alle buttons, stylen, größe --- beim klick wird PLAY methode für den jeweiligen button aufgerufen
        for (int r = 0; r < Game.SIZE; r++) {
            for (int c = 0; c < Game.SIZE; c++) {
                final int row = r, col = c;
                Button cell = new Button();
                cell.getStyleClass().add("cell");
                cell.setPrefSize(110, 110);
                cell.setOnAction(e -> game.play(row, col));
                cells[r][c] = cell;
                add(cell, c, r);
            }
        }
        ///  LISTENER hänge ich im Konstruktor schon überall drauf
        game.currentTheSnakeProperty().addListener((o, a, b) -> refresh());
        game.stateProperty().addListener((o, a, b) -> refresh());
        refresh();
    }

    ///------ KONSTRUKTOR ENDE ------------------------------------------------------




    public void refresh() {
        for (int r = 0; r < Game.SIZE; r++) {
            for (int c = 0; c < Game.SIZE; c++) {
                TheSnake p   = game.at(r, c);
                Button btn = cells[r][c];

                btn.setText(p == TheSnake.NONE ? "" : p.name());
                btn.getStyleClass().removeAll("cell-x", "cell-o");
                if (p == TheSnake.X) btn.getStyleClass().add("cell-x");
                if (p == TheSnake.O) btn.getStyleClass().add("cell-o");

                btn.setDisable(p != TheSnake.NONE
                        || game.stateProperty().get() != GameState.RUNNING);
            }
        }
    }
}
