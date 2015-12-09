package bpaulino.com.br.beatbox.helper;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
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
    private static final int SOUND_PRIORITY = 1;
    private static final int SOUND_LOOP = 0;
    private static final float SOUND_VOLUME = 1.0f;
    private static final float SOUND_RATE = 1.0f;

    private AssetManager mAssets;
    private List<Sound> mSounds;
    private SoundPool mSoundPool;

    public BeatBox(Context context) {
        mAssets = context.getAssets();
        mSounds = new ArrayList<>();

        mSoundPool = new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0);

        loadSounds();
    }

    // Load all sounds and add them to the sound pool
    // The sound pool will help up to to manage and play the sounds
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
            try {
                String assetPath = SOUND_FOLDER + "/" + filename;
                Sound sound = new Sound(assetPath);
                loadSound(sound);
                mSounds.add(sound);
            }catch (IOException e) {
                Log.e(TAG, "Could not load sound " + filename);
            }
        }
    }

    // It will in deed load the specific sound in the Sound Pool
    private void loadSound(Sound sound) throws IOException {
        AssetFileDescriptor fileDescriptor = mAssets.openFd(sound.getAssetPath());
        int soundId = mSoundPool.load(fileDescriptor, SOUND_PRIORITY);
        sound.setSoundId(soundId);
    }

    public void playSound(Sound sound) {
        Integer soundId = sound.getSoundId();
        if(soundId == null) return;
        mSoundPool.play(soundId, SOUND_VOLUME, SOUND_VOLUME, SOUND_PRIORITY, SOUND_LOOP, SOUND_RATE);
    }

    // Clean up the sound pool when we have done
    public void release() {
        mSoundPool.release();
    }

    public List<Sound> getSounds() { return mSounds; }
}
