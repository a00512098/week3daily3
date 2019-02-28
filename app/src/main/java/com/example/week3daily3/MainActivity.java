package com.example.week3daily3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.week3daily3.database.Animal;
import com.example.week3daily3.database.DBHelper;
import com.example.week3daily3.recycler.AnimalsAdapter;
import com.example.week3daily3.recycler.RecyclerItemTouchHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    private List<Animal> animalList;
    private RecyclerView recyclerView;
    private ConstraintLayout mainLayout;
    private AnimalsAdapter adapter;
    private Button button;
    private SharedPreferences sharedPreferences;
    private DBHelper database;

    private boolean firstTimeRunning;

    private final String PREFERENCES = "PREFERENCES";
    private final String FIRST_RUN = "FIRST_RUN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();

        database = new DBHelper(this);

        sharedPreferences = getSharedPreferences(PREFERENCES, MODE_PRIVATE);

        animalList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new AnimalsAdapter(this, animalList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        firstTimeRunning = sharedPreferences.getBoolean(FIRST_RUN, true);
        prepareAnimalsDataForFirstRun(firstTimeRunning);

        button = findViewById(R.id.createNew);
        button.setOnClickListener(this);

        mainLayout = findViewById(R.id.main_layout);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
    }

    private void prepareAnimalsDataForFirstRun(boolean firstRun) {
        if (firstTimeRunning) {
            database.insertAnimalToDB(new Animal("Mammal", "Lion", "Roar", "https://upload.wikimedia.org/wikipedia/commons/thumb/7/73/Lion_waiting_in_Namibia.jpg/1200px-Lion_waiting_in_Namibia.jpg"));
            database.insertAnimalToDB(new Animal("Mammal", "Elephant", "Trrrr", "https://mondrian.mashable.com/uploads%252Fcard%252Fimage%252F887009%252F49d251da-d190-49d8-80ad-5f12221574b4.jpg%252F950x534__filters%253Aquality%252890%2529.jpg?signature=uKR_hXmFN6Tggkc-7UZZ4sipcZw=&source=https%3A%2F%2Fblueprint-api-production.s3.amazonaws.com"));
            database.insertAnimalToDB(new Animal("Mammal", "Polar Bear", "Roar", "https://upload.wikimedia.org/wikipedia/commons/thumb/6/66/Polar_Bear_-_Alaska_%28cropped%29.jpg/220px-Polar_Bear_-_Alaska_%28cropped%29.jpg"));
            database.insertAnimalToDB(new Animal("Oviparous", "Seahorse", "None", "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4b/Hippocampus.jpg/220px-Hippocampus.jpg"));
            database.insertAnimalToDB(new Animal("Oviparous", "Shark", "Crunch", "https://fsmedia.imgix.net/4c/1d/cd/a8/1498/44a1/997e/f3cdbef8f22a/a-baby-great-white-shark.jpeg?rect=0%2C59%2C1920%2C959&auto=format%2Ccompress&dpr=2&w=650"));
            database.insertAnimalToDB(new Animal("Mammal", "Grizzly Bear", "Roar", "https://upload.wikimedia.org/wikipedia/commons/thumb/1/1a/Grizzly_Bear_Family_in_Glacier_National_Park.jpg/1200px-Grizzly_Bear_Family_in_Glacier_National_Park.jpg"));
            database.insertAnimalToDB(new Animal("Mammal", "Coyote", "Ouuuuuuu", "https://cdn.britannica.com/s:300x300/45/125545-004-BB8F48B2.jpg"));
            database.insertAnimalToDB(new Animal("Mammal", "Sea Otter", "Splash", "http://b50ym1n8ryw31pmkr4671ui1c64-wpengine.netdna-ssl.com/wp-content/blogs.dir/11/files/2012/09/Sea-Otter-LeDent-300x254.jpg"));
            database.insertAnimalToDB(new Animal("Mammal", "Cheetah", "Roar", "https://upload.wikimedia.org/wikipedia/commons/thumb/3/36/Cheetah_%28Acinonyx_jubatus%29_female_2.jpg/330px-Cheetah_%28Acinonyx_jubatus%29_female_2.jpg"));

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(FIRST_RUN, false);
            editor.apply();
        }

        getListOfAnimalsFromDB();
    }

    private void getListOfAnimalsFromDB() {
        List<Animal> animals = database.getAllAnimalsInDB();
        animalList.clear();
        for (Animal animal : animals) {
            animalList.add(animal);
            Log.d("Log.d", "Name: " + animal.getName());
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getListOfAnimalsFromDB();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, CreateNewAnimalActivity.class);
        startActivity(intent);
    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof AnimalsAdapter.ViewHolder) {
            String name = animalList.get(viewHolder.getAdapterPosition()).getName();

            final Animal deletedAnimal = animalList.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            adapter.removeItem(viewHolder.getAdapterPosition());

            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar
                    .make(mainLayout, name + " removed from cart!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is selected, restore the deleted item
                    adapter.restoreItem(deletedAnimal, deletedIndex);
                }
            });
            snackbar.addCallback(new Snackbar.Callback() {
                @Override
                public void onDismissed(Snackbar transientBottomBar, int event) {
                    super.onDismissed(transientBottomBar, event);
                    // Remove animal from DB
                    if (event != Snackbar.Callback.DISMISS_EVENT_ACTION) {
                        database.deleteFromDatabaseById(deletedAnimal.getId());
                    }
                }
            });

            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }
}
