package com.example.macbook.umlproject.activitys;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.macbook.umlproject.classes.Clock;
import com.example.macbook.umlproject.classes.Tag;
import com.example.macbook.umlproject.classes.Thing;
import com.example.macbook.umlproject.helpers.BottomNavigationViewHelper;
import com.example.macbook.umlproject.fragments.FifthFragment;
import com.example.macbook.umlproject.fragments.FirstFragment;
import com.example.macbook.umlproject.fragments.ForthFragment;
import com.example.macbook.umlproject.R;
import com.example.macbook.umlproject.fragments.SecondFragment;
import com.example.macbook.umlproject.fragments.ThirdFragment;
import com.example.macbook.umlproject.helpers.ControlScrollViewPager;
import com.example.macbook.umlproject.helpers.DatabaseHelper;
import com.example.macbook.umlproject.helpers.NoScrollViewPager;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private ControlScrollViewPager mViewPager;
    private BottomNavigationView bottomNavigationView;
    public static DatabaseHelper mDatabaseHelper;

    private FragmentManager fmanager;
    private FragmentTransaction ftransaction;

    public static boolean flag=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //关闭自带标题栏
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }

        mDatabaseHelper = new DatabaseHelper(this);
        //initMyData();//添加测试数据

        SharedPreferences sharedPreferences=this.getSharedPreferences("share",MODE_PRIVATE);
        boolean isFirstRun=sharedPreferences.getBoolean("isFirstRun", true);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        if(isFirstRun){
            initMyData();
            //Toast.makeText(MainActivity.this, "第一次运行", Toast.LENGTH_SHORT).show();
            editor.putBoolean("isFirstRun", false);
            editor.commit();
        }

        //初始化当天数据
        Time t=new Time(); t.setToNow(); // 取得系统时间
        int year = t.year;int month = t.month+1;int day = t.monthDay;
        String today=year+"-"+month+"-"+day;
        mDatabaseHelper.insertDay(today,0,0,0);

        mViewPager=(ControlScrollViewPager) findViewById(R.id.mViewPager);
        mViewPager.setScanScroll(false);//删除滑动效果
        bottomNavigationView=(BottomNavigationView) findViewById(R.id.mBottom);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);//删除底部动画效果
        //设置点击监听
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.firstFragment_item:
                        mViewPager.setCurrentItem(0);
                        //跳转
                        break;
                    case R.id.secondFragment_item:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.thirdFragment_item:
                        mViewPager.setCurrentItem(2);
                        break;
                    case R.id.forthFragment_item:
                        mViewPager.setCurrentItem(3);
                        break;
                    case R.id.fifthFragment_item:
                        mViewPager.setCurrentItem(4);
                        break;
                }
                return false;
            }
        });
        //监听ViewPager
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                //滑动页面后做的事，这里与BottomNavigationView结合，使其与正确page对应

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //底部导航栏有几项就有几个Fragment
        final ArrayList<Fragment> fgLists = new ArrayList<>(5);
        ThirdFragment thirdFragment=new ThirdFragment();
        ForthFragment forthFragment=new ForthFragment();
        fgLists.add(new FirstFragment());
        fgLists.add(new SecondFragment());
        fgLists.add(thirdFragment);
        fgLists.add(forthFragment);
        fgLists.add(new FifthFragment());

        //设置适配器用于装载Fragment
        FragmentPagerAdapter mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fgLists.get(position); //得到Fragment
            }
            @Override
            public int getCount() {
                return fgLists.size(); //得到数量
            }

//            @Override
//            public Object instantiateItem(ViewGroup container, int position) {
//                if (position == 3)
//                    removeFragment(container,position);
//                return super.instantiateItem(container, position);
//            }
//
//            private void removeFragment(ViewGroup container,int index) {
//                FragmentManager fm=getSupportFragmentManager();
//                String tag = getFragmentTag(container.getId(), index);
//                Fragment fragment = fm.findFragmentByTag(tag);
//                if (fragment == null) return;
//                FragmentTransaction ft = fm.beginTransaction();
//                ft.remove(fragment);
//                ft.commit();
//                ft = null;
//                fm.executePendingTransactions();
//            }
//
//            private String getFragmentTag(int viewId, int index) {
//                try {
//                    Class<FragmentPagerAdapter> cls = FragmentPagerAdapter.class;
//                    Class<?>[] parameterTypes = { int.class, long.class };
//                    Method method = cls.getDeclaredMethod("makeFragmentName", parameterTypes);
//                    method.setAccessible(true);
//                    String tag = (String) method.invoke(this, viewId, index);
//                    return tag;
//                } catch (Exception e) { e.printStackTrace(); return ""; }
//            }
//
//            @Override
//            public int getItemPosition(Object object) {
//
//                 return POSITION_NONE;
//            }
        };
        mViewPager.setAdapter(mPagerAdapter);//设置适配器
        mViewPager.setOffscreenPageLimit(4);//预加载剩下两页
        //以上将Fagement装入了ViewPager
    }

    //设置后台运行
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }

    //跳转页面
    FragmentToFragment fragmentToFragment;
    public void setFragmentToFragment(FragmentToFragment fragment2Fragment) {
        this.fragmentToFragment = fragment2Fragment;
    }
    public void forSkip(){
        if(fragmentToFragment != null){
            fragmentToFragment.gotoFragment(mViewPager);
        }
    }
    public interface FragmentToFragment{
        void gotoFragment(ViewPager viewPager);
    }

    public void initMyData(){
        //为Day表添加测试数据
//        for(int i=-40;i<0;i++){
//            Calendar now = Calendar.getInstance();
//            now.add(Calendar.DAY_OF_MONTH, i);
//            String date = new SimpleDateFormat("yyyy-M-dd").format(now.getTime());
//            mDatabaseHelper.insertDay(date,(i+30)%5,(i+30)%5,(i+50)%5);
//        }
//        //为Clock表添加测试数据
//        Clock A=new Clock("学习Java","2018-8-25","7:30","90", Color.parseColor("#FF6D6D"));
//        Clock B=new Clock("跑步","2018-8-24","9:30","89",Color.parseColor("#008080"));
//        Clock C=new Clock("复习软件需求","2018-8-24","13:30","42",Color.parseColor("#FF6D6D"));
//        Clock D=new Clock("跳绳","2018-8-24","13:30","42",Color.parseColor("#904CC5"));
//        Clock E=new Clock("打代码","2018-8-26","7:29","90",Color.parseColor("#85B3FF"));
//        Clock F=new Clock("学习计组","2018-8-26","9:29","90",Color.parseColor("#904CC5"));
//        Clock G=new Clock("看电影","2018-8-26","11:29","70",Color.parseColor("#FFD52A"));
//        Clock H=new Clock("学习计组","2018-8-26","13:00","90",Color.parseColor("#FF6D6D"));
//        Clock I=new Clock("看电影","2018-8-26","16:29","80",Color.parseColor("#FFD52A"));
//        mDatabaseHelper.insertClock(A);
//        mDatabaseHelper.insertClock(B);
//        mDatabaseHelper.insertClock(C);
//        mDatabaseHelper.insertClock(D);
//        mDatabaseHelper.insertClock(E);
//        mDatabaseHelper.insertClock(F);
//        mDatabaseHelper.insertClock(G);
//        mDatabaseHelper.insertClock(H);
//        mDatabaseHelper.insertClock(I);
        //预设标签
        Tag tag1=new Tag("学习",Color.parseColor("#FF6D6D"),false);
        Tag tag2=new Tag("娱乐",Color.parseColor("#FFD52A"),false);
        Tag tag3=new Tag("运动",Color.parseColor("#80F18B"),false);
        mDatabaseHelper.insertTag(tag1);
        mDatabaseHelper.insertTag(tag2);
        mDatabaseHelper.insertTag(tag3);

    }

}
