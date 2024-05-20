package db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Clothes {
    public Clothes(@NonNull String url) {this.url = url;}

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @PrimaryKey(autoGenerate = true)
    public int clothesId;

    @ColumnInfo(name = "url")
    public String url;
}
