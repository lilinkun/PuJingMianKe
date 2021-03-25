package cn.com.pujing.presenter;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import cn.com.pujing.base.BasePresenter;
import cn.com.pujing.entity.AttachmentBean;
import cn.com.pujing.entity.FeedbackBean;
import cn.com.pujing.http.PujingService;
import cn.com.pujing.http.rxjavahelper.RxObserver;
import cn.com.pujing.http.rxjavahelper.RxResultHelper;
import cn.com.pujing.http.rxjavahelper.RxSchedulersHelper;
import cn.com.pujing.util.Constants;
import cn.com.pujing.view.FeedbackView;

/**
 * author : liguo
 * date : 2021/3/7 15:32
 * description :
 */
public class FeedbackPresenter extends BasePresenter<FeedbackView> {

    /**
     * 返回反馈类型
     */
    public void giveFeedback(){
        PujingService.giveFeedbackType()
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<List<FeedbackBean>>() {

                    @Override
                    public void _onNext(List<FeedbackBean> feedbackBeans) {
                        getView().giveFeedbackType(feedbackBeans);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }

                });
    }


    /**
     * 保存反馈信息
     */
    public void saveFeedBack(String content,String pId,String type){

        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.CONTENT, content);
        if (!pId.equals(0)){
            params.put(Constants.PHOTO, pId);
        }
        params.put(Constants.TYPE, String.valueOf(type));
        JSONObject jsonObject = new JSONObject(params);


        PujingService.saveFeedback(jsonObject)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<Boolean>() {

                    @Override
                    public void _onNext(Boolean feedbackBeans) {
                        getView().saveFeedback(feedbackBeans);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }

                });
    }

    /**
     * 保存反馈图片
     */
    public void saveFeedFile(JSONObject jsonObject){

        PujingService.saveFeedFile(jsonObject)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<AttachmentBean>() {

                    @Override
                    public void _onNext(AttachmentBean attachmentBean) {
                        getView().saveFeedFile(attachmentBean);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataFail(errorMessage);
                    }

                });
    }

}
