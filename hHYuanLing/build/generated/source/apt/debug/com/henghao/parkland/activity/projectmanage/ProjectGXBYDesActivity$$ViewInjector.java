// Generated code from Butter Knife. Do not modify!
package com.henghao.parkland.activity.projectmanage;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ProjectGXBYDesActivity$$ViewInjector {
  public static void inject(Finder finder, final com.henghao.parkland.activity.projectmanage.ProjectGXBYDesActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624180, "field 'tvGxName'");
    target.tvGxName = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624181, "field 'tvGxProcedure'");
    target.tvGxProcedure = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624182, "field 'tvPersonnelType'");
    target.tvPersonnelType = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624183, "field 'tvWorkPost'");
    target.tvWorkPost = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624184, "field 'tvGxTime'");
    target.tvGxTime = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624092, "field 'gridView'");
    target.gridView = (android.widget.GridView) view;
  }

  public static void reset(com.henghao.parkland.activity.projectmanage.ProjectGXBYDesActivity target) {
    target.tvGxName = null;
    target.tvGxProcedure = null;
    target.tvPersonnelType = null;
    target.tvWorkPost = null;
    target.tvGxTime = null;
    target.gridView = null;
  }
}
