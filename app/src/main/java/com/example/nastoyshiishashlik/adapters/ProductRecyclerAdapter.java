package com.example.nastoyshiishashlik.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.nastoyshiishashlik.App;
import com.example.nastoyshiishashlik.R;
import com.example.nastoyshiishashlik.models.ProductModel;
import com.example.nastoyshiishashlik.ui.ProductActivity;
import com.example.nastoyshiishashlik.utils.OptimizationBitmap;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProductRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final String TAG = ProductRecyclerAdapter.class.getCanonicalName();
    private final Activity context;
    private final List<ProductModel> catalogModels;

    private OnItemClickListener onItemClickListener;

    public ProductRecyclerAdapter(Activity context, List<ProductModel> catalogModels) {
        this.context = context;
        this.catalogModels = catalogModels;
    }

    public void updateItem(int position, ProductModel productModel) {
        if(productModel.getQuantity() > 0) {
            catalogModels.set(position, productModel);
        }
        notifyItemChanged(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ReceiveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ReceiveViewHolder viewHolder = (ReceiveViewHolder) holder;
        viewHolder.title.setText(catalogModels.get(position).getName());

        switch (Double.toString(catalogModels.get(position).getWeight())){
            case ("0.5") :
                viewHolder.weightPrice.setText(String.format(context.getString(R.string.weight_price_litr05_format),
                        catalogModels.get(position).getPrice(), catalogModels.get(position).getWeight()));
                break;
            case ("0.25") :
                viewHolder.weightPrice.setText(String.format(context.getString(R.string.weight_price_litr025_format),
                        catalogModels.get(position).getPrice(), catalogModels.get(position).getWeight()));
                break;
            case ("1.0") :
                viewHolder.weightPrice.setText(String.format(context.getString(R.string.weight_price_shtuka_format),
                        catalogModels.get(position).getPrice(), catalogModels.get(position).getWeight()));
                break;
            default:
                viewHolder.weightPrice.setText(String.format(context.getString(R.string.weight_price_gramm_format),
                        catalogModels.get(position).getPrice(), catalogModels.get(position).getWeight()));
                break;
        }

        if(catalogModels.get(position).getMinWeightForOrder() > catalogModels.get(position).getWeight()){
            viewHolder.minWeight.setText(String.format(context.getString(R.string.min_weight_format),
                    catalogModels.get(position).getMinWeightForOrder()));
            viewHolder.minWeight.setVisibility(View.VISIBLE);
        }

        viewHolder.price.setText(String.format(context.getString(R.string.price_format),
                catalogModels.get(position).getFinalPrice()));

        viewHolder.quantity.setText(String.valueOf(catalogModels.get(position).getQuantity()));

        OptimizationBitmap.optimizationBitmap(
                catalogModels.get(position).getPoster(), 400, 200)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bitmap -> {
                    viewHolder.image.setImageBitmap(bitmap);
                    Log.d(TAG, "bind: optimization poster for hits is successful");
                }, throwable -> Log.e(TAG, "bind: optimization poster for hits isn't successful"));

    }

    @Override
    public int getItemCount() {
        return catalogModels.size();
    }

    public class ReceiveViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.weight_price)
        TextView weightPrice;
        @BindView(R.id.min_weight)
        TextView minWeight;
        @BindView(R.id.quantity)
        TextView quantity;
        @BindView(R.id.plus)
        ImageButton plus;
        @BindView(R.id.minus)
        ImageButton minus;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.buyButton)
        Button buyButton;

        public ReceiveViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            buyButton.setOnClickListener(view -> {
                onItemClickListener.onItemClick(catalogModels.get(getAdapterPosition()));
            });
            minus.setOnClickListener(view -> {
                onItemClickListener.onItemMinusClicked(getAdapterPosition(), catalogModels.get(getAdapterPosition()));
            });
            plus.setOnClickListener(view -> {
                onItemClickListener.onItemPlusClicked(getAdapterPosition(), catalogModels.get(getAdapterPosition()));
            });
            image.setOnClickListener(v -> {
                Intent intent = new Intent(context, ProductActivity.class);
                context.finish();
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
                intent.putExtra("product", catalogModels.get(getAdapterPosition()));
                context.startActivity(intent);
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(ProductModel productModel);
        void onItemPlusClicked(int position, ProductModel productModel);
        void onItemMinusClicked(int position, ProductModel productModel);
    }
}