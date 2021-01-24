package com.example.nastoyshiishashlik.service;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.example.nastoyshiishashlik.ui.ProductActivity;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SentencesAdapter extends RecyclerView.Adapter<SentencesAdapter.SentencesViewHolder> {
    private final String TAG = ProductAdapter.class.getCanonicalName();
    private final List<Product> products;

    public SentencesAdapter(List<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public SentencesAdapter.SentencesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sentences,
                viewGroup, false);
        return new SentencesAdapter.SentencesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SentencesViewHolder holder, int position) {
        holder.bind(products.get(position));

        holder.posterImageView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onClick(View v) {
                //Initialize list for products
                Product product = products.get(position);
                //Initialization intent for creating activity for displaying list dishes
                Intent intent = new Intent(App.getContext(), ProductActivity.class);
                intent.putExtra(TAG, product);
                App.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }


    static final class SentencesViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = ProductAdapter.ProductViewHolder.class.getCanonicalName();
        private final ImageView posterImageView;
        private final TextView nameTextView;
        private final TextView weightAndPriceTextView;
        private final TextView priceTextView;



        public SentencesViewHolder(@NonNull View itemView) {
            super(itemView);
            this.posterImageView = itemView.findViewById(R.id.hits_products__iv_poster);
            this.nameTextView = itemView.findViewById(R.id.hits_products__tv_name);
            this.weightAndPriceTextView = itemView.findViewById(R.id.hits_products_tv_weight_price);
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
            priceTextView.setText(String.format(
                    App.getContext().getResources().getString(R.string.main_activity__total_price),
                    product.getFinalPrice()
            ));
        }
    }


}
