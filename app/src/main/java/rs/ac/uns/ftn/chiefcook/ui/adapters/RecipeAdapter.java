package rs.ac.uns.ftn.chiefcook.ui.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import rs.ac.uns.ftn.chiefcook.R;
import rs.ac.uns.ftn.chiefcook.model.Recipe;
import rs.ac.uns.ftn.chiefcook.model.RecipesListResponse;

/**
 * Created by daliborp on 21.8.16..
 */
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    public static final String LOG_TAG = RecipeAdapter.class.getSimpleName();

    private Context context;
    private RecipesListResponse recipesListResponse;

    public RecipeAdapter(Context context, RecipesListResponse recipesListResponse) {
        this.context = context;
        this.recipesListResponse = recipesListResponse;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(context)
                .inflate(R.layout.card_recipe, parent, false);

        return new ViewHolder(context ,cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Recipe recipe = recipesListResponse.getResults().get(position);
        String recipeImageUrl = recipesListResponse.buildImageUrl(recipe.getImage());

        holder.tvRecipeTitle.setText(recipe.getTitle());
        Picasso.with(context)
                .load(recipeImageUrl)
                .placeholder(R.drawable.ic_search)
                .into(holder.ivRecipeImage);
    }

    @Override
    public int getItemCount() {
        return recipesListResponse.getResults().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private Context context;

        @BindView(R.id.recipe_image)
        protected ImageView ivRecipeImage;

        @BindView(R.id.recipe_title)
        protected TextView tvRecipeTitle;


        public ViewHolder(Context context, CardView cardView) {
            super(cardView);
            this.context = context;
            ButterKnife.bind(this, cardView);
        }
    }
}
