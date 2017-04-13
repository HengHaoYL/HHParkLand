// Generated code from Butter Knife. Do not modify!
package com.henghao.parkland.activity.projectmanage;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ProjectBGManageDesActivity$$ViewInjector {
  public static void inject(Finder finder, final com.henghao.parkland.activity.projectmanage.ProjectBGManageDesActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624161, "field 'tvName'");
    target.tvName = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624162, "field 'tvConfirmingParty'");
    target.tvConfirmingParty = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624163, "field 'tvTimes'");
    target.tvTimes = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624092, "field 'gridView'");
    target.gridView = (com.benefit.buy.library.views.NoScrollGridView) view;
  }

  public static void reset(com.henghao.parkland.activity.projectmanage.ProjectBGManageDesActivity target) {
    target.tvName = null;
    target.tvConfirmingParty = null;
    target.tvTimes = null;
    target.gridView = null;
  }
}
