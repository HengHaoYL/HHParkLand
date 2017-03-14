// Generated code from Butter Knife. Do not modify!
package com.henghao.parkland.activity.projectmanage;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ProjectJunGongDesActivity$$ViewInjector {
  public static void inject(Finder finder, final com.henghao.parkland.activity.projectmanage.ProjectJunGongDesActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624515, "field 'tvTitle'");
    target.tvTitle = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624204, "field 'tvProjectName'");
    target.tvProjectName = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624069, "field 'tvDates'");
    target.tvDates = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624205, "field 'tvInspectionPersonnel'");
    target.tvInspectionPersonnel = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624154, "field 'gridView'");
    target.gridView = (com.benefit.buy.library.views.NoScrollGridView) view;
  }

  public static void reset(com.henghao.parkland.activity.projectmanage.ProjectJunGongDesActivity target) {
    target.tvTitle = null;
    target.tvProjectName = null;
    target.tvDates = null;
    target.tvInspectionPersonnel = null;
    target.gridView = null;
  }
}
