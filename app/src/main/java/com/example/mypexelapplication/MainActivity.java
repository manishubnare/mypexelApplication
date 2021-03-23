package com.example.mypexelapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements PhotoAdapter.onPhotoClick {

    RecyclerView recyclerView;
    PhotoAdapter photoAdapter;
    List<PhotoModel> photoModelList;
    TextView textView;
    SearchView searchView;
    int pageNumber = 1;
    int count;
    Boolean isScrolling  = false;
    int currentItems,totalItems,scrollOutItems;
    String url ="https://api.pexels.com/v1/curated/?page="+pageNumber+"&per_page=80";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText editText= findViewById(R.id.search_bar);
        ImageButton imageButton= findViewById(R.id.search_button);
        textView=findViewById(R.id.textView);
        textView.setText("Page number: " + pageNumber + " of 2");

        photoModelList = new ArrayList<>();
        findViewById(R.id.fragmentimage).setVisibility(View.GONE);

        recyclerView = findViewById(R.id.recyclerview);

        count = 0;
        fetchWallpaper();
        photoAdapter = new PhotoAdapter(this, photoModelList, this);
        count = count + 9;


        findViewById(R.id.next_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count < 70 && photoModelList.size() > count + 8 && pageNumber < 2) {
                    photoAdapter.setPhotoModelList(photoModelList.subList(count, count + 9));
                    count = count + 9;
                    photoAdapter.notifyDataSetChanged();
                    pageNumber++;
                    textView.setText("Page number: " + pageNumber + " of 2");
                }
            }


        });
        findViewById(R.id.prev_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count > 8 && pageNumber >= 1) {
                    photoAdapter.setPhotoModelList(photoModelList.subList(count-9, count));
                    count = count - 9;
                    photoAdapter.notifyDataSetChanged();
                    pageNumber--;
                    textView.setText("Page number: " +pageNumber+ " of 2");

                }
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = editText.getText().toString().toLowerCase();
//
                url = "https://api.pexels.com/v1/search/?page="+pageNumber+"&per_page=80&query="+query;
                photoModelList.clear();
                pageNumber=1;
                fetchWallpaper();
                textView.setText("Page number: " +pageNumber+ " of 2");

            }
        });



        recyclerView.setAdapter(photoAdapter);

        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);

//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//
//                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
//                    isScrolling= true;
//                }
//
//            }

//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//                currentItems = gridLayoutManager.getChildCount();
//                totalItems = gridLayoutManager.getItemCount();
//                scrollOutItems = gridLayoutManager.findFirstVisibleItemPosition();
//
//                if(isScrolling && (currentItems+scrollOutItems==totalItems)){
//                    isScrolling = false;
//                    fetchWallpaper();
//                }
//
//
//            }
//        });


//        fetchWallpaper();

//    }
    }
    public void fetchWallpaper(){

        StringRequest request = new StringRequest(Request.Method.GET,url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{
                            JSONObject jsonObject = new JSONObject(response);

                            JSONArray jsonArray= jsonObject.getJSONArray("photos");

                            int length = jsonArray.length();

                            for(int i=0;i<length;i++){

                                JSONObject object = jsonArray.getJSONObject(i);

                                int id = object.getInt("id");

                                JSONObject objectImages = object.getJSONObject("src");

                                String orignalUrl = objectImages.getString("original");
                                String mediumUrl = objectImages.getString("medium");

                                PhotoModel photoModel = new PhotoModel(id,orignalUrl,mediumUrl);
                                photoModelList.add(photoModel);

                            }
                            if(photoModelList.size()>20)
                            {
                                photoAdapter.setPhotoModelList(photoModelList.subList(0,9));
                            }

                            photoAdapter.notifyDataSetChanged();
//                          pageNumber++;

                        }catch (JSONException e){

                        }





                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Authorization","563492ad6f917000010000011069c623c7ad4919b7f934771d8adbe9");

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main,menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//
//        if(item.getItemId()==R.id.nav_search){

//            AlertDialog.Builder alert = new AlertDialog.Builder(this);
//            final EditText editText = new EditText(this);
//            editText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//
//            alert.setMessage("Enter Category e.g. Nature");
//            alert.setTitle("Search Wallpaper");
//
//            alert.setView(editText);
//
//            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//
//                    String query = editText.getText().toString().toLowerCase();
//
//                    url = "https://api.pexels.com/v1/search/?page="+pageNumber+"&per_page=80&query="+query;
//                    photoModelList.clear();
//                    fetchWallpaper();
//
//                }
//            });
//
//            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//
//                }
//            });
//
//            alert.show();
//
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


    @Override
    public void onItemClick(PhotoModel model) {
        Bundle bundle= new Bundle();
        bundle.putString("url",model.getSmallUrl());
        ImageFragment fragment= new ImageFragment();
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentimage,fragment)
                .commit();
        findViewById(R.id.fragmentimage).setVisibility(View.VISIBLE);
        findViewById(R.id.singleline).setVisibility(View.VISIBLE);

    }

    @Override
    public void onBackPressed()
    {
        if(findViewById(R.id.fragmentimage).getVisibility() == View.VISIBLE)
        {
            findViewById(R.id.fragmentimage).setVisibility(View.GONE);
            findViewById(R.id.singleline).setVisibility(View.GONE);
        }
        else
        {
            moveTaskToBack(true);
        }
    }
}