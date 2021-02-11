package com.example.nastoyshiishashlik.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.nastoyshiishashlik.App;
import com.example.nastoyshiishashlik.R;
import com.example.nastoyshiishashlik.models.CartHelper;
import com.example.nastoyshiishashlik.models.CartItemsEntityModel;
import com.example.nastoyshiishashlik.utils.CropSquareTransformation;
import com.example.nastoyshiishashlik.utils.OptimizationBitmap;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final String TAG = CartRecyclerAdapter.class.getCanonicalName();

    private List<CartItemsEntityModel> productEntityModel;
    private OnItemClickListener onItemClickListener;
    private Context context;

    public CartRecyclerAdapter(Context context, List<CartItemsEntityModel> productEntityModel) {
        this.context = context;
        this.productEntityModel = productEntityModel;
    }

    public void updateItem(int position, CartItemsEntityModel cartItemsEntityModel) {
        if(cartItemsEntityModel.getQuantity() > 0) {
            productEntityModel.set(position, cartItemsEntityModel);
            CartHelper.getCart().update(cartItemsEntityModel.getProduct(), cartItemsEntityModel.getQuantity());
        }
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new ReceiveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ReceiveViewHolder viewHolder = (ReceiveViewHolder) holder;
        viewHolder.name.setText(productEntityModel.get(position).getProduct().getName());

        viewHolder.price.setText(String.format(context.getString(R.string.price_cart_format),
                productEntityModel.get(position).getProduct().getPrice()));

        viewHolder.finalPrice.setText(String.format(context.getString(R.string.total_price_cart_format),
                productEntityModel.get(position).getProduct().getFinalPrice()));

        viewHolder.quantity.setText(String.valueOf(productEntityModel.get(position).getQuantity()));
        Picasso.with(App.getContext())
                .load(productEntityModel.get(position).getProduct().getImage())
                .transform(new CropSquareTransformation())
                .into(viewHolder.image);

    }

    @Override
    public int getItemCount() {
        return productEntityModel.size();
    }

    public class ReceiveViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.final_price)
        TextView finalPrice;
        @BindView(R.id.quantity)
        TextView quantity;
        @BindView(R.id.plus)
        ImageButton plus;
        @BindView(R.id.minus)
        ImageButton minus;
        @BindView(R.id.cancel)
        ImageButton cansel;

        public ReceiveViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(view -> {
                onItemClickListener.onItemClick(productEntityModel.get(getAdapterPosition()));
            });
            minus.setOnClickListener(view -> {
                onItemClickListener.onItemMinusClicked(getAdapterPosition(), productEntityModel.get(getAdapterPosition()));
            });
            plus.setOnClickListener(view -> {
                onItemClickListener.onItemPlusClicked(getAdapterPosition(), productEntityModel.get(getAdapterPosition()));
            });
            cansel.setOnClickListener(v -> {
                CartHelper.getCart().remove(productEntityModel.get(getAdapterPosition()).getProduct());
                onItemClickListener.onUpdateList();
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(CartItemsEntityModel cartItemsEntityModel);
        void onItemPlusClicked(int position, CartItemsEntityModel cartItemsEntityModel);
        void onItemMinusClicked(int position, CartItemsEntityModel cartItemsEntityModel);
        void onUpdateList();
    }
}