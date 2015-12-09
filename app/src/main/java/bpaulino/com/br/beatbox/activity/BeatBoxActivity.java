package bpaulino.com.br.beatbox.activity;

import android.support.v4.app.Fragment;

import bpaulino.com.br.beatbox.fragment.BeatBoxFragment;

public class BeatBoxActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return BeatBoxFragment.newInstance();
    }
}
