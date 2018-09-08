package com.myhexaville.login.views.renders;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.myhexaville.login.R;

public class NewMessage extends AppCompatActivity {
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_message);
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
}
