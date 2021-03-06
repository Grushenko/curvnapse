package com.bugfullabs.curvnapse.game;

import com.bugfullabs.curvnapse.utils.MathUtils;
import com.bugfullabs.curvnapse.utils.SerializableColor;
import com.bugfullabs.curvnapse.powerup.PowerUp;
import com.bugfullabs.curvnapse.utils.ColorBank;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents all settings of the game
 */
public class Game implements Serializable, Cloneable {
    private static int UID = 0;

    private int mID;
    private String mName;
    private int mMaxPlayers;
    private int mHostID;
    private ArrayList<Player> mPlayers;
    private transient ColorBank mColorBank;

    private boolean[] mPowerUps;
    private int mBoardWidth;
    private int mBoardHeight;

    private int mRounds;

    /**
     * Create game object
     *
     * @param pName       Game name
     * @param pHostID     Host client ID
     * @param pRounds     number of rounds
     * @param pMaxPlayers max number of players
     */
    public Game(String pName, int pHostID, int pRounds, int pMaxPlayers) {
        mID = UID;
        UID += 1;
        mName = pName;
        mHostID = pHostID;
        mMaxPlayers = pMaxPlayers;
        mRounds = pRounds;
        mPlayers = new ArrayList<>(mMaxPlayers);
        mColorBank = new ColorBank();

        mBoardWidth = 500;
        mBoardHeight = 500;
        mPowerUps = new boolean[PowerUp.PowerType.values().length];
        for (int i = 0; i < mPowerUps.length; i++)
            mPowerUps[i] = false;
    }

    public Game(Game pGame) {
        mID = pGame.mID;
        mName = pGame.mName;
        mHostID = pGame.mHostID;
        mMaxPlayers = pGame.mMaxPlayers;
        mRounds = pGame.mRounds;
        mPlayers = new ArrayList<>();
        mPlayers.addAll(pGame.mPlayers);
        mColorBank = pGame.mColorBank;

        mBoardWidth = pGame.mBoardWidth;
        mBoardHeight = pGame.mBoardHeight;
        mPowerUps = new boolean[PowerUp.PowerType.values().length];
        System.arraycopy(pGame.mPowerUps, 0, mPowerUps, 0, pGame.mPowerUps.length);
    }

    public int getID() {
        return mID;
    }

    public String getName() {
        return mName;
    }

    public List<Player> getPlayers() {
        return mPlayers;
    }

    public int getHostID() {
        return mHostID;
    }

    public void setName(String pName) {
        mName = pName;
    }

    public int getBoardWidth() {
        return mBoardWidth;
    }

    public int getBoardHeight() {
        return mBoardHeight;
    }

    public int getMaxPlayers() {
        return mMaxPlayers;
    }

    public boolean[] getPowerUps() {
        return mPowerUps;
    }

    public int getRounds() {
        return mRounds;
    }

    public void setRounds(int pRounds) {
        mRounds = pRounds;
    }


    /**
     * Adds player to the game
     *
     * @param pName  Player name
     * @param pOwner owner client ID
     * @return Created {@link Player} or null if cannot add
     */
    public Player addPlayer(String pName, int pOwner) {
        if (mPlayers.size() < mMaxPlayers) {
            SerializableColor color = mColorBank.nextColor();
            Player player = new Player(pName, color, pOwner);
            mPlayers.add(player);
            return player;
        }
        return null;
    }

    /**
     * Decide if given {@link PowerUp} ws activated
     *
     * @param pType    {@link PowerUp.PowerType} type of powerup
     * @param pEnabled enabled?
     */
    public void setPowerUpEnabled(PowerUp.PowerType pType, boolean pEnabled) {
        mPowerUps[pType.ordinal()] = pEnabled;
    }

    public void setHostID(int pID) {
        mHostID = pID;
    }

    public ColorBank getColorBank() {
        return mColorBank;
    }

    /**
     * Check if there are any PowerUps enabled
     *
     * @return true if one or more PowerUps are enabled
     */
    public boolean anyPowerUps() {
        for (boolean b : mPowerUps)
            if (b) return true;
        return false;
    }
}
