package com.example.nastoyshiishashlik.liveData;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.math.BigDecimal;

public class LiveDataOrder {
    private static LiveDataOrder instance;
    private MutableLiveData<BigDecimal> liveData = new MutableLiveData<>();

    public static LiveDataOrder getInstance(){
        if (instance==null){
            instance = new LiveDataOrder();
        }
        return instance;
    }

    public LiveData<BigDecimal> getData() {
        return liveData;
    }

    public void setLiveData(BigDecimal liveData) {
        this.liveData.setValue(liveData);
    }
}
