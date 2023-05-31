package com.example.lr7.adapter;

import androidx.cardview.widget.CardView;

import com.example.lr7.Recipe;

public interface RecipesClickListener {

    void onLongClick (Recipe recipe, CardView cardView);

    void onClick (Recipe recipe);
}
