package ph.com.swak.auth;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ph.com.swak.utils.Constants;
import retrofit.RestAdapter;

/**
 * Created by SWAK-THREE on 3/25/2015.
 */
public class RestClient {

    private static RestApi REST_CLIENT;

    static {
        setupRestClient();
    }

    public static RestApi get() {
        return REST_CLIENT;
    }

    private static void setupRestClient() {
        Gson gson = new GsonBuilder().create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.URL)
                //.setConverter(new GsonConverter(gson))
                //.setRequestInterceptor(new SessionRequestInterceptor())
                .build();

        REST_CLIENT = restAdapter.create(RestApi.class);
    }
}
