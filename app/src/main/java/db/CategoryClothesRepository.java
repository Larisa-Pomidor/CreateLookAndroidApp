package db;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CategoryClothesRepository {

    CategoryClothesDao categoryClothesDao;
    public CategoryClothesRepository(Application application) {
        DBConnection db = DBConnection.getDatabase(application.getApplicationContext());
        categoryClothesDao = db.categoryDao();
    }

    public LiveData<List<Category>> getAllCategories() {
        return categoryClothesDao.getAll();
    }

    public void insertCategory(Category category) {
        DBConnection.databaseWriteExecutor.execute(() -> {
            categoryClothesDao.insertCategory(category);
        });
    }

    public void insertClothes(Clothes clothes) {
        DBConnection.databaseWriteExecutor.execute(() -> {
            categoryClothesDao.insertClothes(clothes);
        });
    }

    public LiveData<List<Clothes>> getClothesByCategory(int categoryId) {
        return categoryClothesDao.getClothesBbyCategory(categoryId);
    }
}
