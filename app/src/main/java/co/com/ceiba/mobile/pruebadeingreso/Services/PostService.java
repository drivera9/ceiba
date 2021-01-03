package co.com.ceiba.mobile.pruebadeingreso.Services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import co.com.ceiba.mobile.pruebadeingreso.Database.DBHelper;
import co.com.ceiba.mobile.pruebadeingreso.Models.Post;
import co.com.ceiba.mobile.pruebadeingreso.Models.User;
import co.com.ceiba.mobile.pruebadeingreso.Models.UserAddress;
import co.com.ceiba.mobile.pruebadeingreso.Models.UserCompany;
import co.com.ceiba.mobile.pruebadeingreso.rest.Endpoints;

/**
 * Created by MONO on 02/01/2021.
 */

public class PostService {

    private DBHelper database;

    public PostService(Context context) {
        this.database = new DBHelper(context);
        this.database.open();
        getDataFromHttp();
    }

    /**
     * Metodo para obtener los datos desde el api
     */
    public void getDataFromHttp(){
        URL url;
        HttpURLConnection urlConnection = null;
        String str = "";
        try {
            url = new URL(Endpoints.URL_BASE + Endpoints.GET_POST_USER);

            urlConnection = (HttpURLConnection) url
                    .openConnection();

            InputStream in = urlConnection.getInputStream();

            InputStreamReader isw = new InputStreamReader(in);

            int data = isw.read();
            while (data != -1) {
                char current = (char) data;
                data = isw.read();
                str += current;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        Post[] data;
        Gson gson = new Gson();
        data = gson.fromJson(str, Post[].class);
        ArrayList<Post> posts = this.getPosts();
        for (int i = 0; i < data.length - 1; i++) {
            if (!this.find(posts, data[i].getId(), data[i].getUserId())) {
                this.insertPost(data[i]);
            }
        }
    }

    /**
     * Metodo para encontrar datos en un array
     * @param posts array a buscar
     * @param id valor a encontrar
     * @param userId valor a encontrar
     * @return Booleano de si lo encontro o no
     */
    public boolean find(ArrayList<Post> posts, int id, int userId) {

        for (Post post : posts) {
            if ((post.getId() == id) && (post.getUserId() == userId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Metodo para obtener los posts de la base de datos SQLite
     * @return Array de posts
     */
    public ArrayList<Post> getPosts() {
        Cursor cursorPosts = this.database.get("posts");
        ArrayList<Post> post = new ArrayList<Post>();
        if (cursorPosts.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursorPosts.getString(cursorPosts.getColumnIndex("id")));
                int userId = Integer.parseInt(cursorPosts.getString(cursorPosts.getColumnIndex("userId")));
                String title = cursorPosts.getString(cursorPosts.getColumnIndex("title"));
                String body = cursorPosts.getString(cursorPosts.getColumnIndex("body"));
                post.add(new Post(userId, id, title, body));

                // do what ever you want here
            } while (cursorPosts.moveToNext());
        }
        cursorPosts.close();
        return post;
    }

    /**
     * Metodo para obtener los posts de la base de datos SQLite por id
     * @param postId Id a buscar
     * @return Array de posts
     */
    public ArrayList<Post> getPost(String postId) {
        Cursor cursorPosts = this.database.getById("posts", "userId", postId);
        ArrayList<Post> posts = new ArrayList<Post>();
        if (cursorPosts.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursorPosts.getString(cursorPosts.getColumnIndex("id")));
                int userId = Integer.parseInt(cursorPosts.getString(cursorPosts.getColumnIndex("userId")));
                String title = cursorPosts.getString(cursorPosts.getColumnIndex("title"));
                String body = cursorPosts.getString(cursorPosts.getColumnIndex("body"));
                posts.add(new Post(userId, id, title, body));

                // do what ever you want here
            } while (cursorPosts.moveToNext());
        }
        cursorPosts.close();
        return posts;
    }

    /**
     * Metodo para insertar los posts en la base de datos SQLite
     * @param post post a insertar
     */
    public void insertPost(Post post) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", post.id);
        contentValues.put("userId", post.userId);
        contentValues.put("title", post.title);
        contentValues.put("body", post.body);
        this.database.insert(contentValues, "posts");
    }

    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        System.out.print(cm.getActiveNetworkInfo().isConnected());
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
