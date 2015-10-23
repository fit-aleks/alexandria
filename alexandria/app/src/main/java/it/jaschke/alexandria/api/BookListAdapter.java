package it.jaschke.alexandria.api;


import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import it.jaschke.alexandria.R;
import it.jaschke.alexandria.data.AlexandriaContract;

/**
 * Created by saj on 11/01/15.
 */
public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.BookViewHolder> {

    private final Context mContext;
    private Cursor mCursor;
    private final BookListAdapterOnClickHandler mClickHandler;

    public BookListAdapter(Context context, BookListAdapterOnClickHandler clickHandler) {
        mContext = context;
        mClickHandler = clickHandler;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (!(parent instanceof RecyclerView )) {
            throw new RuntimeException("Not bound to RecyclerViewSelection");
        }
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_list_item, parent, false);
        v.setFocusable(true);
        return new BookViewHolder(v);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        String imgUrl = mCursor.getString(mCursor.getColumnIndex(AlexandriaContract.BookEntry.IMAGE_URL));
        Glide.with(mContext)
                .load(imgUrl)
                .into(holder.bookCover);

        String bookTitle = mCursor.getString(mCursor.getColumnIndex(AlexandriaContract.BookEntry.TITLE));
        holder.bookTitle.setText(bookTitle);

        String bookSubTitle = mCursor.getString(mCursor.getColumnIndex(AlexandriaContract.BookEntry.SUBTITLE));
        holder.bookSubTitle.setText(bookSubTitle);
    }

    @Override
    public int getItemCount() {
        return mCursor != null ? mCursor.getCount() : 0;
    }

    public void swapCursor(Cursor cursor) {
        this.mCursor = cursor;
        notifyDataSetChanged();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView bookCover;
        public final TextView bookTitle;
        public final TextView bookSubTitle;

        public BookViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            bookCover = (ImageView) view.findViewById(R.id.fullBookCover);
            bookTitle = (TextView) view.findViewById(R.id.listBookTitle);
            bookSubTitle = (TextView) view.findViewById(R.id.listBookSubTitle);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mCursor.moveToPosition(adapterPosition);
            int eanColumnIndex = mCursor.getColumnIndex(AlexandriaContract.BookEntry._ID);
            mClickHandler.onClick(mCursor.getString(eanColumnIndex));
        }
    }

    public static interface BookListAdapterOnClickHandler {
        void onClick(String ean);
    }
}
