package rs.ac.uns.ftn.chiefcook.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daliborp on 20.8.16..
 */
public class RecipesListResponse {

    private List<Recipe> results = new ArrayList<>();

    private String baseUri;

    public List<Recipe> getResults() {
        return results;
    }

    public void setResults(List<Recipe> results) {
        this.results = results;
    }

    public String getBaseUri() {
        return baseUri;
    }

    public void setBaseUri(String baseUri) {
        this.baseUri = baseUri;
    }

    public String buildImageUrl(String relativeImageUrl) {
        return baseUri + relativeImageUrl;
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
