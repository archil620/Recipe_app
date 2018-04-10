package due2do.mobile.com.recipe_app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Archil on 2018-04-06.
 */

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    private Context mContext;
    private List<modelfood> modelfoodList = new ArrayList<>();
    private DatabaseReference databaseReference;

    public FoodAdapter(Context mContext, List<modelfood> list) {
        this.mContext = mContext;
        this.modelfoodList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.fooditems, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final modelfood foodlist = modelfoodList.get(position);


        holder.recipe_name.setText(foodlist.getRecipe_name());
        holder.recipe_type.setText(foodlist.getRecipe_type());

       /* holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               *//* Context context = v.getContext();
                Intent intent = new Intent(context, viewrecipe.class);

                context.startActivity(intent);
                Toast.makeText(mContext,"You Clicked",Toast.LENGTH_SHORT ).show();*//*


            }
        });*/

    }

    @Override
    public int getItemCount() {
        return modelfoodList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //ImageView item_img;
        TextView recipe_name, recipe_type;
        ImageButton editrecipe,deleterecipe;
        public LinearLayout linear;


        public ViewHolder(View itemView) {
            super(itemView);

            // item_img=itemView.findViewById(R.id.item_img);
            recipe_name = itemView.findViewById(R.id.recipe_name);
            recipe_type = itemView.findViewById(R.id.recipe_type);
            editrecipe = itemView.findViewById(R.id.editrecipe);
            deleterecipe= itemView.findViewById(R.id.deleterecipe);
            linear=(LinearLayout) itemView.findViewById(R.id.linear);
            deleterecipe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog diaBox = deleteDialog();
                    diaBox.show();

                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    modelfood foodlist1 = modelfoodList.get(getAdapterPosition());
                    Context context = v.getContext();
                    Intent singleTaskAvtivity = new Intent(context, viewrecipe.class);
                    singleTaskAvtivity.putExtra("ShowData",(modelfoodList.get(getAdapterPosition())));
                    context.startActivity(singleTaskAvtivity);

                }
            });


            editrecipe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    modelfood foodlist1 = modelfoodList.get(getAdapterPosition());
                    Context context = view.getContext();
                    Intent singleTaskAvtivity = new Intent(context, add_edit_recipe.class);
                    singleTaskAvtivity.putExtra("UpdateRecipe",(modelfoodList.get(getAdapterPosition())));
                    context.startActivity(singleTaskAvtivity);

                }
            });



        }

        private AlertDialog deleteDialog() {
            AlertDialog deleteDialog = new AlertDialog.Builder(mContext)
                    //set message, title, and icon
                    .setTitle("Delete")
                    .setMessage("Do you want to Delete")
                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            databaseReference = FirebaseDatabase.getInstance().getReference();
                            modelfood deleterecipe = new modelfood();
                            //Delete task from list and database
                            deleterecipe = modelfoodList.get(getAdapterPosition());
                            modelfoodList.remove(getAdapterPosition());


                            DatabaseReference db1 = databaseReference.child("food-receipe").child(deleterecipe.getKey());
                            db1.setValue(null);

                            Toast.makeText(mContext, "Deleted", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                    }

        })

                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        })
                .create();
            return deleteDialog;
        }


    }
}
