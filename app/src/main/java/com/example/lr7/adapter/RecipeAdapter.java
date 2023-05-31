package com.example.lr7.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lr7.utils.ImageManager;
import com.example.lr7.R;
import com.example.lr7.Recipe;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipesViewHolder>{

    Context context;
    List<Recipe> list;

    RecipesClickListener listener;

    public RecipeAdapter(Context context, List<Recipe> list, RecipesClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecipesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecipesViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_recipe, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesViewHolder holder, int position) {
        Recipe record = list.get(position);

        holder.textView_title.setText(record.getTitle());
        holder.textView_title.setSelected(true);

        holder.textView_description.setText(record.getTitle());
        holder.textView_description.setSelected(true);

        holder.textView_time.setText(record.getTime());
        holder.textView_time.setSelected(true);

        String imageName = record.getImage();
        Bitmap bitmap = ImageManager.getImage(context, imageName);
        holder.imageView_image.setImageBitmap(bitmap);

        holder.cardView.setOnClickListener(v ->
                listener.onClick(list.get(holder.getAdapterPosition())));

        holder.cardView.setOnLongClickListener(v -> {
            listener.onLongClick(list.get(holder.getAdapterPosition()), holder.cardView);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class RecipesViewHolder extends RecyclerView.ViewHolder{

    TextView textView_title;
    TextView textView_description;
    TextView textView_time;
    ImageView imageView_image;
    CardView cardView;

    public RecipesViewHolder(@NonNull View itemView) {
        super(itemView);

        textView_title = itemView.findViewById(R.id.textview_title);
        textView_description = itemView.findViewById(R.id.textview_text);
        textView_time = itemView.findViewById(R.id.textview_time);
        imageView_image = itemView.findViewById(R.id.imageview_recipe);
        cardView = itemView.findViewById(R.id.cardView);
    }
}
