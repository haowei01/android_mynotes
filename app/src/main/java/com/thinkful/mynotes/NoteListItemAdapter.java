package com.thinkful.mynotes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haowei on 7/27/15.
 */
public class NoteListItemAdapter extends RecyclerView.Adapter<NoteListItemAdapter.ViewHolder> {
    private Context mContext;
    private RecyclerView mRecyclerView;
    private List<NoteListItem> mNoteListItems = new ArrayList<NoteListItem>();
    private int color;

    public NoteListItemAdapter(Context context, RecyclerView recyclerView, int color) {
        this.mContext = context;
        this.mRecyclerView = recyclerView;
        this.color = color;
        NoteDAO noteDAO = new NoteDAO(context);
        mNoteListItems = noteDAO.list();
    }

    public void addItem(NoteListItem item) {
        mNoteListItems.add(0, item);
        notifyItemInserted(0);
    }

    NoteListItem removeItem(int position) {
        NoteListItem note = mNoteListItems.remove(position);
        Toast.makeText(mContext, "Deleted: " + note.getText(), Toast.LENGTH_LONG).show();
        notifyItemRemoved(position);
        return note;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.note_list_item, viewGroup, false);
        v.setBackgroundColor(this.color);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the position of v
                int v_position = mRecyclerView.getChildPosition(v);
                // Call the removeItem method with the position
                NoteListItem note = removeItem(v_position);
                NoteDAO dao = new NoteDAO(mContext);
                dao.delete(note);
            }
        });

        v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                NoteListItem noteListItem = mNoteListItems.get(mRecyclerView.getChildPosition(v));
//                Log.i("Database NoteAdapter", noteListItem.getId() + ", text:" + noteListItem.getText());
                Toast.makeText(mContext, "Selected: " + noteListItem.getText(), Toast.LENGTH_LONG).show();
                removeItem(mRecyclerView.getChildPosition(v));
                Intent intent = new Intent(mContext, EditNoteActivity.class);
                intent.putExtra("Note", noteListItem);

                ((Activity)mContext).startActivityForResult(intent, 1);

                return true;
            }
        });

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        NoteListItem noteListItem = mNoteListItems.get(i);
        viewHolder.setText(noteListItem.getText());
    }

    @Override
    public int getItemCount() {
        return mNoteListItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
        }

        public void setText(String text) {
            this.text.setText(text);
        }
    }
}
