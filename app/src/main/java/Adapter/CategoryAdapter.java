package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import model.Category;

import static android.os.Build.VERSION_CODES.R;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    Context context;
    List<Category> categories;
    private View.OnClickListener clickListener;

    public CategoryAdapter(Context context, List<Category> categories, View.OnClickListener clickListener) {
        this.context = context;
        this.categories = categories;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //  View view = LayoutInflater.from(context).inflate(R.layout.activity_category, parent, false);
        // return new CategoryViewHolder(view);
        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.txt.setText(category.getCategoryName());
        holder.btn.setTag(category);
        holder.imageView.setImageResource(category.getImage());
   }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        TextView txt;
        Button btn;
        ImageView imageView;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
          //  txt = itemView.findViewById(R.id.txt_category);
         //   btn = itemView.findViewById(R.id.btn_view);
         //   imageView = itemView.findViewById(R.id.image_category);
            btn.setOnClickListener(clickListener);
        }
    }
}
