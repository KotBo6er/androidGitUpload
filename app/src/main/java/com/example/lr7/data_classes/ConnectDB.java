package com.example.lr7.data_classes;

import android.content.Context;
import android.os.AsyncTask;

import com.example.lr7.Recipe;

import java.util.List;

public class ConnectDB {

    private static final String TAG = "ConnectDB";

    public static void getRecipes(Context context, OnRecipesLoadedListener listener) {
        new AsyncTask<Void, Void, List<Recipe>>() {
            @Override
            protected List<Recipe> doInBackground(Void... voids) {
                AppDatabase database = AppDatabase.getInstance(context);
                RecipeDao recipeDao = database.recipeDao();
                return recipeDao.getAllRecipes();
            }

            @Override
            protected void onPostExecute(List<Recipe> recipes) {
                listener.onRecipesLoaded(recipes);
            }
        }.execute();
    }

    public interface OnRecipesLoadedListener {
        void onRecipesLoaded(List<Recipe> recipes);
    }

    public static void addRecipe(Context context, Recipe newRecipe) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                AppDatabase database = AppDatabase.getInstance(context);
                RecipeDao recipeDao = database.recipeDao();
                recipeDao.addRecipe(newRecipe);
                return null;
            }
        }.execute();
    }

    public static void updateRecipe(Context context, Recipe updatedRecipe) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                AppDatabase database = AppDatabase.getInstance(context);
                RecipeDao recipeDao = database.recipeDao();
                recipeDao.updateRecipe(updatedRecipe);
                return null;
            }
        }.execute();
    }

    public static void deleteRecipe(Context context, Recipe recipe) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                AppDatabase database = AppDatabase.getInstance(context);
                RecipeDao recipeDao = database.recipeDao();
                recipeDao.deleteRecipe(recipe);
                return null;
            }
        }.execute();
    }
}
