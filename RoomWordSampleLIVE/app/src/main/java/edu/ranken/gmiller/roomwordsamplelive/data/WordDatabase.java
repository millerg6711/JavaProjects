package edu.ranken.gmiller.roomwordsamplelive.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import edu.ranken.gmiller.roomwordsamplelive.data.dao.WordDao;
import edu.ranken.gmiller.roomwordsamplelive.data.entity.Word;

@Database(entities = {Word.class}, version = 2)
public abstract class WordDatabase extends RoomDatabase {

    public abstract WordDao getWordDao();

    public static WordDatabase buildDatabase(Application app) {
        return Room.databaseBuilder(app, WordDatabase.class, "word_database")
            .addCallback(callback)
            .fallbackToDestructiveMigration()
            .build();
    }

    private static final RoomDatabase.Callback callback =
        new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                db.execSQL("DELETE FROM word_table;");
                db.execSQL("INSERT INTO word_table (word) VALUES ('dolphin'), ('crocodile'), ('cobra');");
            }
        };
}
