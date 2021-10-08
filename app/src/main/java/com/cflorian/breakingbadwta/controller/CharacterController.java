package com.cflorian.breakingbadwta.controller;

import android.content.Context;
import android.util.Log;

import com.cflorian.breakingbadwta.model.DbData;
import com.cflorian.breakingbadwta.model.ModelCharacter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CharacterController {
    private Context context;
    private DbData conn;
    private ArrayList<ModelCharacter> list = new ArrayList<>();

    public CharacterController(Context ctx) {
        this.context = ctx;
        this.conn = new DbData(ctx);
    }

    public String getCharacter(String respuesta){
        try {
            JSONArray jsonArrayData = new JSONArray(respuesta);
            for (int i = 0; i<jsonArrayData.length();i++){
                JSONObject jObjectCharacter = new JSONObject(jsonArrayData.getString(i));
                conn.InsertarCharacter(
                        jObjectCharacter.getInt("char_id"),
                        jObjectCharacter.getString("name"),
                        jObjectCharacter.getString("birthday"),
                        jObjectCharacter.getString("img"),
                        jObjectCharacter.getString("status"),
                        jObjectCharacter.getString("nickname"),
                        jObjectCharacter.getString("portrayed"),
                        jObjectCharacter.getString("category"));
                JSONArray jsonArray = jObjectCharacter.getJSONArray("occupation");
                for (int j = 0; j<jsonArray.length(); j++)
                    conn.InsertarOccupation(jObjectCharacter.getInt("char_id"),jsonArray.getString(j));
            }
        } catch (JSONException error) {
            error.printStackTrace();
            Log.e("TAG", error.getMessage());
        }

        return "Extraccion Correcta";
    }

    public List<Map> getCharacters (){
        List<Map> mapListCharacter = conn.getAllCharacters();
        return mapListCharacter;
    }

    public List<Map> getOccupation (int id){
        List<Map> mapListOccupation = conn.getDetailOccupation(id);
        return mapListOccupation;
    }

    public int sizeCharacters (){
        List<Map> mapListCharacter = conn.getAllCharacters();
        return mapListCharacter.size();
    }

    public void updateStateFav (int id){
        int resultado = conn.obtenerFav(id);
        if (resultado == 1){
            conn.updateFav(id,0);
        }else if (resultado == 0){
            conn.updateFav(id,1);
        }
    }

    public ArrayList<ModelCharacter> populateRv (){
        list.clear();
        List<Map> mapCharacters = getCharacters();
        if (!mapCharacters.isEmpty()){
            for (int i = 0; i< mapCharacters.size(); i++){
                list.add( new ModelCharacter(
                        Objects.requireNonNull(mapCharacters.get(i).get("char_id")).toString(),
                        Objects.requireNonNull(mapCharacters.get(i).get("name")).toString(),
                        Objects.requireNonNull(mapCharacters.get(i).get("birthday")).toString(),
                        Objects.requireNonNull(mapCharacters.get(i).get("img")).toString(),
                        Objects.requireNonNull(mapCharacters.get(i).get("status")).toString(),
                        Objects.requireNonNull(mapCharacters.get(i).get("nickname")).toString(),
                        Objects.requireNonNull(mapCharacters.get(i).get("portrayed")).toString(),
                        Objects.requireNonNull(mapCharacters.get(i).get("category")).toString(),
                        Objects.requireNonNull(mapCharacters.get(i).get("fav")).toString()
                ));
            }
        }
        return list;
    }


}
