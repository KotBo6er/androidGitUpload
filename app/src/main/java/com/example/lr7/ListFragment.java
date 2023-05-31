package com.example.lr7;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.lr7.adapter.RecipeAdapter;
import com.example.lr7.adapter.RecipesClickListener;
import com.example.lr7.data_classes.ConnectDB;
import com.example.lr7.data_classes.RecipeViewModel;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment implements ConnectDB.OnRecipesLoadedListener, PopupMenu.OnMenuItemClickListener{

    private final static String TAG = "ListFragment";
    private List<Recipe> recipes;
    private RecipeAdapter recipeAdapter;
    private RecyclerView recyclerRecipes;
    private RecipeViewModel recipeViewModel;
    private RecipesClickListener listener;
    private Recipe selectedRecipe;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerRecipes = view.findViewById(R.id.recycler_recipes);

        listener = new RecipesClickListener() {
            @Override
            public void onLongClick(Recipe recipe, CardView cardView) {
                selectedRecipe = recipe;
                showPopUp(cardView);
            }

            @Override
            public void onClick(Recipe recipe) {
                onRecipeClick(recipe);
            }
        };

        updateRecyclerView();

        setRecipes();

        registerForContextMenu(recyclerRecipes);

        recipeViewModel = new RecipeViewModel();
        recipeViewModel.getData().observe(getViewLifecycleOwner(), recipe -> {
            recipes.clear();
            recipes.addAll(recipe);
            recipeAdapter.notifyDataSetChanged();
        });

        return view;
    }

    private void setRecipes() {
        ConnectDB.getRecipes(getContext(), this);
    }

    public void onRecipeClick(Recipe recipe) {
        Bundle args = new Bundle();
        args.putSerializable("recipe", recipe);

        NavHostFragment.findNavController(this)
                .navigate(R.id.action_ListFragment_to_DetailFragment, args);
    }

    @Override
    public void onRecipesLoaded(List<Recipe> recipes) {
        recipeViewModel.updateData(recipes);
    }

    private void updateRecyclerView() {
        recipes = new ArrayList<>();
        recyclerRecipes.setHasFixedSize(true);
        recyclerRecipes.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        recipeAdapter = new RecipeAdapter(requireContext(), recipes, listener);
        recyclerRecipes.setAdapter(recipeAdapter);
    }

    private void showPopUp(CardView cardView) {

        PopupMenu popupMenu = new PopupMenu(requireContext(), cardView);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.context_menu);
        popupMenu.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        setRecipes();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_edit:
                Intent intent = new Intent(requireContext(), AddRecipeActivity.class);
                intent.putExtra("recipe", selectedRecipe);
                startActivityForResult(intent, MainActivity.EDIT_RECIPE_REQUEST_CODE);
                return true;
            case R.id.menu_delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Удаление записи");
                builder.setMessage("Вы уверены, что хотите удалить эту запись?");
                builder.setPositiveButton("Удалить", (dialog, which) -> {
                    ConnectDB.deleteRecipe(this.getContext(), selectedRecipe);
                    setRecipes();
                });
                builder.setNegativeButton("Отмена", null);
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}