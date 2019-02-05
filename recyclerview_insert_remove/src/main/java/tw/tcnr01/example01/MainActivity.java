package tw.tcnr01.example01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private  ArrayList<ExampleItem> mExampleList;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button buttonInsert,buttonRemove;
    private EditText editTextInsert,editTextRemove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViewComponent();

    }

    private void setupViewComponent() {

        buttonInsert = (Button)findViewById(R.id.button_insert);
        buttonRemove = (Button)findViewById(R.id.button_remove);
        editTextInsert = (EditText)findViewById(R.id.edittext_insert);
        editTextRemove = (EditText)findViewById(R.id.edittext_remove);

        buttonInsert.setOnClickListener(this);
        buttonRemove.setOnClickListener(this);

        createExampleList();
        buildRecyclerView();
    }

    private void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);//"true"使recyclerview不會因item的多寡而改變大小
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter =  new ExampleAdapter(mExampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void createExampleList() {
        //************可用巨集做(將從資料庫抓來的資料丟入個別的陣列後,再做巨集,        所以資料的命名最好有規則性  ex:   img01,img02.........)
        mExampleList = new ArrayList<>();
        mExampleList.add(new ExampleItem(R.drawable.ic_android,"Line 1","Line 2"));
        mExampleList.add(new ExampleItem(R.drawable.ic_audio,"Line 3","Line 4"));
        mExampleList.add(new ExampleItem(R.drawable.ic_sun,"Line 5","Line 6"));
        //*********************************
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_insert:
                //取得edittext上的數字作為index的位置
                int position = Integer.parseInt(editTextInsert.getText().toString());
                insertItem(position);
                break;

            case R.id.button_remove:
                //取得edittext上的數字作為index的位置
                position = Integer.parseInt(editTextRemove.getText().toString());
                removeItem(position);
                break;
        }
    }
    //移除一個item
    private void removeItem(int position) {
        //判斷position是否合理(ex position=3 list的size(item個數)卻只有1 會導致閃退)
        if(position>=mExampleList.size()){
            return;
        }
        //在第幾個位置上移除item
        mExampleList.remove(position);

       /* //使整個recyclerview重整,沒有插入或移除的動畫
        mAdapter.notifyDataSetChanged();*/

       //僅移除某位置的item,有移除的動畫
       mAdapter.notifyItemRemoved(position);
    }
    //新增一個item
    private void insertItem(int position) {
        //判斷position是否合理(ex position=3 list的size(item個數)卻只有1 會導致閃退)
        if(position>mExampleList.size()){
            return;
        }
        //在第幾個位置上插入item
        mExampleList.add(position,new ExampleItem(R.drawable.ic_android,"New Item"+position,"Line"+position));

        /*//使整個recyclerview重整,沒有插入或移除的動畫
        mAdapter.notifyDataSetChanged();*/

        //僅在某位置新增item,有插入的動畫
        mAdapter.notifyItemInserted(position);
    }


}
