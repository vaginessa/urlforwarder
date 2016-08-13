/*
    UrlForwarder makes it possible to use bookmarklets on Android
    Copyright (C) 2016 David Laurell

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.daverix.urlforward.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static net.daverix.urlforward.db.UrlForwarderContract.UrlFilters;

public class UrlForwardDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "UrlForward";
    private static final int DB_VERSION = 2;
    public static final String TABLE_FILTER = "filter";

    private static final String CREATE_FILTER = "CREATE TABLE IF NOT EXISTS " + TABLE_FILTER + "(" +
            UrlFilters._ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            UrlFilters.TITLE + " TEXT NOT NULL," +
            UrlFilters.FILTER + " TEXT NOT NULL," +
            UrlFilters.REPLACE_TEXT + " TEXT NOI NULL," +
            UrlFilters.CREATED + " INTEGER NOT NULL," +
            UrlFilters.UPDATED + " INTEGER NOT NULL)";

    public UrlForwardDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_FILTER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion) {
            db.execSQL("DROP TABLE filter");

            onCreate(db);
        }
    }
}
