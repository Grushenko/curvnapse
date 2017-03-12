package com.bugfullabs.curvnapse.network.client;

import java.io.Serializable;

public class Game implements Serializable{
    private int mID;
    private String mName;
    private int mPlayers;
    private int mMaxPlayers;

    public Game() {
    }

    public int getID() {
        return mID;
    }

    public String getName() {
        return mName;
    }
}