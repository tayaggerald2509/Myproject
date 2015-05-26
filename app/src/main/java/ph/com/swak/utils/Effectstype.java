package ph.com.swak.utils;

/**
 * Created by SWAK-THREE on 4/6/2015.
 */
public enum Effectstype {

    BounceUp(BounceInUpAnimator.class);

    private Class Effectstype;

    private Effectstype(Class effectstype) {
        Effectstype = effectstype;
    }

    public BaseEffects getAnimator() {
        try {
            return (BaseEffects) Effectstype.newInstance();
        } catch (Exception e) {
            throw new Error("Can not init animatorClazz instance");
        }
    }
}
