package due2do.mobile.com.recipe_app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    Button addDetails, takeImage;
    ImageView recipe_image;
    modelfood updade;
    private StorageReference mStorageRef;
    RatingBar rateit;
    TextView value;
    Uri photoUri;
    Bitmap cameraBitmap;
    String mCurrentPhotoPath;
    Map<String, String> flagValue = new HashMap<>();
    modelfood modelfood = new modelfood();
    ImageView itemImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_recipe);

        addDetails = findViewById(R.id.addDetails);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance("gs://recipeapp-a0410.appspot.com/").getReference();

        recipe_name = findViewById(R.id.recipe_name);
        ingrediants = findViewById(R.id.incre);
        recipe_type = findViewById(R.id.recipe_type);
        description = findViewById(R.id.description);
        recipe_image = findViewById(R.id.item_img);
        rateit = (RatingBar) findViewById(R.id.rating);
        takeImage = findViewById(R.id.take_img);
        value = findViewById(R.id.value);
        itemImage = findViewById(R.id.item_img);
        rateit.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                value.setText("" + rating);
            }
        });



        //Update method was implemented in our project, so i took the reference from it and implemented the same.
        updade = (modelfood) getIntent().getSerializableExtra("UpdateRecipe");
        if (updade != null) {

            recipe_name.setText(updade.getRecipe_name());
            recipe_type.setText(updade.getRecipe_type());
            ingrediants.setText(updade.getIngrediants());
            description.setText(updade.getDescription());
            rateit.setRating(updade.getRating());
            FirebaseStorage storage = FirebaseStorage.getInstance("gs://recipeapp-a0410.appspot.com/");
            if (updade.getImageUri() != null) {
                StorageReference ref = storage.getReferenceFromUrl(updade.getImageUri());

                try {
                    final File file = File.createTempFile("Images", "JPG");
                    ref.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            cameraBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                            cameraBitmap = Bitmap.createScaledBitmap(cameraBitmap, 500, 500, false);
                            itemImage.setImageBitmap(cameraBitmap);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                        }
                    });

                } catch (IOException e) {
                    Toast.makeText(add_edit_recipe.this, "Some error occured! Please try again", Toast.LENGTH_SHORT).show();
                }
            }
        }


        takeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhotoFromCamera();
            }
        });


        addDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRecipe();

                startActivity(new Intent(add_edit_recipe.this, MainActivity.class));
            }
        });


    }


    private void saveRecipe() {

        if (updade != null) {
            updade.setRecipe_name(String.valueOf(recipe_name.getText()));
            updade.setRecipe_type(String.valueOf(recipe_type.getText()));
            updade.setDescription(String.valueOf(description.getText()));
            updade.setIngrediants(String.valueOf(ingrediants.getText()));
            updade.setRating((rateit.getRating()));

            databaseReference.child("food-receipe").child(updade.getKey()).setValue(updade);

        } else {

            modelfood.setRecipe_name(String.valueOf(recipe_name.getText()));
            modelfood.setRecipe_type(String.valueOf(recipe_type.getText()));
            modelfood.setIngrediants(String.valueOf(ingrediants.getText()));
            modelfood.setDescription(String.valueOf(description.getText()));
            modelfood.setRating((rateit.getRating()));

            if (flagValue.size() >= 1) {
                if ((flagValue.get("Done")).equals("Yes")) {
                    databaseReference.child("food-receipe").push().setValue(modelfood);
                    Toast.makeText(add_edit_recipe.this, "Task Created", Toast.LENGTH_SHORT).show();

                    Intent displayTask = new Intent(add_edit_recipe.this, MainActivity.class);
                    startActivity(displayTask);
                } else {
                    Toast.makeText(add_edit_recipe.this, "Image Still Uploading", Toast.LENGTH_SHORT).show();
                }
            } else {
                databaseReference.child("food-receipe").push().setValue(modelfood);
                Toast.makeText(add_edit_recipe.this, "Task Created", Toast.LENGTH_SHORT).show();

                Intent displayTask = new Intent(add_edit_recipe.this, MainActivity.class);
                startActivity(displayTask);
            }








        }


    }

    //Capture image function
    private void takePhotoFromCamera() {
        Intent m_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Toast.makeText(this, "Please try again", Toast.LENGTH_SHORT).show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoUri = FileProvider.getUriForFile(this,
                        "due2do.mobile.com.duetodo",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePictureIntent, 1);
            }
        }
    }

    //Create and store image file
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    //Post capture image functions
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            if(updade != null){
                StorageReference filepath = mStorageRef.child("food-receipe").child(photoUri.getLastPathSegment());
                flagValue.put("Done", "No");
                Toast.makeText(this, "Image Uploading", Toast.LENGTH_SHORT).show();
                filepath.putFile(photoUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        flagValue.put("Done", "Yes");
                        Toast.makeText(add_edit_recipe.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                        updade.setImageUri(String.valueOf(taskSnapshot.getDownloadUrl()));
                        Bitmap bitmap = null;
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(add_edit_recipe.this.getContentResolver(), photoUri);
                            itemImage.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(add_edit_recipe.this, "Some error occured! Please try again", Toast.LENGTH_SHORT).show();
                    }
                });

            }else{
                StorageReference filepath = mStorageRef.child("food-receipe").child(photoUri.getLastPathSegment());
                flagValue.put("Done", "No");
                Toast.makeText(this, "Image Uploading", Toast.LENGTH_SHORT).show();
                filepath.putFile(photoUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        flagValue.put("Done", "Yes");
                        Toast.makeText(add_edit_recipe.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                        modelfood.setImageUri(String.valueOf(taskSnapshot.getDownloadUrl()));
                        Bitmap bitmap = null;
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(add_edit_recipe.this.getContentResolver(), photoUri);
                            itemImage.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(add_edit_recipe.this, "Some error occured! Please try again", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }

    }
}
