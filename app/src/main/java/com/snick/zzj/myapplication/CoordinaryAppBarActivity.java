package com.snick.zzj.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Created by zzj on 17-5-24.
 */

public class CoordinaryAppBarActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinary_appbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter = new ResourceAdapter());
    }

    class ResourceAdapter extends RecyclerView.Adapter {
        ResourceViewHolder viewHolder;

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //这里一个小知识点，inflate不传入viewgroup会导致recycleview中的item的match_parent设置失效，具体原因可查看源码。
            View view = LayoutInflater.from(CoordinaryAppBarActivity.this).inflate(R.layout.item,parent,false);
            viewHolder = new ResourceViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((ResourceViewHolder)holder).tv_resource.setText("resource "+position);
        }

        @Override
        public int getItemCount() {
            return 50;
        }

        class ResourceViewHolder extends RecyclerView.ViewHolder {

            TextView tv_resource;

            public ResourceViewHolder(View itemView) {
                super(itemView);
                tv_resource = (TextView) itemView.findViewById(R.id.tv_resource);
            }
        }
    }
}
