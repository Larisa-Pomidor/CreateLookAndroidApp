package db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CategoryClothesDao {
    @Query("SELECT * FROM Category")
    LiveData<List<Category>> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertCategory(Category category);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertClothes(Clothes clothes);

    @Insert
    void insertCategoryClothes(CategoryClothes categoryClothes);

    @Query("SELECT * FROM Clothes WHERE clothesId IN (SELECT clothesId FROM CategoryClothes WHERE categoryId = :categoryId)")
    LiveData<List<Clothes>> getClothesBbyCategory(int categoryId);

}
