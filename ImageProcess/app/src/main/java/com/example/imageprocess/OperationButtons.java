package com.example.imageprocess;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imageprocess.ImageOperation.ImageOperator;
import com.example.imageprocess.ImageOperation.ImageProcessor;
import com.example.imageprocess.pixels.Pixels;

import java.util.Stack;

public abstract class OperationButtons extends Activity {
    protected Button blur = null;
    protected Button sharpen = null;
    protected Button sepia = null;
    protected Button greyScale = null;
    protected Button mosaic = null;
    protected Button undo = null;
    Stack<Bitmap> undoLine = new Stack<>();

    PopupWindow popupWindow;
    protected ImageOperator operator;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);
        blur = super.findViewById(R.id.blur);
        sharpen = super.findViewById(R.id.sharpen);
        sepia = super.findViewById(R.id.sepia);
        greyScale = super.findViewById(R.id.greyScale);
        mosaic = super.findViewById(R.id.mosaic);
        undo = super.findViewById(R.id.undo);


        operator = new ImageProcessor();

        View.OnClickListener listener = new OperationButtons.Operation(this);

        blur.setOnClickListener(listener);
        sharpen.setOnClickListener(listener);
        sepia.setOnClickListener(listener);
        greyScale.setOnClickListener(listener);
        mosaic.setOnClickListener(listener);
        undo.setOnClickListener(listener);

    }

    private class Operation implements View.OnClickListener {
        Bitmap bitmap;
        OperationButtons buttons;

        private Operation(OperationButtons bt) {
            buttons = bt;
        }

        public void onClick(View view) {
            if (view.getId() == R.id.undo ) {
                if (!undoLine.empty()) {
                    bitmap = undoLine.pop();
                    operator.setPicture(bitmap);
                    buttons.setProcessedPicture(bitmap);

                }
                return;
            }
            bitmap = buttons.getPictureView();
            undoLine.push(bitmap);
            operator.setPicture(bitmap);
            if (view.getId() == R.id.mosaic) {
                showPopup();

            } else {

                operate(view);
                bitmap = operator.getPicture();
                buttons.setProcessedPicture(bitmap);
                Toast.makeText(getApplicationContext(), "Processed！", Toast.LENGTH_SHORT).show();
            }

        }


        private void operate(View view) {
            switch (view.getId()) {
                case R.id.blur:
                    operator.blur();
                    break;
                case R.id.sharpen:
                    operator.sharpening();
                    break;
                case R.id.sepia:
                    operator.sepia();
                    break;
                case R.id.greyScale:
                    operator.greyScale();
                    break;


            }

        }
    }

    public void showPopup() {

        // Container layout to hold other components
        LinearLayout llContainer = new LinearLayout(this);

        // Set its orientation to vertical to stack item
        llContainer.setOrientation(LinearLayout.VERTICAL);

        // Container layout to hold EditText and Button
        LinearLayout llContainerInline = new LinearLayout(this);

        // Set its orientation to horizontal to place components next to each other
        llContainerInline.setOrientation(LinearLayout.HORIZONTAL);

        // EditText to get input
        final EditText etInput = new EditText(this);

        // TextView to show an error message when the user does not provide input
        final TextView tvError = new TextView(this);

        // For when the user is done
        Button bDone = new Button(this);

// If tvError is showing, make it disappear
        etInput.setOnClickListener(v -> tvError.setVisibility(View.GONE));

        // This is what will show in etInput when the Popup is first created
        etInput.setHint("Please provide a number");

        // Input type allowed: Numbers
        etInput.setRawInputType(Configuration.KEYBOARD_12KEY);

        // Center text inside EditText
        etInput.setGravity(Gravity.CENTER);

        // tvError should be invisible at first
        tvError.setVisibility(View.GONE);

        bDone.setText("Done");
        bDone.setOnClickListener(v -> {

            // If user didn't input anything, show tvError
            if (etInput.getText().toString().equals("")) {
                tvError.setText("Please enter a valid value");
                tvError.setVisibility(View.VISIBLE);
                etInput.setText("");

                // else, call method `doneInput()` which we will define later
            } else {
                doneInput(etInput.getText().toString());
                popupWindow.dismiss();
            }
        });

        // Define LayoutParams for tvError
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.topMargin = 20;

        // Define LayoutParams for InlineContainer
        LinearLayout.LayoutParams layoutParamsForInlineContainer = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParamsForInlineContainer.topMargin = 30;

        // Define LayoutParams for EditText
        LinearLayout.LayoutParams layoutParamsForInlineET = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        // Set ET's weight to 1 // Take as much space horizontally as possible
        layoutParamsForInlineET.weight = 1;

        // Define LayoutParams for Button
        LinearLayout.LayoutParams layoutParamsForInlineButton = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        // Set Button's weight to 0
        layoutParamsForInlineButton.weight = 0;

        // Add etInput to inline container
        llContainerInline.addView(etInput, layoutParamsForInlineET);

        // Add button with layoutParams // Order is important
        llContainerInline.addView(bDone, layoutParamsForInlineButton);

        // Add tvError with layoutParams
        llContainer.addView(tvError, layoutParams);

        // Finally add the inline container to llContainer
        llContainer.addView(llContainerInline, layoutParamsForInlineContainer);

        // Set gravity
        llContainer.setGravity(Gravity.CENTER);

        // Set any color to Container's background
        llContainer.setBackgroundColor(0x95000000);

        // Create PopupWindow
        popupWindow = new PopupWindow(llContainer,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        // Should be focusable
        popupWindow.setFocusable(true);

        // Show the popup window
        popupWindow.showAtLocation(llContainer, Gravity.CENTER, 0, 0);

    }

    public void doneInput(String input) {
        int bias = Integer.parseInt(input);
        operator.mosaic(bias);
        Bitmap bitmap = operator.getPicture();
        this.setProcessedPicture(bitmap);
        Toast.makeText(getApplicationContext(), "Processed！", Toast.LENGTH_SHORT).show();
        popupWindow.dismiss();
        // Work with it // For example, show a Toast
//        Toast.makeText(this, "Number input by user was: " + bias, Toast.LENGTH_LONG).show();
        // Do anything else with input!
    }

    protected abstract Bitmap getPictureView();

    protected abstract void setProcessedPicture(Bitmap bitmap);
}
