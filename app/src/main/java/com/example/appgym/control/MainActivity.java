package com.example.appgym.control;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.example.appgym.R;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.appgym.utils.SessionManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private NavController navController;
    private Toolbar toolbar;
    private int iconBack = R.drawable.arrow_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        isLogged();
        toolbar();
        navigate();
    }

    private void isLogged() {
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        if (!sessionManager.isLogged()){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void toolbar() {
        toolbar.setNavigationIcon(null);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public Toolbar getActivityToolbar(){
        return toolbar;
    }

    private void navigate() {
        navController = Navigation.findNavController(this, R.id.fragment);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

//        Lo quedo comentado porque es interesante, recarga el fragment al seleccionar en la navegación
//        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
//            int id = item.getItemId();
//            navController.navigate(id);
//            return false;
//        });

        // Para que aparezca seleccionado el menu que queramos al cambiar a un fragment que no está
        // definido en el menu (es un submenu).
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                int destinationId = navDestination.getId();
                if (destinationId == R.id.infoRoutineFragment ||
                    destinationId == R.id.newRoutineFragment ||
                    destinationId == R.id.action_infoRoutineFragment_to_detailFragment) {
                    bottomNavigationView.getMenu().findItem(R.id.routineFragment).setChecked(true);
                }
                if (destinationId == R.id.action_mainFragment_to_detailFragment ||
                    destinationId == R.id.action_mainFragment_to_newHistoryFragment) {
                    bottomNavigationView.getMenu().findItem(R.id.mainFragment).setChecked(true);
                }
//                TODO: los action no hacen checked

                setArrowBack(destinationId);
            }
        });

    }

    private void setArrowBack(int destinationId) {
//        Compruebo si el menu está en nav_graph, si no está significa que es un submenu y activo
//        la flecha hacia atrás
        boolean isSubMenu = false;
        for (int i = 0; i < bottomNavigationView.getMenu().size(); i++) {
            MenuItem menuItem = bottomNavigationView.getMenu().getItem(i);
            if (menuItem.getItemId() == destinationId) {
                isSubMenu = true;
                break;
            }
        }
        if (isSubMenu){
            toolbar.setNavigationIcon(null);
        }else {
            toolbar.setNavigationIcon(iconBack);
        }
    }

}