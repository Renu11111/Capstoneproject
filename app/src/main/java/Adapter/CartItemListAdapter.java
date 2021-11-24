package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartItemListAdapter<CartItem> extends RecyclerView.Adapter<CartItemListAdapter.CartItemViewHolder> {
    Context context;
    List<CartItem> cartItemList;

    public CartItemListAdapter(Context context,List<CartItem> cartItemList){
        this.context = context;
        this.cartItemList = cartItemList;
    }

    @NonNull
    @Override
    public CartItemListAdapter.CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item_row,parent,false);
        return new CartItemListAdapter.CartItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemListAdapter.CartItemViewHolder holder, int position) {
        CartItem item = cartItemList.get(position);
        holder.txt1.setText(item.name);
        holder.txt2.setText("Price: $" + item.price);
        holder.txt3.setText("Quantity: " + item.quantity);
        holder.txt4.setText("Total Price: $" + item.total_price);
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }
    public class CartItemViewHolder extends RecyclerView.ViewHolder{

        TextView txt1,txt2,txt3,txt4;

        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            txt1 = itemView.findViewById(R.id.txt_product_name);
            txt2 = itemView.findViewById(R.id.txt_price);
            txt3 = itemView.findViewById(R.id.txt_quantity);
            txt4 = itemView.findViewById(R.id.txt_total_price);
        }
    }
}
