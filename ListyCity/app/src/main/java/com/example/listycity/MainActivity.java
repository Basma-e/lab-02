package com.example.listycity;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView cityList = findViewById(R.id.city_List);
        cityList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        ArrayList<City> dataList = new ArrayList<>();
        dataList.add(new City("Edmonton")); dataList.add(new City("Vancouver"));

        ArrayAdapter<City> cityAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, dataList);
        cityList.setAdapter(cityAdapter);

        final int[] selectedPos = {AdapterView.INVALID_POSITION};
        cityList.setOnItemClickListener((p, v, position, id) -> {
            selectedPos[0] = position;
            cityList.setItemChecked(position, true); // highlights selected row
        });

        findViewById(R.id.btn_add).setOnClickListener(v -> {
            EditText input = new EditText(this);
            input.setHint("City name");

            new AlertDialog.Builder(this)
                    .setTitle("Add City")
                    .setView(input)
                    .setPositiveButton("Confirm", (d, which) -> {
                        String name = input.getText().toString().trim();
                        if (name.isEmpty()) { Toast.makeText(this, "Enter a city", Toast.LENGTH_SHORT).show(); return; }
                        // optional: prevent duplicates
                        for (City c : dataList) if (c.getName().equalsIgnoreCase(name)) {
                            Toast.makeText(this, "Already in list", Toast.LENGTH_SHORT).show(); return;
                        }
                        dataList.add(new City(name));
                        cityAdapter.notifyDataSetChanged();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        findViewById(R.id.btn_delete).setOnClickListener(v -> {
            if (selectedPos[0] == AdapterView.INVALID_POSITION) {
                Toast.makeText(this, "Select a city first", Toast.LENGTH_SHORT).show(); return;
            }
            new AlertDialog.Builder(this)
                    .setTitle("Delete City")
                    .setMessage("Remove " + dataList.get(selectedPos[0]).getName() + "?")
                    .setPositiveButton("Delete", (d, w) -> {
                        dataList.remove(selectedPos[0]);
                        selectedPos[0] = AdapterView.INVALID_POSITION;
                        cityList.clearChoices();
                        cityAdapter.notifyDataSetChanged();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });




    }
}