package cn.com.pujing.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.List;

/**
 * author : liguo
 * date : 2021/3/3 14:11
 * description :
 */
public class BanquetBean implements Serializable {

    private List<CategoryList> categoryList;
    private String typeName;


    public class CategoryList implements MultiItemEntity,Serializable{
        private String groupName;
        private int quantity;

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        @Override
        public int getItemType() {
            return 0;
        }

        public List<Categorys> categorys;

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public class Categorys implements Serializable{
            public String typeName;
            public int id;
            public int foodId;
            public String name;
            public String type;
            public double price;
            public String picId;
            public String remark;
            public int goodsNum = 1;

        }
    }

    public List<CategoryList> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<CategoryList> categoryList) {
        this.categoryList = categoryList;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }


}
