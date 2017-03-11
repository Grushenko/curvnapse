package com.bugfullabs.curvnapse.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.logging.Logger;

public class Server extends Thread {
    private static final Logger LOG = Logger.getLogger(Server.class.getName());

    private ServerSocket mServerSocket;

    private LinkedList<GameLobby> mLobbies;
    private LinkedList<GameThread> mGameThreads;
    private MessageDispatcher mMessageDispatcher;

    public Server(int pPort, int pMaxGames) throws IOException {
        mServerSocket = new ServerSocket(pPort);
        mMessageDispatcher = new MessageDispatcher();

        mLobbies = new LinkedList<>();
        mGameThreads = new LinkedList<>();
        mMessageDispatcher.start();
    }

    public void close() {
        try {
            mServerSocket.close();
            LOG.info("Closed server socket");
        } catch (Exception e) {
            LOG.warning("Could not stop server socket");
        }
    }

    @Override
    public void run() {
        Socket clientSocket;
        LOG.info("Accepting connections...");
        while (!mServerSocket.isClosed()) {
            try {
                clientSocket = mServerSocket.accept();
                mMessageDispatcher.registerClient(new Client(clientSocket));
                LOG.info("Connection from " + clientSocket.getInetAddress());
            } catch (IOException e) {
                System.out.print("Could not accept client: " + e.getMessage());
            }
        }
    }
}

