package com.example.b5025722.foodcam;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import static android.R.attr.data;

public class MainActivity extends AppCompatActivity{

    private final int CAMERA_PIC_REQUEST = 13;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b = (Button) findViewById(R.id.button);
        Button search_button = (Button) findViewById(R.id.search_button);
        final EditText query = (EditText) findViewById(R.id.editText);

        final CheckBox veg = (CheckBox) findViewById(R.id.check_veg);
        final CheckBox vegan = (CheckBox) findViewById(R.id.check_vegan);
        final CheckBox protein = (CheckBox) findViewById(R.id.check_protein);

        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
            }
        });

        search_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent query_intent = new Intent(MainActivity.this, Results.class);
                query_intent.putExtra("query", query.getText().toString());
                query_intent.putExtra("veg", veg.isChecked());
                query_intent.putExtra("vegan", vegan.isChecked());
                query_intent.putExtra("protein", protein.isChecked());
                startActivity(query_intent);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_PIC_REQUEST) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            ImageView imageview = (ImageView) findViewById(R.id.imageView); //sets imageview as the bitmap
            imageview.setImageBitmap(image);
        }
    }
}