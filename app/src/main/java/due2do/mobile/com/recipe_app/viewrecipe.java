package due2do.mobile.com.recipe_app;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class viewrecipe extends AppCompatActivity {


    TextView recipe_names,ingrediants,description;
    ImageView recipe_image;
    DatabaseReference db1;
    modelfood modelfood= new modelfood();
    modelfood views= new modelfood();
    RatingBar rateit;
    Bitmap cameraBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewrecipe);


        db1= FirebaseDatabase.getInstance().getReference();
        recipe_names=findViewById(R.id.recipe_names);
        ingrediants=findViewById(R.id.inced);
        description=findViewById(R.id.descr);
        rateit=findViewById(R.id.rating);
        recipe_image=findViewById(R.id.item_img);




        views =(modelfood) getIntent().getSerializableExtra("ShowData");
        if(views !=null){
            recipe_names.setText(views.getRecipe_name());
            ingrediants.setText(views.getIngrediants());
            description.setText(views.getDescription());
            rateit.setRating(views.getRating());
            description.setMovementMethod(new ScrollingMovementMethod());
            ingrediants.setMovementMethod(new ScrollingMovementMethod());

            FirebaseStorage storage = FirebaseStorage.getInstance("gs://recipeapp-a0410.appspot.com/");
            if (views.getImageUri() != null) {
                StorageReference ref = storage.getReferenceFromUrl(views.getImageUri());

                try {


                    final File file = File.createTempFile("Images", "JPG");
                    ref.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            cameraBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                            cameraBitmap = Bitmap.createScaledBitmap(cameraBitmap, 500, 500, false);
                            recipe_image.setImageBitmap(cameraBitmap);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                        }
                    });

                } catch (IOException e) {
                    Toast.makeText(viewrecipe.this, "Some error occured! Please try again", Toast.LENGTH_SHORT).show();
                }
            }



        }





    }
}
