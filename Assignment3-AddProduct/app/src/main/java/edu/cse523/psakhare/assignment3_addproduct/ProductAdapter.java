package edu.cse523.psakhare.assignment3_addproduct;

/**
 * Created by Priyanka on 11/3/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ProductAdapter extends BaseAdapter {
    ArrayList<Product> products = new ArrayList<>();
    public Context con;
    ProductAdapter(Context con)
    {
        this.con=con;
    }
    void addProduct(String pName, String pPrice, Uri pImage)
    {
        Product product = new Product(pName, pPrice, pImage);
        products.add(product);
    }
    void editProduct(String pName, String pPrice, Uri pImage,int index)
    {
        Product product = new Product(pName, pPrice, pImage);
        int arraysize = products.size();
        if(arraysize>0)
            products.set(index,product);
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int i) {
        return products.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        view = (View) inflater.inflate(R.layout.product_item, null);

        TextView name = (TextView) view.findViewById(R.id.name);
        TextView price = (TextView) view.findViewById(R.id.price);

        name.setText(products.get(i).getName());
        price.setText(products.get(i).getPrice());

        Uri imageUri = products.get(i).getImage();
        ImageView iv = (ImageView) view.findViewById(R.id.imageView);
        iv.setImageURI(imageUri);

        ImageButton deleteButton = (ImageButton) view.findViewById(R.id.delete_button);
        deleteButton.setTag(i);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = (int) v.getTag();
                Log.e("check",products.get(index).getName()+" index"+index);
                products.remove(index);
                Log.e("size",products.size()+" ");
                notifyDataSetChanged();
            }
        });
        ImageButton editButton = (ImageButton) view.findViewById(R.id.edit_button);
        editButton.setTag(i);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = (int) v.getTag();
                Log.e("check",products.get(index).getName()+" index"+index);
                Intent i = new Intent(v.getContext(), EditProduct.class);
                i.putExtra("name",products.get(index).getName());
                i.putExtra("price",products.get(index).getPrice());
                i.putExtra("index",index);
                i.setData(products.get(index).getImage());
                con.startActivity(i);
            }
        });

        return view;
    }
}

