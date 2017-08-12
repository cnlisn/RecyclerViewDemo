package com.lisn.recyclerviewdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
/**
 * RecyclerView多种行视图，SwipRefreshLayout下拉刷新
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private RecyclerView mRecyclerView;
    private android.content.Context mContext;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        //region RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        //(1) 线性布局管理器 方向：默认是垂直方向
        LinearLayoutManager layout = new LinearLayoutManager(mContext);
        //设置布局管理器的方向
        //layout.setOrientation(LinearLayoutManager.HORIZONTAL);

        //(2) 网格布局    第二个参数网格列数
        //GridLayoutManager layout = new GridLayoutManager(mContext, 3);
        //layout.setOrientation(LinearLayoutManager.HORIZONTAL);

        //1.设置布局管理器
        mRecyclerView.setLayoutManager(layout);

        //2.设置适配器
        mRecyclerView.setAdapter(new MyAdapter());
        //endregion



        //region SwipeRefreshLayout下拉刷新

        //下拉刷新
        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        //设置背景色
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.GRAY);
        //设置进度条的颜色
        swipeRefreshLayout.setColorSchemeColors(Color.RED,Color.YELLOW,Color.BLUE);
        //设置下拉刷新的监听
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //加载数据（网络\数据库）耗时
                Toast.makeText(MainActivity.this, "正在努力的加载数据", Toast.LENGTH_SHORT).show();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //隐藏SwipeRefreshLayout
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },3000);
            }
        });
        //endregion

    }

    //region RecyclerView.Adapter
    private class MyAdapter extends RecyclerView.Adapter {
        private int TextViewType = 1;
        private int ImageViewType = 2;

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Log.e(TAG, "onCreateViewHolder:viewType== " + viewType);
            View view = null;
            //根据positio返回对应item的View布局
            if (viewType == TextViewType) {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_text, parent, false);
            } else if (viewType == ImageViewType) {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_image, parent, false);
            }
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            MyViewHolder myHolder = (MyViewHolder) holder;
            //根据positio设置对应item的数据
            if (position % 2 == 0) {
                myHolder.setData(position);
            }

        }

        @Override
        public int getItemViewType(int position) {
            //根据positio返回对应item类型
            if (position % 2 == 0) {
                return TextViewType;
            } else {
                return ImageViewType;
            }
        }

        @Override
        public int getItemCount() {
            return 100;
        }


    }
    //endregion


    //region ViewHolder
    private class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv);
        }

        public void setData(final int position) {
            mTextView.setText("第" + position + " 个条目");
            //设置条目点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "点击了"+position, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    //endregion
}
