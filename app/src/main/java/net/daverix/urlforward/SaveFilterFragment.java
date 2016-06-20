package net.daverix.urlforward;

import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import net.daverix.urlforward.databinding.SaveFilterFragmentBinding;

import static net.daverix.urlforward.db.UrlForwarderContract.UrlFilters;

/**
 * Created by daverix on 12/28/13.
 */
public class SaveFilterFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int LOADER_LOAD_FILTER = 0;
    private static final String ARG_STATE = "state";
    private static final String ARG_URI = "uri";
    private static final int STATE_CREATE = 0;
    private static final int STATE_UPDATE = 1;

    private LinkFilter filter;
    private Uri uri;
    private int state;

    public static SaveFilterFragment newCreateInstance() {
        SaveFilterFragment saveFilterFragment = new SaveFilterFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_STATE, STATE_CREATE);
        saveFilterFragment.setArguments(args);
        return saveFilterFragment;
    }

    public static SaveFilterFragment newUpdateInstance(Uri uri) {
        SaveFilterFragment saveFilterFragment = new SaveFilterFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_STATE, STATE_UPDATE);
        args.putParcelable(ARG_URI, uri);
        saveFilterFragment.setArguments(args);
        return saveFilterFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        Bundle args = getArguments();
        if (args != null) {
            uri = args.getParcelable(ARG_URI);
            state = args.getInt(ARG_STATE);
        }

        if(savedInstanceState != null) {
            filter = savedInstanceState.getParcelable("filter");
        }
        else {
            filter = new LinkFilter();
            filter.setCreated(System.currentTimeMillis());

            if (state == STATE_CREATE) {
                filter.setFilterUrl("http://example.com/?url=@url");
                filter.setReplaceText("@url");
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("filter", filter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SaveFilterFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.save_filter_fragment, container, false);
        binding.setFilter(filter);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (state == STATE_UPDATE && savedInstanceState == null) {
            getLoaderManager().restartLoader(LOADER_LOAD_FILTER, null, this);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.fragment_save_filter, menu);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_LOAD_FILTER:
                return new CursorLoader(getActivity(), uri, new String[]{
                        UrlFilters.TITLE,
                        UrlFilters.FILTER,
                        UrlFilters.REPLACE_TEXT,
                        UrlFilters.CREATED
                }, null, null, null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case LOADER_LOAD_FILTER:
                if (data != null && data.moveToFirst()) {
                    filter.setTitle(data.getString(0));
                    filter.setFilterUrl(data.getString(1));
                    filter.setReplaceText(data.getString(2));
                    filter.setCreated(data.getLong(3));
                }
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public LinkFilter getFilter() {
        return filter;
    }
}
