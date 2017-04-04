package com.example.mvplibrary.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mvplibrary.R;
import com.example.mvplibrary.callbacks.OnItemClickListener;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;

/**
 * Created by dell on 2017/3/25.
 */

public abstract class BaseRvAdaptrer<B> extends RecyclerView.Adapter<BaseRvAdaptrer.BaseHolder> implements View.OnClickListener{

    private static final String TAG = BaseRvAdaptrer.class.getSimpleName();
    private List<B> data;
    private LayoutInflater inflater;
    public int[] layoutIds;
    private String viewType;
    //-----------------------------------
    private RecyclerView recyclerView;
    private OnItemClickListener onItemClickListener;

    //-----------------------------------

    public BaseRvAdaptrer(List<B> data, Context context, String viewType, int... layoutIds) {
        if (data != null) {
            this.data = data;
        } else {
            this.data = new ArrayList<>();
        }
        this.inflater = LayoutInflater.from(context);
        this.layoutIds = layoutIds;
        this.viewType = viewType;
    }

    @Override
    public void onClick(View view) {

        if (onItemClickListener != null) {
            int position = recyclerView.getChildAdapterPosition(view);
            Log.e(TAG, "onClick: " + position);
            onItemClickListener.onItemClick(recyclerView, position);
        }

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }



    public void updateRes(List<B> data) {
        if (data != null) {
            this.data.clear();
            this.data.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void addRes(List<B> data) {
        if (data != null) {
            this.data.addAll(data);
            notifyDataSetChanged();
        }
    }


    public B getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        int type = 0;
        //获取对应位置的对象
        B b = getItem(position);
        //获取对象的Class属性
        Class<?> tClass = b.getClass();
        if (viewType != null) {
            try {
                //获取指定对象中的
                Field field = tClass.getDeclaredField(viewType);
                //添加访问权限
                field.setAccessible(true);
                //获取对应对象的属性的值
                type = field.getInt(b);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return type;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = returnHolderItemView(parent, viewType, inflater);
        itemView.setOnClickListener(this);
        return new BaseHolder(itemView);
    }

    public abstract View returnHolderItemView(ViewGroup parent, int viewType, LayoutInflater inflater);


    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        bindView(holder, position);
    }

    public abstract void bindView(BaseHolder holder, int position);


    @Override
    public int getItemCount() {
        return data.size();
    }


    public static class BaseHolder extends RecyclerView.ViewHolder {


        public BaseHolder(View itemView) {
            super(itemView);
//            ButterKnife.bind(this,itemView);
            this.itemView = itemView;
            cacheViews = new HashMap<>();
        }

        View itemView;
        //利用Map集合,存储View,减少FindViewById的次数
        Map<Integer, View> cacheViews;

//        private ViewHolder(View item){
//            this.itemView = item;
//            cacheViews = new HashMap<>();
//        }

        public View getView(int resId) {
            //先从缓存中查找,利用resId的唯一性
            View v;
            if (cacheViews.containsKey(resId)) {
                //若有则直接get
                v = cacheViews.get(resId);
            } else {
                //若没有,通过findViewById(resId)实例化一个View,将其添加到Map集合中
                v = itemView.findViewById(resId);
                cacheViews.put(resId, v);
            }
            return v;
        }

        public void setText(int resId, String text) {
            TextView textView = (TextView) getView(resId);
            textView.setText(text);
        }

        public void setImage(int resId, String url) {
            ImageView imageView = (ImageView) getView(resId);
            Picasso.with(itemView.getContext()).load(url).placeholder(R.drawable.loading).into(imageView);
        }


    }
}
