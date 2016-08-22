package rs.ac.uns.ftn.chiefcook.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rs.ac.uns.ftn.chiefcook.model.Recipe;
import rs.ac.uns.ftn.chiefcook.model.RecipesListResponse;

/**
 * Created by daliborp on 20.8.16..
 */
public interface RecipesService {

    @GET("/recipes/search")
    public Call<RecipesListResponse> searchRecipes(@Query("query") String query,
                                                   @Query("number") Integer number,
                                                   @Query("type") String type,
                                                   @Query("cuisine") String cuisine,
                                                   @Query("diet") String diet,
                                                   @Query("excludeIngredients") String excludeIngredients,
                                                   @Query("intolerances") String intolerances,
                                                   @Query("limitLicense") Boolean limitLicense,
                                                   @Query("offset") Integer offset);

    @GET("recipes/{id}/information")
    public Call<Recipe> getRecipeInfo(@Path("id") Integer recipeId);
}