package com.myhexaville.login.views.renders;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.myhexaville.login.R;
import com.myhexaville.login.controllers.ContactsController;
import com.myhexaville.login.controllers.NotificationController;
import com.myhexaville.login.controllers.VerificationController;
import com.myhexaville.login.models.Contacts;
import com.myhexaville.login.models.Notifications;
import com.myhexaville.login.models.User;
import com.myhexaville.login.views.lists.conversations.ListItem;
import com.myhexaville.login.views.lists.conversations.RecyclerViewAdapter;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewMessage extends AppCompatActivity {

    DatabaseReference databaseUser;
    ContactsController cd;
    NotificationController nd;


    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private List<ListItem> listItems;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_message);
        String verificationCode = "DummyCode";
        VerificationController db;
        db = new VerificationController(this);
        final Cursor[] res = {db.getVerifiedUser()};
        if(res[0].getCount() == 1){
            while(res[0].moveToNext()){
                verificationCode = res[0].getString(0);
            }
        }
        String url = "https://api.qrserver.com/v1/create-qr-code/?size=150x150&data="+verificationCode;
        ImageView qrcode = findViewById(R.id.qrcode);
        Picasso.with(getApplicationContext()).load(url).placeholder(R.mipmap.coupon).error(R.mipmap.ic_launcher).into(qrcode);

        Button scanQR = findViewById(R.id.QRScanner);
        final Activity activity = this;
        scanQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(true);
                integrator.setBarcodeImageEnabled(true);
                integrator.initiateScan();
            }
        });

        EditText num = findViewById(R.id.newNumber);
        num.setFocusableInTouchMode(true);
        num.requestFocus();
        num.setSelected(true);

        try{
            num.setOnKeyListener(new View.OnKeyListener() {
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    EditText num = findViewById(R.id.newNumber);
                    // If the event is a key-down event on the "enter" button
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                            (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        // Perform action on key press

                        Intent messageWindow = new Intent(getApplicationContext(), Message.class);
                        messageWindow.putExtra("number", num.getText().toString());
                        startActivity(messageWindow);
                        finish();
                        return true;
                    }
                    return false;
                }
            });
        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "KeyCode Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();

        }

        recyclerView = findViewById(R.id.friends);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        listItems = new ArrayList<>();


        cd = new ContactsController(getApplicationContext());
        res[0] = cd.getContacts();
        if(res[0].getCount() > 0){

            while(res[0].moveToNext()){
                ListItem listItem = new ListItem(
                        res[0].getString(0),
                        ""
                );
                listItems.add(listItem);
            }

            adapter = new RecyclerViewAdapter(listItems, getApplicationContext());

            recyclerView.setAdapter(adapter);
        }else{
            res[0] = db.getVerifiedUser();
            if(res[0].getCount() == 1){
                while(res[0].moveToNext()){
                    verificationCode = res[0].getString(0);
                }
            }

//                Add to firebase contact
            databaseUser = FirebaseDatabase.getInstance().getReference("users").child(verificationCode).child("contacts");
            String finalVerificationCode = verificationCode;
            cd = new ContactsController(getApplicationContext());

            databaseUser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot contactSnapshot : dataSnapshot.getChildren()) {
                        Contacts contacts = contactSnapshot.getValue(Contacts.class);
                        cd.insertContact(contacts.getId(), contacts.getNumber());
                    }
                    cd = new ContactsController(getApplicationContext());
                    res[0] = cd.getContacts();
                    if(res[0].getCount() > 0){

                        while(res[0].moveToNext()){
                            ListItem listItem = new ListItem(
                                    res[0].getString(0),
                                    ""
                            );
                            listItems.add(listItem);
                        }

                        adapter = new RecyclerViewAdapter(listItems, getApplicationContext());

                        recyclerView.setAdapter(adapter);
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Toast.makeText(getApplicationContext(), "Network connection not found", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents() == null){
                Toast.makeText(this, "Cancelled the scanning", Toast.LENGTH_SHORT).show();
            }else{
                VerificationController db = new VerificationController(this);
                String verificationCode = "";
                Cursor res = db.getVerifiedUser();
                if(res.getCount() == 1){
                    while(res.moveToNext()){
                        verificationCode = res.getString(0);
                    }
                }

//                Add to firebase contact
                databaseUser = FirebaseDatabase.getInstance().getReference("users");
                Query query = databaseUser.orderByChild("phone");
                String finalVerificationCode = verificationCode;
                cd = new ContactsController(this);
                nd = new NotificationController(this);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (true) {
                            for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                if(userSnapshot.exists()){
                                    User user = userSnapshot.getValue(User.class);
                                    if(user.getId().equals(finalVerificationCode)){//this is me
                                        for (DataSnapshot otherUserSnapshot: dataSnapshot.getChildren()){
                                            User otherUser = otherUserSnapshot.getValue(User.class);
                                            if(otherUser.getId().equals(result.getContents())){//Person I want to add
//                                              Add to SQLite Database
                                                Contacts otherContact = new Contacts(otherUser.getId(), otherUser.getPhone());
                                                Contacts myContact = new Contacts(user.getId(), user.getPhone());
                                                if(cd.insertContact(otherUser.getId().toString(), otherUser.getPhone().toString())){
//                                              Add to Firebase Database
                                                databaseUser//this is mine
                                                    .child(finalVerificationCode)
                                                    .child("contacts")
                                                    .child(otherUser.getId())
                                                    .setValue(otherContact);
                                                databaseUser//this is the other guy
                                                        .child(otherUser.getId())
                                                        .child("contacts")
                                                        .child(user.getId())
                                                        .setValue(myContact);
                                                if(nd.insertNotification("You added " + otherUser.getPhone()+" to your contacts")){
                                                    Notifications myNotification = new Notifications(otherUserSnapshot.getKey(),"You added " + otherUser.getPhone()+" to your contacts");
                                                    Notifications otherGuysNotification = new Notifications(userSnapshot.getKey(),"You and "+user.getPhone()+" are now friends");
                                                    databaseUser//making my notification
                                                            .child(finalVerificationCode)
                                                            .child("notifications")
                                                            .child(otherUserSnapshot.getKey())
                                                            .setValue(myNotification);

                                                    databaseUser//making otherguys notification
                                                            .child(otherUser.getId())
                                                            .child("notifications")
                                                            .child(userSnapshot.getKey())
                                                            .setValue(otherGuysNotification);

                                                }

                                                Toast.makeText(getApplicationContext(), user.getPhone() + " added to friends list", Toast.LENGTH_SHORT).show();
                                                }else{
                                                    Toast.makeText(getApplicationContext(), "Sorry, could not add to friends list", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }
//
                                    }
                                }else{
                                    Toast.makeText(getApplicationContext(), "Network connection not found", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Network connection not found", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Toast.makeText(getApplicationContext(), "Network connection not found", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void newScreenButtons(View v){
        EditText num = findViewById(R.id.newNumber);
        switch (v.getId()){
            case R.id.return_newMSG:
                this.finish();
                break;
            case R.id.numberSubmit:
                if(num.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Number Box Cannot be Empty", Toast.LENGTH_SHORT).show();
                }else{
                    Intent messageWindow = new Intent(getApplicationContext(), Message.class);
                    messageWindow.putExtra("number", num.getText().toString());
                    startActivity(messageWindow);
                    this.finish();
                }
                break;
        }
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            Log.e("src", src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap", "returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception", e.getMessage());
            return null;
        }
    }
}
