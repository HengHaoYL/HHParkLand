// Generated code from Butter Knife. Do not modify!
package com.henghao.parkland.activity.projectmanage;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ProjectSpvLogActivity$$ViewInjector {
  public static void inject(Finder finder, final com.henghao.parkland.activity.projectmanage.ProjectSpvLogActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624271, "field 'tvState'");
    target.tvState = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624272, "field 'listView'");
    target.listView = (com.benefit.buy.library.views.xlistview.XListView) view;
  }

  public static void reset(com.henghao.parkland.activity.projectmanage.ProjectSpvLogActivity target) {
    target.tvState = null;
    target.listView = null;
  }
}
