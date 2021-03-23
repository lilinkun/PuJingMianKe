package cn.com.pujing.entity;

import java.io.Serializable;

/**
 * author : liguo
 * date : 2021/3/9 17:06
 * description : 菜品详情实体
 */
public class RestDetailBean implements Serializable {

    /**
     * 菜品名称
     */
    public String foodCategoryName;
    /**
     * 所需物料
     */
    public String materiel;
    /**
     * 营养元素
     */
    public String nutrientElement;
    /**
     * 禁忌标签
     */
    public String disableLabel;
    /**
     * 封面图片
     */
    public String coverPic;
    /**
     * 零点餐价格
     */
    public double sporadicPrice;
    /**
     * 宴会餐价格
     */
    public double banquetPrice;
    /**
     * 零售价
     */
    public String retailPrice;


}
