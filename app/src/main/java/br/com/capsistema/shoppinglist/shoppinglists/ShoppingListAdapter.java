package br.com.capsistema.shoppinglist.shoppinglists;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.com.capsistema.shoppinglist.R;
import br.com.capsistema.shoppinglist.data.relationentities.ShoppingListWithCollaborators;

import java.util.List;

public class ShoppingListAdapter
        extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingListViewHolder> {

    private List<ShoppingListWithCollaborators> mShoppingLists;
    private ItemListener mItemListener;

    @NonNull
    @Override
    public ShoppingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ShoppingListViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.shopping_list_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingListViewHolder holder, int position) {
        ShoppingListWithCollaborators item = mShoppingLists.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return mShoppingLists == null ? 0 : mShoppingLists.size();
    }

    public void setItems(List<ShoppingListWithCollaborators> items) {
        mShoppingLists = items;
        notifyDataSetChanged();
    }

    public void setItemListener(ItemListener listener) {
        mItemListener = listener;
    }

    interface ItemListener {
        void onClick(ShoppingListWithCollaborators shoppingList);

        void onFavoriteIconClicked(ShoppingListWithCollaborators shoppingList);

        void onDeleteIconClicked(ShoppingListWithCollaborators shoppingList);
    }

    public class ShoppingListViewHolder extends RecyclerView.ViewHolder {
        private final TextView mNameText;
        private final CheckBox mFavoriteButton;
        private final ImageView mDeleteButton;
        private final TextView mCreatedDateText;
        private final TextView mCollaboratorsText;
        private final TextView mItemsCount;

        public ShoppingListViewHolder(@NonNull View itemView) {
            super(itemView);
            mNameText = itemView.findViewById(R.id.name);
            mCreatedDateText = itemView.findViewById(R.id.created_date);
            mFavoriteButton = itemView.findViewById(R.id.favorite_button);
            mDeleteButton = itemView.findViewById(R.id.delete_button);
            mCollaboratorsText = itemView.findViewById(R.id.collaborator_names);
            mItemsCount = itemView.findViewById(R.id.items_count);

            // Setear eventos
            mFavoriteButton.setOnClickListener(this::manageEvents);
            mDeleteButton.setOnClickListener(this::manageEvents);
            itemView.setOnClickListener(this::manageEvents);
        }

        private void manageEvents(View view) {
            if (mItemListener != null) {
                ShoppingListWithCollaborators clickedItem = mShoppingLists.get(getAdapterPosition());

                // Manejar evento de click en Favorito
                if (view.getId() == R.id.favorite_button) {
                    mItemListener.onFavoriteIconClicked(clickedItem);
                    return;
                } else if (view.getId() == R.id.delete_button) {
                    mItemListener.onDeleteIconClicked(clickedItem);
                    return;
                }

                mItemListener.onClick(clickedItem);
            }
        }

        public void bind(ShoppingListWithCollaborators item) {
            mNameText.setText(item.shoppingList.name);
            mFavoriteButton.setChecked(item.shoppingList.favorite);
            mCreatedDateText.setText(item.shoppingList.createdDate);
            mCollaboratorsText.setText(TextUtils.join(",", item.colaboradorNames));
            mItemsCount.setText(String.valueOf(item.shoppingList.itemsCount));
        }
    }
}
