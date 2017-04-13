// Generated code from Butter Knife. Do not modify!
package com.henghao.parkland.activity.projectmanage;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ProjectSGLogSubmitActivity$$ViewInjector {
  public static void inject(Finder finder, final com.henghao.parkland.activity.projectmanage.ProjectSGLogSubmitActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624068, "field 'tvDates' and method 'onClick'");
    target.tvDates = (android.widget.TextView) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131624248, "field 'etProactContent'");
    target.etProactContent = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131624249, "field 'etTechnicalIndex'");
    target.etTechnicalIndex = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131624250, "field 'etWorkingCondition'");
    target.etWorkingCondition = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131624251, "field 'etPrincipal'");
    target.etPrincipal = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131624252, "field 'etBuilder'");
    target.etBuilder = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131624253, "field 'btnSub' and method 'onClick'");
    target.btnSub = (android.widget.TextView) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
  }

  public static void reset(com.henghao.parkland.activity.projectmanage.ProjectSGLogSubmitActivity target) {
    target.tvDates = null;
    target.etProactContent = null;
    target.etTechnicalIndex = null;
    target.etWorkingCondition = null;
    target.etPrincipal = null;
    target.etBuilder = null;
    target.btnSub = null;
  }
}
