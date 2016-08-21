package rs.ac.uns.ftn.chiefcook.model;

import java.util.List;

/**
 * Created by daliborp on 20.8.16..
 */
public class RecipesListResponse {

    private List<Recipe> results;

    public List<Recipe> getResults() {
        return results;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Recipe recipe :
                results) {
            sb.append(recipe.toString());
        }

        return sb.toString();
    }
}
