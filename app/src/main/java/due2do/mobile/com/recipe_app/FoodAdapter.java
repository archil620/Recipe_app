package due2do.mobile.com.recipe_app;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Archil on 2018-04-06.
 */

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<modelfood> mlist;
    FoodAdapter(Context context, ArrayList<modelfood> list){
        mContext = context;
        mlist=list;
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

        modelfood fooditem=mlist.get(position);
        ImageView image = holder.item_img;
        TextView name,recipe_type;

        name= holder.item_name;
        recipe_type= holder.recipe_type;

        //image.setImageResource(fooditem.getImage());

        name.setText("Text");
        recipe_type.setText(fooditem.getRecipe_type());


    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView item_img;
        TextView item_name,recipe_type;
        public ViewHolder(View itemView) {
            super(itemView);

            item_img=itemView.findViewById(R.id.item_img);
            item_name=itemView.findViewById(R.id.itm_name);
            recipe_type= itemView.findViewById(R.id.recipe_type);


        }


    }
}
