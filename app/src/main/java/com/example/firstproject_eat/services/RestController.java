package com.example.firstproject_eat.services;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class RestController {

    /*private final static String BASE_URL = "http://138.68.86.70/";
    private final static String VERSION = "";*/

    //TODO Ferrigno's API
    private final static String BASE_URL = "http://5c659d3419df280014b6272a.mockapi.io/api/";
    private final static String VERSION = "v1/";

    private RequestQueue queue;

    public RestController(Context context){
        queue = Volley.newRequestQueue(context);
    }

    public void getRequest(String endpoint, Response.Listener<String> success, Response.ErrorListener error){
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                BASE_URL.concat(VERSION).concat(endpoint),
                success,
                error
                );
        queue.add(stringRequest);
    }
}

