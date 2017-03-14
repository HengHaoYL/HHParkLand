// Generated code from Butter Knife. Do not modify!
package com.henghao.parkland.activity.projectmanage;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ProjectBGManageDesActivity$$ViewInjector {
  public static void inject(Finder finder, final com.henghao.parkland.activity.projectmanage.ProjectBGManageDesActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624515, "field 'tvTitle'");
    target.tvTitle = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624151, "field 'tvName'");
    target.tvName = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624152, "field 'tvConfirmingParty'");
    target.tvConfirmingParty = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624153, "field 'tvTimes'");
    target.tvTimes = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624154, "field 'gridView'");
    target.gridView = (com.benefit.buy.library.views.NoScrollGridView) view;
  }

  public static void reset(com.henghao.parkland.activity.projectmanage.ProjectBGManageDesActivity target) {
    target.tvTitle = null;
    target.tvName = null;
    target.tvConfirmingParty = null;
    target.tvTimes = null;
    target.gridView = null;
  }
}
