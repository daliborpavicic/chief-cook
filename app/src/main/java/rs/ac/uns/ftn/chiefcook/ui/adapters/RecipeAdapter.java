package rs.ac.uns.ftn.chiefcook.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import rs.ac.uns.ftn.chiefcook.R;
import rs.ac.uns.ftn.chiefcook.model.Recipe;
import rs.ac.uns.ftn.chiefcook.model.RecipesListResponse;
import rs.ac.uns.ftn.chiefcook.ui.activities.RecipeDetailsActivity;

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

        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Recipe recipe = recipesListResponse.getResults().get(position);
        String recipeImageUrl = recipesListResponse.buildImageUrl(recipe.getImage());

        TextView tvRecipeTitle = (TextView) holder.cardView.findViewById(R.id.recipe_title);
        ImageView ivRecipeImage = (ImageView) holder.cardView.findViewById(R.id.recipe_image);

        tvRecipeTitle.setText(recipe.getTitle());
        Picasso.with(context)
                .load(recipeImageUrl)
                .placeholder(R.drawable.ic_action_search)
                .into(ivRecipeImage);

        holder.cardView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RecipeDetailsActivity.class);
                intent.putExtra(RecipeDetailsActivity.RECIPE_ID_KEY, recipe.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipesListResponse.getResults().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;

        public ViewHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }
}
