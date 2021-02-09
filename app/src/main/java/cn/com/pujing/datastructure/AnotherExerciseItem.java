package cn.com.pujing.datastructure;

import java.util.ArrayList;
import java.util.List;

import cn.com.pujing.R;

public class AnotherExerciseItem {
    public Integer imageRes;
    public String imageUrl;
    public String title;
    public String content;
    public int type;

    public static List<AnotherExerciseItem> getTestData() {
        List<AnotherExerciseItem> list = new ArrayList<>();

        AnotherExerciseItem anotherExerciseItem = new AnotherExerciseItem();
        anotherExerciseItem.imageRes = R.mipmap.ic_orange_star;
        anotherExerciseItem.title = "社区元宵节活动";
        anotherExerciseItem.content = "15:00-16:00";
        anotherExerciseItem.type = 1;

        AnotherExerciseItem anotherExerciseItem1 = new AnotherExerciseItem();
        anotherExerciseItem1.imageRes = R.mipmap.ic_orange_star;
        anotherExerciseItem1.title = "我的生日";
        anotherExerciseItem1.content = "祝你生日快乐";
        anotherExerciseItem1.type = 2;

        AnotherExerciseItem anotherExerciseItem2 = new AnotherExerciseItem();
        anotherExerciseItem2.imageRes = R.mipmap.ic_blue_star;
        anotherExerciseItem2.title = "服务预约";
        anotherExerciseItem2.content = "15:00-16:00";
        anotherExerciseItem2.type = 3;

        list.add(anotherExerciseItem);
        list.add(anotherExerciseItem1);
        list.add(anotherExerciseItem2);
        return list;
    }
}
