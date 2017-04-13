// Generated code from Butter Knife. Do not modify!
package com.henghao.parkland.activity.projectmanage;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ProjectKGBGDesActivity$$ViewInjector {
  public static void inject(Finder finder, final com.henghao.parkland.activity.projectmanage.ProjectKGBGDesActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624154, "field 'tvTime'");
    target.tvTime = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624214, "field 'tvFileName'");
    target.tvFileName = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624215, "field 'tvDownload' and method 'onClick'");
    target.tvDownload = (android.widget.TextView) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onClick();
        }
      });
    view = finder.findRequiredView(source, 2131624161, "field 'tvName'");
    target.tvName = (android.widget.TextView) view;
  }

  public static void reset(com.henghao.parkland.activity.projectmanage.ProjectKGBGDesActivity target) {
    target.tvTime = null;
    target.tvFileName = null;
    target.tvDownload = null;
    target.tvName = null;
  }
}
