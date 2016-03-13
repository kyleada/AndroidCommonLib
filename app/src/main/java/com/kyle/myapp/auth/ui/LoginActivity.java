package com.kyle.myapp.auth.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.kyle.commonlib.base.BaseActivity;
import com.kyle.commonlib.utils.ProgressDialogUtils;
import com.kyle.commonlib.utils.RegexUtils;
import com.kyle.commonlib.utils.SpUtils;
import com.kyle.config.AppConstants;
import com.kyle.myapp.R;
import com.kyle.myapp.widget.CleanEditText;
import com.myapp.umenglib.SocialShareUtils;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.exception.SocializeException;

import java.util.Map;

import timber.log.Timber;

import static android.view.View.OnClickListener;

/**
 * @desc 登录界面
 * Created by devilwwj on 16/1/24.
 */
public class LoginActivity extends BaseActivity implements OnClickListener {

    private static final String TAG = "loginActivity";
    private static final int REQUEST_CODE_TO_REGISTER = 0x001;

    // 界面控件
    private CleanEditText accountEdit;
    private CleanEditText passwordEdit;

    // 第三方平台获取的访问token，有效时间，uid
    private String accessToken;
    private String expires_in;
    private String uid;
    private String sns;

    // 整个平台的Controller，负责管理整个SDK的配置、操作等处理
    private UMSocialService mController = UMServiceFactory
            .getUMSocialService(AppConstants.DESCRIPTOR);

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

        initViews();
        // 配置分享平台
        SocialShareUtils.configPlatforms(this);
    }


    /**
     * 初始化视图
     */
    private void initViews() {
        accountEdit = (CleanEditText) this.findViewById(R.id.et_email_phone);
        accountEdit.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        accountEdit.setTransformationMethod(HideReturnsTransformationMethod
                .getInstance());
        passwordEdit = (CleanEditText) this.findViewById(R.id.et_password);
        passwordEdit.setImeOptions(EditorInfo.IME_ACTION_DONE);
        passwordEdit.setImeOptions(EditorInfo.IME_ACTION_GO);
        passwordEdit.setTransformationMethod(PasswordTransformationMethod
                .getInstance());
        passwordEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE
                        || actionId == EditorInfo.IME_ACTION_GO) {
                    clickLogin();
                }
                return false;
            }
        });
    }

    private void clickLogin() {
        String account = accountEdit.getText().toString();
        String password = passwordEdit.getText().toString();
        if (checkInput(account, password)) {
            // TODO: 请求服务器登录账号
        }
    }

    /**
     * 检查输入
     *
     * @param account
     * @param password
     * @return
     */
    public boolean checkInput(String account, String password) {
        // 账号为空时提示
        if (account == null || account.trim().equals("")) {
            Toast.makeText(this, R.string.tip_account_empty, Toast.LENGTH_LONG)
                    .show();
        } else {
            // 账号不匹配手机号格式（11位数字且以1开头）
            if (!RegexUtils.checkMobile(account)) {
                Toast.makeText(this, R.string.tip_account_regex_not_right,
                        Toast.LENGTH_LONG).show();
            } else if (password == null || password.trim().equals("")) {
                Toast.makeText(this, R.string.tip_password_can_not_be_empty,
                        Toast.LENGTH_LONG).show();
            } else {
                return true;
            }
        }

        return false;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.iv_cancel:
                finish();
                break;
            case R.id.btn_login:
                clickLogin();
                break;
            case R.id.iv_wechat:
                clickLoginWexin();
                break;
            case R.id.iv_qq:
                clickLoginQQ();
                break;
            case R.id.iv_sina:
                loginThirdPlatform(SHARE_MEDIA.SINA);
                break;
            case R.id.tv_create_account:
                enterRegister();
                break;
            case R.id.tv_forget_password:
                enterForgetPwd();
                break;
            default:
                break;
        }
    }

    /**
     * 点击使用QQ快速登录
     */
    private void clickLoginQQ() {
        if (!SocialShareUtils.isQQClientAvailable(this)) {
            showToast(getString(R.string.no_install_qq));
        } else {
            loginThirdPlatform(SHARE_MEDIA.QZONE);
        }
    }

    /**
     * 点击使用微信登录
     */
    private void clickLoginWexin() {
        if (!SocialShareUtils.isWeixinAvilible(this)) {
            showToast(R.string.no_install_wechat);
        } else {
            loginThirdPlatform(SHARE_MEDIA.WEIXIN);
        }
    }

    /**
     * 跳转到忘记密码
     */
    private void enterForgetPwd() {
        Intent intent = new Intent(this, ForgetPasswordActivity.class);
        startActivity(intent);
    }

    /**
     * 跳转到注册页面
     */
    private void enterRegister() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivityForResult(intent, REQUEST_CODE_TO_REGISTER);
    }


    /**
     * 授权。如果授权成功，则获取用户信息
     *
     * @param platform
     */
    private void loginThirdPlatform(final SHARE_MEDIA platform) {
        mController.doOauthVerify(LoginActivity.this, platform,
                new SocializeListeners.UMAuthListener() {

                    @Override
                    public void onStart(SHARE_MEDIA platform) {
                        Timber.i("onStart------"
                                + Thread.currentThread().getId());
                        ProgressDialogUtils.getInstance().show(
                                LoginActivity.this,
                                getString(R.string.tip_begin_oauth));
                    }

                    @Override
                    public void onError(SocializeException e,
                                        SHARE_MEDIA platform) {
                        Timber.i("onError------"
                                + Thread.currentThread().getId());
                        showToast(getString(R.string.oauth_fail));
                        ProgressDialogUtils.getInstance().dismiss();
                    }

                    @Override
                    public void onComplete(Bundle value, SHARE_MEDIA platform) {
                        Timber.i("onComplete------" + value.toString());
                        if (platform == SHARE_MEDIA.SINA) {
                            accessToken = value.getString("access_key");
                        } else {
                            accessToken = value.getString("access_token");
                        }
                        expires_in = value.getString("expires_in");
                        // 获取uid
                        uid = value.getString(AppConstants.UID);
                        if (value != null && !TextUtils.isEmpty(uid)) {
                            // uid不为空，获取用户信息
                            getUserInfo(platform);
                        } else {
                            showToast(getString(R.string.oauth_fail));
                        }
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA platform) {
                        Timber.i("onCancel------"
                                + Thread.currentThread().getId());
                        showToast(getString(R.string.oauth_cancle));
                        ProgressDialogUtils.getInstance().dismiss();

                    }
                });
    }


    /**
     * 获取用户信息
     *
     * @param platform
     */
    private void getUserInfo(final SHARE_MEDIA platform) {
        mController.getPlatformInfo(LoginActivity.this, platform,
                new SocializeListeners.UMDataListener() {

                    @Override
                    public void onStart() {
                        // 开始获取
                        Timber.i("onStart------");
                        ProgressDialogUtils.getInstance().dismiss();
                        ProgressDialogUtils.getInstance().show(
                                LoginActivity.this, "正在请求...");
                    }

                    @Override
                    public void onComplete(int status, Map<String, Object> info) {

                        try {
                            String sns_id = "";
                            String sns_avatar = "";
                            String sns_loginname = "";
                            if (info != null && info.size() != 0) {
                                Timber.i(info.toString());

                                if (platform == SHARE_MEDIA.SINA) { // 新浪微博
                                    sns = AppConstants.SINA;
                                    if (info.get(AppConstants.UID) != null) {
                                        sns_id = info.get(AppConstants.UID)
                                                .toString();
                                    }
                                    if (info.get(AppConstants.PROFILE_IMAGE_URL) != null) {
                                        sns_avatar = info
                                                .get(AppConstants.PROFILE_IMAGE_URL)
                                                .toString();
                                    }
                                    if (info.get(AppConstants.SCREEN_NAME) != null) {
                                        sns_loginname = info.get(
                                                AppConstants.SCREEN_NAME)
                                                .toString();
                                    }
                                } else if (platform == SHARE_MEDIA.QZONE) { // QQ
                                    sns = AppConstants.QQ;
                                    if (info.get(AppConstants.UID) == null) {
                                        showToast(R.string.oauth_fail);
                                        return;
                                    }
                                    sns_id = info.get(AppConstants.UID)
                                            .toString();
                                    sns_avatar = info.get(
                                            AppConstants.PROFILE_IMAGE_URL)
                                            .toString();
                                    sns_loginname = info.get(
                                            AppConstants.SCREEN_NAME)
                                            .toString();
                                } else if (platform == SHARE_MEDIA.WEIXIN) { // 微信
                                    sns = AppConstants.WECHAT;
                                    sns_id = info.get(AppConstants.OPENID)
                                            .toString();
                                    sns_avatar = info.get(
                                            AppConstants.HEADIMG_URL)
                                            .toString();
                                    sns_loginname = info.get(
                                            AppConstants.NICKNAME).toString();
                                }

                                // 这里直接保存第三方返回来的用户信息
                                SpUtils.putBoolean(AppConstants.THIRD_LOGIN, true);

                                Timber.i(sns + "," + sns_id + "," + sns_loginname);

                                // TODO: 这里执行第三方连接(绑定服务器账号）


                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                });
    }
}
