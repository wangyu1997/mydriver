package greenstudio.mydriver;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import greenstudio.mydriver.entity.cuotiEntity;

public class cuoti_frag_adapter extends FragmentPagerAdapter {

    private FragmentManager fragmetnmanager;  //创建FragmentManager
    private List<cuoti_frag> listfragment; //创建一个List<Fragment>　　　　　//定义构造带两个参数
    private List<cuotiEntity> data;

    public cuoti_frag_adapter(FragmentManager fm, List<cuotiEntity> data) {
        super(fm);
        listfragment = new ArrayList<>();
        data = new ArrayList<>();
        this.fragmetnmanager = fm;
        this.data = data;
        initFragment();
    }

    public void initFragment() {
        for (cuotiEntity resultEntity : data) {
            listfragment.add(cuoti_frag.newInstance(resultEntity));
        }
    }

    public void updateData(List<cuotiEntity> data) {
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