package edu.ranken.gmiller.notepad.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.ranken.gmiller.notepad.R;
import edu.ranken.gmiller.notepad.data.Note;
import edu.ranken.gmiller.notepad.data.NoteApp;
import edu.ranken.gmiller.notepad.ui.activity.EditNoteActivity;
import edu.ranken.gmiller.notepad.ui.activity.MainActivity;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.NoteViewHolder> {

    private List<Note> mNotes;
    private Context mContext;
    private LayoutInflater mInflater;

    public NotesListAdapter(Context context, List<Note> notes) {
        mNotes = notes;
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    public void setNotes(List<Note> notes) {
        mNotes = notes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_note_card, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = null;
        if (mNotes != null && position < mNotes.size()) {
            note = mNotes.get(position);
        }
        holder.bindTo(note);
    }

    @Override
    public int getItemCount() {
        return mNotes != null ? mNotes.size() : 0;
    }

    public Note getNoteAtPosition(int position) {
        return mNotes.get(position);
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mNoteTitle;
        private TextView mNoteDate;
        private TextView mNoteBody;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            mNoteTitle = itemView.findViewById(R.id.item_note_title);
            mNoteBody = itemView.findViewById(R.id.item_note_body);
            mNoteDate = itemView.findViewById(R.id.item_note_date);

            itemView.setOnClickListener(this);
        }

        public void bindTo(@Nullable Note note) {
            if (note != null) {
                mNoteTitle.setText(note.getTitle());
                mNoteBody.setText(note.getBody());
                mNoteDate.setText(note.getDate());
            } else {
                mNoteTitle.setText("");
                mNoteBody.setText("");
                mNoteDate.setText("");
            }
        }

        @Override
        public void onClick(View v) {
            Note note = mNotes.get(getAdapterPosition());
            Intent intent = new Intent(mContext, EditNoteActivity.class);
            intent.putExtra(NoteApp.EXTRA_NOTE_ID, note.getId());
            MainActivity activity = (MainActivity) mContext;
            activity.startActivityForResult(intent, MainActivity.REQUEST_EDIT_NOTE);
        }
    }
}
