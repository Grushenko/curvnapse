package com.bugfullabs.curvnapse.gui;

import com.bugfullabs.curvnapse.FlowManager;
import com.bugfullabs.curvnapse.game.Game;
import com.bugfullabs.curvnapse.powerup.PowerUp;
import com.bugfullabs.curvnapse.utils.ResourceManager;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GameListBox extends VBox {
    private ListView<Game> mGameList;
    private HBox mButtons;
    private Button mJoinButton;
    private Button mCreateButton;

    private GameListBoxListener mListener;

    public GameListBox() {
        super(5.0f);
        setPadding(new Insets(10.0f));
        setAlignment(Pos.CENTER);

        mGameList = new ListView<>();
        mGameList.setCellFactory(pGameListView -> new GameListElement());

        mButtons = new HBox(5.0f);
        mJoinButton = new Button("Join");
        mCreateButton = new Button("Create new");

        mButtons.setAlignment(Pos.CENTER);
        mButtons.getChildren().addAll(mJoinButton, mCreateButton);
        getChildren().addAll(mGameList, mButtons);

        mJoinButton.setOnAction(event -> {
            if (mListener != null)
                mListener.onJoin(mGameList.getSelectionModel().getSelectedItem());
        });

        mCreateButton.setOnAction(event -> {
            if (mListener != null)
                mListener.onCreate();
        });

        mJoinButton.setDisable(true);
        mGameList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null)
                mJoinButton.setDisable(true);
            else
                mJoinButton.setDisable(false);
        });

    }

    public void setGames(ObservableList<Game> pGames) {
        mGameList.setItems(pGames);
    }

    public void setListener(GameListBoxListener pListener) {
        mListener = pListener;
    }

    public interface GameListBoxListener {
        void onJoin(Game pGame);

        void onCreate();
    }

    private class GameListElement extends ListCell<Game> {
        GameListElement() {
            getStyleClass().add("game");
        }

        @Override
        public void updateItem(Game pGame, boolean pEmpty) {
            super.updateItem(pGame, pEmpty);
            if (pGame != null) {
                HBox box = new HBox(5.0f);
                box.setAlignment(Pos.CENTER_LEFT);
                Label name = new Label(pGame.getName());
                Label players = new Label(pGame.getPlayers().size() + "/" + pGame.getMaxPlayers());
                players.setMinWidth(40);
                players.setAlignment(Pos.CENTER_LEFT);
                box.getChildren().addAll(players, name);


                for (PowerUp.PowerType type : PowerUp.PowerType.values()) {
                    if (pGame.getPowerUps()[type.ordinal()]) {
                        ImageView v = new ImageView(ResourceManager.getInstance().getPowerUpImage(type));
                        v.setPreserveRatio(true);
                        v.setFitWidth(24);
                        v.setFitHeight(24);
                        box.getChildren().add(v);
                    }
                }
                setGraphic(box);
            }
        }
    }

}
