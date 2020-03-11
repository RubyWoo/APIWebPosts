package com.example.yadira.apiwebposts.ClasesJavaUtilidades;


import java.util.List;

public class PostsRespuesta {

    public List<Posts> results; //Referencia 'results' de tipo ArrayList

    public List<Posts> getResults() {
        return results;
    }

    public void setResults(List<Posts> results) {
        this.results = results;
    }
}
