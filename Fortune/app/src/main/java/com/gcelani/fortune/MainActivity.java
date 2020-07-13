package com.gcelani.fortune;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;

import com.gcelani.fortune.database.AppDatabase;
import com.gcelani.fortune.utils.Constants;
import com.gcelani.fortune.view.ViewAnimation;
import com.google.android.material.navigation.NavigationView;

/**
 * MainActivity
 * extends AppCompatActivity
 */
public class MainActivity extends AppCompatActivity {

    /** AppBarConfiguration */
    private AppBarConfiguration mAppBarConfiguration;

    /** AppDatabase */
    private AppDatabase mAppDatabase;

    /**
     * onCreate
     * @param savedInstanceState savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewAnimation.init(findViewById(R.id.fab_revenue_layout));
        ViewAnimation.init(findViewById(R.id.fab_expense_layout));
        ViewAnimation.init(findViewById(R.id.fab_transaction_layout));

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,
                R.id.nav_accounts,
                R.id.nav_transactions,
                R.id.nav_settings,
                R.id.nav_about)
                .setDrawerLayout(drawer)
                .build();

        NavigationView navigationView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

         mAppDatabase = Room.databaseBuilder(this, AppDatabase.class, Constants.DB_NAME).allowMainThreadQueries().build();
         // Usage: ((MainActivity) getActivity()).getAppDatabase().accountDao().insertAll(account);
    }

    /**
     * onCreateOptionsMenu
     * @param menu menu
     * @return True
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * onSupportNavigateUp
     * @return Navigation result
     */
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

    /**
     * getAppDatabase
     * @return appDatabase
     */
    public AppDatabase getAppDatabase() {
        return mAppDatabase;
    }
}
