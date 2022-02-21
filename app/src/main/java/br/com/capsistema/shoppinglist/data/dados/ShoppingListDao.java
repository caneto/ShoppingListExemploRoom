package br.com.capsistema.shoppinglist.data.dados;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import br.com.capsistema.shoppinglist.data.entities.Colaborador;
import br.com.capsistema.shoppinglist.data.entities.Info;
import br.com.capsistema.shoppinglist.data.entities.ShoppingList;
import br.com.capsistema.shoppinglist.data.partialentities.ShoppingListId;
import br.com.capsistema.shoppinglist.data.partialentities.ShoppingListInsert;
import br.com.capsistema.shoppinglist.data.relationentities.ShoppingListWithCollaborators;
import br.com.capsistema.shoppinglist.data.relationentities.ShoppingListWithItems;

import java.util.List;

@Dao
public abstract class ShoppingListDao {
    @Transaction
    @Query("SELECT * FROM v_full_shopping_lists ORDER BY created_date DESC")
    public abstract LiveData<List<ShoppingListWithCollaborators>> shoppingLists();

    @Transaction
    @Query("SELECT * FROM shopping_list WHERE shopping_list_id = :id")
    public abstract LiveData<ShoppingListWithItems> shoppingListWithItems(String id);

    @Transaction
    @Query("SELECT * FROM v_full_shopping_lists WHERE category IN(:categories)" +
            "ORDER BY created_date DESC")
    public abstract LiveData<List<ShoppingListWithCollaborators>> getShoppingListsByCategories(List<String> categories);

    @Transaction
    public void insertWithInfoAndCollaborators(ShoppingListInsert shoppingList,
                                               Info info, List<Colaborador> collaborators) {
        insertShoppingList(shoppingList);
        insertInfo(info);
        insertAllCollaborators(collaborators);
    }

    @Transaction
    public void insertAllWithInfosAndCollaborators(List<ShoppingListInsert> shoppingLists,
                                                   List<Info> infos,
                                                   List<Colaborador> collaborators) {
        insertAll(shoppingLists);
        insertAllInfos(infos);
        insertAllCollaborators(collaborators);
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract void insertAllCollaborators(List<Colaborador> collaborators);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract void insertShoppingList(ShoppingList shoppingList);

    @Insert(onConflict = OnConflictStrategy.IGNORE, entity = ShoppingList.class)
    abstract void insertShoppingList(ShoppingListInsert shoppingList);

    @Insert(onConflict = OnConflictStrategy.IGNORE, entity = ShoppingList.class)
    abstract void insertAll(List<ShoppingListInsert> lists);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract void insertInfo(Info info);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract void insertAllInfos(List<Info> infos);

    @Transaction
    public void markFavorite(String id) {
        updateShoppingListFavorite(id);
        updateInfoLastUpdated(id);
    }

    @Query("UPDATE shopping_list SET is_favorite= NOT is_favorite WHERE shopping_list_id = :id")
    protected abstract void updateShoppingListFavorite(String id);

    @Query("UPDATE info SET last_updated = CURRENT_TIMESTAMP WHERE shopping_list_id=:shoppingListId")
    protected abstract void updateInfoLastUpdated(String shoppingListId);

    @Delete(entity = ShoppingList.class)
    public abstract void deleteShoppingList(ShoppingListId id);

    @Query("DELETE FROM shopping_list")
    public abstract void deleteAll();
}
