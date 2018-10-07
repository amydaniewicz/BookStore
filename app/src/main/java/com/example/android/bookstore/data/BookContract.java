package com.example.android.bookstore.data;

import android.provider.BaseColumns;

public final class BookContract {

    public final static class BookEntry implements BaseColumns {
        public static final String TABLE_NAME = "books";
        public static final String _ID = "_id";
        public static final String COLUMN_PRODUCT_NAME = "name";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_SUPPLIER_NAME = "supplier_name";
        public static final String COLUMN_SUPPLIER_PHONE = "supplier_phone";
    }
}
