package tw.tcnr01.example01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //************可用巨集做(將從資料庫抓來的資料丟入個別的陣列後,再做巨集,        所以資料的命名最好有規則性  ex:   img01,img02.........)
        ArrayList<ExampleItem> exampleList = new ArrayList<>();
        exampleList.add(new ExampleItem(R.drawable.ic_android,"Line 1","Line 2"));
        exampleList.add(new ExampleItem(R.drawable.ic_audio,"Line 3","Line 4"));
        exampleList.add(new ExampleItem(R.drawable.ic_sun,"Line 5","Line 6"));
      //*********************************

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);//"true"使recyclerview不會因item的多寡而改變大小
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter =  new ExampleAdapter(exampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}
