package com.zxp.mvpcuoqv.base;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.yiyatech.utils.NetworkUtils;
import com.zxp.data.Device;
import com.zxp.frame.util.SystemUtils;
import com.zxp.mvpcuoqv.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.zxp.frame.FrameApplication.getFrameApplication;


public abstract class BaseSplashActivity extends BaseMvpActivity {

    @BindView(R.id.advert_image)
   public ImageView advertImage;
    @BindView(R.id.time_view)
    public TextView timeView;

    @Override
    protected int getlayout() {
        return R.layout.activity_splash;
    }

    public void initDevice() {
        Device device = new Device();
        device.setScreenWidth(SystemUtils.getSize(this).x);
        device.setScreenHeight(SystemUtils.getSize(this).y);
        device.setDeviceName(SystemUtils.getDeviceName());
        device.setSystem(SystemUtils.getSystem(this));
        device.setVersion(SystemUtils.getVersion(this));
        device.setDeviceId(SystemUtils.getDeviceId(this));
        device.setLocalIp(NetworkUtils.getLocalIpAddress());
        getFrameApplication().setmDeviceInfo(device);
    }


}
