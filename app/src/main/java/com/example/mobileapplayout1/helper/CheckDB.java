/*
 * Copyright 2014 KC Ochibili
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 *  The "‚‗‚" character is not a comma, it is the SINGLE LOW-9 QUOTATION MARK unicode 201A
 *  and unicode 2017 that are used for separating the items in a list.
 */

package com.example.mobileapplayout1.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.example.mobileapplayout1.model.HotSalePhone;
import com.example.mobileapplayout1.model.Products;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;


public class CheckDB {

    private SharedPreferences preferences;

    public CheckDB(Context appContext) {
        preferences = PreferenceManager.getDefaultSharedPreferences(appContext);
    }

    public ArrayList<String> getListString(String key) {
        return new ArrayList<String>(Arrays.asList(TextUtils.split(preferences.getString(key, ""), "‚‗‚")));
    }


    public ArrayList<HotSalePhone> getListObject(String key){
        Gson gson = new Gson();

        ArrayList<String> objStrings = getListString(key);
        ArrayList<HotSalePhone> playerList =  new ArrayList<HotSalePhone>();

        for(String jObjString : objStrings){
            HotSalePhone player  = gson.fromJson(jObjString,  HotSalePhone.class);
            playerList.add(player);
        }
        return playerList;
    }
    public ArrayList<Products> getListProduct(String key){
        Gson gson = new Gson();

        ArrayList<String> objStrings = getListString(key);
        ArrayList<Products> playerList =  new ArrayList<Products>();

        for(String jObjString : objStrings){
            Products player  = gson.fromJson(jObjString,  Products.class);
            playerList.add(player);
        }
        return playerList;
    }

    public void putListString(String key, ArrayList<String> stringList) {
        checkForNullKey(key);
        String[] myStringList = stringList.toArray(new String[stringList.size()]);
        preferences.edit().putString(key, TextUtils.join("‚‗‚", myStringList)).apply();
    }


    public void putListObject(String key, ArrayList<HotSalePhone> playerList){
        checkForNullKey(key);
        Gson gson = new Gson();
        ArrayList<String> objStrings = new ArrayList<String>();
        for(HotSalePhone player: playerList){
            objStrings.add(gson.toJson(player));
        }
        putListString(key, objStrings);
    }
    public void putListProduct(String key, ArrayList<Products> playerList){
        checkForNullKey(key);
        Gson gson = new Gson();
        ArrayList<String> objStrings = new ArrayList<String>();
        for(Products player: playerList){
            objStrings.add(gson.toJson(player));
        }
        putListString(key, objStrings);
    }
    private void checkForNullKey(String key){
        if (key == null){
            throw new NullPointerException();
        }
    }

}