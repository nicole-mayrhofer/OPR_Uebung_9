package com.example.opr_uebung9;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class StatusBarController {


    // Zugriff auf LABELS aus der fxml
    @FXML private Label currentPlayerLabel;
    @FXML private Label statusLabel;

    // property binding für
    public void bindTo(Game game) {
        currentPlayerLabel.textProperty().bind(
                Bindings.createStringBinding(
                        () -> "Am Zug: " + game.currentTheSnakeProperty().get(),
                        game.currentTheSnakeProperty()
                )
        );

        statusLabel.textProperty().bind(
                Bindings.createStringBinding(
                        () -> switch (game.stateProperty().get()) {
                            case RUNNING -> "läuft";
                            case WON     -> game.winnerProperty().get() + " gewinnt!";
                            case DRAW    -> "Unentschieden";
                        },
                        game.stateProperty(), game.winnerProperty()
                )
        );
    }
}
