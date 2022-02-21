package br.com.capsistema.shoppinglist.data.views;

import androidx.room.ColumnInfo;
import androidx.room.DatabaseView;

@DatabaseView(
        value = "SELECT l.*, i.created_date, COUNT(*) as itemsCount " +
                "FROM shopping_list l " +
                "INNER JOIN info i " +
                "USING(shopping_list_id) " +
                "INNER JOIN shopping_list_item " +
                "USING(shopping_list_id) " +
                "INNER JOIN item " +
                "USING(item_id)" +
                "GROUP BY l.shopping_list_id",
        viewName = "v_full_shopping_lists"
)
public class ShoppingListView {
    @ColumnInfo(name = "shopping_list_id")
    public String id;

    public String name;

    public String category;

    @ColumnInfo(name = "is_favorite")
    public boolean favorite;

    @ColumnInfo(name = "created_date")
    public String createdDate;

    public int itemsCount;
}
