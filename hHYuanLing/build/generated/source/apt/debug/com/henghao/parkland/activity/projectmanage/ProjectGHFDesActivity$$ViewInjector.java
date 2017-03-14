// Generated code from Butter Knife. Do not modify!
package com.henghao.parkland.activity.projectmanage;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ProjectGHFDesActivity$$ViewInjector {
  public static void inject(Finder finder, final com.henghao.parkland.activity.projectmanage.ProjectGHFDesActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624515, "field 'tvTitle'");
    target.tvTitle = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624151, "field 'tvName'");
    target.tvName = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624163, "field 'tvEpName'");
    target.tvEpName = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624164, "field 'tvEpAdd'");
    target.tvEpAdd = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624165, "field 'tvEpDate'");
    target.tvEpDate = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624166, "field 'tvEpTel'");
    target.tvEpTel = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624154, "field 'gridView'");
    target.gridView = (com.benefit.buy.library.views.NoScrollGridView) view;
  }

  public static void reset(com.henghao.parkland.activity.projectmanage.ProjectGHFDesActivity target) {
    target.tvTitle = null;
    target.tvName = null;
    target.tvEpName = null;
    target.tvEpAdd = null;
    target.tvEpDate = null;
    target.tvEpTel = null;
    target.gridView = null;
  }
}
