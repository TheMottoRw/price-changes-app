package com.example.pricechanges;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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
import com.google.android.material.appbar.MaterialToolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class UpdateProduct extends AppCompatActivity {
    public MaterialToolbar toolbar;
    private ProgressDialog pgdialog;
    private EditText name, quantity,price,unit;
    private Button btnSave;
    private Intent intent;
    private String productId,productName,productQuantity,productPrice,productUnit,priceChangeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        name = findViewById(R.id.name);
        quantity = findViewById(R.id.quantity);
        price = findViewById(R.id.price);
        unit = findViewById(R.id.unit);
        btnSave = findViewById(R.id.btnSave);
        pgdialog = new ProgressDialog(this);
        pgdialog.setMessage("Loading data...");
        pgdialog.setCancelable(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        intent = getIntent();
        productId = intent.getStringExtra("id");
        priceChangeId = intent.getStringExtra("prices_changes_id");
        productName = intent.getStringExtra("name");
        productQuantity = intent.getStringExtra("quantity");
        productPrice = intent.getStringExtra("price");
        productUnit = intent.getStringExtra("unit");
        name.setText(productName);
        quantity.setText(productQuantity);
        price.setText(productPrice);
        unit.setText(productUnit);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProduct();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateProduct() {
        final String url = Utils.host + "/product/"+productId;
        JSONObject body = new JSONObject();
        pgdialog.show();
        Log.d("URL", url);
        try{
            body.put("product_name",name.getText().toString().trim());
            body.put("quantity",quantity.getText().toString().trim());
            body.put("price",price.getText().toString().trim());
            body.put("unit",unit.getText().toString().trim());
            body.put("prices_changes_id",priceChangeId);
        }catch (JSONException ex){
            Log.d("JSONErr",ex.getMessage());
        }
//        tvLoggingIn.setVisibility(View.VISIBLE);
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
                            JSONObject obj = new JSONObject(response);
                            Toast.makeText(UpdateProduct.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException ex) {
                            Log.d("Json error", ex.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pgdialog.dismiss();
                        Toast.makeText(UpdateProduct.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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