// Generated code from Butter Knife. Do not modify!
package com.henghao.parkland.activity.projectmanage;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ProjectTeamDesActivity$$ViewInjector {
  public static void inject(Finder finder, final com.henghao.parkland.activity.projectmanage.ProjectTeamDesActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624161, "field 'tv_name'");
    target.tv_name = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624182, "field 'tvPersonnelType'");
    target.tvPersonnelType = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624183, "field 'tvWorkPost'");
    target.tvWorkPost = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624286, "field 'tvPsName'");
    target.tvPsName = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624287, "field 'tvPsIdcard'");
    target.tvPsIdcard = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624288, "field 'tvPsTel'");
    target.tvPsTel = (android.widget.TextView) view;
  }

  public static void reset(com.henghao.parkland.activity.projectmanage.ProjectTeamDesActivity target) {
    target.tv_name = null;
    target.tvPersonnelType = null;
    target.tvWorkPost = null;
    target.tvPsName = null;
    target.tvPsIdcard = null;
    target.tvPsTel = null;
  }
}
