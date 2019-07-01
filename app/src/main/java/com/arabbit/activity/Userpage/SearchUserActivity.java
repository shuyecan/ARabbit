package com.arabbit.activity.Userpage;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.arabbit.R;
import com.arabbit.activity.BaseActivity;
import com.arabbit.activity.persional.StudentDataActivity;
import com.arabbit.activity.shop.ShopdataActivity;
import com.arabbit.adapter.SearchUserAdapter;
import com.arabbit.entity.UserEntity;
import com.arabbit.entity.UserInfoEntity;
import com.arabbit.model.IModelResult;
import com.arabbit.model.SocialModel;
import com.arabbit.net.ApiException;
import com.arabbit.utils.CacheActivity;
import com.arabbit.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Subscription;

/**
 * Created by Administrator on 2018/9/2.
 */

public class SearchUserActivity extends BaseActivity {
    @InjectView(R.id.search)
    ImageView search;
    @InjectView(R.id.iv_back)
    ImageView iv_back;
    @InjectView(R.id.sch_value)
    EditText sch_value;

    @InjectView(R.id.lv_user)
    ListView lv_user;
    SocialModel model;
    List<UserInfoEntity> userEntitys;
    private SearchUserAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
        ButterKnife.inject(this);
        if (!CacheActivity.activityList.contains(SearchUserActivity.this)) {
            CacheActivity.addActivity(SearchUserActivity.this);
        }
        model = new SocialModel(this);
        userEntitys = new ArrayList<>();
        adapter = new SearchUserAdapter(userEntitys,SearchUserActivity.this);
        lv_user.setAdapter(adapter);
        lv_user.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                UserInfoEntity userinfoEntity = (UserInfoEntity)adapterView.getAdapter().getItem(i);
                int role = userinfoEntity.getRole();
                if(role == 1){//公司
                    Intent intent = new Intent(SearchUserActivity.this, StudentDataActivity.class);
                    int user_id = userinfoEntity.getUser_id();
                    intent.putExtra("user_id",user_id+"");
                    intent.putExtra("seat_user_id", user_id+"");
                    startActivity(intent);

                }else if(role == 2){//2,个人
                    Intent intent = new Intent(SearchUserActivity.this, UserInfoActivity.class);
                    int user_id = userinfoEntity.getUser_id();
                    intent.putExtra("user_id",user_id+"");
                    startActivity(intent);
                }else if(role == 3){//3,店铺
                    Intent intent = new Intent(SearchUserActivity.this, ShopdataActivity.class);
                    int user_id = userinfoEntity.getUser_id();
                    intent.putExtra("user_id",user_id+"");
                    startActivity(intent);
                }

            }
        });

}


    @OnClick({R.id.search,R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.search:
                String key = sch_value.getText().toString().trim();
                if(TextUtils.isEmpty(key)){
                    ToastUtils.showToastShort("请先输入要搜索的内容");
                    return;
                }
                searchContent(key);
                break;


        }
    }

    private void searchContent(String key) {
        model.searchUser(key, new IModelResult<List<UserInfoEntity>>() {
            @Override
            public void OnSuccess(List<UserInfoEntity> userEntity) {
                userEntitys.clear();
//                Log.e("aaa","搜索OnSuccess："+userEntity.size());
                if(userEntity != null){
                    if(userEntity.size()>0){
                        userEntitys.addAll(userEntity);
                    }
                    adapter.notifyDataSetChanged();

//                    Log.e("aaa","搜索OnSuccess："+userEntity.size());
                }else{
//                    Log.e("aaa","搜索为空");
                }

//                Log.e("aaa","搜索OnSuccess："+userEntity.message);
//                String account = userEntity.getAccount();
//                        Log.e("aaa","搜索的account："+account);
//                List<UserEntity.ListsBean> lists = userEntity.getLists();
//                if(lists != null){
//                    int size = lists.size();
//                    Log.e("aaa","搜索的结果数量："+size);
//                    if(size>0){
//                        String account = lists.get(0).getAccount();
//                        Log.e("aaa","搜索的account："+account);
//                    }
//                }else{
//                    Log.e("aaa","搜索的数据为空：");
//                }


            }


            @Override
            public void OnError(ApiException e) {
                Log.e("aaa","搜索的结果数量："+e.message);
            }

            @Override
            public void AddSubscription(Subscription subscription) {
                addSubscription(subscription);
            }
        });
    }


}