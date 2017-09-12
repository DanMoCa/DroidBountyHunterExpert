package training.edu.droidbountyhunter;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import training.edu.data.DBProvider;
import training.edu.data.DBProvider.DBHelper;

/**
 * Created by Dan14z on 08/09/2017.
 */

public class FugitivosContentProvider extends ContentProvider {

    public static final String LOG_TAG = FugitivosContentProvider.class.getSimpleName();
    private static final int FUGITIVOS = 100;
    private static final int FUGITIVO_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static{
        sUriMatcher.addURI(DBProvider.CONTENT_AUTHORITY, DBProvider.PATH_FUGITIVOS,FUGITIVOS);

        sUriMatcher.addURI(DBProvider.CONTENT_AUTHORITY, DBProvider.PATH_FUGITIVOS + "/#",FUGITIVO_ID);
    }

    private DBHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new DBHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = null;

        int match = sUriMatcher.match(uri);

        switch (match){
            case FUGITIVOS:
                cursor = db.query(DBProvider.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case FUGITIVO_ID:
                selection = DBProvider.COLUMN_NAME_ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(DBProvider.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unkown URI" + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(),uri);

        return cursor;
    }

    public Cursor getFugitivos(Uri uri){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = null;

        int match = sUriMatcher.match(uri);
        String selection;
        String[] selectionArgs;
        switch (match){
            case FUGITIVOS:
                selection = DBProvider.COLUMN_NAME_STATUS + "=?";
                selectionArgs = new String[] {"0"};
                cursor = db.query(DBProvider.TABLE_NAME,
                        null,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        null);
                break;
            case FUGITIVO_ID:
                selection = DBProvider.COLUMN_NAME_ID + "=?&" + DBProvider.COLUMN_NAME_STATUS + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri)),"0"};
                cursor = db.query(DBProvider.TABLE_NAME,null,selection,selectionArgs,null,null,null);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match){
            case FUGITIVOS:
                return DBProvider.CONTENT_LIST_TYPE;
            case FUGITIVO_ID:
                return DBProvider.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unkown URI " + uri + " with match " + match);
        }
    }
}
