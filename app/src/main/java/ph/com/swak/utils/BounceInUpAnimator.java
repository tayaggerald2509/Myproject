package ph.com.swak.utils;

import android.view.View;

import com.nineoldandroids.animation.ObjectAnimator;

/**
 * Created by SWAK-THREE on 4/6/2015.
 */
public class BounceInUpAnimator extends BaseEffects {

    @Override
    protected void setupAnimation(View target) {
        getAnimatorSet().playTogether(
                ObjectAnimator.ofFloat(target, "translationY", target.getMeasuredHeight(), -30, 10, 0),
                ObjectAnimator.ofFloat(target, "alpha", 0, 1, 1, 1)
        );
    }
}
