package db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Category.class, Clothes.class, CategoryClothes.class}, version = 10, exportSchema = false)
public abstract class DBConnection extends RoomDatabase {
    public abstract CategoryClothesDao categoryDao();

    private static volatile DBConnection INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static DBConnection getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DBConnection.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    DBConnection.class, "look-database")
                            //.fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                CategoryClothesDao categoryClothesDao = INSTANCE.categoryDao();

                // Insert categories
                Category category1 = new Category("Shoes");
                Category category2 = new Category("Shoes1");
                Category category3 = new Category("Shoes2");
                Category category4 = new Category("Shoes3");

                long categoryId1 = categoryClothesDao.insertCategory(category1);
                long categoryId2 = categoryClothesDao.insertCategory(category2);
                long categoryId3 = categoryClothesDao.insertCategory(category3);
                long categoryId4 = categoryClothesDao.insertCategory(category4);

                // Insert clothes
                Clothes clothes1 = new Clothes("look_1");
                Clothes clothes2 = new Clothes("look_2");
                Clothes clothes3 = new Clothes("look_3");
                Clothes clothes4 = new Clothes("look_4");

                long clothesId1 = categoryClothesDao.insertClothes(clothes1);
                long clothesId2 = categoryClothesDao.insertClothes(clothes2);
                long clothesId3 = categoryClothesDao.insertClothes(clothes3);
                long clothesId4 = categoryClothesDao.insertClothes(clothes4);

                // Insert category-clothes relationships
                CategoryClothes categoryClothes1 = new CategoryClothes(categoryId1, clothesId1);
                CategoryClothes categoryClothes2 = new CategoryClothes(categoryId2, clothesId2);
                CategoryClothes categoryClothes3 = new CategoryClothes(categoryId3, clothesId3);
                CategoryClothes categoryClothes4 = new CategoryClothes(categoryId4, clothesId4);

                categoryClothesDao.insertCategoryClothes(categoryClothes1);
                categoryClothesDao.insertCategoryClothes(categoryClothes2);
                categoryClothesDao.insertCategoryClothes(categoryClothes3);
                categoryClothesDao.insertCategoryClothes(categoryClothes4);
            });
        }
    };
}
