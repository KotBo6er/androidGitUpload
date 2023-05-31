package com.example.lr7;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.lr7.utils.ImageManager;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class DetailFragment extends Fragment {

    private TextView textViewDescription;
    private TextView textZag;

    private TextView textTime;

    private Recipe recipe;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        if (getArguments() != null) {
            recipe = (Recipe) getArguments().getSerializable("recipe");
        }
        textZag = view.findViewById(R.id.textView);
        textTime = view.findViewById(R.id.textTime);
        textViewDescription = view.findViewById(R.id.textview_description_detail);
        CollapsingToolbarLayout toolbar = view.findViewById(R.id.toolbar_layout_detail);

        if (recipe != null) {
            toolbar.setTitle(recipe.getTitle());
            textViewDescription.setText(recipe.getText());
            textZag.setText(recipe.getTitle());
            textTime.setText(recipe.getTime());
            String image = recipe.getImage();
            Bitmap bitmap = ImageManager.getImage(requireContext(), image);
            toolbar.setBackground(new BitmapDrawable(getResources(), bitmap));
            toolbar.setTitle(recipe.getText());
        }

        AppBarLayout appBarLayout = view.findViewById(R.id.app_bar_fragment);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isImageShown = false;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    if (!isImageShown) {
                        isImageShown = true;
                        toolbar.setVisibility(View.VISIBLE);
                    }
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    if (isImageShown) {
                        isImageShown = false;
                        toolbar.setVisibility(View.GONE);
                    }
                }
            }
        });

        return view;
    }
}