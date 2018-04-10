package due2do.mobile.com.recipe_app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Archil on 2018-04-06.
 */

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    private Context mContext;
    private List<modelfood> modelfoodList = new ArrayList<>();
    private DatabaseReference databaseReference;
    public FoodAdapter(Context mContext, List<modelfood> list){
        this.mContext = mContext;
        this.modelfoodList=list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
       View view=  layoutInflater.inflate(R.layout.fooditems,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final modelfood foodlist = modelfoodList.get(position);


         holder.recipe_name.setText(foodlist.getRecipe_name());
         holder.recipe_type.setText(foodlist.getRecipe_type());

    }

    @Override
    public int getItemCount() {
        return modelfoodList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        //ImageView item_img;
        TextView recipe_name,recipe_type;
        ImageButton editrecipe;


        public ViewHolder(View itemView) {
            super(itemView);

           // item_img=itemView.findViewById(R.id.item_img);
            recipe_name=itemView.findViewById(R.id.recipe_name);
            recipe_type= itemView.findViewById(R.id.recipe_type);
            editrecipe= itemView.findViewById(R.id.editrecipe);


           /* editrecipe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    modelfood foodlist1 = modelfoodList.get(getAdapterPosition());
                    Context context = view.getContext();
                    Intent singleTaskAvtivity = new Intent(context, add_edit_recipe.class);
                    singleTaskAvtivity.putExtra("clickedData", modelfoodList.get(getAdapterPosition()));
                    context.startActivity(singleTaskAvtivity);*/

        }


    }


}
