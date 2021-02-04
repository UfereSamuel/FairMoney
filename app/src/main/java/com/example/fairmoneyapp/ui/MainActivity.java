package com.example.fairmoneyapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.fairmoneyapp.R;
import com.example.fairmoneyapp.adapter.UserAdapter;
import com.example.fairmoneyapp.model.UserModel;
import com.example.fairmoneyapp.utils.MyDividerItemDecoration;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private KProgressHUD hud;
    private UserModel model;

    private RecyclerView recyclerView;
    private ArrayList<UserModel> modelist;
    private UserAdapter mAdapter;
    private UserAdapter.OnItemClickListener listener;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        getSupportActionBar().hide();

        AndroidNetworking.initialize(getApplicationContext());

        fetchData();

        modelist = new ArrayList<>();
        mAdapter = new UserAdapter(this, modelist, listener);
        recyclerView = findViewById(R.id.recycler_view2);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(mAdapter);
    }

    private void fetchData() {

        hud = KProgressHUD.create(MainActivity.this)
                .setStyle(KProgressHUD.Style.ANNULAR_DETERMINATE)
                .setLabel("Please wait")
                .setMaxProgress(100)
                .show();

        AndroidNetworking.get("https://dummyapi.io/data/api/user")
                .addPathParameter("pageNumber", "0")
                .addQueryParameter("limit", "3")
                .addHeaders("app-id", "601a6876e6cb4eb65044233e")
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hud.dismiss();
                        // do anything with response
                        Log.d("resp22", "suc: "+response.toString());

                        try {
                            JSONObject obj = new JSONObject(response.toString());
                            JSONArray array = obj.getJSONArray("data");
                            for (int i = 0; i < response.length(); i++) {

                                String id = array.getJSONObject(i).getString("id");
                                String fName = array.getJSONObject(i).getString("lastName");
                                String lName = array.getJSONObject(i).getString("firstName");
                                String email = array.getJSONObject(i).getString("email");
                                String title = array.getJSONObject(i).getString("title");
                                String picture = array.getJSONObject(i).getString("picture");

                                model = new UserModel();
                                model.setId(id);
                                model.setFirstName(fName);
                                model.setLastName(lName);
                                model.setEmail(email);
                                model.setTitle(title);
                                model.setPictureUrl(picture);

                                modelist.add(model);
                                mAdapter.notifyDataSetChanged();


                                Log.d("resp221", "id: "+id);
                                Log.d("resp221", "fName: "+fName);
                                Log.d("resp221", "lName: "+lName);
                                Log.d("resp221", "email: "+email);
                                Log.d("resp221", "title: "+title);
                                Log.d("resp221", "picture: "+picture);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }




                    }

                    @Override
                    public void onError(ANError anError) {
                        hud.dismiss();

                        // handle error
                        Log.d("resp23", "err: "+anError.toString());

                    }
                });

    }
}