// Generated code from Butter Knife. Do not modify!
package com.henghao.parkland.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class YhAdapter$ViewHolder$$ViewInjector {
  public static void inject(Finder finder, final com.henghao.parkland.adapter.YhAdapter.ViewHolder target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624559, "field 'tvTreeid'");
    target.tvTreeid = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624557, "field 'tvBehavior'");
    target.tvBehavior = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624556, "field 'tvPlace'");
    target.tvPlace = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624560, "field 'tvTime'");
    target.tvTime = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624558, "field 'btnWrite'");
    target.btnWrite = (android.widget.TextView) view;
  }

  public static void reset(com.henghao.parkland.adapter.YhAdapter.ViewHolder target) {
    target.tvTreeid = null;
    target.tvBehavior = null;
    target.tvPlace = null;
    target.tvTime = null;
    target.btnWrite = null;
  }
}
