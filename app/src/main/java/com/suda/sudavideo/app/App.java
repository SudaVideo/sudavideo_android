package com.suda.sudavideo.app;

import com.jess.arms.base.BaseApplication;
import com.tencent.smtt.sdk.QbSdk;

/**
 * @author guhaibo
 * @date 2018/9/8
 */
public class App extends BaseApplication
{
    @Override
    public void onCreate() {
        super.onCreate();
        QbSdk.initX5Environment(this, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
            }

            @Override
            public void onViewInitFinished(boolean b) {

            }
        });
    }
}
