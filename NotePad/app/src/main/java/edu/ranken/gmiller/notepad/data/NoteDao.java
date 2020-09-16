package edu.ranken.gmiller.notepad.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM Note ORDER BY id ASC;")
    LiveData<List<Note>> getAllNotes();

    @Query("SELECT * FROM Note WHERE id = :id")
    Note getNoteById(int id);

    @Insert
    void insertNote(Note note);

    @Update
    void updateNote(Note note);

    @Query("DELETE FROM Note WHERE id = :id")
    void deleteNote(int id);

    @Query("DELETE FROM Note;")
    void deleteAllNotes();
}
