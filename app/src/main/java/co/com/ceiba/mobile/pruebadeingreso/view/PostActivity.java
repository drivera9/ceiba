package co.com.ceiba.mobile.pruebadeingreso.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import co.com.ceiba.mobile.pruebadeingreso.Adapters.AdapterPosts;
import co.com.ceiba.mobile.pruebadeingreso.Adapters.AdapterUsers;
import co.com.ceiba.mobile.pruebadeingreso.Models.Post;
import co.com.ceiba.mobile.pruebadeingreso.Models.User;
import co.com.ceiba.mobile.pruebadeingreso.R;
import co.com.ceiba.mobile.pruebadeingreso.Services.PostService;
import co.com.ceiba.mobile.pruebadeingreso.Services.UserService;

public class PostActivity extends Activity {

    private RecyclerView reyclerViewPost;
    private AdapterPosts mAdapterPosts;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        Intent intent = getIntent();
        String userId = intent.getStringExtra("userId");
        UserService userService = new UserService(getApplicationContext());
        user = userService.getUser(userId);

        setValues();
        setAdapter();
    }

    /**
     * Metodo para declarar y "setear" los valores
     */
    public void setValues(){
        TextView name = (TextView) findViewById(R.id.name);
        name.setText(user.getName());

        TextView phone = (TextView) findViewById(R.id.phone);
        phone.setText(user.getPhone());

        TextView email = (TextView) findViewById(R.id.email);
        email.setText(user.getEmail());
    }

    /**
     * Metodo para "setear" el adaptador con los datos a msotrar
     */
    public void setAdapter(){
        PostService postService = new PostService(getApplicationContext());
        ArrayList<Post> posts = new ArrayList<Post>();
        posts = postService.getPost(String.valueOf(user.getId()));

        reyclerViewPost = (RecyclerView) findViewById(R.id.recyclerViewPostsResults);
        reyclerViewPost.setHasFixedSize(true);
        reyclerViewPost.setLayoutManager(new LinearLayoutManager(this));
        mAdapterPosts = new AdapterPosts(posts);
        reyclerViewPost.setAdapter(mAdapterPosts);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


}
