package edu.ranken.gmiller.checkboxesandetc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private CheckBox mChocolateSyrupCheckbox;
    private CheckBox mSprinklesCheckbox;
    private CheckBox mCrushedNutsCheckbox;
    private CheckBox mCherriesCheckbox;
    private CheckBox mOreoCookieCrumblesCheckbox;

    private CheckBox[] checkBoxes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkBoxes = new CheckBox[] {
            findViewById(R.id.chocolate_syrup_checkbox),
            findViewById(R.id.sprinkles_checkbox),
            findViewById(R.id.nuts_checkbox),
            findViewById(R.id.cherries_checkbox),
            findViewById(R.id.oreo_checkbox)
        };
    }


    public void showToast(View view) {
        StringBuilder toppingsStrBuilder = new StringBuilder();

        for (CheckBox checkBox : checkBoxes) {
            if (checkBox.isChecked()) {
                toppingsStrBuilder.append(checkBox.getText().toString()).append(", ");
            }
        }

        if (toppingsStrBuilder.length() > 0)
        {
            //toppingsStrBuilder.delete(toppingsStrBuilder.length() - 2, toppingsStrBuilder.length());
            toppingsStrBuilder.setLength(toppingsStrBuilder.length() - 2);

            Toast toast = Toast.makeText(
                getApplicationContext(), "Toppings: \n" + toppingsStrBuilder.toString(), Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
