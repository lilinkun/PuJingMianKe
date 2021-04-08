package cn.com.pujing.entity;

import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.entity.node.BaseNode;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.List;

/**
 * author : liguo
 * date : 2021/4/7 16:14
 * description :
 */
public class ServiceBean implements Serializable {

    private int ServiceType;
    private String ServiceType_label;
    private List<BasicServiceVoListBean> basicServiceVoList;

    public ServiceBean(int serviceType, String serviceType_label, List<BasicServiceVoListBean> basicServiceVoList) {
        ServiceType = serviceType;
        ServiceType_label = serviceType_label;
        this.basicServiceVoList = basicServiceVoList;
    }

    public int getServiceType() {
        return ServiceType;
    }

    public void setServiceType(int serviceType) {
        ServiceType = serviceType;
    }

    public String getServiceType_label() {
        return ServiceType_label;
    }

    public void setServiceType_label(String serviceType_label) {
        ServiceType_label = serviceType_label;
    }

    public List<BasicServiceVoListBean> getBasicServiceVoList() {
        return basicServiceVoList;
    }

    public void setBasicServiceVoList(List<BasicServiceVoListBean> basicServiceVoList) {
        this.basicServiceVoList = basicServiceVoList;
    }
}
