// Generated code from Butter Knife. Do not modify!
package com.henghao.parkland.activity.projectmanage;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ProjectInfoSubmitActivity$$ViewInjector {
  public static void inject(Finder finder, final com.henghao.parkland.activity.projectmanage.ProjectInfoSubmitActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624198, "field 'etXmName'");
    target.etXmName = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131624202, "field 'etXmPerson'");
    target.etXmPerson = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131624203, "field 'etXmContact'");
    target.etXmContact = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131624204, "field 'etXmPersonNum'");
    target.etXmPersonNum = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131624205, "field 'etXmAdd'");
    target.etXmAdd = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131624069, "field 'btnSubmit' and method 'onClick'");
    target.btnSubmit = (android.widget.TextView) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131624199, "field 'etConstructionUnit'");
    target.etConstructionUnit = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131624200, "field 'tvStartTime' and method 'onClick'");
    target.tvStartTime = (android.widget.TextView) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131624201, "field 'tvCompletionTime' and method 'onClick'");
    target.tvCompletionTime = (android.widget.TextView) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
  }

  public static void reset(com.henghao.parkland.activity.projectmanage.ProjectInfoSubmitActivity target) {
    target.etXmName = null;
    target.etXmPerson = null;
    target.etXmContact = null;
    target.etXmPersonNum = null;
    target.etXmAdd = null;
    target.btnSubmit = null;
    target.etConstructionUnit = null;
    target.tvStartTime = null;
    target.tvCompletionTime = null;
  }
}
