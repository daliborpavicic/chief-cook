package rs.ac.uns.ftn.chiefcook.ui.adapters;

import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import rs.ac.uns.ftn.chiefcook.R;

/**
 * Created by daliborp on 2.9.16..
 */
public class SearchSuggestionAdapter extends CursorAdapter {

    public SearchSuggestionAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_search_suggestion, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvSuggestion = (TextView) view.findViewById(R.id.tvSuggestion);
        String suggestionText = cursor.getString(cursor.getColumnIndexOrThrow(SearchManager.SUGGEST_COLUMN_TEXT_1));
        tvSuggestion.setText(suggestionText);
    }
}
