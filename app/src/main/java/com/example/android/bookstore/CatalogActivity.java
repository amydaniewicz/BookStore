package com.example.android.bookstore;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.bookstore.data.BookContract.BookEntry;
import com.example.android.bookstore.data.BookDbHelper;


public class CatalogActivity extends AppCompatActivity {

    private BookDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        mDbHelper = new BookDbHelper(this);
        displayDatabaseInfo();
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the database.
     */
    private void displayDatabaseInfo() {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                BookEntry._ID,
                BookEntry.COLUMN_PRODUCT_NAME,
                BookEntry.COLUMN_AUTHOR,
                BookEntry.COLUMN_PRICE,
                BookEntry.COLUMN_QUANTITY,
                BookEntry.COLUMN_SUPPLIER_NAME,
                BookEntry.COLUMN_SUPPLIER_PHONE
        };
        Cursor cursor = db.query(
                BookEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        TextView displayView = (TextView) findViewById(R.id.text_view_book);

        try {
            displayView.setText(getString(R.string.get_count_string_1) + cursor.getCount() + getString(R.string.get_count_string_2));
            displayView.append(
                    BookEntry._ID + " - " +
                    BookEntry.COLUMN_PRODUCT_NAME + " - " +
                    BookEntry.COLUMN_AUTHOR + " - " +
                    BookEntry.COLUMN_PRICE + " - " +
                    BookEntry.COLUMN_QUANTITY + " - " +
                    BookEntry.COLUMN_SUPPLIER_NAME + " - " +
                    BookEntry.COLUMN_SUPPLIER_PHONE + "\n");

            int idColumnIndex = cursor.getColumnIndex(BookEntry._ID);
            int productNameColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_PRODUCT_NAME);
            int authorColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_AUTHOR);
            int priceColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_QUANTITY);
            int supplierNameColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_SUPPLIER_NAME);
            int supplierPhoneColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_SUPPLIER_PHONE);

            while (cursor.moveToNext()) {
                int currentId = cursor.getInt(idColumnIndex);
                String currentProductName = cursor.getString(productNameColumnIndex);
                String currentAuthor = cursor.getString(authorColumnIndex);
                int currentPrice = cursor.getInt(priceColumnIndex);
                int currentQuantity = cursor.getInt(quantityColumnIndex);
                String currentSupplierName = cursor.getString(supplierNameColumnIndex);
                String currentSupplierPhone = cursor.getString(supplierPhoneColumnIndex);
                displayView.append("\n" +
                        currentId + " - " +
                        currentProductName + " - " +
                        currentAuthor + " - " +
                        currentPrice + " - " +
                        currentQuantity + " - " +
                        currentSupplierName + " - " +
                        currentSupplierPhone);
            }
        }
        finally {
            cursor.close();
        }
    }

    private void insertBook() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BookEntry.COLUMN_PRODUCT_NAME, "Green Eggs and Ham");
        values.put(BookEntry.COLUMN_AUTHOR, "Dr. Seuss");
        values.put(BookEntry.COLUMN_PRICE, 15);
        values.put(BookEntry.COLUMN_QUANTITY, 10);
        values.put(BookEntry.COLUMN_SUPPLIER_NAME, "ABC Books");
        values.put(BookEntry.COLUMN_SUPPLIER_PHONE, "512 123 3344");
        long newRowId = db.insert(BookEntry.TABLE_NAME, null, values);
        Log.v("Catalog Activity", "New row ID " + newRowId);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            case R.id.action_insert_dummy_data:
                insertBook();
                displayDatabaseInfo();
                return true;
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}