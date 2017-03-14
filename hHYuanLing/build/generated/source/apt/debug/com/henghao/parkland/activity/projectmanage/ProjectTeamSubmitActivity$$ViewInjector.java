// Generated code from Butter Knife. Do not modify!
package com.henghao.parkland.activity.projectmanage;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ProjectTeamSubmitActivity$$ViewInjector {
  public static void inject(Finder finder, final com.henghao.parkland.activity.projectmanage.ProjectTeamSubmitActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624292, "field 'etPsName'");
    target.etPsName = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131624293, "field 'etPsIdcard'");
    target.etPsIdcard = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131624294, "field 'etPsTel'");
    target.etPsTel = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131624148, "field 'tvSubmit' and method 'onClick'");
    target.tvSubmit = (android.widget.TextView) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onClick();
        }
      });
    view = finder.findRequiredView(source, 2131624185, "field 'etWorkPost'");
    target.etWorkPost = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624176, "field 'tvPersonnelType'");
    target.tvPersonnelType = (android.widget.TextView) view;
  }

  public static void reset(com.henghao.parkland.activity.projectmanage.ProjectTeamSubmitActivity target) {
    target.etPsName = null;
    target.etPsIdcard = null;
    target.etPsTel = null;
    target.tvSubmit = null;
    target.etWorkPost = null;
    target.tvPersonnelType = null;
  }
}
