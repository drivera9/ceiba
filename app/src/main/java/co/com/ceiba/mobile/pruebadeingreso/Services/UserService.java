package co.com.ceiba.mobile.pruebadeingreso.Services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.facebook.stetho.json.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

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

public class UserService {

    private DBHelper database;

    public UserService(Context context) {
        this.database = new DBHelper(context);
        this.database.open();
        getDataFromHttp();
    }

    /**
     * Metodo para obtener los datos desde el api
     */
    public void getDataFromHttp() {
        URL url;
        HttpURLConnection urlConnection = null;
        String str = "";
        try {
            url = new URL(Endpoints.URL_BASE + Endpoints.GET_USERS);

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

        User[] data;
        Gson gson = new Gson();
        data = gson.fromJson(str, User[].class);
        ArrayList<User> users = this.getUsers();
        for (int i = 0; i < data.length - 1; i++) {
            if (!this.find(users, data[i].getId())) {
                this.insertUser(data[i]);
            }

        }
    }

    /**
     * Metodo para encontrar datos en un array
     * @param users array a buscar
     * @param id valor a encontrar
     * @return Booleano de si lo encontro o no
     */
    public boolean find(ArrayList<User> users, int id) {

        for (User user : users) {
            if (user.getId() == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * Metodo para obtener los usuarios de la base de datos SQLite
     * @return Array de usuarios
     */
    public ArrayList<User> getUsers() {
        Cursor cursorUsers = this.database.get("users");
        ArrayList<User> user = new ArrayList<User>();
        if (cursorUsers.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursorUsers.getString(cursorUsers.getColumnIndex("id")));
                String name = cursorUsers.getString(cursorUsers.getColumnIndex("name"));
                String username = cursorUsers.getString(cursorUsers.getColumnIndex("username"));
                String email = cursorUsers.getString(cursorUsers.getColumnIndex("email"));
                String address = cursorUsers.getString(cursorUsers.getColumnIndex("address"));
                String phone = cursorUsers.getString(cursorUsers.getColumnIndex("phone"));
                String website = cursorUsers.getString(cursorUsers.getColumnIndex("website"));
                String company = cursorUsers.getString(cursorUsers.getColumnIndex("company"));
                UserCompany companyObj = new UserCompany(company, "", "");
                UserAddress addressObj = new UserAddress("", "", "", "");
                user.add(new User(id, name, username, email, addressObj, phone, website, companyObj));

                // do what ever you want here
            } while (cursorUsers.moveToNext());
        }
        cursorUsers.close();
        return user;
    }

    /**
     * Metodo para obtener los usuarios de la base de datos SQLite por id
     * @param userId Id a buscar
     * @return Array de usuarios
     */
    public User getUser(String userId) {
        Cursor cursorUsers = this.database.getById("users", "id", userId);
        User user = null;
        if (cursorUsers.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursorUsers.getString(cursorUsers.getColumnIndex("id")));
                String name = cursorUsers.getString(cursorUsers.getColumnIndex("name"));
                String username = cursorUsers.getString(cursorUsers.getColumnIndex("username"));
                String email = cursorUsers.getString(cursorUsers.getColumnIndex("email"));
                String address = cursorUsers.getString(cursorUsers.getColumnIndex("address"));
                String phone = cursorUsers.getString(cursorUsers.getColumnIndex("phone"));
                String website = cursorUsers.getString(cursorUsers.getColumnIndex("website"));
                String company = cursorUsers.getString(cursorUsers.getColumnIndex("company"));
                UserCompany companyObj = new UserCompany(company, "", "");
                UserAddress addressObj = new UserAddress("", "", "", "");
                user = new User(id, name, username, email, addressObj, phone, website, companyObj);

                // do what ever you want here
            } while (cursorUsers.moveToNext());
        }
        cursorUsers.close();
        return user;
    }

    /**
     * Metodo para obtener los usuarios de la base de datos SQLite por nombre
     * @param nameLike Nombre a buscar
     * @return Array de usuarios
     */
    public ArrayList<User> getUserByName(String nameLike) {
        Cursor cursorUsers = this.database.getByLike("users", "name", nameLike);
        ArrayList<User> users = new ArrayList<User>();
        if (cursorUsers.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursorUsers.getString(cursorUsers.getColumnIndex("id")));
                String name = cursorUsers.getString(cursorUsers.getColumnIndex("name"));
                String username = cursorUsers.getString(cursorUsers.getColumnIndex("username"));
                String email = cursorUsers.getString(cursorUsers.getColumnIndex("email"));
                String address = cursorUsers.getString(cursorUsers.getColumnIndex("address"));
                String phone = cursorUsers.getString(cursorUsers.getColumnIndex("phone"));
                String website = cursorUsers.getString(cursorUsers.getColumnIndex("website"));
                String company = cursorUsers.getString(cursorUsers.getColumnIndex("company"));
                UserCompany companyObj = new UserCompany(company, "", "");
                UserAddress addressObj = new UserAddress("", "", "", "");
                users.add(new User(id, name, username, email, addressObj, phone, website, companyObj));

                // do what ever you want here
            } while (cursorUsers.moveToNext());
        }
        cursorUsers.close();
        return users;
    }

    /**
     * Metodo para insertar los usuarios en la base de datos SQLite
     * @param user usuario a insertar
     */
    public void insertUser(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", user.id);
        contentValues.put("name", user.name);
        contentValues.put("username", user.username);
        contentValues.put("email", user.email);
        contentValues.put("address", user.address.street);
        contentValues.put("phone", user.phone);
        contentValues.put("website", user.website);
        contentValues.put("company", user.company.companyName);
        this.database.insert(contentValues, "users");
    }

    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        System.out.print(cm.getActiveNetworkInfo().isConnected());
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
