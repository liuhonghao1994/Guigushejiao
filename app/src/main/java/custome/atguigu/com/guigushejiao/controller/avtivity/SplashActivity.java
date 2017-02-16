package custome.atguigu.com.guigushejiao.controller.avtivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;

import butterknife.ButterKnife;
import butterknife.InjectView;
import custome.atguigu.com.guigushejiao.R;
import custome.atguigu.com.guigushejiao.model.Model;

public class SplashActivity extends AppCompatActivity {

    @InjectView(R.id.splash_tv_timer)
    TextView splashTvTimer;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.inject(this);
        //1.initview2.initData（）；3.initListener
        initData();

 
    }



    private void initData() {
        //倒计时,第一个为倒计时的毫秒数，第二个是倒计时的间隔时间
        timer=new CountDownTimer(5000,1000) {
            //每次倒计时间隔都会执行这个方法
            @Override
            public void onTick(long millisUntilFinishaed) {
                splashTvTimer.setText((millisUntilFinishaed / 1000) + "s");
            }

            //倒计时完成后执行的方法
            @Override
            public void onFinish() {
                splashTvTimer.setText("0s");
                Model.getInstance().getGloableThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        //从服务器获取是否登录过
                        boolean loggedInBefore = EMClient.getInstance().isLoggedInBefore();
                        if (loggedInBefore)

                        {
                            //登陆成功后需要的处理
                            Model.getInstance().loginSuccess(EMClient.getInstance().getCurrentUser());
                            //跳转时候需要考虑的事情，是否要带数据，是否销毁当前页面，是不是待回调
                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(intent);
                            //结束当前页面
                            finish();
                        } else

                        {
                            //没登陆过
                            //跳转
                            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        }.start();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
