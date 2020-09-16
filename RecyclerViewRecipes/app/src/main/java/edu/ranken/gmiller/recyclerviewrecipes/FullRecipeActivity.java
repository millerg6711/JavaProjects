package edu.ranken.gmiller.recyclerviewrecipes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class FullRecipeActivity extends AppCompatActivity {
    private ImageView mRecipeImage;
    private TextView mIngredients;
    private TextView mProcedures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_recipe);
        mIngredients = findViewById(R.id.ingredients_text);
        mProcedures = findViewById(R.id.procedures_text);
        mRecipeImage = findViewById(R.id.recipe_image);

        Intent intent = getIntent();
        Recipe recipe = intent.getParcelableExtra("Recipe");
        if (recipe != null) {
            byte[] imageBytes = recipe.getImageBytes();
            if (imageBytes != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                mRecipeImage.setImageBitmap(bitmap);
            }

            StringBuilder ingredients = new StringBuilder();
            ingredients.append("Ingredients:");
            for (String ingredient : recipe.getIngredients()) {
                ingredients.append("\n").append(ingredient);
            }
            mIngredients.setText(ingredients.toString());

            StringBuilder procedures = new StringBuilder();
            procedures.append("Procedure:");

            String[] proceduresArr = recipe.getProcedures();
            for (int i = 0; i < proceduresArr.length; i++) {
                procedures.append(
                    String.format(Locale.getDefault(),"\n%d. %s", i+1, proceduresArr[i]));
            }
            mProcedures.setText(procedures.toString());
        }
    }
}
