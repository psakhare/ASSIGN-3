package edu.cse523.psakhare.assignment3_addproduct;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Priyanka on 11/3/2017.
 */

public class AddProduct extends AppCompatActivity {
    Button saveButton = null;
    EditText name = null, price = null;
    ImageView imageView = null;
    private static int PICK_IMAGE_REQUEST = 1;
    public Uri imageUri;
    String imgDecodableString;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);
        name = (EditText)findViewById(R.id.productName);
        price = (EditText)findViewById(R.id.productPrice);
        imageView = (ImageView)findViewById(R.id.newImage);

        saveButton = (Button) findViewById(R.id.saveBtn);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name = name.getText().toString();
                String Price = price.getText().toString();

                Intent i = new Intent();
                i.putExtra("NAME", Name);
                i.putExtra("PRICE", Price);
                i.setData(imageUri);

                setResult(Activity.RESULT_OK, i);
                finish();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && null != data) {
                // Get uri
                imageUri = data.getData();

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                int nh = (int) (bitmap.getHeight() * (1024.0 / bitmap.getWidth()));
                Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 1024, nh, true);
                // Set image
                imageView.setImageBitmap(scaled);

            } else {
                Toast.makeText(this, "No.", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            Toast.makeText(this, "Oops! Sorry", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
