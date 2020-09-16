package edu.ranken.gmiller.navdrawerexperiment;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get widgets
        Toolbar toolbar = findViewById(R.id.toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Enable the action bar
        setSupportActionBar(toolbar);

        // Add drawer toggle button
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Register a listener for the drawer menu items
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    displayToast(getString(R.string.menu_home));
                    return true;
                case R.id.nav_gallery:
                    displayToast(getString(R.string.menu_gallery));
                    return true;
                case R.id.nav_slideshow:
                    displayToast(getString(R.string.menu_slideshow));
                    return true;
                case R.id.nav_tools:
                    displayToast(getString(R.string.menu_tools));
                    return true;
                case R.id.nav_share:
                    displayToast(getString(R.string.menu_share));
                    return true;
                case R.id.nav_send:
                    displayToast(getString(R.string.menu_send));
                    return true;
                default:
                    return false;
            }
        });
    }

    private void displayToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
