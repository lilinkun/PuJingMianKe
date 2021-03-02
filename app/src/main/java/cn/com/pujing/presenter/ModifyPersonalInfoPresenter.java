package cn.com.pujing.presenter;

import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.MyInfoBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.http.rxjavahelper.RxObserver;
import cn.com.pujing.http.rxjavahelper.RxResultHelper;
import cn.com.pujing.http.rxjavahelper.RxSchedulersHelper;
import cn.com.pujing.view.ModifyPersonalInfoView;

/**
 * author : liguo
 * date : 2021/3/1 10:26
 * description :
 */
public class ModifyPersonalInfoPresenter extends BasePresenter<ModifyPersonalInfoView> {

    public void modifyPersonalInfo(String nickName,String personal_signature,String personal_birthday,String personal_room_number){
        PujingService.editMyInfo(nickName,personal_signature,personal_birthday,personal_room_number)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<MyInfoBean>() {
                    @Override
                    public void _onNext(MyInfoBean myInfoBean) {
                        getView().modifyPersonalInfoSuccess(myInfoBean);
                    }


                    @Override
                    public void _onError(String errorMessage) {
                        getView().modifyFail(errorMessage);
                    }
                });
    }


}
