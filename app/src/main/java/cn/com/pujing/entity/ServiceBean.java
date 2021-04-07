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
public class ServiceBean extends BaseExpandNode implements Serializable {

    private int ServiceType;
    private String ServiceType_label;
    private List<BasicServiceVoListBean> basicServiceVoList;
    private List<BaseNode> baseNodes;

    public ServiceBean(int serviceType, String serviceType_label, List<BasicServiceVoListBean> basicServiceVoList) {
        ServiceType = serviceType;
        ServiceType_label = serviceType_label;
        this.basicServiceVoList = basicServiceVoList;
        for (int i = 0; i < basicServiceVoList.size();i++){
            baseNodes.add(basicServiceVoList.get(i));
        }
    }

    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        return baseNodes;
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
        for (int i = 0; i < basicServiceVoList.size();i++){
            baseNodes.add(basicServiceVoList.get(i));
        }
        return basicServiceVoList;
    }

    public void setBasicServiceVoList(List<BasicServiceVoListBean> basicServiceVoList) {
        this.basicServiceVoList = basicServiceVoList;
        for (int i = 0; i < basicServiceVoList.size();i++){
            baseNodes.add(basicServiceVoList.get(i));
        }
    }
}
