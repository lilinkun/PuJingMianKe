package cn.com.pujing.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * author : liguo
 * date : 2021/3/26 14:11
 * description :
 */
public class HealthCenterBean implements MultiItemEntity,Serializable {

    private boolean isTitle;
    private String projectTypeTitle;
    private String projectTitleName;
    private String projectContent;

    public boolean isTitle() {
        return isTitle;
    }

    public void setTitle(boolean title) {
        isTitle = title;
    }

    public String getProjectTypeTitle() {
        return projectTypeTitle;
    }

    public void setProjectTypeTitle(String projectTypeTitle) {
        this.projectTypeTitle = projectTypeTitle;
    }

    public String getProjectTitleName() {
        return projectTitleName;
    }

    public void setProjectTitleName(String projectTitleName) {
        this.projectTitleName = projectTitleName;
    }

    public String getProjectContent() {
        return projectContent;
    }

    public void setProjectContent(String projectContent) {
        this.projectContent = projectContent;
    }

    @Override
    public int getItemType() {
        return isTitle?0:-1;
    }
}
