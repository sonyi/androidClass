package com.mybookcollection.data;

import android.provider.BaseColumns;

public final class MetaDataContract {
	public final class BookCatagoryContract implements BaseColumns {
		public static final String TABLE_NAME = "book_catagory";

		public static final String CATAGORY_NAME = "gotagory_name";
	}

	public final class BookContract implements BaseColumns {
		public static final String TABLE_NAME = "book";

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
