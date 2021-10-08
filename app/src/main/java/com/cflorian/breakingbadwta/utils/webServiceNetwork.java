package com.cflorian.breakingbadwta.utils;

import android.content.Context;
import android.content.res.Resources;

import com.cflorian.breakingbadwta.R;

public class webServiceNetwork {
    private Context ctx;

    public webServiceNetwork(Context ctx) {
        this.ctx = ctx;
    }

    public String getcharacters(){
        return ctx.getString(R.string.api_characters);
    }
}
