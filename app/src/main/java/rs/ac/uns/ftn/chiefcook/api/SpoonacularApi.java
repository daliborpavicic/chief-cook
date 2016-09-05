package rs.ac.uns.ftn.chiefcook.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rs.ac.uns.ftn.chiefcook.BuildConfig;

/**
 * Created by daliborp on 20.8.16..
 */
public final class SpoonacularApi {

    private static final String BASE_API_URL = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com";

    // Define the interceptor, add API authentication headers
    private static Interceptor interceptor;

    private static OkHttpClient.Builder builder;
    private static OkHttpClient httpClient;

    static {
        interceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request newRequest = chain.request()
                        .newBuilder()
                        .addHeader("X-Mashape-Key", BuildConfig.SPOONACULAR_API_KEY)
                        .build();

                return chain.proceed(newRequest);
            }
        };

        builder = new OkHttpClient().newBuilder();

        // Add the interceptor to OkHttpClient
        builder.interceptors().add(interceptor);

        // Add interceptor to log HTTP traffic
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        builder.addInterceptor(logging);

        httpClient = builder.build();
    }


    private static final Retrofit RETROFIT = new Retrofit.Builder()
            .baseUrl(BASE_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient) // Set the custom client when building adapter
            .build();

    public static RecipesService getRecipesService() {
        return RETROFIT.create(RecipesService.class);
    }

}


