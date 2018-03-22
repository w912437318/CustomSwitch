package org.wong.createview;

import android.app.Activity;
import android.os.Bundle;

import org.wong.createview.view.MView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    /**
     * 初始化Activity
     */
    private void init() {
        MView mView = findViewById(R.id.mview);
        mView.setOnSwitchChangeListener(new MView.OnSwitchChangeListener() {
            @Override
            public void onStateChange(boolean state) {
                System.out.println("状态发生改变");
            }
        });
    }
}
