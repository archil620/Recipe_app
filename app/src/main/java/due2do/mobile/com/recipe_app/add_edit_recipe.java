package due2do.mobile.com.recipe_app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class add_edit_recipe extends AppCompatActivity {

    DatabaseReference databaseReference;
    EditText recipe_name, ingrediants, recipe_type, description;
    Button addDetails;
    ImageView recipe_image;
    modelfood updade;
    private StorageReference mStorageRef;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_recipe);

        addDetails=findViewById(R.id.addDetails);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        recipe_name = findViewById(R.id.recipe_name);
        ingrediants = findViewById(R.id.incre);
        recipe_type = findViewById(R.id.recipe_type);
        description = findViewById(R.id.description);
        recipe_image = findViewById(R.id.item_img);

       //Update method was implemented in our project, so i took the reference from it and implemented the same.
        updade= (modelfood) getIntent().getSerializableExtra("UpdateRecipe");
        if(updade != null){

            recipe_name.setText(updade.getRecipe_name());
            recipe_type.setText(updade.getRecipe_type());
            ingrediants.setText(updade.getIngrediants());
            description.setText(updade.getDescription());

        }


        addDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRecipe();

                startActivity(new Intent(add_edit_recipe.this,MainActivity.class));
            }
        });


    }



    private void saveRecipe() {

        if(updade != null){
            updade.setRecipe_name(String.valueOf(recipe_name.getText()));
            updade.setRecipe_type(String.valueOf(recipe_type.getText()));
            updade.setDescription(String.valueOf(description.getText()));
            updade.setIngrediants(String.valueOf(ingrediants.getText()));
            databaseReference.child("food-receipe").child(updade.getKey()).setValue(updade);

        }else{
            modelfood modelfood = new modelfood();

            modelfood.setRecipe_name(String.valueOf(recipe_name.getText()));
            modelfood.setRecipe_type(String.valueOf(recipe_type.getText()));
            modelfood.setIngrediants(String.valueOf(ingrediants.getText()));
            modelfood.setDescription(String.valueOf(description.getText()));


            databaseReference.child("food-receipe").push().setValue(modelfood);


        }


    }
}
