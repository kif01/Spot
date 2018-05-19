package com.home.khalil.bottomsheet;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.hujiaweibujidao.wava.Techniques;
import com.github.hujiaweibujidao.wava.YoYo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by khalil on 1/31/17.
 */

public class RecyclerViewCategoryAdapter extends RecyclerView.Adapter<RecyclerViewCategoryAdapter.CategoryViewHolder>  {
    private static List<Category> itemList;
    public static boolean visible=false;




    public static Context context;
    private DatabaseReference mDatabase;



    public RecyclerViewCategoryAdapter(Context context, List<Category> itemList){
        this.itemList=itemList;
        this.context=context;
    }


    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_element_design,null);

        CategoryViewHolder cvh=new CategoryViewHolder(layoutView);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        return cvh;
    }

    @Override
    public void onBindViewHolder(RecyclerViewCategoryAdapter.CategoryViewHolder holder, int position) {

        String l=itemList.get(position).getLevel();
        holder.categotyTitle.setText(itemList.get(position).getTitle());
  if(l.equals("1")){
            holder.categotyTitle.setTextColor(Color.parseColor("#44ffffff"));
            holder.categotyTitle.setTextSize(16);
            holder.layout.setBackgroundColor(Color.parseColor("#99000000"));
            level1(itemList.get(position).getTitle(),holder);
      holder.megaLike.setVisibility(View.GONE);
        }else if(l.equals("2")){
            holder.categotyTitle.setTextColor(Color.parseColor("#ffffff"));
            holder.categotyTitle.setTextSize(22);
            holder.flag=true;
            holder.layout.setBackgroundColor(Color.parseColor("#D500F9"));
            level2(itemList.get(position).getTitle(),holder);
      holder.megaLike.setVisibility(View.VISIBLE);
        }/*else if(l.equals("3")){
            holder.categotyTitle.setTextColor(Color.parseColor("#ffffff"));
            holder.categotyTitle.setTextSize(22);
            holder.layout.setBackgroundColor(Color.parseColor("#D500F9"));
            level3(itemList.get(position).getTitle(),holder);
            holder.megaLike.setVisibility(View.VISIBLE);
        }*/

        //holder.categoryImage.setImageResource(itemList.get(position).getImage());



    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder{

        public TextView categotyTitle;
        public ImageView categoryImage;
        public boolean flag=false;
        public boolean flag2=false;
        public RelativeLayout layout;
        public ImageView megaLike;
        public CardView card;
        public static Map<String,String> prefs=new HashMap<String,String>();
        public static HashMap<Integer,String> items=new HashMap();

        public CategoryViewHolder(View itemView) {
            super(itemView);
            categotyTitle=(TextView) itemView.findViewById(R.id.category_title);
            categoryImage=(ImageView) itemView.findViewById(R.id.category_image);
            layout=(RelativeLayout) itemView.findViewById(R.id.layout);
            card= (CardView) itemView.findViewById(R.id.card);
            setupMapList();

              megaLike= (ImageView) itemView.findViewById(R.id.button_like);

            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int i=getAdapterPosition();

                   // visible=true;

                    if (flag==false && flag2==false) {
                        YoYo.with(Techniques.BounceIn).duration(1000).playOn(card);


                        // love.setBackgroundResource(R.drawable.ic_like_fill);
/*                        MainActivity.mSmallBang.bang(v, 250, new SmallBangListener() {
                            @Override
                            public void onAnimationStart() {
                                YoYo.with(Techniques.FadeIn).duration(1000).playOn(card);
                            }

                            @Override
                            public void onAnimationEnd() {

                            }
                        });*/
                        megaLike.setVisibility(View.VISIBLE);




                        //categotyTitle.setTextColor(Color.parseColor("#3cb2e2"));
                        categotyTitle.setTextColor(Color.parseColor("#ffffff"));
                        // categotyTitle.setBackgroundColor(Color.parseColor("#F8CA00"));
                        layout.setBackgroundColor(Color.parseColor("#D500F9"));




                        categotyTitle.setTextSize(22);
                        flag=true;
                        if(i==0){

                            categoryImage.setImageResource(R.drawable.american);
                            prefs.put("american","2");
                            SaveSharedPreference.setAmerican( context,"2");
                            items.put(0,"/american");

                        }else if(i==1) {

                            categoryImage.setImageResource(R.drawable.cafe);
                            prefs.put("cafe","2");
                            SaveSharedPreference.setCafe( context,"2");
                            items.put(1,"/cafe");


                        }else if(i==2){

                            categoryImage.setImageResource(R.drawable.chinese);
                            prefs.put("chinese","2");
                            SaveSharedPreference.setChinese( context,"2");
                            items.put(2,"/chinese");

                        }else if (i==3){
                            Picasso.with(context).load(R.drawable.dessert).into(categoryImage);
                            //categoryImage.setImageResource(R.drawable.dessert);
                            prefs.put("dessert","2");
                            SaveSharedPreference.setDessert( context,"2");
                            items.put(3,"/dessert");

                        }else if(i==4){

                            categoryImage.setImageResource(R.drawable.fastfood);
                            prefs.put("fastfood","2");
                            items.put(4,"/fastfood");

                            SaveSharedPreference.setFastFood( context,"2");
                        }else if (i==5){

                            categoryImage.setImageResource(R.drawable.italian);
                            prefs.put("italian","2");
                            SaveSharedPreference.setItalian( context,"2");
                            items.put(5,"/italian");

                        }else if(i==6){

                            categoryImage.setImageResource(R.drawable.lebanese);
                            prefs.put("lebanese","2");
                            SaveSharedPreference.setLebanese( context,"2");
                            items.put(6,"/lebanese");

                        }else if (i==7){
                            categoryImage.setImageResource(R.drawable.sushi);
                            prefs.put("sushi","2");
                            SaveSharedPreference.setSushi( context,"2");
                            items.put(7,"/sushi");
                        }
                        flag=true;

                    }else if(flag==true && flag2==false){
                        YoYo.with(Techniques.BounceIn).duration(1000).playOn(card);


                        //categotyTitle.setTextColor(Color.parseColor("#C0C0C0"));
                        categotyTitle.setTextColor(Color.parseColor("#44ffffff"));
                        categotyTitle.setTextSize(16);

                        //categotyTitle.setBackgroundColor(Color.parseColor("#ffffff"));
                        layout.setBackgroundColor(Color.parseColor("#99000000"));
                        megaLike.setVisibility(View.GONE);
                        if(i==0){
                            Picasso.with(context).load(R.drawable.american1).into(categoryImage);
                            //categoryImage.setImageResource(R.drawable.american1);
                            prefs.put("american","1");
                            SaveSharedPreference.setAmerican( context,"1");
                            items.remove(0);


                        }else if(i==1) {

                            categoryImage.setImageResource(R.drawable.cafe1);
                            prefs.put("cafe","1");
                            SaveSharedPreference.setCafe( context,"1");
                            items.remove(1);

                        }else if(i==2){

                           categoryImage.setImageResource(R.drawable.chinese1);
                            prefs.put("chinese","1");
                            SaveSharedPreference.setChinese(context,"1");
                            items.remove(2);

                        }else if (i==3){

                            categoryImage.setImageResource(R.drawable.dessert1);
                            prefs.put("dessert","1");
                            SaveSharedPreference.setDessert( context,"1");
                            items.remove(3);

                        }else if(i==4){

                            categoryImage.setImageResource(R.drawable.fastfood1);
                            prefs.put("fastfood","1");
                            SaveSharedPreference.setFastFood( context,"1");
                            items.remove(4);

                        }else if (i==5){

                            categoryImage.setImageResource(R.drawable.italian1);
                            prefs.put("italian","1");
                            SaveSharedPreference.setItalian( context,"1");
                            items.remove(5);

                        }else if (i==6){

                            categoryImage.setImageResource(R.drawable.lebanese1);
                            prefs.put("lebanese","1");
                            SaveSharedPreference.setLebanese( context,"1");
                            items.remove(6);

                        }else if (i==7){

                            categoryImage.setImageResource(R.drawable.sushi1);
                            prefs.put("sushi","1");
                            SaveSharedPreference.setSushi( context,"1");
                            items.remove(7);

                        }
                        flag=false;




                   /* }else if(flag==false && flag2==true){

                        YoYo.with(Techniques.BounceIn).duration(1000).playOn(card);

                        // categotyTitle.setBackgroundColor(Color.parseColor("#ffffff"));
                        layout.setBackgroundColor(Color.parseColor("#99000000"));
                        megaLike.setVisibility(View.GONE);
                        // categotyTitle.setTextColor(Color.parseColor("#C0C0C0"));
                        categotyTitle.setTextColor(Color.parseColor("#44ffffff"));
                        categotyTitle.setTextSize(16);
                        if(i==0){
                            categoryImage.setImageResource(R.drawable.electronics1);
                            prefs.put("electronics","1");
                            SaveSharedPreference.setAmerican( context,"1");
                        }else if(i==1) {
                            categoryImage.setImageResource(R.drawable.fashion1);
                            prefs.put("fashion","1");
                            SaveSharedPreference.setCafe( context,"1");
                        }else if(i==2){
                            categoryImage.setImageResource(R.drawable.furniture1);
                            prefs.put("furniture","1");
                            SaveSharedPreference.setFastFood( context,"1");
                        }else if (i==3){
                            categoryImage.setImageResource(R.drawable.gaming1);
                            prefs.put("american","1");
                            SaveSharedPreference.setDessert( context,"1");
                        }else if(i==4){
                            categoryImage.setImageResource(R.drawable.restaurants1);
                            prefs.put("restaurants","1");
                            SaveSharedPreference.setChinese( context,"1");
                        }else if (i==5){
                            categoryImage.setImageResource(R.drawable.sports1);
                            prefs.put("sports","1");
                            SaveSharedPreference.setItalian( context,"1");
                        }
                        flag2=false;*/
                  /*  }else if(flag==true && flag2==true){
                        YoYo.with(Techniques.BounceIn).duration(1000).playOn(card);
                        categotyTitle.setTextColor(Color.parseColor("#99ffffff"));
                        //  categotyTitle.setBackgroundColor(Color.parseColor("#F8CA00"));
                        layout.setBackgroundColor(Color.parseColor("#98ce1e"));

                        megaLike.setVisibility(View.GONE);
                        // categotyTitle.setTextColor(Color.parseColor("#2EBAFF"));
                        categotyTitle.setTextSize(19);
                        if(i==0){
                            categoryImage.setImageResource(R.drawable.electronics2);
                            prefs.put("electronics","2");
                            SaveSharedPreference.setAmerican( context,"2");
                        }else if(i==1) {
                            categoryImage.setImageResource(R.drawable.fashion2);
                            prefs.put("fashion","2");
                            SaveSharedPreference.setCafe( context,"2");
                        }else if(i==2){
                            categoryImage.setImageResource(R.drawable.furniture2);
                            prefs.put("furniture","2");
                            SaveSharedPreference.setFastFood( context,"2");
                        }else if (i==3){
                            categoryImage.setImageResource(R.drawable.gaming2);
                            prefs.put("american","2");
                            SaveSharedPreference.setDessert( context,"2");
                        }else if(i==4){
                            categoryImage.setImageResource(R.drawable.restaurants2);
                            prefs.put("restaurants","2");
                            SaveSharedPreference.setChinese( context,"2");
                        }else if (i==5){
                            categoryImage.setImageResource(R.drawable.sports2);
                            prefs.put("sports","2");
                            SaveSharedPreference.setItalian( context,"2");
                        }
                        flag2=false;*/

                    }



                }
            });



           /* card.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    visible = true;
                    card.setPressed(true);
                    final int i = getAdapterPosition();


                    if (flag2 == true) {
                        return true;
                    } else {

                        YoYo.with(Techniques.BounceIn).duration(1000).playOn(card);
                        if (i == 0) {
                            categoryImage.setImageResource(R.drawable.electronics3);
                            prefs.put("electronics","3");
                            SaveSharedPreference.setAmerican( context,"3");
                        } else if (i == 1) {
                            categoryImage.setImageResource(R.drawable.fashion3);
                            prefs.put("fashion","3");
                            SaveSharedPreference.setCafe( context,"3");
                        } else if (i == 2) {
                            categoryImage.setImageResource(R.drawable.furniture3);
                            prefs.put("furniture","3");
                            SaveSharedPreference.setFastFood( context,"3");
                        } else if (i == 3) {
                            categoryImage.setImageResource(R.drawable.gaming3);
                            prefs.put("american","3");
                            SaveSharedPreference.setDessert( context,"3");
                        } else if (i == 4) {
                            categoryImage.setImageResource(R.drawable.restaurants3);
                            prefs.put("restaurants","3");
                            SaveSharedPreference.setChinese( context,"3");
                        } else if (i == 5) {
                            categoryImage.setImageResource(R.drawable.sports3);
                            prefs.put("sports","3");
                            SaveSharedPreference.setItalian( context,"3");
                        }
                        megaLike.setVisibility(View.VISIBLE);

                        categotyTitle.setTextColor(Color.parseColor("#ffffff"));


                        layout.setBackgroundColor(Color.parseColor("#D500F9"));
                        categotyTitle.setTextSize(22);
                        flag2 = true;

                        return true;
                    }
                }
            });*/
        }

        public void setupMapList(){
            prefs.put("american",SaveSharedPreference.getAmerican(context));
            prefs.put("cafe",SaveSharedPreference.getCafe(context));
            prefs.put("chinese",SaveSharedPreference.getChinese(context));
            prefs.put("dessert",SaveSharedPreference.getDessert(context));
            prefs.put("fastfood",SaveSharedPreference.getFastFood(context));
            prefs.put("italian",SaveSharedPreference.getItalian(context));
            prefs.put("lebanese",SaveSharedPreference.getLebanese(context));
            prefs.put("sushi",SaveSharedPreference.getSushi(context));
        }

        }

    private void level1(String str,RecyclerViewCategoryAdapter.CategoryViewHolder holder){
        if(str.equals("American"))
            holder.categoryImage.setImageResource(R.drawable.american1);


         if(str.equals("Cafe"))
            holder.categoryImage.setImageResource(R.drawable.cafe1);

         if(str.equals("Chinese"))
            holder.categoryImage.setImageResource(R.drawable.chinese1);

         if (str.equals("Dessert"))
            holder.categoryImage.setImageResource(R.drawable.dessert1);

         if(str.equals("Fastfood"))
            holder.categoryImage.setImageResource(R.drawable.fastfood1);

         if (str.equals("Italian"))
            holder.categoryImage.setImageResource(R.drawable.italian1);

        if (str.equals("Lebanese"))
            holder.categoryImage.setImageResource(R.drawable.lebanese1);

        if (str.equals("Sushi"))
            holder.categoryImage.setImageResource(R.drawable.sushi1);




    }

   /* private void level2(String str,RecyclerViewCategoryAdapter.CategoryViewHolder holder){
        if(str.equals("Electronics")){
            holder.categoryImage.setImageResource(R.drawable.electronics2);

        }else if(str.equals("Fashion")) {
            holder.categoryImage.setImageResource(R.drawable.fashion2);

        }else if(str.equals("Restaurants")){
            holder.categoryImage.setImageResource(R.drawable.restaurants2);

        }else if (str.equals("Gaming")){
            holder.categoryImage.setImageResource(R.drawable.gaming2);

        }else if(str.equals("Furniture")){
            holder.categoryImage.setImageResource(R.drawable.furniture2);

        }else if (str.equals("Sports")){
            holder.categoryImage.setImageResource(R.drawable.sports2);

        }

    }*/
    private void level2(String str,RecyclerViewCategoryAdapter.CategoryViewHolder holder){
        if(str.equals("American"))
            holder.categoryImage.setImageResource(R.drawable.american);

        if(str.equals("Cafe"))
            holder.categoryImage.setImageResource(R.drawable.cafe);

        if(str.equals("Chinese"))
            holder.categoryImage.setImageResource(R.drawable.chinese);

        if (str.equals("Dessert"))
            holder.categoryImage.setImageResource(R.drawable.dessert);

        if(str.equals("Fastfood"))
            holder.categoryImage.setImageResource(R.drawable.fastfood);

        if (str.equals("Italian"))
            holder.categoryImage.setImageResource(R.drawable.italian);

        if (str.equals("Lebanese"))
            holder.categoryImage.setImageResource(R.drawable.lebanese);

        if (str.equals("Sushi"))
            holder.categoryImage.setImageResource(R.drawable.sushi);

    }

}