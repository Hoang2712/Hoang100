package dev.edmt.android_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import dev.edmt.android_app.Adapter.BannerCoffePagerAdapter;
import dev.edmt.android_app.Common.Common;
import dev.edmt.android_app.Interface.ItemClickListener;
import dev.edmt.android_app.Service.ListenOrder;
import dev.edmt.android_app.ViewHolder.MenuViewHolder;
import dev.edmt.android_app.model.BannerCoffee;
import dev.edmt.android_app.model.Category;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    FirebaseDatabase database;
    DatabaseReference category;

    BannerCoffePagerAdapter bannerCoffePagerAdapter;
    TabLayout tabLayout;
    ViewPager bannerCoffeeViewPage;
    List<BannerCoffee> bannerCoffeesList;

    TextView txtFullName;

    RecyclerView recyler_menu;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Category,MenuViewHolder>adapter;

    SwipeRefreshLayout swipeRefreshLayout;

    //Slider
    HashMap<String,String> image_list;
    SliderLayout mSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tabLayout = findViewById(R.id.tab_indicator);

        bannerCoffeesList = new ArrayList<>();
        bannerCoffeesList.add(new BannerCoffee(1, "coffee", "https://scontent.fhan2-4.fna.fbcdn.net/v/t1.6435-9/154939715_251051793236531_6055937718767526162_n.jpg?_nc_cat=111&ccb=1-3&_nc_sid=730e14&_nc_ohc=0lylpM9BAQgAX-I6V2_&_nc_ht=scontent.fhan2-4.fna&oh=42647b4b10d7fc71521b6479aed90fc0&oe=60C8E83D", ""));
        bannerCoffeesList.add(new BannerCoffee(2, "coffee", "https://scontent.fhan14-2.fna.fbcdn.net/v/t1.6435-9/141756916_227171778957866_5650020856718976387_n.jpg?_nc_cat=105&ccb=1-3&_nc_sid=730e14&_nc_ohc=XR_ynwazFwMAX8PxFB6&_nc_ht=scontent.fhan14-2.fna&oh=3e070c76b1f3a973f26ac6e008f18655&oe=60CA7644", ""));
        bannerCoffeesList.add(new BannerCoffee(3, "coffee", "https://scontent.fhan14-2.fna.fbcdn.net/v/t1.6435-9/152089751_244722567202787_9082139026686303652_n.jpg?_nc_cat=105&ccb=1-3&_nc_sid=730e14&_nc_ohc=S7BHM1iEL84AX83dgGC&_nc_ht=scontent.fhan14-2.fna&oh=e5c68411c393c6c65a8029498a643d93&oe=60CAEF46", ""));
        bannerCoffeesList.add(new BannerCoffee(4, "coffee", "https://scontent.fhan14-2.fna.fbcdn.net/v/t1.6435-9/111090199_150943859913992_5193632064631912713_n.png?_nc_cat=101&ccb=1-3&_nc_sid=730e14&_nc_ohc=smvireigfLIAX_KPmHX&_nc_ht=scontent.fhan14-2.fna&oh=2bccc27eb173eb13abfeece8a8c9a19a&oe=60CBCF11", ""));
        bannerCoffeesList.add(new BannerCoffee(5, "Tr?? S???a CHEESY BLU", "https://scontent.fhan14-2.fna.fbcdn.net/v/t1.6435-9/118287792_161684155506629_2284978374569873626_n.jpg?_nc_cat=102&ccb=1-3&_nc_sid=730e14&_nc_ohc=xHgfZgJVWLMAX8Vd75y&_nc_ht=scontent.fhan14-2.fna&oh=ade836f80c92f0bc90a06215786d32bb&oe=60C9B693", ""));

        setBannerCoffePagerAdapter(bannerCoffeesList);

        Timer sliderTimer = new Timer();
        sliderTimer.scheduleAtFixedRate(new AutoSlider(), 2000,3000);
        tabLayout.setupWithViewPager(bannerCoffeeViewPage, true);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("DANH M???C");
        setSupportActionBar(toolbar);





        //view swipe
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swip_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(Common.isConnectedToInterner(getBaseContext()))
                    loadMenu();
                else
                    Toast.makeText(getBaseContext(),"Ki???m tra l???i k???t n???i!!",Toast.LENGTH_SHORT).show();
                return;

            }
        });
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                if(Common.isConnectedToInterner(getBaseContext()))
                    loadMenu();
                else
                    Toast.makeText(getBaseContext(),"Ki???m tra l???i k???t n???i!!",Toast.LENGTH_SHORT).show();
                return;
            }
        });

        //init FB

        database=FirebaseDatabase.getInstance("https://order-1f25c-default-rtdb.asia-southeast1.firebasedatabase.app/");
        category=database.getReference("Category");

        FloatingActionButton fab =(FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cartIntent =new Intent( Home.this, Cart.class);
                startActivity(cartIntent); }

        });


                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
       ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
              this, drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //set Name
        View headerView = navigationView.getHeaderView(0);
        txtFullName = (TextView)headerView.findViewById(R.id.txtFullName);
        txtFullName.setText(Common.currentUser.getName());

        //Load menu
        recyler_menu = (RecyclerView)findViewById(R.id.recycler_menu);
        recyler_menu.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyler_menu.setLayoutManager(layoutManager);

        loadMenu();





        ///Register service
        Intent service = new Intent(Home.this, ListenOrder.class);
        startService(service);
    }



    private void loadMenu() {
            adapter = new FirebaseRecyclerAdapter<Category,MenuViewHolder>(Category.class,R.layout.menu_item,MenuViewHolder.class,category) {
            @Override
            protected void populateViewHolder(MenuViewHolder viewHolder, dev.edmt.android_app.model.Category model, int position) {

                viewHolder.txtMenuName.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.imageView);
                final Category clickitem = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //get category id
                        Intent foodList = new Intent(Home.this,FoodList.class);
                        //Because Category id is key
                        foodList.putExtra("CategoryId",adapter.getRef(position).getKey());
                        startActivity(foodList);
                    }
                });
            }
        };
        recyler_menu.setAdapter(adapter);
        swipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_menu) {

        } else if (id == R.id.nav_Carts) {
            Intent cartIntent = new Intent(Home.this, Cart.class);
            startActivity(cartIntent);

        } else if (id == R.id.nav_orders) {
            Intent orderIntent = new Intent (Home.this, OrderStatus.class);
            startActivity(orderIntent);

        } else if (id == R.id.nav_Log_out) {

            Intent SignIn = new Intent(Home.this, Signin.class);
            SignIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(SignIn);

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void setBannerCoffePagerAdapter(List<BannerCoffee> bannerCoffeesList){
        bannerCoffeeViewPage = findViewById(R.id.banner_viewPager);
        bannerCoffePagerAdapter = new BannerCoffePagerAdapter(this, bannerCoffeesList);
        bannerCoffeeViewPage.setAdapter(bannerCoffePagerAdapter);
        tabLayout.setupWithViewPager(bannerCoffeeViewPage);

    }
    class AutoSlider extends TimerTask{
        @Override
        public void run() {
            Home.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(bannerCoffeeViewPage.getCurrentItem() < bannerCoffeesList.size() -1){
                        bannerCoffeeViewPage.setCurrentItem(bannerCoffeeViewPage.getCurrentItem() +1);
                    }
                    else {
                        bannerCoffeeViewPage.setCurrentItem(0);
                    }
                }
            });
        }
    }

}
