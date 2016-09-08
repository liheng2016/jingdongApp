package com.yju.app.shihui.welfare.view;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yju.app.R;
import com.yju.app.shihui.welfare.adapter.WelfareAdapter;
import com.yju.app.shihui.welfare.bean.FineFareEntity.PanicBean;
import com.yju.app.shihui.welfare.utis.ScalePagerTransformer;
import com.yju.app.utils.UIUtils;
import com.yju.app.widght.SimpleLinearLayout;
import com.yju.app.widght.viewpager.LoopViewPager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 精品福利
 */
public class WelfareView extends SimpleLinearLayout {

    @BindView(R.id.finefare_count)
    TextView finefareCount;
    @BindView(R.id.viewPager)
    LoopViewPager viewPager;
    @BindView(R.id.finefare_name)
    TextView finefareName;
    @BindView(R.id.welfare_view)
    LinearLayout welfareView;

    private WelfareAdapter adapter = null;
    private List<PanicBean> welfareList = null;

    public WelfareView(Context context) {
        super(context);
    }

    public WelfareView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initViews() {
        contentView = inflate(mContext, R.layout.layout_welfare, this);
        ButterKnife.bind(this);

        initViewPager();
        initTouch();
    }


    private void initTouch() {
        //这里要把父类的touch事件传给子类，不然边上的会滑不动
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return viewPager.dispatchTouchEvent(event);
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
//                    if (position==0||position==getCount() - 1)
//                    welfareView.invalidate();
//                }
            }

            @Override
            public void onPageSelected(int position) {
                finefareName.setText(welfareList.get(getCurrentDisplayItem()).id);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initViewPager() {
        viewPager.setOffscreenPageLimit(3);
        viewPager.setPageTransformer(true, new ScalePagerTransformer());
        //设置Pager之间的间距
        viewPager.setPageMargin(UIUtils.dp2px(mContext, 10));

        adapter = new WelfareAdapter(mContext);
        viewPager.setAdapter(adapter);
    }

    public void setWelfareData(List<PanicBean> datas) {
        this.welfareList = datas;
        welfareView.setVisibility(datas.size()>0?VISIBLE:GONE);
        finefareCount.setText("共有" + datas.size() + "个福利");
        finefareName.setText(welfareList.get(getCurrentDisplayItem()).id);

        adapter = new WelfareAdapter(mContext);
        adapter.setDatas(datas);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(adapter.getCount() > 0 ? 1 : 0, true);
    }

    private int getCount() {
        if (adapter != null) {
            return adapter.getCount();
        }
        return 0;
    }

    public int getCurrentDisplayItem() {
        if (viewPager != null) {
            return viewPager.getCurrentItem();
        }
        return 0;
    }

}
