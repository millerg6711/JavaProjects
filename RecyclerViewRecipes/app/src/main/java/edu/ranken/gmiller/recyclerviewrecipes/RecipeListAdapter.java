package edu.ranken.gmiller.recyclerviewrecipes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {
    private final List<Recipe> mRecipeList;
    private final LayoutInflater mInflater;
    private final Context mContext;

    public RecipeListAdapter(Context context, List<Recipe> mRecipeList) {
        this.mRecipeList = mRecipeList;
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecipeListAdapter.RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View recipeView = mInflater.inflate(R.layout.recipe_item, parent, false);
        return new RecipeViewHolder(recipeView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeListAdapter.RecipeViewHolder holder, int position) {
        Recipe recipe = mRecipeList.get(position);
        holder.mRecipeTitle.setText(recipe.getTitle());
        holder.mRecipeDescription.setText(recipe.getDescripition());
    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //private final View mLinearLayout;
        private final TextView mRecipeTitle;
        private final TextView mRecipeDescription;

        private RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            //mLinearLayout = itemView.findViewById(R.id.recipe_item_layout);
            mRecipeTitle = itemView.findViewById(R.id.recipe_title);
            mRecipeDescription = itemView.findViewById(R.id.recipe_description);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // get clicked item
            int index = getLayoutPosition();
            Recipe item = mRecipeList.get(index);
            //Context context = v.getContext();
            Intent intent = new Intent(mContext, FullRecipeActivity.class);
            intent.putExtra("Recipe", item);
            mContext.startActivity(intent);
        }
    }
}

