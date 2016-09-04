package rs.ac.uns.ftn.chiefcook.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by daliborp on 3.9.16..
 */
public class ChiefCookProvider extends ContentProvider {

    private static final int RECIPE = 100;
    private static final int RECIPE_ID = 101;
    private static final int INGREDIENT = 200;
    private static final int INGREDIENT_ID = 201;

    private static final UriMatcher uriMatcher = buildUriMatcher();
    private ChiefCookDBHelper openHelper;

    private static UriMatcher buildUriMatcher() {
        String content = ChiefCookContract.CONTENT_AUTHORITY;

        // All paths to the UriMatcher have a corresponding code to return
        // when a match is found (the ints above).
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(content, ChiefCookContract.PATH_RECIPE, RECIPE);
        matcher.addURI(content, ChiefCookContract.PATH_RECIPE + "/#", RECIPE_ID);
        matcher.addURI(content, ChiefCookContract.PATH_INGREDIENT, INGREDIENT);
        matcher.addURI(content, ChiefCookContract.PATH_INGREDIENT + "/#", INGREDIENT_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        openHelper = new ChiefCookDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = openHelper.getWritableDatabase();
        Cursor returnCursor;

        switch (uriMatcher.match(uri)) {
            case RECIPE:
                returnCursor = db.query(
                        ChiefCookContract.RecipeEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case RECIPE_ID:
                long _id = ContentUris.parseId(uri);
                returnCursor = db.query(
                        ChiefCookContract.RecipeEntry.TABLE_NAME,
                        projection,
                        ChiefCookContract.RecipeEntry._ID + " = ?",
                        new String[] { String.valueOf(_id) },
                        null,
                        null,
                        sortOrder
                );
                break;
            case INGREDIENT:
                returnCursor = db.query(
                        ChiefCookContract.IngredientEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case INGREDIENT_ID:
                _id = ContentUris.parseId(uri);
                returnCursor = db.query(
                        ChiefCookContract.RecipeEntry.TABLE_NAME,
                        projection,
                        ChiefCookContract.RecipeEntry._ID + " = ?",
                        new String[] { String.valueOf(_id) },
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return returnCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case RECIPE:
                return ChiefCookContract.RecipeEntry.CONTENT_TYPE;
            case RECIPE_ID:
                return ChiefCookContract.RecipeEntry.CONTENT_ITEM_TYPE;
            case INGREDIENT:
                return ChiefCookContract.IngredientEntry.CONTENT_TYPE;
            case INGREDIENT_ID:
                return ChiefCookContract.IngredientEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = openHelper.getWritableDatabase();
        long _id;
        Uri returnUri;

        switch (uriMatcher.match(uri)) {
            case RECIPE:
                _id = db.insert(ChiefCookContract.RecipeEntry.TABLE_NAME, null, values);

                if(_id > 0){
                    returnUri =  ChiefCookContract.RecipeEntry.buildRecipeUri(_id);
                } else{
                    throw new UnsupportedOperationException("Unable to insert rows into: " + uri);
                }

                break;

            case INGREDIENT:
                _id = db.insert(ChiefCookContract.IngredientEntry.TABLE_NAME, null, values);

                if(_id > 0){
                    returnUri =  ChiefCookContract.IngredientEntry.buildIngredientnUri(_id);
                } else{
                    throw new UnsupportedOperationException("Unable to insert rows into: " + uri);
                }

                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Use this on the URI passed into the function to notify any observers that the uri has
        // changed.
        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
