package br.com.capsistema.shoppinglist.data;

import android.content.Context;

import androidx.lifecycle.LiveData;

import br.com.capsistema.shoppinglist.data.dados.ItemDao;
import br.com.capsistema.shoppinglist.data.dados.ShoppingListDao;
import br.com.capsistema.shoppinglist.data.dados.ShoppingListItemDao;
import br.com.capsistema.shoppinglist.data.entities.Colaborador;
import br.com.capsistema.shoppinglist.data.entities.Info;
import br.com.capsistema.shoppinglist.data.entities.Item;
import br.com.capsistema.shoppinglist.data.entities.ShoppingListItem;
import br.com.capsistema.shoppinglist.data.partialentities.ShoppingListId;
import br.com.capsistema.shoppinglist.data.partialentities.ShoppingListInsert;
import br.com.capsistema.shoppinglist.data.relationentities.ShoppingListWithCollaborators;
import br.com.capsistema.shoppinglist.data.relationentities.ShoppingListWithItems;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListRepository {
    private final ShoppingListDao mShoppingListDao;
    private final ShoppingListDatabase mDb;
    private final ItemDao mItemDao;
    private final ShoppingListItemDao mShoppingListItemDao;

    public ShoppingListRepository(Context context) {
        mDb = ShoppingListDatabase.getInstance(context);
        mShoppingListDao = mDb.shoppingListDao();
        mItemDao = mDb.itemDao();
        mShoppingListItemDao = mDb.shoppingListItemDao();
    }

    public LiveData<List<ShoppingListWithCollaborators>> getShoppingLists() {
        return mShoppingListDao.shoppingLists();
    }

    public LiveData<List<ShoppingListWithCollaborators>> getShoppingListsWithCategories(List<String> categories) {
        return mShoppingListDao.getShoppingListsByCategories(categories);
    }

    public LiveData<ShoppingListWithItems> getShoppingList(String id) {
        return mShoppingListDao.shoppingListWithItems(id);
    }

    public void insert(ShoppingListInsert shoppingList, Info info,
                       List<Colaborador> colaboradors, List<Item> items) {
        ShoppingListDatabase.dbExecutor.execute(
                () -> mDb.runInTransaction(
                        () -> processInsert(shoppingList, info, colaboradors, items)
                )
        );
    }

    private void processInsert(ShoppingListInsert shoppingList, Info info,
                               List<Colaborador> colaboradors, List<Item> items) {
        // Insertar lista de compras
        mShoppingListDao.insertWithInfoAndCollaborators(shoppingList, info, colaboradors);

        // Insertar items
        mItemDao.insertAll(items);

        // Generar registros de relación
        List<ShoppingListItem> shoppingListItems = new ArrayList<>();
        for (Item item : items) {
            shoppingListItems.add(new ShoppingListItem(shoppingList.id, item.id));
        }

        // Insertar registros de relación
        mShoppingListItemDao.insertAll(shoppingListItems);
    }

    public void markFavorite(String shoppingListId) {
        ShoppingListDatabase.dbExecutor.execute(
                () -> mShoppingListDao.markFavorite(shoppingListId)
        );
    }

    public void deleteShoppingList(ShoppingListId id) {
        ShoppingListDatabase.dbExecutor.execute(
                () -> mShoppingListDao.deleteShoppingList(id)
        );
    }

    public void deleteAll() {
        ShoppingListDatabase.dbExecutor.execute(
                mShoppingListDao::deleteAll
        );
    }
}
