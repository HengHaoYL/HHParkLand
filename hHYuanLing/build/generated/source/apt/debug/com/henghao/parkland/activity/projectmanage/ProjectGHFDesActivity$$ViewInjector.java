// Generated code from Butter Knife. Do not modify!
package com.henghao.parkland.activity.projectmanage;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ProjectGHFDesActivity$$ViewInjector {
  public static void inject(Finder finder, final com.henghao.parkland.activity.projectmanage.ProjectGHFDesActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624161, "field 'tvName'");
    target.tvName = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624170, "field 'tvEpName'");
    target.tvEpName = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624171, "field 'tvEpAdd'");
    target.tvEpAdd = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624172, "field 'tvEpDate'");
    target.tvEpDate = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624173, "field 'tvEpTel'");
    target.tvEpTel = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624092, "field 'gridView'");
    target.gridView = (com.benefit.buy.library.views.NoScrollGridView) view;
  }

  public static void reset(com.henghao.parkland.activity.projectmanage.ProjectGHFDesActivity target) {
    target.tvName = null;
    target.tvEpName = null;
    target.tvEpAdd = null;
    target.tvEpDate = null;
    target.tvEpTel = null;
    target.gridView = null;
  }
}
