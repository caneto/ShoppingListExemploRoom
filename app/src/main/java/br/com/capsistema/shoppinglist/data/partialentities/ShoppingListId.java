package br.com.capsistema.shoppinglist.data.partialentities;

import androidx.room.ColumnInfo;

public class ShoppingListId {
    @ColumnInfo(name = "shopping_list_id")
    public String id;

    public ShoppingListId(String id) {
        this.id = id;
    }
}
