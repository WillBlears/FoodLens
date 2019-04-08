package com.example.b5025722.foodcam;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Recipe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView recipe_image_view = (ImageView) findViewById(R.id.recipe_image);
        TextView portion_view = (TextView) findViewById(R.id.portion);
        TextView labels_view = (TextView) findViewById(R.id.labels);
        TextView ingredients_view = (TextView) findViewById(R.id.ingredients);
        TextView link_view = (TextView) findViewById(R.id.recipe_link);

        try {
            //image
            Bitmap image = (Bitmap) getIntent().getParcelableExtra("image");
            recipe_image_view.setImageBitmap(image);

            JSONObject recipe = new JSONObject(getIntent().getStringExtra("recipe"));

            //portions
            int yield = recipe.getInt("yield");
            int portion_weight = (int) recipe.getDouble("totalWeight") / yield;
            portion_view.setText("This recipe will serve " + yield + " x " + portion_weight + "g portions");

            //labels
            JSONArray label_array = recipe.getJSONArray("dietLabels");
            JSONArray health_array = recipe.getJSONArray("healthLabels");
            for (int i = 0; i < health_array.length(); i++)
                label_array.put(health_array.getString(i));
            String labels_string = "";
            for (int label = 0; label < label_array.length(); label++)
                labels_string = labels_string + "    " + label_array.getString(label);
            labels_view.setText(labels_string);

            //ingredients
            JSONArray ingredients_array = recipe.getJSONArray("ingredients");
            String ingredients_string = "";
            for (int ing = 0; ing < ingredients_array.length(); ing++)
                ingredients_string += ingredients_array.getJSONObject(ing).getString("text") + "\r\n";
            ingredients_view.setText(ingredients_string);

            //URL
            final String url = recipe.getString("url");
            link_view.setText(url);
            link_view.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(browserIntent);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
            portion_view.setText("Error");
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}




