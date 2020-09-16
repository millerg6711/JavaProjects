package edu.ranken.gmiller.drawabletest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ImageView mBatteryImage;

    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBatteryImage = findViewById(R.id.battery_level_image);
        count = 1;
    }

    public void removeLevel(View view) {
        if (count > 0 ) {
            count--;
        }
        updateLevel();
    }

    public void addLevel(View view) {
         if (count < 6) {
             count++;
         }
         updateLevel();
    }

    public void updateLevel() {
        mBatteryImage.setImageLevel(count);
        Log.d("COUNT", Integer.toString(count));
    }
}
