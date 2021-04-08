package cn.com.pujing.entity;

import java.io.Serializable;

/**
 * author : liguo
 * date : 2021/3/30 18:10
 * description :
 */
public class RightsVoucherVoBean implements Serializable {
    public String quantity;
    public String rightsVoucherId;
    public String voucherName;
    public int deductMethod;
    // 1 满减  0 直接抵扣
    public int fullReductionFlag;
    public double fullReductionThreshold;
    public double deductAmount;
}
