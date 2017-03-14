// Generated code from Butter Knife. Do not modify!
package com.henghao.parkland.activity.projectmanage;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ProjectSpvLogSubmitActivity$$ViewInjector {
  public static void inject(Finder finder, final com.henghao.parkland.activity.projectmanage.ProjectSpvLogSubmitActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624515, "field 'tvTitle'");
    target.tvTitle = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624151, "field 'tvName'");
    target.tvName = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624069, "field 'tvDates' and method 'onClick'");
    target.tvDates = (android.widget.TextView) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131624280, "field 'etSupervisionPosition'");
    target.etSupervisionPosition = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131624281, "field 'etProgressSituation'");
    target.etProgressSituation = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131624282, "field 'etWorkingSitustion'");
    target.etWorkingSitustion = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131624283, "field 'etQuestion'");
    target.etQuestion = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131624284, "field 'etOther'");
    target.etOther = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131624285, "field 'etNoteTaker'");
    target.etNoteTaker = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131624286, "field 'etEngineer'");
    target.etEngineer = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131624253, "field 'btnSub' and method 'onClick'");
    target.btnSub = (android.widget.Button) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
  }

  public static void reset(com.henghao.parkland.activity.projectmanage.ProjectSpvLogSubmitActivity target) {
    target.tvTitle = null;
    target.tvName = null;
    target.tvDates = null;
    target.etSupervisionPosition = null;
    target.etProgressSituation = null;
    target.etWorkingSitustion = null;
    target.etQuestion = null;
    target.etOther = null;
    target.etNoteTaker = null;
    target.etEngineer = null;
    target.btnSub = null;
  }
}
