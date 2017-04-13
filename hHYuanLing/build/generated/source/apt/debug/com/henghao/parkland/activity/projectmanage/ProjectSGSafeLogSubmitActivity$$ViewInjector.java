// Generated code from Butter Knife. Do not modify!
package com.henghao.parkland.activity.projectmanage;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ProjectSGSafeLogSubmitActivity$$ViewInjector {
  public static void inject(Finder finder, final com.henghao.parkland.activity.projectmanage.ProjectSGSafeLogSubmitActivity target, Object source) {
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
    view = finder.findRequiredView(source, 2131624264, "field 'etConstructionSite'");
    target.etConstructionSite = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131624265, "field 'etConstructionDynamic'");
    target.etConstructionDynamic = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131624266, "field 'etSafetySituation'");
    target.etSafetySituation = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131624267, "field 'etSafetyProblems'");
    target.etSafetyProblems = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131624268, "field 'etFillPeople'");
    target.etFillPeople = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131624269, "field 'btnSubmit' and method 'onClick'");
    target.btnSubmit = (android.widget.TextView) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
  }

  public static void reset(com.henghao.parkland.activity.projectmanage.ProjectSGSafeLogSubmitActivity target) {
    target.tvDates = null;
    target.etConstructionSite = null;
    target.etConstructionDynamic = null;
    target.etSafetySituation = null;
    target.etSafetyProblems = null;
    target.etFillPeople = null;
    target.btnSubmit = null;
  }
}
