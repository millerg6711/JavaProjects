package edu.ranken.gmiller.hot2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "RectangleCalcActivity";

    private EditText mWidthInput;
    private EditText mHeightInput;
    private TextView mAreaOutput;
    private TextView mPerimeterOutput;
    private TextView mErrorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWidthInput = findViewById(R.id.width_edit_text);
        mHeightInput = findViewById(R.id.height_edit_text);
        mAreaOutput = findViewById(R.id.area_output_text_view);
        mPerimeterOutput = findViewById(R.id.perimeter_output_text_view);
        mErrorMessage = findViewById(R.id.error_text_view);
    }

    public void calcRectangle(View view) {
        // Clear error messages
        mErrorMessage.setText(R.string.empty_message);

        double width;
        double height;
        try {
            width = getOperand(mWidthInput);
            height = getOperand(mHeightInput);
        } catch (NumberFormatException ex) {
            Log.e(TAG, "NumberFormatException", ex);
            mErrorMessage.setText(R.string.computation_error);
            return;
        } catch (IllegalArgumentException ex) {
            Log.e(TAG,"IllegalArgumentException", ex);
            mErrorMessage.setText(R.string.computation_error);
            return;
        }

        double area = width * height;
        double perimeter = 2 * width + 2 * height;

        mAreaOutput.setText(
            String.format(Locale.ENGLISH,"%.2f sq ft", area));
        mPerimeterOutput.setText(
            String.format(Locale.ENGLISH, "%.2f ft", perimeter));

        Toast toast = Toast.makeText(this, R.string.toast_message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.show();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    private static Double getOperand(EditText operandEditText) throws IllegalArgumentException {
        String operandText = getOperandText(operandEditText);

        if ("".equals(operandText) || ".".equals(operandText)) {
            throw new IllegalArgumentException("Please enter a number.");
        }

        return Double.valueOf(operandText);
    }

    private static String getOperandText(EditText operandEditText) {
        return operandEditText.getText().toString();
    }
}
