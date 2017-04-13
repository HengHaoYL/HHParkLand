// Generated code from Butter Knife. Do not modify!
package com.henghao.parkland.activity.projectmanage;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ProjectJunGongSubmitActivity$$ViewInjector {
  public static void inject(Finder finder, final com.henghao.parkland.activity.projectmanage.ProjectJunGongSubmitActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624207, "field 'tvProjectName'");
    target.tvProjectName = (android.widget.TextView) view;
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
    view = finder.findRequiredView(source, 2131624209, "field 'etInspectionPersonnel'");
    target.etInspectionPersonnel = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131624210, "field 'tvInspectionSituation' and method 'onClick'");
    target.tvInspectionSituation = (android.widget.TextView) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131624211, "field 'tvCompletionDrawing' and method 'onClick'");
    target.tvCompletionDrawing = (android.widget.TextView) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131624212, "field 'tvCompletionReport' and method 'onClick'");
    target.tvCompletionReport = (android.widget.TextView) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131624156, "field 'tvSubmit' and method 'onClick'");
    target.tvSubmit = (android.widget.TextView) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
  }

  public static void reset(com.henghao.parkland.activity.projectmanage.ProjectJunGongSubmitActivity target) {
    target.tvProjectName = null;
    target.tvDates = null;
    target.etInspectionPersonnel = null;
    target.tvInspectionSituation = null;
    target.tvCompletionDrawing = null;
    target.tvCompletionReport = null;
    target.tvSubmit = null;
  }
}
