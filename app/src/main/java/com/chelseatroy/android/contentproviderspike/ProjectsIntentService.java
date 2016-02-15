package com.chelseatroy.android.contentproviderspike;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public class ProjectsIntentService extends IntentService {
    public ProjectsIntentService() {
        super("ProjectsIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        System.out.println("ProjectsIntentService.onHandleIntent");
        try {
            System.out.println("about to sleep");
            Thread.sleep(5000);
            System.out.println("done sleeping");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<String> strings = Arrays.asList("hello", "bye", "world");
        for (String string : strings) {
            ContentValues values = new ContentValues();
            values.put("DISPLAY_NAME", string);
            getContentResolver()
                    .insert(
                            Uri.parse("content://com.chelseatroy.android.contentproviderspike/projects"),
                            values
                    );
        }
    }
//    @Override
//    protected void onHandleIntent(Intent intent) {
//        System.out.println("ProjectsIntentService.onHandleIntent");
//
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://panoptes.zooniverse.org/api/")
//                .addConverterFactory(JacksonConverterFactory.create(mapper))
//                .build();
//
//        ProjectsRetrofitInterface projectsRetrofitInterface = retrofit.create(ProjectsRetrofitInterface.class);
//        Call<ProjectResponse> projects = projectsRetrofitInterface.getProjects();
//
//        try {
//            Response<ProjectResponse> response = projects.execute();
//            ProjectResponse body = response.body();
//            for (Project project : body.getProjects()) {
//                System.out.println("project = " + project);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public interface ProjectsRetrofitInterface {
        @GET("/projects")
        @Headers("Accept: application/vnd.api+json; version=1")
        Call<ProjectResponse> getProjects();
    }
}
