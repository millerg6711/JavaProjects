package edu.ranken.gmiller.codingchallenge22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity {
    public static final String EXTRA_ITEMS =
        "edu.ranken.gmiller.extra.CART";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    public void addItemToCart(View view) {
        Button mButtonAdd = (Button) view;
        String text = mButtonAdd.getText().toString();
        Intent addItem = new Intent(this, MainActivity.class);
        addItem.putExtra(EXTRA_ITEMS, text);
        setResult(RESULT_OK, addItem);
        finish();
    }
}
