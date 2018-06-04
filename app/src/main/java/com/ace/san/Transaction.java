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
import android.widget.Toast;

import com.ace.san.Login.LoginActivity;
import com.ace.san.db.Trans;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Transaction extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    DatabaseReference databaseTransaction;

    //objects
    EditText etname, etquantity, etfrom, etto;
    Button btnmove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etname = (EditText)findViewById(R.id.name);
        etquantity = (EditText)findViewById(R.id.quantity) ;
        etfrom = (EditText)findViewById(R.id.from);
        etto = (EditText)findViewById(R.id.to);
        btnmove = (Button)findViewById(R.id.move);

        databaseTransaction = FirebaseDatabase.getInstance().getReference("transaction");

        btnmove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transaction();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser()==null){
                    startActivity(new Intent(Transaction.this,LoginActivity.class));
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void transaction(){
        String name = etname.getText().toString().trim();
        String quantity = etquantity.getText().toString().trim();
        String from = etfrom.getText().toString().trim();
        String to = etto.getText().toString().trim();

        if (!TextUtils.isEmpty(name)){
            String nm = databaseTransaction.push().getKey();
            Trans trans = new Trans(name, quantity, from, to);
            databaseTransaction.child(nm).setValue(trans);
            Toast.makeText(this,"Succesfully", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this,"Failed", Toast.LENGTH_LONG).show();
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        //here is the main place where we need to work on.
        int id=item.getItemId();
        switch (id){

            case R.id.home:
                Intent h= new Intent(Transaction.this,Home.class);
                startActivity(h);
                break;
            case R.id.add:
                Intent i= new Intent(Transaction.this,Add.class);
                startActivity(i);
                break;
            case R.id.list:
                Intent j= new Intent(Transaction.this,list.class);
                startActivity(j);
                break;
            case R.id.transaction:
                Intent l= new Intent(Transaction.this,Transaction.class);
                startActivity(l);
            case R.id.logout:
                Intent n= new Intent(Transaction.this,Logout.class);
                startActivity(n);
                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
