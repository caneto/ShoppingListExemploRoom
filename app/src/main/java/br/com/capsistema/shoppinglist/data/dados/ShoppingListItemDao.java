package br.com.capsistema.shoppinglist.data.dados;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import br.com.capsistema.shoppinglist.data.entities.ShoppingListItem;

import java.util.List;

@Dao
public abstract class ShoppingListItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insertAll(List<ShoppingListItem> shoppingListItems);
}
