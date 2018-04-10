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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    //Toolbar toolbar;
    RecyclerView recyclerView;
    Button addRecipe;

    private ArrayList<String> mRecipenames;

    private DatabaseReference mref;



    EditText recipe_name, recipe_type;
    ImageView item_img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //toolbar= findViewById(R.id.tool_bar);
        recyclerView = findViewById(R.id.rv);
        recipe_name=findViewById(R.id.recipe_name);
        recipe_type=findViewById(R.id.recipe_type);

        mref= FirebaseDatabase.getInstance().getReference();





        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.LayoutManager rvLiLayoutManager = layoutManager;
        recyclerView.setLayoutManager(rvLiLayoutManager);




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
