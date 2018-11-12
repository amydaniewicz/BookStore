package com.example.android.bookstore;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bookstore.data.BookContract.BookEntry;

public class BookCursorAdapter extends CursorAdapter {

    public BookCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();

        int idColumnIndex = cursor.getColumnIndex(BookEntry._ID);
        int bookNameColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_NAME);
        int priceColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_QUANTITY);

        final int id = cursor.getInt(idColumnIndex);
        String bookName = cursor.getString(bookNameColumnIndex);
        String priceString = "$" + cursor.getString(priceColumnIndex);
        final int quantity = cursor.getInt(quantityColumnIndex);
        String quantityString = "QUANTITY: " + cursor.getString(quantityColumnIndex);
        String summaryText = priceString + "  |  " + quantityString;

        viewHolder.bookNameTextView.setText(bookName);
        viewHolder.summaryTextView.setText(summaryText);


        viewHolder.saleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity == 0) {
                    Toast.makeText(context, R.string.sale_button_below_zero,
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                int reducedQuantity = quantity -1;
                ContentValues values = new ContentValues();
                values.put(BookEntry.COLUMN_QUANTITY, reducedQuantity);
                Uri updateUri = ContentUris.withAppendedId(BookEntry.CONTENT_URI,id);
                int rowsUpdated = context.getContentResolver().update(
                        updateUri,
                        values,
                        null,
                        null);
                if (rowsUpdated == 0) {
                    Toast.makeText(context, R.string.detail_activity_update_book_failed,
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, R.string.detail_activity_update_book_successful,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static class ViewHolder {
        public final TextView bookNameTextView;
        public final TextView summaryTextView;
        public final Button saleButton;


        public ViewHolder(View view) {
            bookNameTextView =
                    (TextView) view.findViewById(R.id.book_name);
            summaryTextView =
                    (TextView) view.findViewById(R.id.summary);
            saleButton =
                    (Button) view.findViewById(R.id.sale_button);
        }
    }
}