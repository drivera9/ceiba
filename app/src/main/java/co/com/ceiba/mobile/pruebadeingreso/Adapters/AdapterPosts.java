package co.com.ceiba.mobile.pruebadeingreso.Adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import co.com.ceiba.mobile.pruebadeingreso.Models.Post;
import co.com.ceiba.mobile.pruebadeingreso.Models.User;
import co.com.ceiba.mobile.pruebadeingreso.R;
import co.com.ceiba.mobile.pruebadeingreso.view.PostActivity;

/**
 * Created by MONO on 02/01/2021.
 */

public class AdapterPosts extends RecyclerView.Adapter<AdapterPosts.ViewHolder> {

    private ArrayList<Post> postModelList;

    public AdapterPosts(ArrayList<Post> postModelList) {
        this.postModelList = postModelList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String title = postModelList.get(position).getTitle();
        String body = postModelList.get(position).getBody();
        holder.title.setText(title);
        holder.body.setText(body);
    }

    @Override
    public int getItemCount() {
        return postModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView body;
        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.title);
            body = (TextView) v.findViewById(R.id.body);
        }
    }

}
