
        package com.myhexaville.login.views.renders;

        import android.Manifest;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.net.Uri;
        import android.os.Bundle;
        import android.support.design.widget.Snackbar;
        import android.support.v4.app.ActivityCompat;
        import android.support.v4.content.ContextCompat;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.telephony.SmsManager;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.myhexaville.login.R;

        import java.util.Objects;

        public class Message extends AppCompatActivity {

            private static final int REQUEST_CALL = 1;
            public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.message);
                TextView number = findViewById(R.id.number_messageBox);
                number.setText(Objects.requireNonNull(getIntent().getExtras()).getString("number"));
            }

            public void msgScreenButtons(View v) {
                TextView number = findViewById(R.id.number_messageBox);
                EditText messageBox = findViewById(R.id.messageBox);
                String message = messageBox.getText().toString();
                String numb = number.getText().toString();
                switch (v.getId()) {
                    case R.id.return_messageBox:
                        this.finish();
                        break;
                    case R.id.call_actions:
                        final CharSequence actions[] = new CharSequence[]{"Cellular Call", "Internet Call"};
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("Select call actions");
                        builder.setItems(actions, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                TextView number = findViewById(R.id.number_messageBox);
                                String numb= number.getText().toString();
                                // the user clicked on actions[which]
                                switch (which) {
                                    case 0:
                                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number.getText().toString()));
                                        try {
                                            if (ContextCompat.checkSelfPermission(Message.this,Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                ActivityCompat.requestPermissions(Message.this,new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                                            } else {
                                                Toast.makeText(getApplicationContext(), numb, Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+numb)));
                                            }

                                        }catch(Exception e){
                                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                        break;
                                    case 1:
                                        Toast.makeText(getApplicationContext(),"Internet Call", Toast.LENGTH_SHORT).show();
                                        break;
                                }
                            }
                        });
                        builder.show();
                        break;
                    case R.id.videocall:
                        Snackbar mySnackbar = Snackbar.make(findViewById(R.id.parent),
                                "Video Call Feature Coming Soon", Snackbar.LENGTH_LONG);
                        mySnackbar.show();
                        break;
                    case R.id.sendButton:
                        if (ContextCompat.checkSelfPermission(Message.this,Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(Message.this,new String[]{Manifest.permission.SEND_SMS}, REQUEST_CALL);
                        } else {
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(numb, null, message, null, null);
                        }
                        break;
                }
            }
        }