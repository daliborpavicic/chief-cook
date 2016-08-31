package rs.ac.uns.ftn.chiefcook.ui.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rs.ac.uns.ftn.chiefcook.R;
import rs.ac.uns.ftn.chiefcook.model.Step;

/**
 * Created by daliborp on 21.8.16..
 */
public class RecipeStepAdapter extends RecyclerView.Adapter<RecipeStepAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cvRecipeStep) protected CardView cardView;
        @BindView(R.id.cardContainer) protected LinearLayout cardContainer;
        @BindView(R.id.tvStepNumber) protected TextView tvStepNumber;
        @BindView(R.id.tvStepDescription) protected TextView tvStepDescription;
        @BindView(R.id.tvEquipment) protected TextView tvEquipment;
        @BindView(R.id.tvIngredients) protected TextView tvIngredients;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private Context context;
    private List<Step> recipeSteps;

    public RecipeStepAdapter(Context context, List<Step> recipeSteps) {
        this.context = context;
        this.recipeSteps = recipeSteps;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View recipeView = inflater.inflate(R.layout.card_recipe_step, parent, false);

        return new ViewHolder(recipeView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Step recipeStep = recipeSteps.get(position);
        int stepsCount = recipeSteps.size();

        holder.tvStepNumber.setText(String.format("%s / %s", position + 1, stepsCount));
        holder.tvStepDescription.setText(recipeStep.getStep());
        holder.tvEquipment.setText(TextUtils.join(",", recipeStep.getEquipment()));
        holder.tvIngredients.setText(TextUtils.join("/", recipeStep.getIngredients()));
    }

    @Override
    public int getItemCount() {
        return recipeSteps.size();
    }
}
