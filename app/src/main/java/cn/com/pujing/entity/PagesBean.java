package cn.com.pujing.entity;

import java.io.Serializable;
import java.util.List;

/**
 * author : liguo
 * date : 2021/4/15 14:00
 * description :
 */
public class PagesBean<T> implements Serializable {

    public int current;
    public int pages;
    public int size;
    public int total;
    public List<T> records;

}
