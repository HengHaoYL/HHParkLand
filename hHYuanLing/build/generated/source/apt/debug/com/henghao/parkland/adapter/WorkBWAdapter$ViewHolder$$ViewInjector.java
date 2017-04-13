// Generated code from Butter Knife. Do not modify!
package com.henghao.parkland.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class WorkBWAdapter$ViewHolder$$ViewInjector {
  public static void inject(Finder finder, final com.henghao.parkland.adapter.WorkBWAdapter.ViewHolder target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624554, "field 'tvStartTime'");
    target.tvStartTime = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624555, "field 'tvEndTime'");
    target.tvEndTime = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624552, "field 'tvWorkContent'");
    target.tvWorkContent = (android.widget.TextView) view;
  }

  public static void reset(com.henghao.parkland.adapter.WorkBWAdapter.ViewHolder target) {
    target.tvStartTime = null;
    target.tvEndTime = null;
    target.tvWorkContent = null;
  }
}
