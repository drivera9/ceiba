package co.com.ceiba.mobile.pruebadeingreso.Models;

/**
 * Created by MONO on 02/01/2021.
 */

public class Post {

    public int userId;
    public int id;
    public String title;
    public String body;

    public Post(int userId, int id, String title, String body) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

}
