package com.example.nastoyshiishashlik.fragments;

import android.view.View;

import com.example.nastoyshiishashlik.R;
import com.example.nastoyshiishashlik.models.SliderItem;
import com.example.nastoyshiishashlik.service.SliderAdapter;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SliderBarFragment extends BaseFragment{

    @BindView(R.id.slider)
    SliderView sliderView;

    @Override
    public int getViewId() {
        return R.layout.fragment_slider_bar;
    }

    @Override
    public void onViewCreated(View view) {
        connectToSliderBar();
    }

    /**
     * create and handling slider bar
     */
    private void connectToSliderBar(){
        SliderAdapter sliderAdapter = new SliderAdapter(sliderItems());

        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setScrollTimeInMillis(5000);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.startAutoCycle();
    }

    private List<SliderItem> sliderItems(){
        List<SliderItem> sliderItems = new ArrayList<>();

        sliderItems.add(new SliderItem(R.drawable.baner1));
        sliderItems.add(new SliderItem(R.drawable.baner2));
        sliderItems.add(new SliderItem(R.drawable.baner3));

        return sliderItems;
    }
}
