package ph.com.swak.auth;

import retrofit.Callback;
import retrofit.RetrofitError;

/**
 * Created by SWAK-THREE on 3/25/2015.
 */
public abstract class RestCallback<T> implements Callback<T> {

    public abstract void failure(RestError error);

    @Override
    public void failure(RetrofitError error) {
        RestError restError = (RestError) error.getBodyAs(RestError.class);

        if (restError != null)
            failure(restError);
        else {
            failure(new RestError(error.getMessage()));
        }
    }
}
