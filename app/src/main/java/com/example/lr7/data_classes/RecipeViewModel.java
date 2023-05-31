package com.example.lr7.data_classes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lr7.Recipe;

import java.util.List;

public class RecipeViewModel extends ViewModel {

    private MutableLiveData<List<Recipe>> data = new MutableLiveData<>();

    public LiveData<List<Recipe>> getData() {
        return data;
    }

    public void updateData(List<Recipe> newData) {
        data.setValue(newData);
    }
}
