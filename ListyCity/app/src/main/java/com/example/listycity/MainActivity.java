package com.example.listycity;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView cityList;
    private ArrayAdapter<City> cityAdapter;
    private ArrayList<City> dataList;
    private int selectedPos = AdapterView.INVALID_POSITION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityList = findViewById(R.id.city_List);
        cityList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        dataList = new ArrayList<>();
        dataList.add(new City("Edmonton"));
        dataList.add(new City("Vancouver"));

        cityAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_activated_1, dataList
        );
        cityList.setAdapter(cityAdapter);

        cityList.setOnItemClickListener((p, v, position, id) -> {
            selectedPos = position;
            cityList.setItemChecked(position, true);
        });

        findViewById(R.id.btn_add).setOnClickListener(v -> {
            EditText input = new EditText(this);
            input.setHint("City name");

            new AlertDialog.Builder(this)
                    .setTitle("Add City")
                    .setView(input)
                    .setPositiveButton("Confirm", (d, which) -> {
                        String name = input.getText().toString().trim();
                        if (name.isEmpty()) { toast("Enter a city"); return; }
                        for (City c : dataList)
                            if (c.getName().equalsIgnoreCase(name)) { toast("Already in list"); return; }
                        dataList.add(new City(name));
                        cityAdapter.notifyDataSetChanged();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        findViewById(R.id.btn_delete).setOnClickListener(v -> {
            if (selectedPos == AdapterView.INVALID_POSITION) { toast("Select a city first"); return; }
            new AlertDialog.Builder(this)
                    .setTitle("Delete City")
                    .setMessage("Remove " + dataList.get(selectedPos).getName() + "?")
                    .setPositiveButton("Delete", (d, w) -> {
                        dataList.remove(selectedPos);
                        selectedPos = AdapterView.INVALID_POSITION;
                        cityList.clearChoices();
                        cityAdapter.notifyDataSetChanged();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}