package dev.edmt.android_app.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import dev.edmt.android_app.R;
import dev.edmt.android_app.model.BannerCoffee;

public class BannerCoffePagerAdapter extends PagerAdapter {

    Context context;
    List<BannerCoffee> bannerCoffeeList;

    public BannerCoffePagerAdapter(Context context, List<BannerCoffee> bannerCoffeeList){
        this.context = context;
        this.bannerCoffeeList = bannerCoffeeList;
    }


    @Override
    public int getCount() {
        return bannerCoffeeList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
       container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.banner_coffee_layout,null);

        ImageView bannerImage = view.findViewById(R.id.banner_image);

        Glide.with(context).load(bannerCoffeeList.get(position).getImageUrl()).into(bannerImage);
        container.addView(view);
        return view;
    };
}
