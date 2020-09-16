package edu.ranken.gmiller.roomwordsamplelive.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import edu.ranken.gmiller.roomwordsamplelive.data.WordApp;
import edu.ranken.gmiller.roomwordsamplelive.data.WordRepository;
import edu.ranken.gmiller.roomwordsamplelive.data.entity.Word;

public class WordViewModel extends AndroidViewModel {

    private WordApp mApp;
    private WordRepository mRepo;
    private LiveData<List<Word>> mAllWords;

    public WordViewModel(@NonNull Application application) {
        super(application);

        mApp = (WordApp) application;
        mRepo = mApp.getRepo();
        mAllWords = mRepo.getAllWords();
    }

    public LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

    public void insertWord(Word word) {
        mRepo.insertWord(word);
    }
}
