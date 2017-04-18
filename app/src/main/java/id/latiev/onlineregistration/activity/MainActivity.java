package id.latiev.onlineregistration.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import id.latiev.onlineregistration.R;
import id.latiev.onlineregistration.adapter.JadwalDokterAdapter;
import id.latiev.onlineregistration.model.Dokter;
import id.latiev.onlineregistration.network.AppController;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "MainActivity";
    public static final String ANONYMOUS = "anonymous";
    private String username, photoUrl, email;
    private SharedPreferences sharedPreferences;
    private List<Dokter> dokterList;

    // Firebase instance variables
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;

    // Google instance variables
    private GoogleApiClient googleApiClient;

    // Adapter
    private JadwalDokterAdapter jadwalDokterAdapter;

    // UI instance variables
    private RecyclerView rvJadwalDokter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        username = ANONYMOUS;

        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API).build();

        // Initialize firebase auth
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser == null){
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(MainActivity.this, SignInActivity.class));
            finish();
            return;
        } else {
            username = firebaseUser.getDisplayName();
            email = firebaseUser.getEmail();
            if (firebaseUser.getPhotoUrl() != null){
                photoUrl = firebaseUser.getPhotoUrl().toString();
            }
        }

        createNavigationView();
        initCollapsingToolbar();

        rvJadwalDokter = (RecyclerView)findViewById(R.id.rv_jadwal_dokter);
        dokterList = new ArrayList<>();

        getListJadwalDokter("");

        jadwalDokterAdapter = new JadwalDokterAdapter(MainActivity.this, dokterList);

        rvJadwalDokter.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        rvJadwalDokter.setLayoutManager(layoutManager);
        rvJadwalDokter.setItemAnimator(new DefaultItemAnimator());
        rvJadwalDokter.setAdapter(jadwalDokterAdapter);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_sign_out){
            firebaseAuth.signOut();
            Auth.GoogleSignInApi.signOut(googleApiClient);
            username = ANONYMOUS;
            startActivity(new Intent(MainActivity.this, SignInActivity.class));
            finish();
            return true;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    private void createNavigationView(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);
        CircleImageView userAvatar = (CircleImageView)hView.findViewById(R.id.iv_drawer_user_image);
        TextView avatarName = (TextView)hView.findViewById(R.id.tv_drawer_title);
        TextView avatarSubName = (TextView)hView.findViewById(R.id.tv_drawer_subtitle);
        avatarName.setText(username);
        if (username == null){
            avatarName.setText(ANONYMOUS);
        } else {
            avatarName.setText(username);
        }

        if (photoUrl == null){
            userAvatar.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, android.R.drawable.sym_def_app_icon));
        } else {
            Glide.with(MainActivity.this).load(photoUrl).into(userAvatar);
        }

        if (email == null){
            avatarSubName.setText("");
        } else {
            avatarSubName.setText(email);
        }
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.ctl_abm_collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.abl_abm_appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle("Pendaftaran Online");
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    private void getListJadwalDokter(String idKLinik){
        String url = "http://10.0.2.2/hkiosk_v3/api/content/jadwal_dokter_list/page/1?clinic_id=" + idKLinik + "&docter_id=";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray datas = response.getJSONArray("data");
                    if (datas.length() > 0){
                        for (int i = 0; i < datas.length(); i++){
                            JSONObject data = datas.getJSONObject(i);
                            dokterList.add(new Dokter(data.getString("dokter"), data.getString("ava_url"), data.getString("klinik"), data.getString("waktu")));
                            //dokterList.add(new Dokter(data.getString("docter_id"), data.getString("dokter"), data.getString("kelamin"), data.getInt("clinic_id"), data.getString("klinik"), data.getString("waktu")));
                        }
                        jadwalDokterAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(TAG, "Log catch : " + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Log error response : " + error.toString());
            }
        });
        AppController.getInstance().addToRequestQueue(request);
    }
}
