package com.example.lr7;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_RECIPE_REQUEST_CODE = 1;
    public static final int EDIT_RECIPE_REQUEST_CODE = 2;
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_add) {
            openAddRecordActivity();
            return true;
        } else if(item.getItemId() == R.id.menu_up){
            RecyclerView recyclerRecipes = findViewById(R.id.recycler_recipes);
            recyclerRecipes.smoothScrollToPosition(0);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openAddRecordActivity() {
        Intent intent = new Intent(this, AddRecipeActivity.class);
        startActivityForResult(intent, ADD_RECIPE_REQUEST_CODE);
    }
}