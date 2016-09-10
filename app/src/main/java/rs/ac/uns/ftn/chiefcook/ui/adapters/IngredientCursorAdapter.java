package rs.ac.uns.ftn.chiefcook.ui.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rs.ac.uns.ftn.chiefcook.R;
import rs.ac.uns.ftn.chiefcook.data.ChiefCookContract;

/**
 * Created by daliborp on 10.9.16..
 */
public class IngredientCursorAdapter extends CursorAdapter {

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
        ViewHolder holder = (ViewHolder) view.getTag();

        int nameIndex = cursor.getColumnIndexOrThrow(ChiefCookContract.IngredientEntry.COLUMN_NAME);
        int amountIndex = cursor.getColumnIndexOrThrow(ChiefCookContract.IngredientEntry.COLUMN_AMOUNT);
        int unitIndex = cursor.getColumnIndexOrThrow(ChiefCookContract.IngredientEntry.COLUMN_UNIT);

        String name = cursor.getString(nameIndex);
        float amount = cursor.getFloat(amountIndex);
        String unit = cursor.getString(unitIndex);

        holder.chbIngredientItem.setChecked(false);
        holder.tvIngredientName.setText(name);
        holder.tvIngredientAmount.setText(
                String.format("%.2f %s", amount, unit)
        );
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
