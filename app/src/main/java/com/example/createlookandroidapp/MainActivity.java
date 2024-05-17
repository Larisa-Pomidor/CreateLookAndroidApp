package com.example.createlookandroidapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    List<String> categoryList;
    ListView categoryView;
    RecyclerView clothesView;
    LinearLayoutManager linearLayoutManager;
    ClothesAdapter clothesAdapter;
    int[] clothesImages = {R.drawable.look_1, R.drawable.look_2, R.drawable.look_3, R.drawable.look_4,
            R.drawable.look_5, R.drawable.look_6, R.drawable.look_7, R.drawable.look_8, R.drawable.look_9};

    int[] clothesImages2 = {R.drawable.look_2, R.drawable.look_9};

    int[] background = {R.drawable.background_1, R.drawable.background_2, R.drawable.background_3};
    int currentBackgroundIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        categoryView = findViewById(R.id.category_list);
        categoryView.setOnItemClickListener(this);
        initCategories();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                R.layout.activity_category_item_detail, R.id.category_text_view, categoryList);
        categoryView.setAdapter(arrayAdapter);

        clothesView = findViewById(R.id.clothes_list);
        linearLayoutManager = new LinearLayoutManager(MainActivity.this,
                LinearLayoutManager.HORIZONTAL, false);
        clothesAdapter = new ClothesAdapter(clothesImages);

        clothesView.setLayoutManager(linearLayoutManager);
        clothesView.setAdapter(clothesAdapter);
    }

    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
        Log.i("HelloListView", "You clicked Item: " + id + " at position:" + position);

        clothesAdapter = new ClothesAdapter(clothesImages2);
        clothesView.setAdapter(clothesAdapter);
    }

    private void initCategories() {
        categoryList = new ArrayList<>();
        categoryList.add("Shoes");
        categoryList.add("Accessories");
        categoryList.add("Dresses");
        categoryList.add("Pants");
    }

    public void toggleCategories(View v) {
        Button categoryToggleButton = findViewById(R.id.category_toggle_button);
        int categoryListWidth = categoryView.getWidth();
        if (categoryView.getTranslationX() == 0) {
            categoryView.setTranslationX(-categoryListWidth);
            categoryToggleButton.setTranslationX(-categoryListWidth);
        } else {
            categoryView.setTranslationX(0);
            categoryToggleButton.setTranslationX(0);
        }
    }

    public void toggleClothes(View v) {
        Button clothesToggleButton = findViewById(R.id.clothes_toggle_button);
        int clothesListHeight = clothesView.getHeight();
        if (clothesView.getTranslationY() == 0) {
            clothesView.setTranslationY(clothesListHeight);
            clothesToggleButton.setTranslationY(clothesListHeight);
        } else {
            clothesView.setTranslationY(0);
            clothesToggleButton.setTranslationY(0);
        }
    }

    public void toggleBackground(View v) {
        ImageView backgroundImage = findViewById(R.id.background_image);
        backgroundImage.setImageResource(background[(++currentBackgroundIndex) % (background.length)]);
    }
}