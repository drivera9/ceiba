package co.com.ceiba.mobile.pruebadeingreso.Adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import co.com.ceiba.mobile.pruebadeingreso.Models.User;
import co.com.ceiba.mobile.pruebadeingreso.R;
import co.com.ceiba.mobile.pruebadeingreso.view.PostActivity;

/**
 * Created by MONO on 02/01/2021.
 */

public class AdapterUsers extends RecyclerView.Adapter<AdapterUsers.ViewHolder> {

    private ArrayList<User> userModelList;

    public AdapterUsers(ArrayList<User> userModelList) {
        this.userModelList = userModelList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String name = userModelList.get(position).getName();
        String phone = userModelList.get(position).getPhone();
        String email = userModelList.get(position).getEmail();
        String id = String.valueOf(userModelList.get(position).getId());
        holder.name.setText(name);
        holder.phone.setText(phone);
        holder.email.setText(email);
        holder.id.setText(id);
    }

    public void refresh(ArrayList<User> users)
    {
        userModelList = users;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return userModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView phone;
        private TextView email;
        private TextView id;
        private Button btnViewPosts;
        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            phone = (TextView) v.findViewById(R.id.phone);
            email = (TextView) v.findViewById(R.id.email);
            id = (TextView) v.findViewById(R.id.userId);
            btnViewPosts = (Button) v.findViewById(R.id.btn_view_post);
            btnViewPosts.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String userId = id.getText().toString();
                    Intent intent = new Intent (v.getContext(), PostActivity.class);
                    intent.putExtra("userId", userId);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

}
