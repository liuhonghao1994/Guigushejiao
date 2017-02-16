package custome.atguigu.com.guigushejiao.controller.avtivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import custome.atguigu.com.guigushejiao.R;
import custome.atguigu.com.guigushejiao.model.Model;
import custome.atguigu.com.guigushejiao.utils.ShowToast;

public class AddContactActivity extends AppCompatActivity {

    @InjectView(R.id.invite_btn_search)
    Button inviteBtnSearch;
    @InjectView(R.id.invite_et_search)
    EditText inviteEtSearch;
    @InjectView(R.id.invite_tv_username)
    TextView inviteTvUsername;
    @InjectView(R.id.invite_btn_add)
    Button inviteBtnAdd;
    @InjectView(R.id.invite_ll_item)
    LinearLayout inviteLlItem;
    private String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        ButterKnife.inject(this);

    }

    @OnClick({R.id.invite_btn_search, R.id.invite_btn_add})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.invite_btn_search:
                if(verify()){
                    inviteLlItem.setVisibility(View.VISIBLE);
                    inviteTvUsername.setText(username);
                }else{
                    inviteLlItem.setVisibility(View.GONE);
                }
                break;
            case R.id.invite_btn_add:
                Model.getInstance().getGloableThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            EMClient.getInstance().contactManager().addContact(username,"添加好友");
                            ShowToast.showUI(AddContactActivity.this,"添加成功");
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                            ShowToast.showUI(AddContactActivity.this,"添加失败"+e.getMessage());
                        }
                    }
                });
                break;
        }
    }

    private boolean verify() {
        //本地验证
         username = inviteEtSearch.getText().toString().trim();
        if(TextUtils.isEmpty(username)){
            ShowToast.show(this,"输入用户名不能为空");
            return false;
        }
        //服务器验证暂时没有写，但要记住
            return true;

    }
}
