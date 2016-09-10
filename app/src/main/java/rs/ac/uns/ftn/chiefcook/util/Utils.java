package rs.ac.uns.ftn.chiefcook.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import rs.ac.uns.ftn.chiefcook.R;
import rs.ac.uns.ftn.chiefcook.model.AutocompleteRecipeSearchModel;

/**
 * Created by daliborp on 26.8.16..
 */
public class Utils {

    public static List<String> mapToStringList(List<AutocompleteRecipeSearchModel> recipeAutocompletes) {
        return mapToStringList(recipeAutocompletes, true);
    }

    public static List<String> mapToStringList(List<AutocompleteRecipeSearchModel> recipeAutocompletes, boolean removeDuplicates) {
        List<String> mappedList = new ArrayList<>();

        for (AutocompleteRecipeSearchModel recipeAutocomplete :
                recipeAutocompletes) {
            mappedList.add(recipeAutocomplete.getTitle());
        }

        if (removeDuplicates) {
            Set<String> withoutDuplicates = new LinkedHashSet<>(mappedList);
            mappedList = new ArrayList<>(withoutDuplicates);
        }

        return mappedList;
    }

    public static boolean isSearchSuggestionsEnabled(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(
                context.getString(R.string.pref_enable_search_suggestions_key),
                Boolean.valueOf(context.getString(R.string.pref_enable_search_suggestions_default))
        );
    }
}
