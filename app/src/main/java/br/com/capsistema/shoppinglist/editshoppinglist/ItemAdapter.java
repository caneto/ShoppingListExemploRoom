package br.com.capsistema.shoppinglist.editshoppinglist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.com.capsistema.shoppinglist.R;
import br.com.capsistema.shoppinglist.data.entities.Item;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private List<Item> mItems;

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(
                parent.getContext()).inflate(
                R.layout.list_item,
                parent,
                false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = mItems.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    public void setItems(List<Item> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView mNameText;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mNameText = itemView.findViewById(R.id.item_name);
        }

        public void bind(Item item) {
            mNameText.setText(item.name);
        }
    }
}
