package com.arabbit.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.activity.BaseActivity;
import com.arabbit.utils.CommonUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class TypeActivity extends BaseActivity {

    @InjectView(R.id.title_value)
    TextView titleValue;
    @InjectView(R.id.iv_student)
    ImageView ivStudent;
    @InjectView(R.id.iv_teacher)
    ImageView ivTeacher;
    @InjectView(R.id.iv_normal)
    ImageView ivNormal;
    String type = "";
    String roles = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);
        ButterKnife.inject(this);
        titleValue.setText(R.string.type_);

        type = CommonUtils.formatNull(getIntent().getStringExtra("type"));
        if (type.equals(getString(R.string.average_user))) {
            ivStudent.setVisibility(View.GONE);
            ivTeacher.setVisibility(View.GONE);
            ivNormal.setVisibility(View.VISIBLE);
        } else if (type.equals(getString(R.string.shop))) {
            ivStudent.setVisibility(View.GONE);
            ivTeacher.setVisibility(View.VISIBLE);
            ivNormal.setVisibility(View.GONE);
        } else {
            ivStudent.setVisibility(View.VISIBLE);
            ivTeacher.setVisibility(View.GONE);
            ivNormal.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.layout_student:
                type =  getString(R.string.student);
                roles = "1";
                ivStudent.setVisibility(View.VISIBLE);
                ivTeacher.setVisibility(View.GONE);
                ivNormal.setVisibility(View.GONE);
                break;
            case R.id.layout_teacher:
                type =  getString(R.string.shop);
                roles = "3";
                ivStudent.setVisibility(View.GONE);
                ivTeacher.setVisibility(View.VISIBLE);
                ivNormal.setVisibility(View.GONE);
                break;
            case R.id.layout_normal:
                type = getString(R.string.average_user);
                roles = "2";
                ivStudent.setVisibility(View.GONE);
                ivTeacher.setVisibility(View.GONE);
                ivNormal.setVisibility(View.VISIBLE);
                break;
        }
    }

    @OnClick({R.id.layout_student, R.id.layout_teacher, R.id.layout_normal})
    public void ItemSelectOnClick(View view) {
        switch (view.getId()) {
            case R.id.layout_student:
                type =  getString(R.string.student);
                roles = "1";
                ivStudent.setVisibility(View.VISIBLE);
                ivTeacher.setVisibility(View.GONE);
                ivNormal.setVisibility(View.GONE);
                break;
            case R.id.layout_teacher:
                type =  getString(R.string.shop);
                roles = "3";
                ivStudent.setVisibility(View.GONE);
                ivTeacher.setVisibility(View.VISIBLE);
                ivNormal.setVisibility(View.GONE);
                break;
            case R.id.layout_normal:
                type = getString(R.string.average_user);
                roles = "2";
                ivStudent.setVisibility(View.GONE);
                ivTeacher.setVisibility(View.GONE);
                ivNormal.setVisibility(View.VISIBLE);
                break;
        }

        Intent intent = new Intent();
        intent.putExtra("value", type);
        intent.putExtra("roles", roles);
        setResult(RESULT_OK, intent);
        finish();
    }

//    @Override
//    public void onBackPressed() {
//        Intent intent = new Intent();
//        intent.putExtra("value", type);
//        setResult(RESULT_OK, intent);
//        finish();
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }
}
