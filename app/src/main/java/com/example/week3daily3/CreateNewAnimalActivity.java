package com.example.week3daily3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.week3daily3.database.Animal;
import com.example.week3daily3.database.DBHelper;

import java.util.ArrayList;

public class CreateNewAnimalActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText name, url, sound;
    private ListView typeListView;
    private Button button;
    private ArrayList<String> typesList;
    private String selectedType;
    private DBHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_animal);
        database = new DBHelper(this);
        initViews();
        initListAndAdapter();
    }

    private void initListAndAdapter() {
        typesList = new ArrayList<>();
        typesList.add("Mammal");
        typesList.add("Oviparous");

        ArrayAdapter<String> arrayAdapter
                = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, typesList);

        typeListView.setAdapter(arrayAdapter);
        typeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedType = typeListView.getAdapter().getItem(position).toString();
                Log.d("Log.d", "Selected Item: " + selectedType);
            }
        });
    }

    private void initViews() {
        name = findViewById(R.id.name);
        url = findViewById(R.id.url);
        sound = findViewById(R.id.sound);
        typeListView = findViewById(R.id.typeListView);
        button = findViewById(R.id.createNewAnimal);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (!viewsAreEmpty()) {
            String typeStr = selectedType;
            String nameStr = name.getText().toString();
            String soundStr = sound.getText().toString();
            String urlStr = url.getText().toString();
            database.insertAnimalToDB(new Animal(typeStr, nameStr, soundStr, urlStr));
            Toast.makeText(this, "Animal Created", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public boolean viewsAreEmpty() {
        if (selectedType == null) {
            Toast.makeText(this, "Select a type first", Toast.LENGTH_SHORT).show();
        }

        return name.getText().toString().isEmpty() ||
                url.getText().toString().isEmpty() ||
                sound.getText().toString().isEmpty() ||
                selectedType == null;
    }
}
