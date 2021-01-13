package com.example.nastoyshiishashlik.hitsProductsBar;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nastoyshiishashlik.App;
import com.example.nastoyshiishashlik.R;
import com.example.nastoyshiishashlik.model.Product;
import com.example.nastoyshiishashlik.optimization.OptimizationBitmap;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private final String TAG = ProductAdapter.class.getSimpleName();
    private final List<Product> products;

    public ProductAdapter(List<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_main__hits_products,
                viewGroup, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int position) {
        productViewHolder.bind(products.get(position));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    static final class ProductViewHolder extends RecyclerView.ViewHolder{
        private static final String TAG = ProductViewHolder.class.getCanonicalName();
        private final ImageView posterImageView;
        private final TextView nameTextView;
        private final TextView weightAndPriceTextView;
        private final TextView minWeightTextView;
        private final TextView priceTextView;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            this.posterImageView = itemView.findViewById(R.id.hits_products__iv_poster);
            this.nameTextView = itemView.findViewById(R.id.hits_products__tv_name);
            this.weightAndPriceTextView = itemView.findViewById(R.id.hits_products_tv_weight_price);
            this.minWeightTextView = itemView.findViewById(R.id.hits_products_tv_weight);
            this.priceTextView = itemView.findViewById(R.id.hits_products_tv_total_price);
        }

        @SuppressLint("CheckResult")
        private void bind(@NonNull Product product){
            OptimizationBitmap optimizationBitmap = new OptimizationBitmap();
            optimizationBitmap.optimizationBitmap(product.getPoster(), 400, 200)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(bitmap -> {
                        posterImageView.setImageBitmap(bitmap);
                        Log.d(TAG, "bind: optimization poster for hits is successful");
                    }, throwable -> Log.e(TAG, "bind: optimization poster for hits isn't successful"));

            nameTextView.setText(product.getName());

            weightAndPriceTextView.setText(String.format(
                    App.getContext().getResources().getString(R.string.main_activity__weight_price),
                    product.getPrice(), product.getWeight()
            ));
            minWeightTextView.setText(String.format(
                    App.getContext().getResources().getString(R.string.main_activity__min_weight),
                    product.getMinWeightForOrder()
            ));
            priceTextView.setText(String.format(
                    App.getContext().getResources().getString(R.string.main_activity__total_price),
                    product.getFinalPrice()
            ));
        }
    }

}
