// Generated code from Butter Knife. Do not modify!
package com.henghao.parkland.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class SGBWAdapter$ViewHolder$$ViewInjector {
  public static void inject(Finder finder, final com.henghao.parkland.adapter.SGBWAdapter.ViewHolder target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624551, "field 'tvProjectname'");
    target.tvProjectname = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624552, "field 'tvWorkcontent'");
    target.tvWorkcontent = (android.widget.TextView) view;
  }

  public static void reset(com.henghao.parkland.adapter.SGBWAdapter.ViewHolder target) {
    target.tvProjectname = null;
    target.tvWorkcontent = null;
  }
}
