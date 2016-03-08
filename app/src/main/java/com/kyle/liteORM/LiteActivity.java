package com.kyle.liteORM;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import butterknife.ButterKnife;
import com.kyle.common.R;
import com.kyle.commonlib.db.LiteOrmDBUtil;
import com.kyle.commonlib.utils.GsonHelper;
import java.util.ArrayList;
import java.util.List;

public class LiteActivity extends AppCompatActivity {

    TextView tvData;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lite);
        tvData = ButterKnife.findById(this,R.id.textView);
        LiteOrmDBUtil.init(LiteApp.sDb);
    }
    public void onClickInsertData(View view){
        Conversation mConversation = new Conversation();
        List<Conversation> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            list.add(mConversation);
        }
        LiteOrmDBUtil.insertAll(list);
    }

    public void onClickReadData(View view){
        List<Conversation> list = LiteOrmDBUtil.getQueryAll(Conversation.class);
        String jsonStr  = GsonHelper.get().toJson(list);
        tvData.setText(jsonStr);
    }

    private static void test(){
        Conversation mConversation = new Conversation();
        List<Conversation> list = new ArrayList<Conversation>();
        for (int i = 0; i < 10; i++) {
            list.add(mConversation);
        }
        //1、插入单条数据
        LiteOrmDBUtil.insert(mConversation);
        //2、插入多条数据
        LiteOrmDBUtil.insertAll(list);
        //3、查询Conversation表中所有记录
 //       List<Conversation> list = LiteOrmDBUtil.getQueryAll(Conversation.class);
        //4、查询Conversation表中 isVisibility 字段 等于 true 的记录
        //List<Conversation> list =  LiteOrmDBUtil.getQueryByWhere(Conversation.class, Conversation.ISVISIBILITY, new String[]{"true"});
        ////5、查询Conversation表中 isVisibility 字段 等于 true 的记录,并且只取20条
        //List<Conversation> list =  LiteOrmDBUtil.getQueryByWhereLength(Conversation.class, Conversation.ISVISIBILITY, new String[]{"true"},0,20);

        //6、删除Conversation表中 isVisibility 字段 等于 true 的记录
        LiteOrmDBUtil.deleteWhere(Conversation.class,Conversation.ISVISIBILITY , new String[]{"true"});
        //7、删除所有
        LiteOrmDBUtil.deleteAll(Conversation.class);
    }
}
