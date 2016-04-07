package com.TMAProject.EzzSteel.Activities.Base.Parameters;

/**
 * Created by TarekkMA on 4/1/16.
 */
public class BackButtonInfo{
    public boolean exists = false;
    public Class previousAcitivity = null;

    public BackButtonInfo(boolean exists) {
        this.exists = exists;
    }

    public BackButtonInfo(boolean exists, Class previousAcitivity) {
        this.exists = exists;
        this.previousAcitivity = previousAcitivity;
    }
}