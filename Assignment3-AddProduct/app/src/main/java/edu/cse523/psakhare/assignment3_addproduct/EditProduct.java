package edu.cse523.psakhare.assignment3_addproduct;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import static edu.cse523.psakhare.assignment3_addproduct.MainActivity.productAdapter;

/**
 * Created by Priyanka on 11/4/2017.
 */

public class EditProduct extends AppCompatActivity {

    Button saveButton = null;
    EditText name = null, price = null;
    ImageView imageView = null;
    private static int PICK_IMAGE_REQUEST = 1;
    public Uri imageUripassed = null;
    String nameEdit, priceEdit = null;
    int indexProduct=0;
    Uri imageURI = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_product);

        name = (EditText)findViewById(R.id.editProductName);
        price = (EditText)findViewById(R.id.editProductPrice);
        imageView = (ImageView)findViewById(R.id.editImage);

        nameEdit = getIntent().getExtras().getString("name");
        priceEdit = getIntent().getExtras().getString("price");
        if(getIntent().getData()!=null)
            imageURI = getIntent().getData();
        indexProduct = getIntent().getExtras().getInt("index");

        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageURI);
            int nh = (int) (bitmap.getHeight() * (1024.0 / bitmap.getWidth()));
            Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 1024, nh, true);
            // Set image
            imageView.setImageBitmap(scaled);

        } catch (IOException e) {
            e.printStackTrace();
        }

        name.setText(nameEdit);
        price.setText(priceEdit);

        saveButton = (Button)findViewById(R.id.editSaveBtn);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imageUripassed!=null)
                    productAdapter.editProduct(name.getText().toString(), price.getText().toString(), imageUripassed, indexProduct);
                else
                    productAdapter.editProduct(name.getText().toString(), price.getText().toString(), imageURI, indexProduct);

                productAdapter.notifyDataSetChanged();
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
                imageUripassed = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUripassed);
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
