package br.com.capsistema.shoppinglist.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import br.com.capsistema.shoppinglist.Utils;
import br.com.capsistema.shoppinglist.data.dados.ItemDao;
import br.com.capsistema.shoppinglist.data.dados.ShoppingListDao;
import br.com.capsistema.shoppinglist.data.dados.ShoppingListItemDao;
import br.com.capsistema.shoppinglist.data.entities.Colaborador;
import br.com.capsistema.shoppinglist.data.entities.Info;
import br.com.capsistema.shoppinglist.data.entities.Item;
import br.com.capsistema.shoppinglist.data.entities.ShoppingList;
import br.com.capsistema.shoppinglist.data.entities.ShoppingListItem;
import br.com.capsistema.shoppinglist.data.partialentities.ShoppingListInsert;
import br.com.capsistema.shoppinglist.data.views.ShoppingListView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {
        ShoppingList.class,
        Info.class,
        Colaborador.class,
        Item.class,
        ShoppingListItem.class},
        views = ShoppingListView.class,
        version = 7, exportSchema = false)
public abstract class ShoppingListDatabase extends RoomDatabase {

    // Exposición de DAOs
    public abstract ShoppingListDao shoppingListDao();

    public abstract ItemDao itemDao();

    public abstract ShoppingListItemDao shoppingListItemDao();

    private static final String DATABASE_NAME = "shopping-list-db";

    private static ShoppingListDatabase INSTANCE;

    private static final int THREADS = 4;

    public static final ExecutorService dbExecutor = Executors.newFixedThreadPool(THREADS);

    public static ShoppingListDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (ShoppingListDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(), ShoppingListDatabase.class,
                            DATABASE_NAME)
                            .addCallback(new Callback() {
                                @Override
                                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                                    super.onOpen(db);
                                    dbExecutor.execute(() -> prepopulate(context));
                                }
                            })
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static void prepopulate(Context context) {
        // Obtener instancias de Daos
        ShoppingListDao shoppingListDao = INSTANCE.shoppingListDao();
        ItemDao itemDao = INSTANCE.itemDao();
        ShoppingListItemDao shoppingListItemDao = INSTANCE.shoppingListItemDao();

        List<ShoppingListInsert> lists = new ArrayList<>();
        List<Info> infos = new ArrayList<>();
        List<Colaborador> colaboradors = new ArrayList<>();
        List<Item> items = new ArrayList<>();
        List<ShoppingListItem> shoppingListItems = new ArrayList<>();

        for (int i = 0; i < 5; i++) {

            String dummyId = String.valueOf((i + 1));

            // Crear lista de compras
            ShoppingListInsert shoppingList = new ShoppingListInsert(
                    dummyId,
                    "Lista " + (i + 1)
            );

            // Crear info
            String date = Utils.getCurrentDate();
            Info info = new Info(
                    shoppingList.id, date, date);

            // Crear colaborador
            Colaborador colaborador = new Colaborador(dummyId,
                    "Colaborador " + dummyId, dummyId);

            // Crear ítems de la lista
            for (int j = 0; j < 5; j++) {
                Item item = new Item(dummyId + (j + 1), "Item #" + (j + 1));

                // Crear filas de "lista <contiene> item"
                ShoppingListItem shoppingListItem = new ShoppingListItem(shoppingList.id, item.id);

                items.add(item);
                shoppingListItems.add(shoppingListItem);
            }

            lists.add(shoppingList);
            infos.add(info);
            colaboradors.add(colaborador);

        }


        // Crear transacción para llamar DAOs
        getInstance(context).runInTransaction(() -> {
            shoppingListDao.insertAllWithInfosAndCollaborators(lists, infos, colaboradors);
            itemDao.insertAll(items);
            shoppingListItemDao.insertAll(shoppingListItems);
        });
    }

}
