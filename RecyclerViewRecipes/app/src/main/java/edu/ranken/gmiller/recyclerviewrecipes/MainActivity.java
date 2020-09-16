package edu.ranken.gmiller.recyclerviewrecipes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Recipe> mRecipeList;
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private RecipeListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get widgets
        mToolbar = findViewById(R.id.toolbar);
        mRecyclerView = findViewById(R.id.recycler_view);

        // configure toolbar
        setSupportActionBar(mToolbar);

        // initialize recipe list
        mRecipeList = new ArrayList<>();

        // build word list
        buildList();

        // set layout
        mRecyclerView.setLayoutManager(
            new LinearLayoutManager(this)
        );
    }

    private void buildList() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bread_image);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        byte[] bitMapData = stream.toByteArray();

        for (int i = 0; i < 20; i++) {
            String[] ingredients = {"Pepper" + i, "Salt" + i, "Corn" + i, "Milk"+ i, "Egg" + i};
            String[] procedures = {"Whisk flour" + i, "Whisk milk" + i, "Stir" + i};
            Recipe recipe = new Recipe("Recipe" + i,"Description" + i, ingredients, procedures);

            //Convert drawable image to bytes
            if (i % 2 == 0) {
                recipe.setImageBytes(bitMapData);
            }

            mRecipeList.add(recipe);
        }

        mAdapter = new RecipeListAdapter(this, mRecipeList);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList("Recipes", mRecipeList);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mRecipeList = savedInstanceState.getParcelableArrayList("Recipes");
        mAdapter = new RecipeListAdapter(this, mRecipeList);
        mRecyclerView.setAdapter(mAdapter);
    }
}
