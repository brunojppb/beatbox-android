package bpaulino.com.br.beatbox.helper;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bpaulino.com.br.beatbox.model.Sound;

/**
 * Created by bruno on 12/9/15.
 */
public class BeatBox {

    private static final String TAG = "BeatBox";
    private static final String SOUND_FOLDER = "sample_sounds";
    private static final int MAX_SOUNDS = 5;

    private AssetManager mAssets;
    private List<Sound> mSounds;

    public BeatBox(Context context) {
        mAssets = context.getAssets();
        mSounds = new ArrayList<>();
        loadSounds();
    }

    private void loadSounds(){
        String[] soundNames;

        try {
            soundNames = mAssets.list(SOUND_FOLDER);
            Log.i(TAG, "Found " + soundNames.length + " sounds");
        }catch (IOException e) {
            Log.e(TAG, "Could not list assets", e);
            return;
        }

        for(String filename: soundNames) {
            String assetPath = SOUND_FOLDER + "/" + filename;
            Sound sound = new Sound(assetPath);
            mSounds.add(sound);
        }
    }

    public List<Sound> getSounds() { return mSounds; }
}
