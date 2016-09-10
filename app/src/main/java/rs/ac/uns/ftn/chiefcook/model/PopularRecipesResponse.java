package rs.ac.uns.ftn.chiefcook.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daliborp on 20.8.16..
 */
public class PopularRecipesResponse {

    private List<Recipe> recipes = new ArrayList<>();

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
}
