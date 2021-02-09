package cn.com.pujing.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import cn.com.pujing.R;
import cn.com.pujing.fragment.PaidBillFragment;
import cn.com.pujing.fragment.PayBillFragment;

public class MyBillActivity extends AppCompatActivity {
    public static final int PAID = 0;
    public static final int PAY = 1;
    private PaidBillFragment paidBillFragment;
    private PayBillFragment payBillFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        setContentView(R.layout.activity_my_bill);
        showFragment(PAID);
    }

    public void showFragment(int type) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (paidBillFragment != null) {
            fragmentTransaction.hide(paidBillFragment);
        }

        if (payBillFragment != null) {
            fragmentTransaction.hide(payBillFragment);
        }

        switch (type) {
            case PAID:
                if (paidBillFragment == null) {
                    paidBillFragment = new PaidBillFragment();
                    fragmentTransaction.add(R.id.fl, paidBillFragment);
                } else {
                    fragmentTransaction.show(paidBillFragment);
                }
                break;
            case PAY:
                if (payBillFragment == null) {
                    payBillFragment = new PayBillFragment();
                    fragmentTransaction.add(R.id.fl, payBillFragment);
                } else {
                    fragmentTransaction.show(payBillFragment);
                }
                break;
        }
        fragmentTransaction.commit();
    }
}
