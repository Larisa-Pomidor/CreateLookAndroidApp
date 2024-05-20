package com.example.createlookandroidapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.stream.Collectors;

import db.Category;
import db.CategoryClothesViewModel;
import db.Clothes;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView categoryView;
    RecyclerView clothesView;
    LinearLayoutManager linearLayoutManager;
    ClothesAdapter clothesAdapter;
    CategoryClothesViewModel categoryClothesViewModel;

    List<Category> categoryObjectList;
    List<String> categoryList;
    ArrayAdapter<String> arrayAdapter;
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

        categoryClothesViewModel = new ViewModelProvider(this).get(CategoryClothesViewModel.class);

        categoryClothesViewModel.getAllCategories().observe(this, categories -> {
            arrayAdapter = new ArrayAdapter<String>(this,
                    R.layout.activity_category_item_detail, R.id.category_text_view,
                    categories.stream().map(Category::getName).collect(Collectors.toList()));
            categoryView = findViewById(R.id.category_list);
            categoryView.setOnItemClickListener(this);
            categoryView.setAdapter(arrayAdapter);
        });
    }

    public void onItemClick(AdapterView<?> l, View v, int position, long id) {

        categoryClothesViewModel.getClothesByCategory(position + 1)
                .observe(this, clothes -> {
                    clothesView = findViewById(R.id.clothes_list);

                    FrameLayout imageContainer = findViewById(R.id.image_container);

                    linearLayoutManager = new LinearLayoutManager(MainActivity.this,
                            LinearLayoutManager.HORIZONTAL, false);

                    int[] clothesImages = clothes.stream()
                            .mapToInt(clothesItem -> getDrawableResourceId(clothesItem.url))
                            .toArray();

                    clothesAdapter = new ClothesAdapter(clothesImages, imageContainer);

                    clothesView.setLayoutManager(linearLayoutManager);
                    clothesView.setAdapter(clothesAdapter);
                });
    }

    private int getDrawableResourceId(String drawableName) {
        return getResources().getIdentifier(drawableName, "drawable", getPackageName());
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