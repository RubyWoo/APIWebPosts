package com.example.yadira.apiwebposts.ClasesJavaUtilidades;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yadira.apiwebposts.DetailsPostsActivity;
import com.example.yadira.apiwebposts.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerPostsAdaptador extends RecyclerView.Adapter<RecyclerPostsAdaptador.MiViewHolder>{

    private List<Posts> dataset;
    private Context mContext   ;

    public RecyclerPostsAdaptador(Context context) { //Constructor
        mContext = context;
        dataset = new ArrayList<>();
    } // END constructor

    @NonNull
    @Override
    public MiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pedido,parent,false);
        return new MiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MiViewHolder holder, final int position) {

        holder.idPost.setText("id Posts: "+dataset.get(position).getId());
        holder.titlePosts.setText(dataset.get(position).getTitle());
        holder.userId.setText(" user Id "+dataset.get(position).getUserId()+" ");

       holder.detailsPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentEnviar = new Intent(mContext, DetailsPostsActivity.class);
                Posts posts = dataset.get(position);
                intentEnviar.putExtra("posts",posts);
                mContext.startActivity(intentEnviar);
                Toast.makeText(mContext,"Details Posts",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    //*****************************************************************************************************
    public void adicionarListaPosts(List<Posts> listaPosts) { //Metodo utilizado en MainActivity
        dataset.addAll(listaPosts);
        notifyDataSetChanged()    ;
    }

    //-----------------------------------------------------------------------------------INNER CLASS
    public class MiViewHolder extends RecyclerView.ViewHolder {

        private TextView idPost;
        private TextView titlePosts;
        private TextView userId;
        private ImageButton detailsPosts;

        public MiViewHolder(@NonNull View itemView) {
            super(itemView);

            idPost  = itemView.findViewById(R.id.idPosts);
            titlePosts = itemView.findViewById(R.id.titlePosts);
            userId = itemView.findViewById(R.id.userIdPosts);
            detailsPosts = itemView.findViewById(R.id.detailsPosts);

        }////// END Constructor INNER CLASS
    }///////////////////////////////////////////////////////////////////////////////END INNER CLASS
}///////////////////////////////////////////////////////////////////////////////////END CLASS
