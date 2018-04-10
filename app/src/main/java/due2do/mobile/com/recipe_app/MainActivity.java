package due2do.mobile.com.recipe_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    //Toolbar toolbar;
    RecyclerView recyclerView;
    Button addRecipe;
    modelfood foodlist = new modelfood();
    FoodAdapter adapter;
    List<modelfood> modelfoodList = new ArrayList<>();


    private DatabaseReference mref;
    EditText recipe_name, recipe_type;
    RatingBar rateit;
    //ImageView item_img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //toolbar= findViewById(R.id.tool_bar);
        recyclerView = findViewById(R.id.rv);
        recipe_name = findViewById(R.id.recipe_name);
        recipe_type = findViewById(R.id.recipe_type);
        rateit=findViewById(R.id.rating);



        mref = FirebaseDatabase.getInstance().getReference().child("food-receipe");
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                modelfoodList.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    modelfood foodlist = ds.getValue(modelfood.class);
                    foodlist.setKey(ds.getKey());
                    modelfoodList.add(foodlist);
                }


                adapter = new FoodAdapter(MainActivity.this, modelfoodList);
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.searchmenu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.addrecipe:
                startActivity(new Intent(MainActivity.this, add_edit_recipe.class));
        }
        return super.onOptionsItemSelected(item);
    }

    // return super.onCreateOptionsMenu(menu);
}
