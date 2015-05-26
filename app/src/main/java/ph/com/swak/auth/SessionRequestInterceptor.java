package ph.com.swak.auth;

import retrofit.RequestInterceptor;

/**
 * Created by SWAK-THREE on 3/25/2015.
 */
public class SessionRequestInterceptor implements RequestInterceptor{

    @Override
    public void intercept(RequestFacade request) {
        // request.addHeader("Content-Type", "application/json");//you can add header here if you need in your api
    }
}
