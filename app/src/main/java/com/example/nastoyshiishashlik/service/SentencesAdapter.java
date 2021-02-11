/*
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
import com.example.nastoyshiishashlik.models.ProductModel;
import com.example.nastoyshiishashlik.ui.ProductActivity;
import com.example.nastoyshiishashlik.utils.OptimizationBitmap;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SentencesAdapter extends RecyclerView.Adapter<SentencesAdapter.SentencesViewHolder> {
    private final String TAG = ProductAdapter.class.getCanonicalName();
    private final List<ProductModel> productModels;

    public SentencesAdapter(List<ProductModel> productModels) {
        this.productModels = productModels;
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
        holder.bind(productModels.get(position));

        holder.posterImageView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onClick(View v) {
                //Initialize list for products
                ProductModel productModel = productModels.get(position);
                //Initialization intent for creating activity for displaying list dishes
                Intent intent = new Intent(App.getContext(), ProductActivity.class);
                intent.putExtra(TAG, productModels);
                App.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productModels.size();
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
        private void bind(@NonNull ProductModel productModel){
            OptimizationBitmap optimizationBitmap = new OptimizationBitmap();
            optimizationBitmap.optimizationBitmap(productModel.getPoster(), 400, 200)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(bitmap -> {
                        posterImageView.setImageBitmap(bitmap);
                        Log.d(TAG, "bind: optimization poster for hits is successful");
                    }, throwable -> Log.e(TAG, "bind: optimization poster for hits isn't successful"));

            nameTextView.setText(productModel.getName());

            weightAndPriceTextView.setText(String.format(
                    App.getContext().getResources().getString(R.string.main_activity__weight_price),
                    productModel.getPrice(), productModel.getWeight()
            ));
            priceTextView.setText(String.format(
                    App.getContext().getResources().getString(R.string.main_activity__total_price),
                    productModel.getFinalPrice()
            ));
        }
    }


}
*/
