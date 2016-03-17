package com.hewuzhe.ui.activity;

import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public abstract class BaseActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);// 使得音量键控制媒体声音

    }


}
