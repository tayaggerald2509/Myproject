package ph.com.swak.callback;

import android.view.View;

/**
 * Created by SWAK-THREE on 4/7/2015.
 */
public interface DialogCallback {

    public void positiveSelectionCallback(View v);
    public void negativeSelectionCallback(View v);
    public void neutralSelectionCallback(View v);

}
