package net.daverix.urlforward;

import android.content.ContentValues;
import android.database.Cursor;

import net.daverix.urlforward.db.UrlForwarderContract;

public class LinkFilterMapperImpl implements LinkFilterMapper {
    @Override
    public String[] getColumns() {
        return new String[] {
                UrlForwarderContract.UrlFilters._ID,
                UrlForwarderContract.UrlFilters.TITLE,
                UrlForwarderContract.UrlFilters.FILTER,
                UrlForwarderContract.UrlFilters.REPLACE_TEXT,
                UrlForwarderContract.UrlFilters.CREATED,
                UrlForwarderContract.UrlFilters.UPDATED,
                UrlForwarderContract.UrlFilters.SKIP_ENCODE,
                UrlForwarderContract.UrlFilters.REPLACE_SUBJECT
        };
    }

    @Override
    public LinkFilter mapFilter(Cursor cursor) {
        LinkFilter filter = new LinkFilter();
        filter.setId(cursor.getLong(0));
        filter.setTitle(cursor.getString(1));
        filter.setFilterUrl(cursor.getString(2));
        filter.setReplaceText(cursor.getString(3));
        filter.setCreated(cursor.getLong(4));
        filter.setUpdated(cursor.getLong(5));
        filter.setEncoded(cursor.getShort(6) != 1);
        filter.setReplaceSubject(cursor.getString(7));
        return filter;
    }

    @Override
    public ContentValues getValues(LinkFilter filter) {
        ContentValues values = new ContentValues();
        values.put(UrlForwarderContract.UrlFilters.CREATED, filter.getCreated());
        values.put(UrlForwarderContract.UrlFilters.UPDATED, filter.getUpdated());
        values.put(UrlForwarderContract.UrlFilters.TITLE, filter.getTitle());
        values.put(UrlForwarderContract.UrlFilters.FILTER, filter.getFilterUrl());
        values.put(UrlForwarderContract.UrlFilters.REPLACE_TEXT, filter.getReplaceText());
        values.put(UrlForwarderContract.UrlFilters.SKIP_ENCODE, !filter.isEncoded());
        values.put(UrlForwarderContract.UrlFilters.REPLACE_SUBJECT, filter.getReplaceSubject());

        return values;
    }
}
