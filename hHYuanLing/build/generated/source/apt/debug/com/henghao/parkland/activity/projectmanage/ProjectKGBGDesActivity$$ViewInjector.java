// Generated code from Butter Knife. Do not modify!
package com.henghao.parkland.activity.projectmanage;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ProjectKGBGDesActivity$$ViewInjector {
  public static void inject(Finder finder, final com.henghao.parkland.activity.projectmanage.ProjectKGBGDesActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624515, "field 'tvTitle'");
    target.tvTitle = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624146, "field 'tvTime'");
    target.tvTime = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624212, "field 'tvFileName'");
    target.tvFileName = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624213, "field 'tvDownload' and method 'onClick'");
    target.tvDownload = (android.widget.TextView) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onClick();
        }
      });
    view = finder.findRequiredView(source, 2131624151, "field 'tvName'");
    target.tvName = (android.widget.TextView) view;
  }

  public static void reset(com.henghao.parkland.activity.projectmanage.ProjectKGBGDesActivity target) {
    target.tvTitle = null;
    target.tvTime = null;
    target.tvFileName = null;
    target.tvDownload = null;
    target.tvName = null;
  }
}
