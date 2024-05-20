package db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Category {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category(@NonNull String name) {this.name = name;}
    @PrimaryKey(autoGenerate = true)
    public int categoryId;

    @ColumnInfo(name = "name")
    public String name;

}
