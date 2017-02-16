package custome.atguigu.com.guigushejiao.controller.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import custome.atguigu.com.guigushejiao.R;
import custome.atguigu.com.guigushejiao.controller.avtivity.LoginActivity;
import custome.atguigu.com.guigushejiao.model.Model;
import custome.atguigu.com.guigushejiao.utils.ShowToast;

/**
 * Created by 刘红豪 on 2017/2/13.
 */
public class SettingFragment extends Fragment {
    @InjectView(R.id.settings_btn_logout)
    Button settingsBtnLogout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_setting, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Model.getInstance().getGloableThread().execute(new Runnable() {
            @Override
            public void run() {
                String currentUser = EMClient.getInstance().getCurrentUser();
                settingsBtnLogout.setText("退出登录("+currentUser+")");
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();


    }

    @OnClick(R.id.settings_btn_logout)
    public void onClick() {
        Model.getInstance().getGloableThread().execute(new Runnable() {
            @Override
            public void run() {
                //如果继承了第三方推送用true，执行顺序就是先网络，再本地，最后考虑界面
                EMClient.getInstance().logout(false, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        //数据库关闭


                        //跳转，结束
                        if(getActivity()==null){
                            return;
                        }
                        Intent intent=new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        ShowToast.showUI(getActivity(),"退出成功");
                        getActivity().finish();


                    }

                    @Override
                    public void onError(int i, String s) {
                        ShowToast.showUI(getActivity(),"退出失败");
                    }
                    //加载的东西
                    @Override
                   public void onProgress(int i, String s) {

                    }
                });
            }
        });
    }
}
