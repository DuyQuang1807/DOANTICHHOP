package com.app.cafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.app.cafe.Adapter.AdapterViewPayer;
import com.app.cafe.CallBack.ItemCallback;
import com.app.cafe.CallBack.UserCallBack;
import com.app.cafe.Databases.DatabaseItem;
import com.app.cafe.Databases.DatabaseUser;
import com.app.cafe.Model.ItemAdapter;
import com.app.cafe.Model.Model;
import com.app.cafe.Model.ModelItem;
import com.app.cafe.Model.User;
import com.app.cafe.Model.ViewHolder;
import com.app.cafe.view.DangNhapActivity;
import com.app.cafe.view.editprofile;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class trangchu extends AppCompatActivity {
    Toolbar toolbar;
    Button dangxuat,datcho;
    CircleImageView anh;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
//    NavigationView navigationView;
 //   ListView listviewmanhinhchinh;
    DrawerLayout drawerLayout;
    LinearLayout linearLayout;
    private AdapterViewPayer adapter;
    SliderView sliderView;
    DatabaseReference database;
   // MyAdapter myAdapter;
   // ArrayList<Item> list;
    LinearLayoutManager mlinearLayoutManager;
    DatabaseReference mDatabaseReference;
    FirebaseDatabase mFirebaseDatabase;
    FirebaseRecyclerAdapter<Model,ViewHolder> firebaseRecyclerAdapter;
    FirebaseRecyclerOptions<Model> options;

    FirebaseStorage storage;
    StorageReference storageReference;
    DatabaseUser databaseUser;
    FirebaseUser firebaseUser;
    String anhp;
    public static String emailuser = "";
    SharedPreferences sharedPreferences;
    DatabaseItem databaseItem;
    ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trangchu);
        sliderView = findViewById(R.id.imageSlider);
        dangxuat = findViewById(R.id.dangxuat);
       // datcho = findViewById(R.id.btnDatcho);
        anh = findViewById(R.id.anh);
        recyclerView = findViewById(R.id.rcv_trangchu);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        databaseUser = new DatabaseUser(getApplicationContext());
        databaseUser.getAll(new UserCallBack() {
            @Override
            public void onSuccess(ArrayList<User> lists) {
                for (int i = 0; i < lists.size(); i++) {
                    if (lists.get(i).getToken()!=null && lists.get(i).getToken().equalsIgnoreCase(firebaseUser.getUid())) {
                        anhp = lists.get(i).getImage();
                    }
                }
                if (anh ==null){
                    Picasso.get().load("https://vnn-imgs-a1.vgcloud.vn/image1.ictnews.vn/_Files/2020/03/17/trend-avatar-1.jpg").into(anh);
                }else if (anh !=null){
                    Picasso.get().load(anhp).into(anh);
                }
            }

            @Override
            public void onError(String message) {

            }
        });



        dangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
//                LoginManager.getInstance().logOut();
                startActivity(new Intent(getApplicationContext(), DangNhapActivity.class));
                finish();
            }
        });
        anh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iCapNhat = new Intent(getApplicationContext(), editprofile.class);
                startActivity(iCapNhat);
            }
        });
        Anhxa();
    //    ActionBar();

        adapter = new AdapterViewPayer(getApplicationContext());
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();
        renewItems(sliderView);
        removeLastItem(sliderView);
        addNewItem(sliderView);

        //ActionViewFlipper();

        //Recycleview
        mlinearLayoutManager = new LinearLayoutManager(this);
        mlinearLayoutManager.setReverseLayout(true);
        mlinearLayoutManager.setStackFromEnd(true);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("Item" );
        ShowData();
    }
    ArrayList<Model> ItemArrayList;
    private  void  ShowData(){

//

        options = new FirebaseRecyclerOptions.Builder<Model>().setQuery(mDatabaseReference,Model.class).build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Model, ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int i, @NonNull @NotNull Model model) {

                holder.setDetails(getApplicationContext(),model.getName(),model.getAdress(),model.getImage());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent =  new Intent(getApplicationContext(),DatChoActivity.class);
                        startActivity(intent);

                    }
                });
            }

            @NonNull
            @NotNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);

                ViewHolder viewHolder = new ViewHolder(itemView);
                viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int i) {
                        Toast.makeText(trangchu.this, "hello", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onItemLongClick(View view, int i) {
                        Toast.makeText(trangchu.this, "Long Click", Toast.LENGTH_SHORT).show();

                    }
                });

                return viewHolder;
            }
        };

        recyclerView.setLayoutManager(mlinearLayoutManager);
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);

   }

    protected void onStart(){

        super.onStart();
//        if(firebaseRecyclerAdapter != null){
//            firebaseRecyclerAdapter.startListening();
//        }
    }
//    private void ActionViewFlipper() {
//        ArrayList<String> mangquangcao = new ArrayList<>();
//        mangquangcao.add("https://xuongmocgocongnghiep.com/upload/images/thiet-ke-quan-cafe-dep-02.jpg");
//        mangquangcao.add("https://cdn.vietnamtours247.com/2019/12/1EE5448A-1DBC-4F9A-B322-5653427E5403-1920x1920.jpeg");
//        mangquangcao.add("https://tuarts.net/wp-content/uploads/2019/10/79203743_732156290524449_492674440098742272_o-800x600.jpg");
//        mangquangcao.add("https://dulich9.com/wp-content/uploads/2020/07/Quan-cafe-noi-tieng-o-Nha-Trang-4.jpg");
//        for (int i=0;i<mangquangcao.size();i++){
//            ImageView imageView = new ImageView(getApplicationContext());
//            Picasso.get().load(mangquangcao.get(i)).into(imageView);
//            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//        }
//    }
    /*----------- Tạo ảnh SliderLoad -----------*/

    public void renewItems(SliderView sliderView) {
        List<ModelItem> sliderItemList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ModelItem sliderItem = new ModelItem();
            switch (i) {
                case 0:
                    sliderItem.setImageurl("https://xuongmocgocongnghiep.com/upload/images/thiet-ke-quan-cafe-dep-02.jpg");
                    break;
                case 1:
                    sliderItem.setImageurl("https://cdn.vietnamtours247.com/2019/12/1EE5448A-1DBC-4F9A-B322-5653427E5403-1920x1920.jpeg");
                    break;
                case 2:
                    sliderItem.setImageurl("https://tuarts.net/wp-content/uploads/2019/10/79203743_732156290524449_492674440098742272_o-800x600.jpg");
                    break;
                case 3:
                    sliderItem.setImageurl("https://dulich9.com/wp-content/uploads/2020/07/Quan-cafe-noi-tieng-o-Nha-Trang-4.jpg");
                    break;

            }
            sliderItemList.add(sliderItem);
        }
        adapter.ViewPagerAdapter(sliderItemList);
    }


    public void removeLastItem(SliderView sliderView) {
        if (adapter.getCount() - 1 >= 0)
            adapter.deleteitem(adapter.getCount() - 1);
    }

    public void addNewItem(SliderView sliderView) {
        ModelItem sliderItem = new ModelItem();
        sliderItem.setImageurl("https://1.bp.blogspot.com/-W445RQ2YtbU/YKOI0sIDkzI/AAAAAAAAACA/7r2uGRJFKzkbNjG7i6mZbKaJTLzpNhKwQCNcBGAsYHQ/s970/giamgia.png");
        adapter.addItem(sliderItem);
    }

    @Override
    public void onResume() {
        super.onResume();
    }



    /*----------- End tạo ảnh SliderLoad -----------*/

//    private void ActionBar() {
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                drawerLayout.openDrawer(GravityCompat.START);
//            }
//        });
//    }

    private void Anhxa() {
        toolbar = (Toolbar) findViewById(R.id.toolbarmanhinhchinh);
        recyclerView = (RecyclerView) findViewById(R.id.rcv_trangchu);
//        navigationView = (NavigationView) findViewById(R.id.navigationview);
        //listviewmanhinhchinh = (ListView) findViewById(R.id.listviewmanhinhchinh);
        linearLayout = (LinearLayout) findViewById(R.id.drawerlayout);
    }
}