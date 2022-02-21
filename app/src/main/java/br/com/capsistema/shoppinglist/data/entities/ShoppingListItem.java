package br.com.capsistema.shoppinglist.data.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "shopping_list_item",
        primaryKeys = {"shopping_list_id", "item_id"},
        foreignKeys = {
                @ForeignKey(
                        entity = ShoppingList.class,
                        parentColumns = "shopping_list_id",
                        childColumns = "shopping_list_id",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = Item.class,
                        parentColumns = "item_id",
                        childColumns = "item_id")
        }
)
public class ShoppingListItem {
    @NonNull
    @ColumnInfo(name = "shopping_list_id")
    public String shoppingListId;

    @NonNull
    @ColumnInfo(name = "item_id")
    public String itemId;

    public ShoppingListItem(@NonNull String shoppingListId, @NonNull String itemId) {
        this.shoppingListId = shoppingListId;
        this.itemId = itemId;
    }
}
