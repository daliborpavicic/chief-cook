package rs.ac.uns.ftn.chiefcook.ui.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rs.ac.uns.ftn.chiefcook.R;
import rs.ac.uns.ftn.chiefcook.model.Recipe;

/**
 * Created by daliborp on 21.8.16..
 */
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private Context context;
    private List<Recipe> recipes;

    public RecipeAdapter(Context context, List<Recipe> recipes) {
        this.context = context;
        this.recipes = recipes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(context)
                .inflate(R.layout.card_recipe, parent, false);

        return new ViewHolder(context ,cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);

        holder.ivRecipeImage.setImageResource(R.mipmap.ic_launcher);
        holder.tvRecipeTitle.setText(recipe.getTitle());
    }

    @Override
    public int getItemCount() {
        return recipes.size();
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
