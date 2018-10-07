/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.bookstore;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.bookstore.data.BookContract.BookEntry;
import com.example.android.bookstore.data.BookDbHelper;

/**
 * Allows user to create a new book or edit an existing one.
 */
public class EditorActivity extends AppCompatActivity {

    private EditText mProductNameEditText;
    private EditText mAuthorEditText;
    private EditText mPriceEditText;
    private EditText mQuantityEditText;
    private EditText mSupplierNameEditText;
    private EditText mSupplierPhoneEditText;

    private BookDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Find all relevant views that we will need to read user input from
        mProductNameEditText = (EditText) findViewById(R.id.edit_product_name);
        mAuthorEditText = (EditText) findViewById(R.id.edit_author);
        mPriceEditText = (EditText) findViewById(R.id.edit_price);
        mQuantityEditText = (EditText) findViewById(R.id.edit_quantity);
        mSupplierNameEditText = (EditText) findViewById(R.id.edit_supplier_name);
        mSupplierPhoneEditText = (EditText) findViewById(R.id.edit_supplier_phone);

        mDbHelper = new BookDbHelper(this);
    }


//    when user clicks save
    private void insertBook() {
        String productNameString = mProductNameEditText.getText().toString().trim();
        String authorString = mAuthorEditText.getText().toString().trim();
        String priceString = mPriceEditText.getText().toString().trim();
        int priceInt = Integer.parseInt(priceString);
        String quantityString = mQuantityEditText.getText().toString().trim();
        int quantityInt = Integer.parseInt(quantityString);
        String supplierNameString = mSupplierNameEditText.getText().toString().trim();
        String supplierPhoneString = mSupplierPhoneEditText.getText().toString().trim();

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BookEntry.COLUMN_PRODUCT_NAME, productNameString);
        values.put(BookEntry.COLUMN_AUTHOR, authorString);
        values.put(BookEntry.COLUMN_PRICE, priceInt);
        values.put(BookEntry.COLUMN_QUANTITY, quantityInt);
        values.put(BookEntry.COLUMN_SUPPLIER_NAME, supplierNameString);
        values.put(BookEntry.COLUMN_SUPPLIER_PHONE, supplierPhoneString);
        long newRowId = db.insert(BookEntry.TABLE_NAME, null, values);

        if (newRowId < 0) {
            Toast.makeText(this, "Error with saving book",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Book saved with ID" + newRowId,
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                insertBook();
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}