package ph.com.swak.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import ph.com.swak.fragment.BulletinActivity;
import ph.com.swak.fragment.BulletinFragment;
import ph.com.swak.utils.Constants;

/**
 * Created by SWAK-THREE on 4/6/2015.
 */
public class SwakActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int frIndex = getIntent().getIntExtra(Constants.Extra.FRAGMENT_INDEX, 0);
        Fragment fr = null;
        String tag = "";
        int titleRes;

        switch (frIndex) {
            default:
                break;

            case BulletinFragment.INDEX:
                tag = BulletinFragment.class.getSimpleName();
                fr = getSupportFragmentManager().findFragmentByTag(tag);
                if (fr == null) {
                    fr = new BulletinFragment();
                }
                break;
            case BulletinActivity.INDEX:
                tag = BulletinActivity.class.getSimpleName();
                fr = getSupportFragmentManager().findFragmentByTag(tag);
                if (fr == null) {
                    fr = new BulletinActivity();
                }
                break;
        }

        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, fr, tag).commit();
    }
}
