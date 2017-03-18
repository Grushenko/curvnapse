package com.bugfullabs.curvnapse.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;

public class ToggleImageButton extends Button {
    private ImageView mImage;
    private boolean mIsActive;
    private ColorAdjust mColorAdjust;
    private OnToogleListener mListener;

    public ToggleImageButton() {
        mImage = new ImageView();
        mColorAdjust = new ColorAdjust();
        mImage.setEffect(mColorAdjust);
        mIsActive = true;
        setGraphic(mImage);
        setOnAction(pActionEvent -> {
            if (mIsActive) {
                mIsActive = false;
                mColorAdjust.setBrightness(-0.5f);
            } else {
                mIsActive = true;
                mColorAdjust.setBrightness(0.0f);
            }
            if(mListener != null)
                mListener.onToogle(mIsActive);
        });
    }

    public void setOnToogleListener(OnToogleListener pListener) {
        mListener = pListener;
    }

    public interface OnToogleListener {
        void onToogle(boolean pIsActive);
    }

}