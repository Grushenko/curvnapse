package com.bugfullabs.curvnapse.scene;

import com.bugfullabs.curvnapse.gui.GameOptionsBox;
import com.bugfullabs.curvnapse.gui.MessageBox;
import com.bugfullabs.curvnapse.gui.PlayersBox;
import com.bugfullabs.curvnapse.network.client.ServerConnector;
import com.bugfullabs.curvnapse.network.message.Message;
import com.bugfullabs.curvnapse.network.message.TextMessage;
import com.bugfullabs.curvnapse.player.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.util.Observable;
import java.util.logging.Logger;


public class GameLobbyScene implements ServerConnector.MessageListener {
    private static final Logger LOG = Logger.getLogger(GameLobbyScene.class.getName());
    private BorderPane mRoot;
    private Scene mScene;

    private MessageBox mMessageBox;
    private GameOptionsBox mGameOptionsBox;
    private PlayersBox mPlayersBox;
    private HBox mButtons;
    private Button mBackButton;
    private Button mStartButton;

    private ServerConnector mConnector;
    private ObservableList<Player> mPlayers;

    public GameLobbyScene(ServerConnector pConnector) {

        mConnector = pConnector;

        mRoot = new BorderPane();
        mScene = new Scene(mRoot);

        mMessageBox = new MessageBox();
        mGameOptionsBox = new GameOptionsBox();
        mPlayersBox = new PlayersBox();
        mButtons = new HBox(5.0f);

        mBackButton = new Button("Back");
        mStartButton = new Button("Start");

        mButtons.setAlignment(Pos.CENTER);
        mButtons.getChildren().addAll(mBackButton, mStartButton);

        mRoot.setLeft(mMessageBox);
        mRoot.setCenter(mGameOptionsBox);
        mRoot.setRight(mPlayersBox);
        mRoot.setBottom(mButtons);
        mRoot.setPadding(new Insets(10.0f));
        mConnector.registerListener(this);

        mMessageBox.setSendListener(pMessage -> mConnector.sendMessage(new TextMessage("MS", pMessage)));

        mPlayers = FXCollections.observableArrayList();
        mPlayers.add(new Player("asd", Color.AQUA, true));
        mPlayersBox.setPlayersList(mPlayers);
    }

    public Scene getScene() {
        return mScene;
    }

    @Override
    public void onClientMessage(Message pMessage) {
        switch (pMessage.getType()) {
            case TEXT:
                mMessageBox.addMessage((TextMessage) pMessage);
                break;
            default:
                break;
        }
    }
}