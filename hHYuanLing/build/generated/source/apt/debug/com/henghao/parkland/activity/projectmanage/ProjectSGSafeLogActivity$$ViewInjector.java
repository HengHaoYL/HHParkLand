// Generated code from Butter Knife. Do not modify!
package com.henghao.parkland.activity.projectmanage;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ProjectSGSafeLogActivity$$ViewInjector {
  public static void inject(Finder finder, final com.henghao.parkland.activity.projectmanage.ProjectSGSafeLogActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624257, "field 'listView'");
    target.listView = (com.benefit.buy.library.views.xlistview.XListView) view;
    view = finder.findRequiredView(source, 2131624158, "field 'layoutBottom'");
    target.layoutBottom = (android.widget.LinearLayout) view;
    view = finder.findRequiredView(source, 2131624159, "method 'onClick'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131624160, "method 'onClick'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
  }

  public static void reset(com.henghao.parkland.activity.projectmanage.ProjectSGSafeLogActivity target) {
    target.listView = null;
    target.layoutBottom = null;
  }
}
