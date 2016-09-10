package rs.ac.uns.ftn.chiefcook.ui.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import rs.ac.uns.ftn.chiefcook.R;
import rs.ac.uns.ftn.chiefcook.data.ChiefCookContract;

/**
 * Created by daliborp on 10.9.16..
 */
public class FavoriteRecipeAdapter extends CursorAdapter {

    public interface Listener {
        void onRecipeClick(int recipeId);
    }

    private Listener listener;

    public FavoriteRecipeAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View convertView = LayoutInflater.from(context)
                .inflate(R.layout.card_recipe, parent, false);

        ViewHolder viewHolder = new ViewHolder(convertView);
        convertView.setTag(viewHolder);

        return convertView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();

        int titleIndex = cursor.getColumnIndexOrThrow(ChiefCookContract.RecipeEntry.COLUMN_TITLE);
        int readyInMinutesIndex = cursor.getColumnIndexOrThrow(ChiefCookContract.RecipeEntry.COLUMN_READY_IN_MINUTES);
        int imageUrlIndex = cursor.getColumnIndexOrThrow(ChiefCookContract.RecipeEntry.COLUMN_IMAGE_URL);
        int apiIdIndex = cursor.getColumnIndexOrThrow(ChiefCookContract.RecipeEntry.COLUMN_API_ID);

        String recipeTitle = cursor.getString(titleIndex);
        int readyInMinutes = cursor.getInt(readyInMinutesIndex);
        String imageUrl = cursor.getString(imageUrlIndex);
        final int recipeApiId = cursor.getInt(apiIdIndex);

        holder.tvRecipeNameItem.setText(recipeTitle);
        holder.tvTimeItem.setText(String.valueOf(readyInMinutes));
        Picasso.with(context)
                .load(imageUrl)
                .into(holder.ivRecipeImage);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onRecipeClick(recipeApiId);
            }
        });
    }

    protected static class ViewHolder {

        @BindView(R.id.cvRoot) protected CardView cardView;
        @BindView(R.id.tvRecipeNameItem) protected TextView tvRecipeNameItem;
        @BindView(R.id.tvTimeItem) protected TextView tvTimeItem;
        @BindView(R.id.ivRecipeImage) protected ImageView ivRecipeImage;

        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
