package rs.ac.uns.ftn.chiefcook.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rs.ac.uns.ftn.chiefcook.model.AutocompleteRecipeSearchModel;
import rs.ac.uns.ftn.chiefcook.model.Recipe;
import rs.ac.uns.ftn.chiefcook.model.RecipeInstructionsItem;
import rs.ac.uns.ftn.chiefcook.model.RecipesListResponse;

/**
 * Created by daliborp on 20.8.16..
 */
public interface RecipesService {

    @GET("/recipes/search")
    Call<RecipesListResponse> searchRecipes(@Query("query") String query,
                                            @Query("number") Integer numberOfResults,
                                            @Query("offset") Integer offset,
                                            @Query("type") String type,
                                            @Query("cuisine") String cuisine,
                                            @Query("diet") String diet,
                                            @Query("excludeIngredients") String excludeIngredients,
                                            @Query("intolerances") String intolerances,
                                            @Query("limitLicense") Boolean limitLicense);

    @GET("recipes/{id}/information")
    Call<Recipe> getRecipeInfo(@Path("id") Integer recipeId);

    @GET("recipes/{id}/analyzedInstructions")
    Call<List<RecipeInstructionsItem>> getAnalyzedRecipeInstructions(@Path("id") Integer recipeId);


    @GET("recipes/autocomplete")
    Call<List<AutocompleteRecipeSearchModel>> autocompleteRecipeSearch(@Query("query") String query,
                                                                       @Query("number") Integer number);

}