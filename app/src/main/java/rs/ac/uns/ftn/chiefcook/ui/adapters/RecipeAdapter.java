package rs.ac.uns.ftn.chiefcook.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rs.ac.uns.ftn.chiefcook.R;
import rs.ac.uns.ftn.chiefcook.model.Recipe;

/**
 * Created by daliborp on 21.8.16..
 */
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cvRoot) protected CardView cardView;
        @BindView(R.id.cardContainer) protected RelativeLayout cardContainer;
        @BindView(R.id.tvRecipeNameItem) protected TextView tvRecipeNameItem;
        @BindView(R.id.tvTimeItem) protected TextView tvTimeItem;
        @BindView(R.id.ivRecipeImage) protected ImageView ivRecipeImage;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface Listener {
        void onClick(int recipeId);
    }

    private Context context;
    private List<Recipe> recipes;
    private String baseImageUrl;
    private Listener listener;

    public RecipeAdapter(Context context, List<Recipe> recipes) {
        this.context = context;
        this.recipes = recipes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View recipeView = inflater.inflate(R.layout.card_recipe, parent, false);

        return new ViewHolder(recipeView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Recipe recipe = recipes.get(position);
        String imageUrl = createAbsoluteImageUrl(recipe.getImage());

        holder.tvRecipeNameItem.setText(recipe.getTitle());
        holder.tvTimeItem.setText(String.valueOf(recipe.getReadyInMinutes()));
        Picasso.with(context)
                .load(imageUrl)
                .into(holder.ivRecipeImage);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(recipe.getId());
            }
        });
    }

    @NonNull
    private String createAbsoluteImageUrl(String imageUrl) {
        return imageUrl.startsWith("http") ? imageUrl : baseImageUrl + imageUrl;
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void setBaseImageUrl(String baseImageUrl) {
        this.baseImageUrl = baseImageUrl;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
