package com.ldw.test.socketclienttest;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ldw.test.socketclienttest.event.ChildView1;
import com.ldw.test.socketclienttest.event.ChildView2;
import com.ldw.test.socketclienttest.event.ParentsView1;
import com.ldw.test.socketclienttest.event.ParentsView2;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventTransActivity extends Activity {
    @BindView(R.id.cv_1)
    ChildView1 cv1;
    @BindView(R.id.cv_2)
    ChildView2 cv2;
    @BindView(R.id.pv_parents_2)
    ParentsView2 pvParents2;
    @BindView(R.id.pv_parents_1)
    ParentsView1 pvParents1;
    @BindView(R.id.ll_list)
    ListView llList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_trans);
        ButterKnife.bind(this);
//        String schools[] = {"山东大学", "山东理工大学", "山东科技大学", "山东农业大学","GGG","FFFF","gghhhh","dsafsafsdafasfd"};
        String schools[] = new String[30];
        for (int i = 0; i < schools.length; i++) {
            schools[i] = "FFFFF" + " i";
        }
        ArrayAdapter arrayadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, schools);
        //ListView视图加载适配器
        llList.setAdapter(arrayadapter);
        RecyclerView f;
    }
}
