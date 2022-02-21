package br.com.capsistema.shoppinglist.data.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Item {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "item_id")
    public String id;

    @NonNull
    public String name;

    public Item(@NonNull String id, @NonNull String name) {
        this.id = id;
        this.name = name;
    }
}
