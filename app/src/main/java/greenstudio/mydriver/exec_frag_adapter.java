package greenstudio.mydriver;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;   //注意打包是用support.v4的
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;//继承

import greenstudio.mydriver.entity.ResultEntity;

public class exec_frag_adapter extends FragmentPagerAdapter {

    private FragmentManager fragmetnmanager;  //创建FragmentManager
    private List<exec_frag> listfragment; //创建一个List<Fragment>　　　　　//定义构造带两个参数
    private List<ResultEntity> data;

    public exec_frag_adapter(FragmentManager fm, List<ResultEntity> data) {
        super(fm);
        listfragment = new ArrayList<>();
        data = new ArrayList<>();
        this.fragmetnmanager = fm;
        this.data = data;
        initFragment();
    }

    public void initFragment() {
        for (ResultEntity resultEntity : data) {
            listfragment.add(exec_frag.newInstance(resultEntity));
        }
    }

    public void updateData(List<ResultEntity> data) {
        this.data = data;
        initFragment();
    }

    @Override
    public Fragment getItem(int arg0) {
        // TODO Auto-generated method stub
        return listfragment.get(arg0); //返回第几个fragment
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listfragment.size(); //总共有多少个fragment
    }


}