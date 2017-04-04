package com.example.mvplibrary.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mvplibrary.R;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/3/25.
 */

public abstract class BaseLvAdapter<B> extends BaseAdapter{

    private List<B> data;
    private LayoutInflater inflater;
    private int[] layoutIds;
    private String viewType;

    public BaseLvAdapter(List<B> data, Context context,String viewType,int... layoutIds) {
        if (data != null) {
        this.data = data;
        }else{
            this.data = new ArrayList<>();
        }
        this.inflater = LayoutInflater.from(context);
        this.layoutIds = layoutIds;
        this.viewType = viewType;
    }

    //注意最好不要将数据源提成全局
    public void updateRes(List<B> data){
        if (data != null) {
            this.data.clear();
            this.data.addAll(data);
            notifyDataSetChanged();
        }

    }

    public void addRes(List<B> data){
        if (data != null) {
            this.data.addAll(data);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getViewTypeCount() {
        return layoutIds.length;
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
    public int getCount() {
        return data!=null?data.size():0;
    }

    @Override
    public B getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            //通过获取当前位置的数据的Type类型,根据其返回值,获取存放在数组中的布局id
            convertView = inflater.inflate(layoutIds[getItemViewType(position)],parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        //下面开始数据填充
        /**
         * 参数一:View的持有者
         * 参数二:数据
         * 参数三:预留参数
         */
        bindData(holder,getItem(position),position);

        return convertView;
    }

    //在子类数据绑定时,通过item.getType方法获取Type值,通过switch对不同布局的View进行数据绑定
    protected abstract void bindData(ViewHolder holder, B item, int position) ;


    protected  static class ViewHolder {
        View itemView;
        //利用Map集合,存储View,减少FindViewById的次数
        Map<Integer,View> cacheViews ;

        private ViewHolder(View item){
            this.itemView = item;
            cacheViews = new HashMap<>();
        }

        public View getView(int resId){
            //先从缓存中查找,利用resId的唯一性
            View v;
            if (cacheViews.containsKey(resId)) {
                //若有则直接get
                v = cacheViews.get(resId);
            }else{
                //若没有,通过findViewById(resId)实例化一个View,将其添加到Map集合中
                v = itemView.findViewById(resId);
                cacheViews.put(resId,v);
            }
            return v;
        }

        public void setText(int resId,String text){
            TextView textView = (TextView) getView(resId);
            textView.setText(text);
        }

        public void setImage(int resId,String url){
            ImageView imageView = (ImageView) getView(resId);
            Picasso.with(itemView.getContext()).load(url).placeholder(R.drawable.loading).into(imageView);
        }

    }




}
