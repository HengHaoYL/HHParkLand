// Generated code from Butter Knife. Do not modify!
package com.henghao.parkland.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ProjectSGSafeLogAdapter$ViewHolder$$ViewInjector {
  public static void inject(Finder finder, final com.henghao.parkland.adapter.ProjectSGSafeLogAdapter.ViewHolder target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624517, "field 'checkBox'");
    target.checkBox = (android.widget.CheckBox) view;
    view = finder.findRequiredView(source, 2131624161, "field 'tvName'");
    target.tvName = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624258, "field 'tvConstructionUnit'");
    target.tvConstructionUnit = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624200, "field 'tvStartTime'");
    target.tvStartTime = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624201, "field 'tvCompletionTime'");
    target.tvCompletionTime = (android.widget.TextView) view;
  }

  public static void reset(com.henghao.parkland.adapter.ProjectSGSafeLogAdapter.ViewHolder target) {
    target.checkBox = null;
    target.tvName = null;
    target.tvConstructionUnit = null;
    target.tvStartTime = null;
    target.tvCompletionTime = null;
  }
}
