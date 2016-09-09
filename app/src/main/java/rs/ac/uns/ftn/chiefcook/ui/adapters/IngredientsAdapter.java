package rs.ac.uns.ftn.chiefcook.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rs.ac.uns.ftn.chiefcook.R;
import rs.ac.uns.ftn.chiefcook.model.ExtendedIngredient;

/**
 * Created by daliborp on 9.9.16..
 */
public class IngredientsAdapter extends ArrayAdapter<ExtendedIngredient> {

    public IngredientsAdapter(Context context, ExtendedIngredient[] objects) {
        super(context, R.layout.item_ingredient, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ExtendedIngredient ingredient = getItem(position);

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_ingredient, parent, false);

            viewHolder = new ViewHolder(convertView);

            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.chbIngredientItem.setChecked(ingredient.isSelected());
        viewHolder.tvIngredientName.setText(ingredient.getName());
        viewHolder.tvIngredientAmount.setText(
                String.format("%.2f %s", ingredient.getAmount(), ingredient.getUnit())
        );

        return convertView;
    }

    protected static class ViewHolder {
        @BindView(R.id.chbIngredientItem) CheckBox chbIngredientItem;
        @BindView(R.id.tvIngredientName)TextView tvIngredientName;
        @BindView(R.id.tvIngredientAmount) TextView tvIngredientAmount;

        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }
}
