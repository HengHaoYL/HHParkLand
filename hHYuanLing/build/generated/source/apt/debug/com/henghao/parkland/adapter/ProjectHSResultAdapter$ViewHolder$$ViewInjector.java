// Generated code from Butter Knife. Do not modify!
package com.henghao.parkland.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ProjectHSResultAdapter$ViewHolder$$ViewInjector {
  public static void inject(Finder finder, final com.henghao.parkland.adapter.ProjectHSResultAdapter.ViewHolder target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624069, "field 'tvDates'");
    target.tvDates = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624151, "field 'tvName'");
    target.tvName = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624538, "field 'imageView'");
    target.imageView = (android.widget.ImageView) view;
  }

  public static void reset(com.henghao.parkland.adapter.ProjectHSResultAdapter.ViewHolder target) {
    target.tvDates = null;
    target.tvName = null;
    target.imageView = null;
  }
}
