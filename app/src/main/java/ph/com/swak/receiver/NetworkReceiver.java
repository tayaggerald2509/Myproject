package ph.com.swak.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.ArrayList;
import java.util.List;

import ph.com.swak.callback.NetworkCallback;

/**
 * Created by SWAK-THREE on 4/1/2015.
 */
public class NetworkReceiver extends BroadcastReceiver {

    protected List<NetworkCallback> listeners;
    protected Boolean connected;

    public NetworkReceiver() {
        listeners = new ArrayList<NetworkCallback>();
        connected = null;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getExtras() == null)
            return;

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = manager.getActiveNetworkInfo();

        if (ni != null && ni.getState() == NetworkInfo.State.CONNECTED) {
            connected = true;
        } else if (intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, Boolean.FALSE)) {
            connected = false;
        }
        notifyStateToAll();
    }


    private void notifyStateToAll() {
        for (NetworkCallback listener : listeners)
            notifyState(listener);
    }

    private void notifyState(NetworkCallback listener) {
        if (connected == null || listener == null)
            return;

        if (connected == true)
            listener.networkAvailable();
        else
            listener.networkUnavailable();
    }

    public void addListener(NetworkCallback l) {
        listeners.add(l);
        notifyState(l);
    }

    public void removeListener(NetworkCallback l) {
        listeners.remove(l);
    }
}
