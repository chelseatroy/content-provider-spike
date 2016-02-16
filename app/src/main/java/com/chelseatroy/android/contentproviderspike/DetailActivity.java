package com.chelseatroy.android.contentproviderspike;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private Uri data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        data = getIntent().getData();

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                this,
                data,
                new String[]{"_id", "display_name"},
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        data.moveToFirst();

        ((TextView) findViewById(R.id.contact_id)).setText(String.valueOf(data.getInt(0)));
        ((TextView) findViewById(R.id.contact_name)).setText(data.getString(1));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
