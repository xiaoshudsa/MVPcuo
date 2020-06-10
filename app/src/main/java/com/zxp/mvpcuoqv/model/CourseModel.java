package com.zxp.mvpcuoqv.model;

import com.zxp.frame.ApiConfig;
import com.zxp.frame.FengUrl;
import com.zxp.frame.FrameApplication;
import com.zxp.frame.Host;
import com.zxp.frame.IContractModel;
import com.zxp.frame.IContractPresenter;
import com.zxp.frame.NetManger;
import com.zxp.frame.PrameHashMap;
import com.zxp.frame.constants.ConstantKey;
import com.zxp.frame.constants.Constants;

import java.lang.reflect.Method;
import java.util.Collections;

/**
 * Created by 任小龙 on 2020/6/9.
 */
public class CourseModel implements IContractModel {
    private String subjectId = FrameApplication.getFrameApplication().getSelectedInfo().getSpecialty_id();

    @Override
    public void getData(IContractPresenter iContractPresenter, int whichApi, Object[] parms) {
        switch (whichApi){
            case ApiConfig.COURSE_CHILD:
                PrameHashMap add = new PrameHashMap().add("specialty_id", subjectId).add("page", parms[2]).add("limit", Constants.LIMIT_NUM).add("course_type", parms[1]);
                NetManger.getInstance().netWork(NetManger.getService().getCourseChildData(Host.EDU_OPENAPI+ FengUrl.GETLESSONLISTFORAPI,add),iContractPresenter,whichApi,parms[0]);
                break;
        }
    }
}
