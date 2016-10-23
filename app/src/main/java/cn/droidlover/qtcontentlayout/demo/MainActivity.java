package cn.droidlover.qtcontentlayout.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cn.droidlover.qtcontentlayout.QTContentLayout;

/**
 * Created by wanglei on 2016/10/23.
 */

public class MainActivity extends Activity implements View.OnClickListener {

    Button bt_showLoading;
    Button bt_showError;
    Button bt_showEmpty;
    Button bt_showContent;

    QTContentLayout contentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_showLoading= (Button) findViewById(R.id.bt_showLoading);
        bt_showError= (Button) findViewById(R.id.bt_showError);
        bt_showEmpty= (Button) findViewById(R.id.bt_showEmpty);
        bt_showContent= (Button) findViewById(R.id.bt_showContent);
        contentLayout= (QTContentLayout) findViewById(R.id.contentLayout);

        bt_showLoading.setOnClickListener(this);
        bt_showEmpty.setOnClickListener(this);
        bt_showError.setOnClickListener(this);
        bt_showContent.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_showContent:
                contentLayout.showContent();
                break;

            case R.id.bt_showEmpty:
                contentLayout.showEmpty();
                break;

            case R.id.bt_showError:
                contentLayout.showError();
                break;

            case R.id.bt_showLoading:
                contentLayout.showLoading();
                break;
        }
    }
}
