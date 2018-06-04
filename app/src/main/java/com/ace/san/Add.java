package com.ace.san;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ace.san.Login.LoginActivity;
import com.ace.san.db.Item;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Add extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    DatabaseReference databaseItem;

    //objects
    EditText ednama, edstatus, edplace, edquantity;
    Spinner spcat;
    Button btnsub;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //get ID
        ednama = (EditText) findViewById(R.id.name);
        edstatus = (EditText) findViewById(R.id.status);
        edplace = (EditText) findViewById(R.id.place);
        edquantity = (EditText) findViewById(R.id.quantity);
        spcat = (Spinner) findViewById(R.id.spinnercat);
        btnsub= (Button) findViewById(R.id.submit);

        databaseItem = FirebaseDatabase.getInstance().getReference();

        btnsub.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                AddItem();
            }
                });

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser()==null){
                    startActivity(new Intent(Add.this,LoginActivity.class));
                }
            }
        };

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void AddItem() {
        String name = ednama.getText().toString().trim();
        String status = edstatus.getText().toString().trim();
        String place = edplace.getText().toString().trim();
        String category = spcat.getSelectedItem().toString();
        String quantity = edquantity.getText().toString().trim();


        //checker
        if(!TextUtils.isEmpty(name)){

            String id = databaseItem.push().getKey();

            Item item = new Item(name,status,place,category,quantity);

            databaseItem.child(id).setValue(item);

            Toast.makeText(this, "Data added", Toast.LENGTH_LONG).show();

        }
        else{
            Toast.makeText(this, "Please input the data", Toast.LENGTH_LONG).show();
        }
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    public void signOut() {
        mAuth.signOut();


// this listener will be called when there is change in firebase user session
        FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(Add.this, LoginActivity.class));
                    finish();
                }
            }
        };
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        //here is the main place where we need to work on.
        int id=item.getItemId();
        switch (id){

            case R.id.home:
                Intent h= new Intent(Add.this,Home.class);
                startActivity(h);
                break;
            case R.id.add:
                Intent i= new Intent(Add.this,Add.class);
                startActivity(i);
                break;
            case R.id.list:
                Intent j= new Intent(Add.this,list.class);
                startActivity(j);
                break;
            case R.id.transaction:
                Intent l= new Intent(Add.this,Transaction.class);
                startActivity(l);
            case R.id.logout:
                Intent n= new Intent(Add.this,Logout.class);
                startActivity(n);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
