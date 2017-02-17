package custome.atguigu.com.guigushejiao.controller.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hyphenate.easeui.ui.EaseContactListFragment;

import custome.atguigu.com.guigushejiao.R;
import custome.atguigu.com.guigushejiao.controller.avtivity.AddContactActivity;
import custome.atguigu.com.guigushejiao.controller.avtivity.InviteMessageActivity;
import custome.atguigu.com.guigushejiao.utils.Contacts;
import custome.atguigu.com.guigushejiao.utils.ShowToast;
import custome.atguigu.com.guigushejiao.utils.SpUtils;

/**
 * Created by 刘红豪 on 2017/2/13.
 */
public class ContactListFragment extends EaseContactListFragment {
    private LinearLayout ll_new_friends;
    private LinearLayout ll_groups;
    private ImageView reddot;
    private LocalBroadcastManager lm;
    private BroadcastReceiver notifyReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            changeDot();
        }
    };

    @Override
    protected void initView() {
        super.initView();
        titleBar.setRightImageResource(R.drawable.em_add);
        //添加头部
        View view=View.inflate(getActivity(), R.layout.fragment_contact_list_head,null);
        ll_new_friends= (LinearLayout) view.findViewById(R.id.ll_new_friends);
        ll_groups= (LinearLayout) view.findViewById(R.id.ll_groups);
        reddot= (ImageView) view.findViewById(R.id.contanct_iv_invite);
        listView.addHeaderView(view);
        //初始化小红点
        changeDot();
        //获取广播监听
        LocalBroadcastManager lm=LocalBroadcastManager.getInstance(getActivity());

        lm.registerReceiver(notifyReceiver,new IntentFilter(Contacts.NEW_INVITE_CHAGED));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //解注册
        lm.unregisterReceiver(notifyReceiver);
    }

    @Override
    protected void setUpView() {
        super.setUpView();
        initListener();


    }
    //红点是否显示的方法
    private void changeDot(){
        boolean isRedShow = SpUtils.getInstace().getBoolean(SpUtils.NEW_INVITE, false);
        Log.e("TGA","11111111111111111111111111111111111111111111"+isRedShow);
        reddot.setVisibility(isRedShow ? View.VISIBLE : View.GONE);
    }

    private void initListener() {
        titleBar.setRightLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //跳转
                Intent intent=new Intent(getActivity(),AddContactActivity.class);
                startActivity(intent);
            }
        });
        ll_new_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //隐藏小红点
                SpUtils.getInstace().save(SpUtils.NEW_INVITE,false);
                changeDot();
                //跳转
                Intent intent=new Intent(getActivity(),InviteMessageActivity.class);
                startActivity(intent);
                //ShowToast.show(getActivity(),"6666666666666666666");
            }
        });
        ll_groups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowToast.show(getActivity(),"bupa");
            }
        });
    }
}
