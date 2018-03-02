package com.example.ammulu.waterbilling;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class BillActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String UPLOAD_KEY = "image";
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    private int PICK_IMAGE_REQUEST = 1;
    private ImageView imageView1;
    private Bitmap bitmap;
    private Uri filePath;
    private Button buttonChoose,b1,upload,billsubmitbtn;
    EditText etconno,etflatno,etreading;
    String connno,flatno,reading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        buttonChoose = (Button) findViewById(R.id.browse);
        upload = (Button) findViewById(R.id.billsubmit);
        b1 = (Button) findViewById(R.id.b1);
        imageView1 = (ImageView) findViewById(R.id.img);
        billsubmitbtn=(Button)findViewById(R.id.billsubmit);
        etconno=(EditText)findViewById(R.id.editconno);
        etflatno=(EditText)findViewById(R.id.editflatno);
        etreading=(EditText)findViewById(R.id.editreading);
        buttonChoose.setOnClickListener(this);
        b1.setOnClickListener(this);
        upload.setOnClickListener(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //buttonChoose.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }
        billsubmitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connno=etconno.getText().toString();
                flatno=etflatno.getText().toString();
                reading=etreading.getText().toString();
                if(connno.equals("")){
                    etconno.setError("Enter connection no");
                    etconno.setFocusable(true);

                }else if(flatno.equals("")) {
                    etflatno.setError("Enter valid Flat no");
                    etflatno.setFocusable(true);

                }else if(reading.equals("")){
                     etreading.setError("Enter Reading");
                     etreading.setFocusable(true);
                }else if(imageView1.getDrawable() == null){
                    Toast.makeText(getApplicationContext(),"Please insert image",Toast.LENGTH_LONG).show();
                } else{
                    Toast.makeText(getApplicationContext(),"Submitted Successfully",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(BillActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= true;

                if (items[item].equals("Take Photo")) {
                    userChoosenTask ="Take Photo";
                    if(result)
                        cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask ="Choose from Library";
                    if(result)
                        showFileChooser();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView1.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CAMERA) {

            //onCaptureImageResult(data);
            bitmap = (Bitmap) data.getExtras().get("data");
            //storeImage(bm);
            imageView1.setImageBitmap(bitmap);


        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    @Override
    public void onClick(View v) {
        if (v == buttonChoose) {
            selectImage();
        }
        if (v == upload) {
            //selectImage();
        } if (v == b1) {
            startActivity(new Intent(this, SubBillingActivity.class));
        }

    }
}
