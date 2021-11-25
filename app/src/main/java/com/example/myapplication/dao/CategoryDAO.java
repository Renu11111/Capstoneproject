package com.example.myapplication.dao;

import com.example.myapplication.R;


import java.util.ArrayList;
import java.util.List;

import com.example.myapplication.model.Category;

public class CategoryDAO {
    public static List<Category> getAllCategories(){
        List<Category> categories = new ArrayList<Category>();
        categories.add(new Category("BV","Beverages", R.drawable.beverage));
        categories.add(new Category("PC","Persoanl Care", R.drawable.personal));
        categories.add(new Category("BK","Bakery", R.drawable.bakery));
        categories.add(new Category("CA","Canned Goods", R.drawable.canned));
        categories.add(new Category("DA","Dairy", R.drawable.dairy));
        categories.add(new Category("FF","Frozen Foods", R.drawable.frozen));
        categories.add(new Category("MT","Meat", R.drawable.meat));
        categories.add(new Category("PR","Produce", R.drawable.produce));
        categories.add(new Category("CL","Cleaners", R.drawable.cleaner));
        categories.add(new Category("PG","Paper Goods", R.drawable.papers));
        categories.add(new Category("OT","Others", R.drawable.others));
        return categories;
    }
}



