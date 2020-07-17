package com.gcelani.fortune;

import android.app.KeyguardManager;
import android.content.Intent;
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
import com.gcelani.fortune.model.Settings;
import com.gcelani.fortune.utils.Constants;
import com.gcelani.fortune.utils.Utils;
import com.gcelani.fortune.view.FabAnimation;
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

        FabAnimation.init(findViewById(R.id.fab_revenue_layout));
        FabAnimation.init(findViewById(R.id.fab_expense_layout));
        FabAnimation.init(findViewById(R.id.fab_transaction_layout));

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

        mAppDatabase = Room.databaseBuilder(
                this,
                AppDatabase.class,
                Constants.DB_NAME)
                .allowMainThreadQueries()
                .build();

        Settings settings = mAppDatabase.settingsDao().findById(Constants.SETTINGS_TABLE_ID);
        if (settings == null) {
            settings = Utils.generateDefaultSettings(this);
            mAppDatabase.settingsDao().insertAll(settings);
        }

        if (settings.isAuthenticate) {
            requestAuthentication();
        }
    }

    /**
     * requestAuthentication
     */
    private void requestAuthentication() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

            if (keyguardManager.isKeyguardSecure()) {
                Intent authenticationIntent = keyguardManager.createConfirmDeviceCredentialIntent(
                        getString(R.string.app_name),
                        getString(R.string.authentication_request_message));
                startActivityForResult(authenticationIntent, Constants.AUTHENTICATION_INTENT_REQUEST_CODE);
            }
        }
    }

    /**
     * onActivityResult
     * @param requestCode requestCode
     * @param resultCode resultCode
     * @param data data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.AUTHENTICATION_INTENT_REQUEST_CODE && resultCode != RESULT_OK) {
            finish();
        }
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
