package db;

import androidx.room.Entity;

@Entity(primaryKeys = {"categoryId", "clothesId"})
    public class CategoryClothes {
    public CategoryClothes(long categoryId, long clothesId) {
        this.categoryId = categoryId;
        this.clothesId = clothesId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public long getClothesId() {
        return clothesId;
    }

    public void setClothesId(long clothesId) {
        this.clothesId = clothesId;
    }

    public long categoryId;
        public long clothesId;
    }
