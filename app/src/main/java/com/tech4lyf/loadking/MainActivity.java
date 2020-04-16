package com.tech4lyf.loadking;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tech4lyf.loadking.DashboardFragments.More;
import com.tech4lyf.loadking.DashboardFragments.MyOrders;
import com.tech4lyf.loadking.DashboardFragments.Wallet;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private CardView enter;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        enter = findViewById(R.id.enter_new_order);
        if (!getSupportFragmentManager().isDestroyed())
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.dashboard_fragment_container, new MyOrders(), "MY_ORDERS").commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.my_orders:
                if (!getSupportFragmentManager().isDestroyed())
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.dashboard_fragment_container, new MyOrders(), "MY_ORDERS").commit();
                break;
            case R.id.wallet:
                if (!getSupportFragmentManager().isDestroyed())
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.dashboard_fragment_container, new Wallet(), "MY_ORDERS").commit();
                break;
                case R.id.more:
                    if (!getSupportFragmentManager().isDestroyed())
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.dashboard_fragment_container, new More(), "MY_ORDERS").commit();
                break;
            default:
                break;

        }
        return true;
    }
}
