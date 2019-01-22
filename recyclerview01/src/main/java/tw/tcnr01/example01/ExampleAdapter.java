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

    public static class ExampleViewHolder extends RecyclerView.ViewHolder{

        //在選單中的選項裡 有幾個View就要宣告幾個view,   以本module為例 選項中只有3個view 分別為ImageView,及兩個TextView
        public ImageView mImageView ;
        public TextView mTextView1;
        public TextView mTextView2;
        //---------------------------------------------------

        public ExampleViewHolder(View itemView) {
            super(itemView);
            //Log.d(TAG,"itemView= "+itemView);//確認itemView為何者 itemView為CardView 即example_item這個物件

            //----請在此方法裡做 findViewById的動作  id皆為選項裡的id
            mImageView = itemView.findViewById(R.id.imageView);
            mTextView1 = itemView.findViewById(R.id.textView);
            mTextView2 = itemView.findViewById(R.id.textVIew2);
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
        //Log.d(TAG,"v= "+v);//確認v為何者 itemView為CardView 即example_item這個物件

        ExampleViewHolder evh = new ExampleViewHolder(v);
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
