package com.weatherapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {
  // @GET("weather")
   // Call<List<APIModel>> getTemp(@Query("q") String city,@Query("appid") String appid);
    @GET("weather")
    Call<APIModel> getTemp(@Query("q") String city,@Query("appid") String appid,@Query("units") String unit);

  @GET("weather")
  Call<APIModel> getTempByLoc(@Query("lon") double lon,@Query("lat") double lat,@Query("appid") String appid,@Query("units") String unit);
   /* @GET
    Call<List<APIModel>> getTemp();*/
}
