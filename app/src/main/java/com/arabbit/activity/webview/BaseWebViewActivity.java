package com.arabbit.activity.webview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arabbit.R;
import com.just.agentweb.AgentWeb;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class BaseWebViewActivity extends AppCompatActivity {
    public AgentWeb mAgentWeb;
    @InjectView(R.id.lin_web)
    LinearLayout lin_web;
    @InjectView(R.id.iv_back)
    ImageView ivBack;
    @InjectView(R.id.title_value)
    TextView titleValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_web_view);
        ButterKnife.inject(this);
        String title = getIntent().getStringExtra("title_name");
        String url = getIntent().getStringExtra("url");
        titleValue.setText(title);
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent((LinearLayout) lin_web, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(url);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
            finish();
    }
}
