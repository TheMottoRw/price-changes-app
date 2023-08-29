package com.example.pricechanges;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigationView;
    private FloatingActionButton fab;
    private String activeFragment = "Customers";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        navigationView = findViewById(R.id.navigation);
        fab = findViewById(R.id.fab);
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, new PricesFragment()).commit();


        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.action_subscribers) {
                    activeFragment = "Customers";
                    navigationView.getMenu().getItem(1).setChecked(true);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new CustomersFragment()).commit();
                }
                if (itemId == R.id.action_changes) {
                    activeFragment = "Prices";
                    navigationView.getMenu().getItem(0).setChecked(true);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new PricesFragment()).commit();
                }
                if (itemId == R.id.action_logout) {
                    Utils.logout(MainActivity.this);
                    finish();
                    startActivity(new Intent(MainActivity.this, Login.class));
                    return true;
                }

                return false;
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activeFragment.equals("Customers"))
                    startActivity(new Intent(MainActivity.this, AddCustomer.class));
                else
                    startActivity(new Intent(MainActivity.this, AddPrices.class));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        Toast.makeText(MainActivity.this, "Logout clicked", Toast.LENGTH_SHORT).show();

        return super.onOptionsItemSelected(item);
    }
}