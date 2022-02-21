package br.com.capsistema.shoppinglist.data.entities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "shopping_list")
public class ShoppingList {
    @NonNull
    @PrimaryKey
    @ColumnInfo (name = "shopping_list_id")
    public final String id;

    @NonNull
    public final String name;

    public final String category;

    @ColumnInfo(name = "is_favorite", defaultValue = "0")
    public final boolean favorite;

    public ShoppingList(@NonNull String id, @NonNull String name,
                        @Nullable String category, boolean favorite) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.favorite = favorite;
    }

}
