package com.sebamitradata.laundry.request;

import com.sebamitradata.laundry.model.LaundryModel;
import com.sebamitradata.laundry.response.LaundryResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface LaundryRequest {
    @GET("laundry/show")
    Call<LaundryResponse> laundry_show();

    @FormUrlEncoded
    @POST("laundry/create")
    Call<LaundryResponse> laundry_create(
            @Field("nama") String nama,
            @Field("alamat") String alamat,
            @Field("telepon") String telepon
    );

    @DELETE("laundry/delete/{id}")
    Call<LaundryResponse> laundry_delete(@Path("id") String id);

    @FormUrlEncoded
    @POST("laundry/update")
    Call<LaundryResponse> laundry_update(
            @Field("id") String id,
            @Field("nama") String nama,
            @Field("alamat") String alamat,
            @Field("telepon") String telepon);
}
