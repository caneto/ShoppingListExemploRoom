package br.com.capsistema.shoppinglist.editshoppinglist;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import br.com.capsistema.shoppinglist.R;

public class EditShoppingListActivity extends AppCompatActivity {

    public static final String EXTRA_SHOPPING_LIST_ID = "com.develou.shoppinglist.shoppingListId";
    private EditShoppingListViewModel mViewModel;
    private ActionBar mActionBar;
    private RecyclerView mItemsList;
    private ItemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_shopping_list);


        ViewModelProvider.AndroidViewModelFactory factory
                = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication());
        mViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) factory)
                .get(EditShoppingListViewModel.class);

        // Obtener id de la lista de compras
        String id = getIntent().getStringExtra(EXTRA_SHOPPING_LIST_ID);

        // Cargar lista
        mViewModel.start(id);

        setupActionBar();
        setupItemsList();
        subscribeToUi();
    }

    private void subscribeToUi() {
        mViewModel.getShoppingList().observe(this,
                shoppingList -> {
                    mActionBar.setTitle(shoppingList.shoppingList.name);
                    mAdapter.setItems(shoppingList.items);
                }
        );
    }

    private void setupActionBar() {
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void setupItemsList() {
        mItemsList = findViewById(R.id.items_list);
        mAdapter = new ItemAdapter();
        mItemsList.setAdapter(mAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}