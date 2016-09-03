package rs.ac.uns.ftn.chiefcook.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by daliborp on 3.9.16..
 */
public final class ChiefCookContract {

    public static final String CONTENT_AUTHORITY = "rs.ac.uns.ftn.chiefcook.provider";

    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_RECIPE = "recipe";
    public static final String PATH_INGREDIENT = "ingredient";

    public static final class RecipeEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_RECIPE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RECIPE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RECIPE;

        public static final String TABLE_NAME = "recipe";

        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_READY_IN_MINUTES = "readyInMinutes";
        public static final String COLUMN_IMAGE_URL = "recipeImageUrl";
        public static final String COLUMN_API_ID = "recipeApiId";

        public static Uri buildRecipeUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class IngredientEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_INGREDIENT).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INGREDIENT;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INGREDIENT;

        public static final String TABLE_NAME = "ingredient";

        public static final String COLUMN_NAME= "name";
        public static final String COLUMN_AMOUNT = "amount";
        public static final String COLUMN_UNIT = "unit";
        public static final String COLUMN_UNIT_SHORT = "unitShort";
        public static final String COLUMN_IMAGE_URL = "ingredientImageUrl";
        public static final String COLUMN_ORIGINAL_STRING = "originalString";
        public static final String COLUMN_API_ID = "ingredientApiId";
        public static final String COLUMN_RECIPE_ID = "recipeId";

        public static Uri buildIngredientnUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
