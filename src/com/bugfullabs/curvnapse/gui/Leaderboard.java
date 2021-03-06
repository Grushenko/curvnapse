package com.bugfullabs.curvnapse.gui;


import com.bugfullabs.curvnapse.game.Player;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Control class for Leaderboard
 */
public class Leaderboard extends VBox {
    private ListView<Player> mPlayersList;

    /**
     * Create new leaderboard
     */
    public Leaderboard() {
        super(10.0f);
        setPadding(new Insets(10.f));
        setAlignment(Pos.TOP_CENTER);

        Label title = new Label("Leaderboard");
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 2em");

        mPlayersList = new ListView<>();
        mPlayersList.setCellFactory(param -> new PlayersLeaderboardListElement());
        getChildren().addAll(title, mPlayersList);
        setMaxWidth(200);
    }

    /**
     * set observable source of players {@link ObservableList}
     *
     * @param pPlayers players to be displayed
     */
    public void setPlayers(ObservableList<Player> pPlayers) {
        mPlayersList.setItems(pPlayers);
    }

    /**
     * Leaderboard list cell element
     */
    private class PlayersLeaderboardListElement extends ListCell<Player> {
        @Override
        public void updateItem(Player pPlayer, boolean pEmpty) {
            super.updateItem(pPlayer, pEmpty);
            HBox box = new HBox(10.0f);
            box.setAlignment(Pos.CENTER_LEFT);
            if (pPlayer != null) {
                Label points = new Label(Integer.toString(pPlayer.getPoints()));
                points.setStyle("-fx-background-color: " + pPlayer.getColor().toHex() + "; -fx-font-weight: bold; -fx-font-family: \"Segoe UI Bold\"");
                points.setMinHeight(40);
                points.setMinWidth(40);
                points.setAlignment(Pos.CENTER);
                Label label = new Label(pPlayer.getName());
                label.setTextFill(pPlayer.getColor().toFXColor());
                box.getChildren().addAll(points, label);
            }
            setGraphic(box);
        }
    }
}
