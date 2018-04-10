package due2do.mobile.com.recipe_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class viewrecipe extends AppCompatActivity {


    TextView recipe_names,ingrediants,description;
    ImageView recipe_image;
    DatabaseReference db1;
    modelfood modelfood= new modelfood();
    modelfood views= new modelfood();
    RatingBar rateit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewrecipe);


        db1= FirebaseDatabase.getInstance().getReference();
        recipe_names=findViewById(R.id.recipe_names);
        ingrediants=findViewById(R.id.inced);
        description=findViewById(R.id.descr);
        rateit=findViewById(R.id.rating);



        views =(modelfood) getIntent().getSerializableExtra("ShowData");
        if(views !=null){
            recipe_names.setText(views.getRecipe_name());
            ingrediants.setText(views.getIngrediants());
            description.setText(views.getDescription());
            rateit.setRating(views.getRating());
            description.setMovementMethod(new ScrollingMovementMethod());
            ingrediants.setMovementMethod(new ScrollingMovementMethod());



        }





    }
}
