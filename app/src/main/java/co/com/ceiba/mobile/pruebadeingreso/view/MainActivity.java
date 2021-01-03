package co.com.ceiba.mobile.pruebadeingreso.view;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import co.com.ceiba.mobile.pruebadeingreso.Adapters.AdapterUsers;
import co.com.ceiba.mobile.pruebadeingreso.Models.User;
import co.com.ceiba.mobile.pruebadeingreso.R;
import co.com.ceiba.mobile.pruebadeingreso.Services.UserService;

public class MainActivity extends Activity {

    private RecyclerView reyclerViewUser;
    private AdapterUsers mAdapterUsers;
    ArrayList<User> users;
    UserService userService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        userService = new UserService(getApplicationContext());
        users =  userService.getUsers();
        setAdapters();

        configureSearch();
    }

    /**
     * Metodo para configurar el EditText y refrescar la lista a mostrar
     */
    public void configureSearch(){
        EditText search = (EditText) findViewById(R.id.editTextSearch);
        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                users = userService.getUserByName(s.toString());
                RelativeLayout emptyList = (RelativeLayout) findViewById(R.id.emptyList);
                RecyclerView recyclerViewSearchResults = (RecyclerView) findViewById(R.id.recyclerViewSearchResults);

                // Se realiza validacion para mostrar la lista vacia o con datos
                if (users.size() == 0){
                    emptyList.setVisibility(View.VISIBLE);
                    recyclerViewSearchResults.setVisibility(View.INVISIBLE);
                }else{
                    emptyList.setVisibility(View.INVISIBLE);
                    recyclerViewSearchResults.setVisibility(View.VISIBLE);
                    mAdapterUsers.refresh(users);
                }
            }
        });
    }

    /**
     * Metodo para "setear" el adaptador con los datos a msotrar
     */
    public void setAdapters(){
        reyclerViewUser = (RecyclerView) findViewById(R.id.recyclerViewSearchResults);
        reyclerViewUser.setHasFixedSize(true);
        reyclerViewUser.setLayoutManager(new LinearLayoutManager(this));
        mAdapterUsers = new AdapterUsers(users);
        reyclerViewUser.setAdapter(mAdapterUsers);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


}