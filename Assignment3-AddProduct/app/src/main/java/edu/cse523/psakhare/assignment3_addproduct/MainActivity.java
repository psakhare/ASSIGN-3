package edu.cse523.psakhare.assignment3_addproduct;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ListView listView = null;
    static public ProductAdapter productAdapter = null;
    Button newBtn = null;
    int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.MANAGE_DOCUMENTS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        ActivityCompat.requestPermissions(
                this,
                PERMISSIONS_STORAGE,1
        );
        // Find listview by id
        listView = (ListView) findViewById(R.id.listView);
        productAdapter = new ProductAdapter(this);

        listView.setAdapter(productAdapter);

        newBtn = (Button)findViewById(R.id.newBtn);
        newBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(v.getContext(), AddProduct.class);
                startActivityForResult(i, REQUEST_CODE);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE)
        {
            if (data.hasExtra("PRICE") && data.hasExtra("NAME") && data.getData() != null)
            {
                String text = data.getExtras().getString("NAME");
                String price = data.getExtras().getString("PRICE");
                Uri imageUri = data.getData();

                productAdapter.addProduct(text, price, imageUri);
                productAdapter.notifyDataSetChanged();
            }
        }
    }
}
