package com.mymoonapplab.oxfirat.network;

import com.mymoonapplab.oxfirat.model.Announcement;
import com.mymoonapplab.oxfirat.model.ApiResponse;
import com.mymoonapplab.oxfirat.model.Event;
import com.mymoonapplab.oxfirat.model.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("api/news/{lang}/{offset}/{limit}")
    Call<ApiResponse<News>> getNews(
            @Path("lang") String lang,
            @Path("offset") int offset,
            @Path("limit") int limit
    );

    @GET("api/get-all-events/{lang}/{offset}/{limit}")
    Call<ApiResponse<Event>> getEvents(
            @Path("lang") String lang,
            @Path("offset") int offset,
            @Path("limit") int limit
    );

    @GET("api/announcement/{lang}/{offset}/{limit}")
    Call<ApiResponse<Announcement>> getAnnouncements(
            @Path("lang") String lang,
            @Path("offset") int offset,
            @Path("limit") int limit
    );
}
