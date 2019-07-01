package com.arabbit.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.activity.login.LoginActivity;
import com.arabbit.fragment.ApplicationFragment;
import com.arabbit.fragment.CommunityFragment;
import com.arabbit.fragment.ContactFragment;
import com.arabbit.fragment.PersionDataFragment;
import com.arabbit.fragment.PersionInfoFragment;
import com.arabbit.fragment.ShopDataFragment;
import com.arabbit.utils.ActivityUtils;
import com.arabbit.utils.SPUtils;
import com.arabbit.view.dialog.DialogWithContent;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

//    public static String b_id = "";
    @InjectView(R.id.iv_back)
    ImageView ivBack;
    @InjectView(R.id.title_value)
    TextView titleValue;
    @InjectView(R.id.tv_add)
    TextView tvAdd;
    @InjectView(R.id.fl_main)
    FrameLayout flMain;

    @InjectView(R.id.tv_message_icon_click)
    TextView tvMessageIconClick;
    @InjectView(R.id.tv_contact_icon_click)
    TextView tvContactIconClick;
    @InjectView(R.id.tv_news_icon_click)
    TextView tvNewsIconClick;
    @InjectView(R.id.tv_applcation_icon_click)
    TextView tvApplcationIconClick;
    @InjectView(R.id.tv_persional_icon_click)
    TextView tvPersionalIconClick;


    private int currentIndex = 0;//控制当前需要显示第几个Fragment
    private ArrayList<Fragment> fragmentArrayList;//用List来存储Fragment,List的初始化没有写
    private ArrayList<TextView> textViewArrayList;//用List来存储textView
    private Fragment mCurrentFrgment;//显示当前Fragment

    MyMainHandler myMainHandler;
    boolean isGoPersional;
    boolean isContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        myMainHandler = new MyMainHandler(this);
        isGoPersional = getIntent().getBooleanExtra("isGoPersional", false);
        isContact= getIntent().getBooleanExtra("isContact", false);
//        b_id = getIntent().getStringExtra("b_id");
        initView();
    }
//    public static void setBid(String bid){
//        b_id = bid;
//    }

    //    String[] titles = new String[]{"消息", "联系人", "动态", "应用", "个人中心"};
    int[] title = new int[]{R.string.message, R.string.contacts, R.string.dynamic, R.string.application, R.string.mypage};

    private void initTitle(int position) {
        ivBack.setVisibility(View.GONE);
//        titleValue.setText(titles[position]);
        titleValue.setText(title[position]);

        if (position == 4) {
            tvAdd.setVisibility(View.VISIBLE);
            tvAdd.setText("");
            tvAdd.setBackground(null);
            Drawable drawableNromal = ContextCompat.getDrawable(mActivity, R.mipmap.gerenzhongxin_icon_shezhi);
            drawableNromal.setBounds(0, 0, drawableNromal.getMinimumWidth(), drawableNromal.getMinimumHeight());
            tvAdd.setCompoundDrawables(null, null, drawableNromal, null); //设置图标
        } else {
            tvAdd.setVisibility(View.GONE);
        }

    }

    private void initView() {

        textViewArrayList = new ArrayList<>();
        textViewArrayList.add(tvMessageIconClick);
        textViewArrayList.add(tvContactIconClick);
        textViewArrayList.add(tvNewsIconClick);
        textViewArrayList.add(tvApplcationIconClick);
        textViewArrayList.add(tvPersionalIconClick);
        fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(new CommunityFragment());
        fragmentArrayList.add(new ContactFragment());
        fragmentArrayList.add(new CommunityFragment());
        fragmentArrayList.add(new ApplicationFragment());
//        persionDataFragment = new PersionDataFragment();
//        fragmentArrayList.add(persionDataFragment);
        showpersion();
        if (isGoPersional) {
            changeTab(4);
            isGoPersional = false;
        } else {
            changeTab(3);
        }

    }

    public void showpersion() {
        String role= SPUtils.getString("role", "");
        if (role.equals("1") ){
            persionDataFragment = new PersionDataFragment();
            fragmentArrayList.add(persionDataFragment);

        }else if (role.equals("2") ) {
            persionInfoFragment = new PersionInfoFragment();
            fragmentArrayList.add(persionInfoFragment);
        }else if (role.equals("3") ) {
            shopDataFragment = new ShopDataFragment();
            fragmentArrayList.add(shopDataFragment);
        }

    }

    public static boolean isGoMain = false;
    public static boolean isGoPersionl = false;

    @Override
    protected void onResume() {
        Log.e("aaa","回到主页onResume:"+isGoMain+",isGoPersionl："+isGoPersionl);
        if (isGoMain) {
            myMainHandler.sendEmptyMessage(1);
            isGoMain = false;
        } else if (isGoPersionl) {
            myMainHandler.sendEmptyMessage(2);
            isGoPersionl = false;
        }

        super.onResume();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e("aaa","回到主页onNewIntent:"+isGoMain+",isGoPersionl："+isGoPersionl);
    }

    PersionDataFragment persionDataFragment;
    PersionInfoFragment persionInfoFragment;
    ShopDataFragment shopDataFragment;

    private void changeTab(int index) {
        currentIndex = index;

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //判断当前的Fragment是否为空，不为空则隐藏
        if (null != mCurrentFrgment) {
            ft.hide(mCurrentFrgment);
        }
        //先根据Tag从FragmentTransaction事物获取之前添加的Fragment
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(fragmentArrayList.get(currentIndex).getClass().getName());

        if (null == fragment) {
            //如fragment为空，则之前未添加此Fragment。便从集合中取出
            fragment = fragmentArrayList.get(index);
        }
        mCurrentFrgment = fragment;
        //判断此Fragment是否已经添加到FragmentTransaction事物中
        if (!fragment.isAdded()) {
            ft.add(R.id.fl_main, fragment, fragment.getClass().getName());
        } else {
            ft.show(fragment);
        }
        ft.commit();
        initTitle(index);
        cahngeTabStatus(index);
    }

    private void cahngeTabStatus(int index) {
        int[] resId = new int[]{R.mipmap.btn_xiaoxi_default, R.mipmap.btn_lianxiren_default, R.mipmap.btn_dongtai_default,
                R.mipmap.btn_yingyong_default, R.mipmap.btn_geren_default};
        int[] resIdChoesn = new int[]{R.mipmap.btn_xiaoxi_selected, R.mipmap.btn_lianxiren_selected, R.mipmap.btn_dongtai_selected,
                R.mipmap.btn_yingyong_selected, R.mipmap.btn_geren_selected};
        int ColorIdYell = ContextCompat.getColor(mActivity, R.color.hn_492eed);
        int ColorIdGray = ContextCompat.getColor(mActivity, R.color.color_cccccc);
        Drawable drawableNromal;
        Drawable drawableChosen;
        TextView view;
        for (int i = 0; i < textViewArrayList.size(); i++) {
            view = textViewArrayList.get(i);
            drawableNromal = ContextCompat.getDrawable(mActivity, resId[i]);
            drawableChosen = ContextCompat.getDrawable(mActivity, resIdChoesn[i]);
            if (index == i) {
                view.setTextColor(ColorIdYell);
                drawableChosen.setBounds(0, 0, drawableChosen.getMinimumWidth(), drawableChosen.getMinimumHeight());
                view.setCompoundDrawables(null, drawableChosen, null, null); //设置图标
            } else {
                view.setTextColor(ColorIdGray);
                drawableNromal.setBounds(0, 0, drawableNromal.getMinimumWidth(), drawableNromal.getMinimumHeight());
                view.setCompoundDrawables(null, drawableNromal, null, null); //设置图标
            }
        }

    }


    @OnClick({R.id.tv_add,
            R.id.tv_message_icon_click, R.id.tv_contact_icon_click, R.id.tv_news_icon_click,
            R.id.tv_applcation_icon_click, R.id.tv_persional_icon_click
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_add:
                isContact=false;
                startActivity(new Intent(mActivity, SetUpActivity.class));
                break;
            case R.id.tv_message_icon_click:
                isContact=false;
               changeTab(0);
                break;
            case R.id.tv_contact_icon_click:
               changeTab(1);
                isContact=true;
                break;
            case R.id.tv_news_icon_click:
                isContact=false;
               changeTab(2);
                break;
            case R.id.tv_applcation_icon_click:
                isContact=false;
                changeTab(3);
                break;
            case R.id.tv_persional_icon_click:
                isContact=false;
                int type = SPUtils.getInt("type", 1);
                if (type == 2) {
                    showTip();
                    return;
                }
                changeTab(4);
                break;
        }
        showlife();
    }


    DialogWithContent mDialogWithContent;

    public void showTip() {
        mDialogWithContent = new DialogWithContent(mActivity);
        mDialogWithContent.setContent(getString(R.string.unsignal));
        mDialogWithContent.setYesText(getString(R.string.confirm), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                mDialogWithContent.dismiss();
            }
        });
        mDialogWithContent.show();


//
//        final DialogYesOrNoUtil yesOrNoUtil = new DialogYesOrNoUtil(mActivity);
//        yesOrNoUtil.setDialogTitle("提示");
//        yesOrNoUtil.setContent("尚未登录，是否先登录?");
//        yesOrNoUtil.setYesText("是", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        yesOrNoUtil.setNoText("否", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                yesOrNoUtil.dismiss();
//            }
//        });
//        yesOrNoUtil.show();
    }

    public void showlife() {
        View layouttitle=findViewById(R.id.l_title);
        if (isContact ){
        layouttitle.setVisibility(View.GONE);
        }else{
            layouttitle.setVisibility(View.VISIBLE);
        isContact=false;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String role= SPUtils.getString("role", "");
        if (role.equals("1") ){
            persionDataFragment.onActivityResult(requestCode, resultCode, data);
        }else  if (role.equals("2") ) {
            persionInfoFragment.onActivityResult(requestCode, resultCode, data);
        }else  if (role.equals("3") ) {
            shopDataFragment.onActivityResult(requestCode, resultCode, data);
        }

    }


    public static class MyMainHandler extends Handler {

        private WeakReference<Activity> activityReference;

        public MyMainHandler(Activity activity) {
            activityReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity activity = (MainActivity) activityReference.get();
            switch (msg.what) {
                case 1:
//                    activity.changeTab(3);
                    Intent intent = new Intent(activity, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    activity.startActivity(intent);
                    activity.finish();
                    ActivityUtils.jump2Previous(activity);
                    break;
                case 2:
                    Log.e("aaa","回到主页MyMainHandler:"+isGoMain+",isGoPersionl："+isGoPersionl);
                    Intent intent2 = new Intent(activity, MainActivity.class);
                    intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent2.putExtra("isGoPersional", true);
//                    intent2.putExtra("b_id", b_id);
                    activity.startActivity(intent2);
                    activity.finish();
                    ActivityUtils.jump2Previous(activity);
                    break;
            }
        }
    }


}
