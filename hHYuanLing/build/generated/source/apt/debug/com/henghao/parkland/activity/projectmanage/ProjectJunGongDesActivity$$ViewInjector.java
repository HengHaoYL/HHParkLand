// Generated code from Butter Knife. Do not modify!
package com.henghao.parkland.activity.projectmanage;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ProjectJunGongDesActivity$$ViewInjector {
  public static void inject(Finder finder, final com.henghao.parkland.activity.projectmanage.ProjectJunGongDesActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624207, "field 'tvProjectName'");
    target.tvProjectName = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624068, "field 'tvDates'");
    target.tvDates = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624208, "field 'tvInspectionPersonnel'");
    target.tvInspectionPersonnel = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624092, "field 'gridView'");
    target.gridView = (com.benefit.buy.library.views.NoScrollGridView) view;
  }

  public static void reset(com.henghao.parkland.activity.projectmanage.ProjectJunGongDesActivity target) {
    target.tvProjectName = null;
    target.tvDates = null;
    target.tvInspectionPersonnel = null;
    target.gridView = null;
  }
}
