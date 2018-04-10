//Edit recipe and Delete Recipe module has been referenced from our project Due2Do;
//[1]"archil620/Due2Do", GitHub, 2018. [Online]. Available: https://github.com/archil620/Due2Do. [Accessed: 10- Apr- 2018].

//For Rating Bar
// [2]H. stars, "How to make RatingBar to show five stars", Stackoverflow.com, 2018. [Online]. Available: https://stackoverflow.com/questions/3858600/how-to-make-ratingbar-to-show-five-stars. [Accessed: 10- Apr- 2018].
// [3]R. Android, "Rating Bar Android", Stackoverflow.com, 2018. [Online]. Available: https://stackoverflow.com/questions/32619672/rating-bar-android. [Accessed: 10- Apr- 2018].


//For Recyclerview and cardview
//[4]"Android RecyclerView and CardView Tutorial - Android Tutorial", YouTube, 2018. [Online]. Available: https://www.youtube.com/watch?v=QTiQawMBPUA. [Accessed: 10- Apr- 2018].

//For Search Function
// [5]H. properly, "How to implement search in Recycler View properly", Stackoverflow.com, 2018. [Online]. Available: https://stackoverflow.com/questions/48105976/how-to-implement-search-in-recycler-view-properly. [Accessed: 10- Apr- 2018].

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
import android.widget.SearchView;

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
    SearchView searchView;


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
        MenuItem search=menu.findItem(R.id.menuSearch);

        //this 2 lines
        SearchView searchView=(SearchView)search.getActionView();
        search(searchView);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.addrecipe:
                startActivity(new Intent(MainActivity.this, add_edit_recipe.class));
                return true;

            case R.id.menuSearch:


        }
        return super.onOptionsItemSelected(item);
    }

    private void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText=newText.toLowerCase();
                ArrayList<modelfood> newList=new ArrayList<>();
                for (modelfood info : modelfoodList){
                    String recipename=info.getRecipe_name().toLowerCase();
                    String recipetype=info.getRecipe_type().toLowerCase();
                    if (recipename.contains(newText)||recipetype.contains(newText)){
                        newList.add(info);
                    }
                }
                adapter.setFilter(newList);
                return true;
            }
        });
    }

    // return super.onCreateOptionsMenu(menu);
}
