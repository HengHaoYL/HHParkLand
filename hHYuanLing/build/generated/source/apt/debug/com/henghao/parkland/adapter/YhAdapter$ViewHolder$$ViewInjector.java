// Generated code from Butter Knife. Do not modify!
package com.henghao.parkland.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class YhAdapter$ViewHolder$$ViewInjector {
  public static void inject(Finder finder, final com.henghao.parkland.adapter.YhAdapter.ViewHolder target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624366, "field 'tvId'");
    target.tvId = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624367, "field 'tvTreeid'");
    target.tvTreeid = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624368, "field 'tvBehavior'");
    target.tvBehavior = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624369, "field 'tvPlace'");
    target.tvPlace = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624370, "field 'tvTime'");
    target.tvTime = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624371, "field 'btnWrite'");
    target.btnWrite = (android.widget.Button) view;
  }

  public static void reset(com.henghao.parkland.adapter.YhAdapter.ViewHolder target) {
    target.tvId = null;
    target.tvTreeid = null;
    target.tvBehavior = null;
    target.tvPlace = null;
    target.tvTime = null;
    target.btnWrite = null;
  }
}
