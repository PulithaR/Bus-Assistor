package com.example.busp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class routestart extends AppCompatActivity {

    private EditText txtto, txtfrom, amount;
    private Button btnsave,btnshow,btndelete,btnupdate;
    private routes rout;
    DatabaseReference dbRef;




    private void clearControls(){
        txtto.setText("");
        txtfrom.setText("");
        amount.setText("");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routestart);



        txtto = findViewById(R.id.routeto);
        txtfrom = findViewById(R.id.routefrom);
        amount = findViewById(R.id.amount);

        btnsave = findViewById(R.id.btnSave);
        btndelete = findViewById(R.id.btnDelete);
        btnshow = findViewById(R.id.btnShow);
        btnupdate = findViewById(R.id.btnUpdate);

        rout = new routes();

        btnshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child("routes").child("rot1");
                readRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChildren()){
                            txtto.setText(snapshot.child("routeto").getValue().toString());
                            txtfrom.setText(snapshot.child("routefrom").getValue().toString());
                            amount.setText(snapshot.child("fare").getValue().toString());
                        }
                        else
                            Toast.makeText(getApplicationContext(),"Nothing to Display",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference delRef = FirebaseDatabase.getInstance().getReference().child("routes");
                delRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild("rot1")){
                            dbRef = FirebaseDatabase.getInstance().getReference().child("routes").child("rot1");
                            dbRef.removeValue();
                            clearControls();
                            Toast.makeText(getApplicationContext(),"Data deleted sucessfully",Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(getApplicationContext(),"No source to delete",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference udRef = FirebaseDatabase.getInstance().getReference().child("routes");
                udRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild("rot1")){
                            try{
                                rout.setRouteto(txtto.getText().toString().trim());
                                rout.setRoutefrom(txtfrom.getText().toString().trim());
                                rout.setFare(amount.getText().toString().trim());

                                dbRef = FirebaseDatabase.getInstance().getReference().child("routes").child("rot1");
                                dbRef.setValue(rout);
                                clearControls();

                                Toast.makeText(getApplicationContext(),"Data updated successfully",Toast.LENGTH_SHORT).show();
                            }
                            catch(NumberFormatException e){
                                Toast.makeText(getApplicationContext(),"Invalid contact no",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                            Toast.makeText(getApplicationContext(),"No data to upload",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });



        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbRef = FirebaseDatabase.getInstance().getReference().child("routes");
                try{
                    if(TextUtils.isEmpty(txtto.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter the destination",Toast.LENGTH_SHORT).show();
                    else if(TextUtils.isEmpty(txtfrom.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter from",Toast.LENGTH_SHORT).show();
                    else if(TextUtils.isEmpty(amount.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter amount",Toast.LENGTH_SHORT).show();
                    else{
                        rout.setRouteto(txtto.getText().toString().trim());
                        rout.setRoutefrom(txtfrom.getText().toString().trim());
                        rout.setFare(amount.getText().toString().trim());
                        dbRef.push().setValue(rout);

                        Toast.makeText(getApplicationContext(),"Data saved successfully",Toast.LENGTH_SHORT).show();
                        clearControls();
                    }
                }
                catch(NumberFormatException e){
                    Toast.makeText(getApplicationContext(),"Invalid contact Number",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
