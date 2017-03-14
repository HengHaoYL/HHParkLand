// Generated code from Butter Knife. Do not modify!
package com.henghao.parkland.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class Logdapter$ViewHolder$$ViewInjector {
  public static void inject(Finder finder, final com.henghao.parkland.adapter.Logdapter.ViewHolder target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624549, "field 'tvProjectname'");
    target.tvProjectname = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624550, "field 'tvWorkcontent'");
    target.tvWorkcontent = (android.widget.TextView) view;
  }

  public static void reset(com.henghao.parkland.adapter.Logdapter.ViewHolder target) {
    target.tvProjectname = null;
    target.tvWorkcontent = null;
  }
}
