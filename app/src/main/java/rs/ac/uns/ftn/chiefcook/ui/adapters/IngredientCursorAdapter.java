package rs.ac.uns.ftn.chiefcook.ui.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rs.ac.uns.ftn.chiefcook.R;
import rs.ac.uns.ftn.chiefcook.data.ChiefCookContract;

/**
 * Created by daliborp on 10.9.16..
 */
public class IngredientCursorAdapter extends CursorAdapter {

    private List<Integer> selectedIngredientIds = new ArrayList<>();

    public IngredientCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View convertView = LayoutInflater.from(context)
                .inflate(R.layout.item_ingredient, parent, false);

        ViewHolder viewHolder = new ViewHolder(convertView);
        convertView.setTag(viewHolder);

        return convertView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final ViewHolder holder = (ViewHolder) view.getTag();

        int idIndex = cursor.getColumnIndexOrThrow(ChiefCookContract.IngredientEntry._ID);
        int nameIndex = cursor.getColumnIndexOrThrow(ChiefCookContract.IngredientEntry.COLUMN_NAME);
        int amountIndex = cursor.getColumnIndexOrThrow(ChiefCookContract.IngredientEntry.COLUMN_AMOUNT);
        int unitIndex = cursor.getColumnIndexOrThrow(ChiefCookContract.IngredientEntry.COLUMN_UNIT);

        int ingredientId = cursor.getInt(idIndex);
        String name = cursor.getString(nameIndex);
        float amount = cursor.getFloat(amountIndex);
        String unit = cursor.getString(unitIndex);

        holder.tvIngredientName.setText(name);
        holder.tvIngredientAmount.setText(
                String.format("%.2f %s", amount, unit)
        );

        holder.chbIngredientItem.setChecked(false);
        holder.chbIngredientItem.setTag(ingredientId);
        holder.chbIngredientItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                Integer ingredientId = (Integer) holder.chbIngredientItem.getTag();

                if (compoundButton.isChecked()) {
                    selectedIngredientIds.add(ingredientId);
                } else {
                    selectedIngredientIds.remove(ingredientId);
                }
            }
        });
    }

    protected static class ViewHolder {
        @BindView(R.id.chbIngredientItem) CheckBox chbIngredientItem;
        @BindView(R.id.tvIngredientName)TextView tvIngredientName;
        @BindView(R.id.tvIngredientAmount) TextView tvIngredientAmount;

        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }

    public List<Integer> getSelectedIngredientIds() {
        return selectedIngredientIds;
    }
}
