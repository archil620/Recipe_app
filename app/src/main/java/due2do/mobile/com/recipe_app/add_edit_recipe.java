package due2do.mobile.com.recipe_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class add_edit_recipe extends AppCompatActivity {

    DatabaseReference databaseReference;
    EditText recipe_name, ingrediants, recipe_type, description;
    Button addDetails;
    ImageView recipe_image;

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
        //addDetails = findViewById(R.id.addDetails);


        addDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRecipe();
            }
        });
    }

    private void saveRecipe() {

        modelfood modelfood = new modelfood();

        modelfood.setRecipe_name(String.valueOf(recipe_name.getText()));
        modelfood.setRecipe_type(String.valueOf(recipe_type.getText()));
        modelfood.setIngrediants(String.valueOf(ingrediants.getText()));
        modelfood.setDescription(String.valueOf(description.getText()));


        databaseReference.child("food-receipe").push().setValue(modelfood);
    }
}
