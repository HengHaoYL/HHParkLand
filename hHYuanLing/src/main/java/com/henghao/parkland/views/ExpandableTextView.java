package com.henghao.parkland.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.TextView;

/**
 * 可扩展TextView
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author Administrator
 * @version HDMNV100R001, 2016年8月27日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ExpandableTextView extends TextView {

    // 最大的行，默认只显示2行  
    private final int MAX = 2;

    // 如果完全伸展需要多少行？  
    private int lines;

    private final ExpandableTextView mPhilTextView;

    // 标记当前TextView的展开/收缩状态  
    // true，已经展开  
    // false，以及收缩  
    private boolean expandableStatus = false;

    public ExpandableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPhilTextView = this;
        init();
    }

    private void init() {
        // ViewTreeObserver View观察者，在View即将绘制但还未绘制的时候执行的，在onDraw之前  
        final ViewTreeObserver mViewTreeObserver = this.getViewTreeObserver();
        mViewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

            @Override
            public boolean onPreDraw() {
                // 避免重复监听  
                mPhilTextView.getViewTreeObserver().removeOnPreDrawListener(this);
                lines = getLineCount();
                // Log.d(this.getClass().getName(), lines+"");  
                return true;
            }
        });
        setMaxLines(MAX);
        //setEllipsize(TextUtils.TruncateAt.END);  
    }

    // 是否展开或者收缩，  
    // true，展开；  
    // false，不展开  
    public void setExpandable(boolean isExpand) {
        if (isExpand) {
            setMaxLines(lines + 1);
        } else {
            setMaxLines(MAX);
        }
        expandableStatus = isExpand;
    }

    public int getLines() {
        return getLineCount();
    }

    public boolean getExpandableStatus() {
        return expandableStatus;
    }
}
