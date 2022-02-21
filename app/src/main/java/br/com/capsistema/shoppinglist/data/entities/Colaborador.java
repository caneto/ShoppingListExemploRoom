package br.com.capsistema.shoppinglist.data.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "colaborador",
        foreignKeys = @ForeignKey(
                entity = ShoppingList.class,
                parentColumns = "shopping_list_id",
                childColumns = "shopping_list_id",
                onDelete = ForeignKey.SET_NULL)
)
public class Colaborador {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "colaborador_id")
    public String id;

    public String name;

    @ColumnInfo(name = "shopping_list_id")
    public String shoppingListId;

    public Colaborador(@NonNull String id, String name, String shoppingListId) {
        this.id = id;
        this.name = name;
        this.shoppingListId = shoppingListId;
    }
}
