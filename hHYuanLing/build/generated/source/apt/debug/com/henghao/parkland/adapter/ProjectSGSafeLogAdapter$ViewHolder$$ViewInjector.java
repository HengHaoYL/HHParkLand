// Generated code from Butter Knife. Do not modify!
package com.henghao.parkland.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ProjectSGSafeLogAdapter$ViewHolder$$ViewInjector {
  public static void inject(Finder finder, final com.henghao.parkland.adapter.ProjectSGSafeLogAdapter.ViewHolder target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624151, "field 'tvName'");
    target.tvName = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624259, "field 'tvConstructionUnit'");
    target.tvConstructionUnit = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624196, "field 'tvStartTime'");
    target.tvStartTime = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624197, "field 'tvCompletionTime'");
    target.tvCompletionTime = (android.widget.TextView) view;
  }

  public static void reset(com.henghao.parkland.adapter.ProjectSGSafeLogAdapter.ViewHolder target) {
    target.tvName = null;
    target.tvConstructionUnit = null;
    target.tvStartTime = null;
    target.tvCompletionTime = null;
  }
}
