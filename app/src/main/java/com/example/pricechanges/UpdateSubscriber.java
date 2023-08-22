package com.example.pricechanges;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UpdateSubscriber extends AppCompatActivity {
    private ProgressDialog pgdialog;
    private EditText name,phone;
    private Button btnUpdate;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_subscriber);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        pgdialog = new ProgressDialog(this);
        pgdialog.setMessage("Loading data...");
        pgdialog.setCancelable(false);
        btnUpdate = findViewById(R.id.btnUpdate);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        name.setText(intent.getStringExtra("name"));
        phone.setText(intent.getStringExtra("phone"));
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });
    }
    private void update() {
        final String url = Utils.host + "/subscriber/"+id;
        JSONObject body = new JSONObject();
        Log.d("URL", url);
        pgdialog.show();
        try{
            body.put("name",name.getText().toString().trim());
            body.put("phone",phone.getText().toString().trim());
        }catch (JSONException ex){
            Log.d("JSONErr",ex.getMessage());
        }
        RequestQueue queue = Volley.newRequestQueue(this);
// prepare the Request
        StringRequest getRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // display response
                        pgdialog.dismiss();
                        Log.d("Logresp", response);
                        try {
                            JSONObject res = new JSONObject(response);
                            Snackbar.make(name,res.getString("message"), Snackbar.LENGTH_SHORT).show();
                        } catch (JSONException ex) {
                            Log.d("Json error", ex.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pgdialog.dismiss();
                        Toast.makeText(UpdateSubscriber.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        Log.e("jsonerr", "JSON Error " + (error != null ? error.getMessage() : ""));
                    }
                }
        ) {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final Map<String, String> headers = new HashMap<>();
                return headers;
            }
            @Override
            public byte[] getBody() {
                return body.toString().getBytes();
            }
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        ;

// add it to the RequestQueue
        queue.add(getRequest);
    }

}