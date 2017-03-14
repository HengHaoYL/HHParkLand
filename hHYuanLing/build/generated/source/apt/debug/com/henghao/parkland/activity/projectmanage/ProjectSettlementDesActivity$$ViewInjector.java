// Generated code from Butter Knife. Do not modify!
package com.henghao.parkland.activity.projectmanage;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ProjectSettlementDesActivity$$ViewInjector {
  public static void inject(Finder finder, final com.henghao.parkland.activity.projectmanage.ProjectSettlementDesActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624515, "field 'tvTitle'");
    target.tvTitle = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624069, "field 'tvDates'");
    target.tvDates = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624156, "field 'tvFiles'");
    target.tvFiles = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624154, "field 'gridView'");
    target.gridView = (com.benefit.buy.library.views.NoScrollGridView) view;
    view = finder.findRequiredView(source, 2131624151, "field 'tvName'");
    target.tvName = (android.widget.TextView) view;
  }

  public static void reset(com.henghao.parkland.activity.projectmanage.ProjectSettlementDesActivity target) {
    target.tvTitle = null;
    target.tvDates = null;
    target.tvFiles = null;
    target.gridView = null;
    target.tvName = null;
  }
}
