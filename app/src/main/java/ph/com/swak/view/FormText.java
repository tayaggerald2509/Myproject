package ph.com.swak.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by SWAK-THREE on 3/21/2015.
 */
public class FormText extends EditText{

    public FormText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setError(CharSequence error, Drawable icon) {
        setCompoundDrawables(null, null, icon, null);
    }
}
