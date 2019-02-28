package com.example.week3daily3.recycler;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.week3daily3.AnimalDetailsActivity;
import com.example.week3daily3.R;
import com.example.week3daily3.database.Animal;
import com.example.week3daily3.database.DBHelper;

import java.util.List;

public class AnimalsAdapter extends RecyclerView.Adapter<AnimalsAdapter.ViewHolder> {
    private List<Animal> animalList;
    private Context context;
    private DBHelper database;

    public AnimalsAdapter(Context context, List<Animal> animalList) {
        this.animalList = animalList;
        this.context = context;
        database = new DBHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.animal_item, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Animal animal = animalList.get(i);
        viewHolder.name.setText(animal.getName());
        viewHolder.type.setText(animal.getType());
        viewHolder.animalId.setText("ID: " + animal.getId());
        viewHolder.sound.setText("Sound: " + animal.getSound());
        Glide.with(context).load(animal.getImageUrl()).placeholder(R.drawable.nophoto).centerCrop().into(viewHolder.image);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AnimalDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("animal", animal);
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return animalList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name, type, animalId, sound;
        public ImageView image;
        public ConstraintLayout viewBackground, viewForeground;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.animalImage);
            name = itemView.findViewById(R.id.name);
            type = itemView.findViewById(R.id.type);
            animalId = itemView.findViewById(R.id.animalId);
            sound = itemView.findViewById(R.id.sound);
            viewBackground = itemView.findViewById(R.id.view_background);
            viewForeground = itemView.findViewById(R.id.view_foreground);
        }
    }

    public void removeItem(int position) {
        // The removal from database happens in main activity
        animalList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Animal animal, int position) {
        animalList.add(position, animal);
        notifyItemInserted(position);
    }
}
