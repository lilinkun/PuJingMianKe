package cn.com.pujing.callback;

/**
 * author : liguo
 * date : 2021/3/5 17:05
 * description :
 */
public interface CheckListener {
    void check(int position, boolean isScroll);
    void onAdd(int menuItemId,int quantity);
    void query();
}
