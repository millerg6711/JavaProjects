package edu.ranken.gmiller.notepad.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    public abstract NoteDao getNoteDao();

    public static NoteDatabase build(Application app) {
        return Room.databaseBuilder(app, NoteDatabase.class, "notes.db")
            .addCallback(callback)
            .fallbackToDestructiveMigration()
            .build();
    }

    private static final Callback callback =
        new RoomDatabase.Callback() {
            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);
                db.execSQL("DELETE FROM Note;");
                db.execSQL("INSERT INTO Note (title, date, body) VALUES" +
                    "('Note 1', '4/15/2020', 'I must remember this.'), " +
                    "('Note 2', '4/16/2020', 'This is another thing that I must remember.'), " +
                    "('Note 3', '4/17/2020', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit." +
                        "Praesent lorem nunc, eleifend eu facilisis eget, interdum a risus. Donec nec libero" +
                        "tristique, sodales lorem quis, fermentum nunc. Etiam quis turpis dictum, bibendum" +
                        "justo non, aliquet quam. Morbi porttitor, mi non convallis tempus, ante magna" +
                        "vulputate libero, commodo porta felis augue nec ligula.');");
            }
        };

}
