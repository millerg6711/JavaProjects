package edu.ranken.gmiller.orderactivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "edu.ranken.gmiller.MainActivity.extra.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            }
        });
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

    public void showDonutOrder(View view) {
        Intent intent = new Intent(this, DonutActivity.class);
        intent.putExtra(EXTRA_MESSAGE, getString(R.string.donuts));
        startActivity(intent);
    }

    public void showIceCreamOrder(View view) {
        Intent intent = new Intent(this, IceCreamActivity.class);
        intent.putExtra(EXTRA_MESSAGE, getString(R.string.ice_cream_sandwiches));
        startActivity(intent);
    }

    public void showFroyoOrder(View view) {
        Intent intent = new Intent(this, FroyoActivity.class);
        intent.putExtra(EXTRA_MESSAGE, getString(R.string.froyo));
        startActivity(intent);
    }
}