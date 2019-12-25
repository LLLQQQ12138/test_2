package com.example.text_2;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.widget.Toast;

public class MyContentProvider extends ContentProvider {
    public static final int WORD_DIR = 0;
    public static final int WORD_ITEM = 1;
    public static final String AUTHORITY = "com.example.text_2.provider";

    private static UriMatcher uriMatcher;
    private DBHelper dbHelper;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "words", WORD_DIR);
        uriMatcher.addURI(AUTHORITY, "words/#", WORD_ITEM);
    }



    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int deletedRows = 0;
        switch (uriMatcher.match(uri)) {
            case WORD_DIR:
                deletedRows = db.delete("words", selection, selectionArgs);
                break;
            case WORD_ITEM:
                //String bookId = uri.getPathSegments().get(1);
                deletedRows = db.delete("words", selection, selectionArgs);
                break;
        }
        return deletedRows;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case WORD_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.text_2.provider.words";
            case WORD_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.text_2.provider.words";
            default:
                break;
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Uri uriReturn = null;
        switch (uriMatcher.match(uri)) {
            case WORD_DIR:
            case WORD_ITEM:
                long newWordId = db.insert("words", null, values);
                //Toast.makeText(getContext(), (int) newWordId, Toast.LENGTH_SHORT).show();
                uriReturn = Uri.parse("content://" + AUTHORITY + "/words/" + newWordId);
                break;
        }
        return uriReturn;
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext(), "words_db", null, 1);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        switch (uriMatcher.match(uri)) {
            case WORD_DIR:
                cursor = db.query("words", projection, selection, selectionArgs, null, null, sortOrder);
                break;
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int updatedRows = 0;
        switch (uriMatcher.match(uri)) {
            case WORD_DIR:
                updatedRows = db.update("words", values, selection, selectionArgs);
                break;
            case WORD_ITEM:
                //String bookId = uri.getPathSegments().get(1);
                updatedRows = db.update("words", values, selection, selectionArgs);
                break;
            default:
                break;
        }
        return updatedRows;
    }
}