package com.cflorian.breakingbadwta.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cflorian.breakingbadwta.R;
import com.cflorian.breakingbadwta.controller.CharacterController;
import com.cflorian.breakingbadwta.controller.CharacterModelAdapter;
import com.cflorian.breakingbadwta.model.ModelCharacter;
import com.cflorian.breakingbadwta.utils.OnItemClickListener;
import com.cflorian.breakingbadwta.utils.webServiceNetwork;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements OnItemClickListener {

    private RecyclerView rvItems;
    private webServiceNetwork ws;
    private RequestQueue request;
    private ProgressDialog dialog;
    private CharacterController characters;
    private RecyclerView.LayoutManager layoutManager;
    private CharacterModelAdapter rvAdapter;
    private ArrayList<ModelCharacter> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ws = new webServiceNetwork(getApplicationContext());
        request = Volley.newRequestQueue(this);
        characters = new CharacterController(this);
        if (characters.sizeCharacters() == 0){
            peticionData();
        }
        getData();
        initUI();

    }


    private void initUI() {
        rvItems = findViewById(R.id.rvItems);
        rvItems.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        rvItems.setLayoutManager(layoutManager);

        rvAdapter = new CharacterModelAdapter(this, list);
        rvItems.setAdapter(rvAdapter);
        rvAdapter.setClickListener(this);
        Animation rigth_to_left = AnimationUtils.loadAnimation(getApplication(), R.anim.rigth_to_left);
        rvItems.setAnimation(rigth_to_left);
    }

    private void peticionData() {
        dialog = new ProgressDialog(MainActivity.this);
        dialog.setMessage("Obteniendo Caracteres");
        dialog.setCancelable(false);
        dialog.show();
        new Thread(
                () -> {
                    StringRequest getCharacters = new StringRequest(Request.Method.GET, ws.getcharacters(),
                            response -> {
                                String mensaje  = characters.getCharacter(response);
                                Log.e("TAG", mensaje);
                                dialog.dismiss();
                                getData();
                                refreshView();
                            },
                            error -> {
                                // error
                                Log.e("TAG", error.toString());
                                dialog.dismiss();
                            }
                    );
                    getCharacters.setRetryPolicy(new DefaultRetryPolicy(
                            30000,
                            0,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    request.add(getCharacters);
                }
        ).start();
    }


    private void getData() {
        list = characters.populateRv();
    }

    @Override
    public void onClick(View view, int position) {
        final ModelCharacter data = list.get(position);
        switch (view.getId()){
            case R.id.tbnFav:
                characters.updateStateFav(Integer.parseInt(data.getChar_id()));
                getData();
                refreshView();
                break;
            case R.id.lLinear:
                Intent sharedIntent = new Intent(getApplicationContext(),DetailActivity.class);
                sharedIntent.putExtra("ID",data.getChar_id());
                sharedIntent.putExtra("NAME",data.getName());
                sharedIntent.putExtra("STATUS",data.getStatus());
                sharedIntent.putExtra("PORTRAYED",data.getPortrayed());
                sharedIntent.putExtra("IMG",data.getImg());
                sharedIntent.putExtra("FAV",data.getFav());
                startActivity(sharedIntent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        }
    }

    private void refreshView(){
        runOnUiThread(() -> rvAdapter.notifyDataSetChanged());
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}