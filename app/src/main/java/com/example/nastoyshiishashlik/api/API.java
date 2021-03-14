package com.example.nastoyshiishashlik.api;

import com.example.nastoyshiishashlik.api.model.requestOrder.Order;
import com.example.nastoyshiishashlik.api.model.responseOrder.Example;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface API {

    @POST("incomingOrders.createIncomingOrder")
    Call<Example> postOrder(@Query("token") String authHeader,
                            @Body Order data);
}
