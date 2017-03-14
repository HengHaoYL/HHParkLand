// Generated code from Butter Knife. Do not modify!
package com.henghao.parkland.activity.projectmanage;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ProjectSGBWActivity$$ViewInjector {
  public static void inject(Finder finder, final com.henghao.parkland.activity.projectmanage.ProjectSGBWActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624238, "field 'tvState'");
    target.tvState = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624239, "field 'listView'");
    target.listView = (com.benefit.buy.library.views.xlistview.XListView) view;
    view = finder.findRequiredView(source, 2131624515, "field 'tv_title'");
    target.tv_title = (android.widget.TextView) view;
  }

  public static void reset(com.henghao.parkland.activity.projectmanage.ProjectSGBWActivity target) {
    target.tvState = null;
    target.listView = null;
    target.tv_title = null;
  }
}
