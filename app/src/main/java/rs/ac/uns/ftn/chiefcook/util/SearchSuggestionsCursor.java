package rs.ac.uns.ftn.chiefcook.util;

import android.app.SearchManager;
import android.database.MatrixCursor;
import android.provider.BaseColumns;

import java.util.List;

/**
 * Created by daliborp on 2.9.16..
 */
public class SearchSuggestionsCursor extends MatrixCursor {

    public static final String[] SEARCH_SUGGESTION_COLUMNS = new String[]{
            BaseColumns._ID,
            SearchManager.SUGGEST_COLUMN_TEXT_1,
            SearchManager.SUGGEST_COLUMN_INTENT_DATA
    };

    public SearchSuggestionsCursor() {
        super(SEARCH_SUGGESTION_COLUMNS);
    }

    /**
     * Populates search suggestion database with provided string values
     * @param searchSuggestions - the list of strings which will be shown as a search suggestions
     */
    public void addSearchSuggestions(List<String> searchSuggestions) {
        int suggestionsLength = searchSuggestions.size();

        for (int i = 0; i < suggestionsLength; i++) {
            String suggestion = searchSuggestions.get(i);
            String[] columnValues = {Integer.toString(i), suggestion, suggestion};
            super.addRow(columnValues);
        }
    }
}
