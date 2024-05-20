package db;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CategoryClothesViewModel extends AndroidViewModel {

    private final LiveData<List<Category>> allCategories;
    CategoryClothesRepository categoryClothesRepository;
    public CategoryClothesViewModel(Application application) {
        super(application);
        categoryClothesRepository = new CategoryClothesRepository(application);
        allCategories = categoryClothesRepository.getAllCategories();
    }

    public LiveData<List<Category>> getAllCategories() {
        return allCategories;
    }

    public LiveData<List<Clothes>> getClothesByCategory(int categoryId) {
        return categoryClothesRepository.getClothesByCategory(categoryId);
    }
}
