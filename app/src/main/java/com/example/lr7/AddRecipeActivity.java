package com.example.lr7;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lr7.data_classes.ConnectDB;
import com.example.lr7.utils.ImageManager;
import com.example.lr7.utils.Utils;

public class AddRecipeActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST_CODE = 12;
    private static final String TAG = "AddRecipeActivity";
    private EditText editTextTitle;
    private EditText editTextTime;
    private EditText editTextDescription;
    private ImageView imageView;
    private Button buttonAdd;
    private boolean isUpdating = false;
    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        editTextTitle = findViewById(R.id.editText_title);
        editTextTime = findViewById(R.id.editText_time);
        editTextDescription = findViewById(R.id.editText_description);
        imageView = findViewById(R.id.imageView);
        buttonAdd = findViewById(R.id.button_add);

        buttonAdd.setOnClickListener(v -> {
            if(isUpdating){
                editRecipe();
            } else {
                addRecipe();
            }
        });

        imageView.setOnClickListener(v -> openImagePicker());

        editTextTitle.addTextChangedListener(textWatcher);
        editTextTime.addTextChangedListener(textWatcher);
        editTextDescription.addTextChangedListener(textWatcher);

        buttonAdd.setEnabled(true);
        editTextTitle.setEnabled(true);

        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("recipe")) {
            recipe = (Recipe) intent.getSerializableExtra("recipe");
            isUpdating = true;

            editTextTitle.setText(recipe.getTitle());
            editTextTime.setText(recipe.getTime());
            editTextDescription.setText(recipe.getText());

            Bitmap bitmap = ImageManager.getImage(this, recipe.getImage());
            imageView.setImageBitmap(bitmap);

            buttonAdd.setText("Изменить");
        }
    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            boolean isFieldsFilled = !editTextTitle.getText().toString().isEmpty()
                    && !editTextTime.getText().toString().isEmpty()
                    && !editTextDescription.getText().toString().isEmpty();

            boolean isFieldsValid = Utils.isTimeValid(editTextTime.getText().toString());

//            buttonAdd.setEnabled(isFieldsFilled && isFieldsValid);
        }
    };

    private void addRecipe() {
        Bitmap bitmap = imageView.getDrawingCache();
        String imageName = ImageManager.saveImage(this, bitmap);

        recipe = new Recipe(editTextTitle.getText().toString(),
                editTextDescription.getText().toString(),
                editTextTime.getText().toString(),
                imageName);

        ConnectDB.addRecipe(this, recipe);

        finishWithResultOK();
    }

    private void editRecipe() {

        Bitmap bitmap = imageView.getDrawingCache();
        String imageName = ImageManager.saveImage(this, bitmap);

        Recipe updatedRecipe = new Recipe(recipe.getId(), editTextTitle.getText().toString(),
                editTextDescription.getText().toString(),
                editTextTime.getText().toString(),
                imageName);

        ConnectDB.updateRecipe(this, updatedRecipe);

        finishWithResultOK();
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            imageView.setImageURI(data.getData());
        }
    }

    private void finishWithResultOK(){
        Intent resultIntent = new Intent();
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}