package com.example.yadira.apiwebposts.RetroInterfaces;

import com.example.yadira.apiwebposts.ClasesJavaUtilidades.Posts;
import com.example.yadira.apiwebposts.ClasesJavaUtilidades.PostsRespuesta;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PlaceholderService {

    @GET("posts")//Hago la peticion al recurso "posts"
    Call<List<Posts>> obtenerListaPosts();

}
