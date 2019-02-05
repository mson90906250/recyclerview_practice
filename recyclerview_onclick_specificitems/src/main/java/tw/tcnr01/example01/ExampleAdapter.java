package tw.tcnr01.example01;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {
    public static final String TAG = "tcnr01=>";
    private ArrayList<ExampleItem> mExampleList;
    private  OnItemClickListener mListener; //記得是要用自己的而不是系統的

    //*************自定義一個OnItemClickListener的interface
    public interface OnItemClickListener{
        void onItemClick(int position);
        void onDeleteClick(int position);
    }

    //*************自定義setOnItemClickListener的方法
    public void setOnItemClickListerner(OnItemClickListener listerner){
        mListener = listerner;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder{

        //在選單中的選項裡 有幾個View就要宣告幾個view,   以本module為例 選項中只有4個view 分別為2個ImageView,及兩個TextView
        public ImageView mImageView ;
        public TextView mTextView1;
        public TextView mTextView2;
        public ImageView mDeleteImage;
        //---------------------------------------------------

        //由於下面方法位在static class裡所以不能直接使用mListener,所以必須在建構元裡多放一個參數OnItemClickListener(自己做的)
       // public ExampleViewHolder(View itemView) {
            public ExampleViewHolder(View itemView,final OnItemClickListener listener){
            super(itemView);
            //Log.d(TAG,"itemView= "+itemView);//確認itemView為何者 itemView為CardView 即example_item這個物件

            //----請在此方法裡做 findViewById的動作  id皆為選項裡的id
            //----或是做監聽的動作
            mImageView = itemView.findViewById(R.id.imageView);
            mTextView1 = itemView.findViewById(R.id.textView);
            mTextView2 = itemView.findViewById(R.id.textVIew2);
            mDeleteImage = itemView.findViewById(R.id.image_delete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        //取得該item的index值
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
            //對mDeleteImage做監聽
            mDeleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        //取得該item的index值
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }

    public ExampleAdapter(ArrayList<ExampleItem> examplelist){
        mExampleList = examplelist;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Log.d(TAG,"viewType= "+viewType);//確認viewType為何者  我不知道  logcat 顯示為0
       // Log.d(TAG,"parent= "+parent);//確認parent為何者 parent為RecyclerView

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item,parent,false);
        //Log.d(TAG,"v= "+v);//確認v為何者 v為CardView 即example_item這個物件

        //由於建構元位在static class裡所以不能直接使用mListener,所以必須在建構元裡多放一個參數OnItemClickListener(自己做的)
        //ExampleViewHolder evh = new ExampleViewHolder(v);
        ExampleViewHolder evh = new ExampleViewHolder(v,mListener);

        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        //Log.d(TAG,"holder= "+holder);//確認holder為何者 ViewHolder 一個我不知道的東西

        ExampleItem currentItem = mExampleList.get(position);//取出第幾個選項,位置由position決定

        holder.mImageView.setImageResource(currentItem.getImageResource());
        holder.mTextView1.setText(currentItem.getText1());
        holder.mTextView2.setText(currentItem.getText2());
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();//回傳選單中有幾個選項
    }
}
