package com.LemonSpirit.BloEdit;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.WindowManager;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
public class MainActivity extends AndroidApplication {
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //不显示标题和状态栏
        sqqx(MainActivity.this);
        hideNavigationBar();

        //让屏幕长亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //动态设置横屏和坚屏
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//横屏
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏

        //先推断是否已经为横屏了，假设不是再旋转
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();

       initialize(new MyGdxGame(new BsuEvent(){

                           @Override
                           public void notify(Object obj, String msg) {
                               if(msg.equals("Action")){
                               Intent intent = getPackageManager().getLaunchIntentForPackage("com.LemonSpirit.BloPhy");// 这里如果intent为空，就说名没有安装要跳转的应用嘛                        
                               if (intent != null) {                            
                                   // 这里跟Activity传递参数一样的嘛，不要担心怎么传递参数，还有接收参数也是跟Activity和Activity传参数一样//                            
                                   startActivity(intent);                        
                               } 
                               }
                               
                           }
                           
            
        }), cfg);
        
    }
    
    // 隐藏虚拟按键
    public void hideNavigationBar() {
        int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION//布局隐藏导航
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN//布局全屏
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION //隐藏导航
            | View.SYSTEM_UI_FLAG_FULLSCREEN //全屏
            | View.SYSTEM_UI_FLAG_IMMERSIVE//沉浸式系统
            ;

        //兼容性判断
        if (android.os.Build.VERSION.SDK_INT >= 19) {
            uiFlags |= 0x00001000;
        } else {
            uiFlags |= View.SYSTEM_UI_FLAG_LOW_PROFILE;
        }
        getWindow().getDecorView().setSystemUiVisibility(uiFlags);
    }

    //如果窗体发生改变 自动隐藏
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideNavigationBar();
        }
    }
//申请存储权限

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
        "android.permission.READ_EXTERNAL_STORAGE",
        "android.permission.WRITE_EXTERNAL_STORAGE" };
    public static void sqqx(Activity activity) {
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,                                   "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
