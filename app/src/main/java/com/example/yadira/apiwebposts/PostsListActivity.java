package com.example.yadira.apiwebposts;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.yadira.apiwebposts.ClasesJavaUtilidades.Posts;
import com.example.yadira.apiwebposts.ClasesJavaUtilidades.PostsRespuesta;
import com.example.yadira.apiwebposts.ClasesJavaUtilidades.RecyclerPostsAdaptador;
import com.example.yadira.apiwebposts.RetroInterfaces.PlaceholderService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostsListActivity extends AppCompatActivity {

    private Retrofit retro                     ; //Referencia de tipo Retrofit
    private RecyclerView recycler              ; //Referencia de tipo RecyclerView
    private RecyclerPostsAdaptador listaPostsA ; //Referencia de tipo Adaptador

    private ProgressBar progressBarCircular;
    private SwipeRefreshLayout swipeRefreshLayout;

    private List<Posts> listaPostsTemp;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts_list);

        progressBarCircular = findViewById(R.id.progressBar);
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("APIWebPosts");
        setSupportActionBar(toolbar);

        recycler = findViewById(R.id.recyclerView);//Asigno el RecyclerView del layout(Instancia)
        recycler.setLayoutManager(new LinearLayoutManager(PostsListActivity.this));
        listaPostsA = new RecyclerPostsAdaptador(PostsListActivity.this);
        recycler.setAdapter(listaPostsA);

        retro = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        obtenerDatos();
    }//////////////////////////////////////////////////////////////////////////////////END onCreate

    @Override
    protected void onResume() {
        super.onResume();


        //swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.colorAccent));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                listaPostsTemp.clear();
                obtenerDatos();
            }
        });
    }//////////////////////////////////////////////////////////////////////////////////END onResume

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_options, menu);


        final MenuItem search = menu.findItem(R.id.search);
        searchView = (SearchView)search.getActionView();
        searchView.setQueryHint("Search by User Id");

        search.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                Toast.makeText(PostsListActivity.this,"Expand",Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {

                Toast.makeText(PostsListActivity.this,"Collapse",Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Toast.makeText(PostsListActivity.this,"Submit",Toast.LENGTH_SHORT).show();
                //searchView.setQuery("",false);
                //searchView.setIconified(false);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);

    }//////////////////////////////////////////////////////////////////////END onCreateOptionsMenu()


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.favorites){
            Toast.makeText(PostsListActivity.this,"Favorites",Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }/////////////////////////////////////////////////////// END METHOD - BOTÓN 'BACK' EN ACTION BAR

    //to rerun the animation after changing the data
    private void runLayoutAnimation(RecyclerView recycler, RecyclerPostsAdaptador listaA, List<Posts> listaPostsTemp){

        Context context = recycler.getContext();
        LayoutAnimationController layoutAnimationController =
                AnimationUtils.loadLayoutAnimation(context,R.anim.layout_animation_fall_down);

        recycler.setLayoutAnimation(layoutAnimationController);
        recycler.scheduleLayoutAnimation();

        listaA.adicionarListaPosts(listaPostsTemp);
        listaA.notifyDataSetChanged();
        Toast.makeText(getApplicationContext(), "Loading Posts...", Toast.LENGTH_SHORT).show();



    }///////////////////////////////////////////////////////////////////////END runLayoutAnimation()

    private void obtenerDatos() {

        //create() Metodo de Retrofit...Referencia 'service' de tipo PlaceholderService (Interfaz)
        PlaceholderService service = retro.create(PlaceholderService.class);
        Call<List<Posts>> postsRespuestaCall = service.obtenerListaPosts();

        postsRespuestaCall.enqueue(new Callback<List<Posts>>() {
            @Override
            public void onResponse(Call<List<Posts>> call, Response<List<Posts>> response) {

                if(response.isSuccessful()){
                    listaPostsTemp = response.body();

                    if(listaPostsTemp.size() == 0){
                        Toast.makeText(getApplicationContext(), "Lista vacía", Toast.LENGTH_SHORT).show();

                    } else {
                        recycler.setVisibility(View.VISIBLE);
                        progressBarCircular.setVisibility(View.GONE);
                        swipeRefreshLayout.setVisibility(View.VISIBLE);
                        runLayoutAnimation(recycler,listaPostsA,listaPostsTemp);
                        swipeRefreshLayout.setRefreshing(false);
                    }

                }else{
                    progressBarCircular.setVisibility(View.GONE);
                    swipeRefreshLayout.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getApplicationContext(), "Error de respuesta", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<Posts>> call, Throwable t) {
                progressBarCircular.setVisibility(View.GONE);
                swipeRefreshLayout.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Error de respuesta 2", Toast.LENGTH_SHORT).show();

            }
        });

    }/////////////////////////////////////////////////////////////////////////////////// END obtenerDatos
}//////////////////////////////////////////////////////////////////////////////////////// END CLASS
