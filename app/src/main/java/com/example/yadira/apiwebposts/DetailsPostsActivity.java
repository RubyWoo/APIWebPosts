package com.example.yadira.apiwebposts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.yadira.apiwebposts.ClasesJavaUtilidades.Posts;

public class DetailsPostsActivity extends AppCompatActivity {

    TextView bodyPosts,titleDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_posts);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Details");
        setSupportActionBar(toolbar);

        bodyPosts = findViewById(R.id.bodyPosts);
        titleDetails = findViewById(R.id.titleDetails);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        Posts posts = (Posts) intent.getSerializableExtra("posts");
        titleDetails.setText("body Posts "+posts.getId());
        bodyPosts.setText(posts.getBody());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options, menu);
        return super.onCreateOptionsMenu(menu);

    }//////////////////////////////////////////////////////////////////////END onCreateOptionsMenu()

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }///////////////////////////////////////////////////// END METHOD - BOTÓN 'BACK' EN ACTION BAR
}
