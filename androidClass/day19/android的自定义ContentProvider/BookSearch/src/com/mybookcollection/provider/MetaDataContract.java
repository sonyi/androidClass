package com.mybookcollection.provider;

import java.net.URI;

import android.net.Uri;
import android.provider.BaseColumns;

public final class MetaDataContract {
	public static final String SCHEMA = "content://";
	public static final String AUTHORITY = "com.bookcollection";

	public final class BookCatagoryContract implements BaseColumns {
		public static final String TABLE_NAME = "book_catagory";

		public static final String CATAGORY_NAME = "gotagory_name";
	}

	public static final class BookContract implements BaseColumns {

		public static final String TABLE_NAME = "book";
		
		// 图书数据的Uri
		public static final Uri CONTENT_URI = Uri.parse(SCHEMA + AUTHORITY
				+ "/" + TABLE_NAME);
		
		// Content类型
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/book";
		public static final String ENTRY_CONTENT_TYPE = "vnd.android.cursor.item/book";

		public static final String TITLE = "title";

		public static final String AUTHOR = "author";

		public static final String CATAGORY_ID = "c_id";

		public static final String DATE = "date";

		public static final String PRICE = "price";

		public static final String PAGES = "pages";

		public static final String ART = "art";

		public static final String DESCRIPTION = "description";
	}
}
