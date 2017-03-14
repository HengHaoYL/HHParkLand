// Generated code from Butter Knife. Do not modify!
package com.henghao.parkland.activity.projectmanage;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ProjectHSResultDesActivity$$ViewInjector {
  public static void inject(Finder finder, final com.henghao.parkland.activity.projectmanage.ProjectHSResultDesActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624189, "field 'tvHsDeparment'");
    target.tvHsDeparment = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624151, "field 'tvName'");
    target.tvName = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624515, "field 'tvTitle'");
    target.tvTitle = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624154, "field 'gridView'");
    target.gridView = (com.benefit.buy.library.views.NoScrollGridView) view;
  }

  public static void reset(com.henghao.parkland.activity.projectmanage.ProjectHSResultDesActivity target) {
    target.tvHsDeparment = null;
    target.tvName = null;
    target.tvTitle = null;
    target.gridView = null;
  }
}
