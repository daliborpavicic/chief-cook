package rs.ac.uns.ftn.chiefcook.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by daliborp on 3.9.16..
 */
public class ChiefCookDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "chiefcook.db";

    public ChiefCookDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        addRecipeTable(db);
        addIngredientTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private void addRecipeTable(SQLiteDatabase db) {
        final String SQL_CREATE_RECIPE_TABLE = "CREATE TABLE " + ChiefCookContract.RecipeEntry.TABLE_NAME + " (" +
                ChiefCookContract.RecipeEntry._ID + " INTEGER PRIMARY KEY, " +
                ChiefCookContract.RecipeEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                ChiefCookContract.RecipeEntry.COLUMN_READY_IN_MINUTES + " INTEGER NOT NULL, " +
                ChiefCookContract.RecipeEntry.COLUMN_IMAGE_URL + " TEXT, " +
                ChiefCookContract.RecipeEntry.COLUMN_API_ID + " INTEGER NOT NULL " +
                " );";

        db.execSQL(SQL_CREATE_RECIPE_TABLE);
    }

    private void addIngredientTable(SQLiteDatabase db) {
        final String SQL_CREATE_INGREDIENT_TABLE = "CREATE TABLE " + ChiefCookContract.IngredientEntry.TABLE_NAME + " (" +
                ChiefCookContract.IngredientEntry._ID + " INTEGER PRIMARY KEY, " +
                ChiefCookContract.IngredientEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                ChiefCookContract.IngredientEntry.COLUMN_AMOUNT + " REAL NOT NULL, " +
                ChiefCookContract.IngredientEntry.COLUMN_UNIT + " TEXT, " +
                ChiefCookContract.IngredientEntry.COLUMN_UNIT_SHORT + " TEXT, " +
                ChiefCookContract.IngredientEntry.COLUMN_IMAGE_URL + " TEXT, " +
                ChiefCookContract.IngredientEntry.COLUMN_ORIGINAL_STRING + " TEXT, " +
                ChiefCookContract.IngredientEntry.COLUMN_API_ID + " INTEGER NOT NULL, " +
                ChiefCookContract.IngredientEntry.COLUMN_RECIPE_ID + " INTEGER NOT NULL, " +
                "FOREIGN KEY (" + ChiefCookContract.IngredientEntry.COLUMN_RECIPE_ID + ") " +
                "REFERENCES " + ChiefCookContract.RecipeEntry.TABLE_NAME + " (" + ChiefCookContract.RecipeEntry._ID + ")" +
                " );";

        db.execSQL(SQL_CREATE_INGREDIENT_TABLE);
    }
}
