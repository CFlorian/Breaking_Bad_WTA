package com.cflorian.breakingbadwta.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.cflorian.breakingbadwta.R;
import com.cflorian.breakingbadwta.controller.CharacterController;
import com.cflorian.breakingbadwta.model.ModelCharacter;
import com.cflorian.breakingbadwta.utils.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DetailActivity extends AppCompatActivity {

    private String  sName, sStatus, sPortrayed, sImg, sOccupation = "", id, valueFav;
    private ArrayList<ModelCharacter> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getIntents();
        getOccupation();
        initUI();
    }

    private void getIntents() {
        Bundle sharedData = getIntent().getExtras();
        id = sharedData.getString("ID");
        sName = sharedData.getString("NAME");
        sStatus = sharedData.getString("STATUS");
        sPortrayed = sharedData.getString("PORTRAYED");
        valueFav = sharedData.getString("FAV");
        sImg = sharedData.getString("IMG");
    }

    private void getOccupation() {
        CharacterController characters = new CharacterController(this);
        list.clear();
        List<Map> mapOccupation = characters.getOccupation(Integer.parseInt(id));
        if (!mapOccupation.isEmpty()){
            for (int i = 0; i< mapOccupation.size(); i++){
                sOccupation = sOccupation.concat(
                        Objects.requireNonNull(mapOccupation.get(i).get("occupation")).toString())
                        .concat(", ");
            }
        }
    }

    private void initUI() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ToggleButton tbnFavDetail = findViewById(R.id.tbnFavDetail);
        TextView tvNombre = findViewById(R.id.tvNameDetail);
        TextView tvOccupation = findViewById(R.id.tvOccupation);
        TextView tvStatus = findViewById(R.id.tvStatus);
        TextView tvPortrayed = findViewById(R.id.tvPortrayed);
        ImageView ivImgDetail = findViewById(R.id.ivImgDetail);

        if (valueFav.equals("1")){
            tbnFavDetail.setBackgroundResource(R.drawable.ic_android_heart_on);
            tbnFavDetail.setChecked(true);
        }else if (valueFav.equals("0")){
            tbnFavDetail.setBackgroundResource(R.drawable.ic_android_heart_off);
            tbnFavDetail.setChecked(false);
        }
        tvNombre.setText(sName);
        tvOccupation.setText(sOccupation);
        tvStatus.setText(sStatus);
        tvPortrayed.setText(sPortrayed);
        Picasso.get().load(sImg).into(ivImgDetail);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
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