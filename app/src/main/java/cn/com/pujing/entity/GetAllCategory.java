package cn.com.pujing.entity;

import java.util.List;

public class GetAllCategory extends Base {
    public List<Data> data;

    public class Data {
        public int id;
        public String categoryName;
    }
}
