package com.example.week3daily3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.week3daily3.database.Animal;

import java.util.Random;

public class AnimalDetailsActivity extends AppCompatActivity {

    private TextView name, type, animalId, population, sound;
    private ImageView imageView;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_details);

        initViews();

        intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            Animal animal = bundle.getParcelable("animal");
            pupulateViews(animal);
        }
    }

    private void pupulateViews(Animal animal) {
        name.setText(animal.getName());
        type.setText(animal.getType());
        animalId.setText("ID: " + animal.getId());
        sound.setText("Sound: " + animal.getSound());

        Random random = new Random();
        int totalPopulation = random.nextInt(10000000) + 1;
        population.setText("Global population: " + totalPopulation);

        Glide.with(this).load(animal.getImageUrl()).centerCrop().into(imageView);
    }

    private void initViews() {
        name = findViewById(R.id.name);
        type = findViewById(R.id.type);
        animalId = findViewById(R.id.animalId);
        population = findViewById(R.id.population);
        imageView = findViewById(R.id.animalImage);
        sound = findViewById(R.id.sound);
    }
}
