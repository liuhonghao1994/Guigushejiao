package custome.atguigu.com.guigushejiao.controller.avtivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import butterknife.ButterKnife;
import butterknife.InjectView;
import custome.atguigu.com.guigushejiao.R;
import custome.atguigu.com.guigushejiao.controller.fragment.ContactListFragment;
import custome.atguigu.com.guigushejiao.controller.fragment.ConversitionFragment;
import custome.atguigu.com.guigushejiao.controller.fragment.SettingFragment;

public class MainActivity extends AppCompatActivity {
    private Fragment conversitionFragment;
    private Fragment contactListFragment;
    private Fragment settingFragment;

    @InjectView(R.id.main_f1)
    FrameLayout mainF1;
    @InjectView(R.id.rg_main)
    RadioGroup rgMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        initData();
        initListener();
        switchFragment(R.id.rb_main_chat);

    }
//初始化fragment
    private void initData() {
         conversitionFragment = new ConversitionFragment();
         contactListFragment = new ContactListFragment();
         settingFragment = new SettingFragment();
    }
    private void initListener() {
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //切换fragmment
                switchFragment(checkedId);
            }
        });

    }
    private void switchFragment(int checkedId) {
        Fragment fragment=null;
        switch (checkedId){
            case R.id.rb_main_chat:
                fragment=conversitionFragment;
                break;
            case R.id.rb_main_contact:
                fragment=contactListFragment;
                break;
            case R.id.rb_main_setting:
                fragment=settingFragment;
                break;
        }
        if(fragment==null){
            return;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.main_f1,fragment).commit();
    }
}
