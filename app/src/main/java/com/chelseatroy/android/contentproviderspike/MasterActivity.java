package com.chelseatroy.android.contentproviderspike;

import android.app.IntentService;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.os.Bundle;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class MasterActivity extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private CursorAdapter cursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cursorAdapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_2,
                null,
                new String[]{"_id", "display_name"},
                new int[]{android.R.id.text1, android.R.id.text2},
                0
        );

        setListAdapter(cursorAdapter);

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = new Intent(this, ProjectsIntentService.class);
        startService(intent);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Uri uri = ContentUris.withAppendedId(Uri.parse("content://com.chelseatroy.android.contentproviderspike/projects"), id);

        Intent intent = new Intent(this, DetailActivity.class);
        intent.setData(uri);
        startActivity(intent);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                this,
                Uri.parse("content://com.chelseatroy.android.contentproviderspike/projects"),
                new String[]{"_id", "display_name"},
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        cursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
