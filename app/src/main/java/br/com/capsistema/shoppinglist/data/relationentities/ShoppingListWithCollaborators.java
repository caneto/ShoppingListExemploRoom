package br.com.capsistema.shoppinglist.data.relationentities;

import androidx.room.Embedded;
import androidx.room.Relation;

import br.com.capsistema.shoppinglist.data.entities.Colaborador;
import br.com.capsistema.shoppinglist.data.views.ShoppingListView;

import java.util.List;

public class ShoppingListWithCollaborators {
    @Embedded
    public ShoppingListView shoppingList;

    @Relation(
            entity = Colaborador.class,
            parentColumn = "shopping_list_id",
            entityColumn = "shopping_list_id",
            projection = {"name"}
    )
    public List<String> colaboradorNames;
}
