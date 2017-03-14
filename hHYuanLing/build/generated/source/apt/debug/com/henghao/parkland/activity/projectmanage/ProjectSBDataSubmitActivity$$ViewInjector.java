// Generated code from Butter Knife. Do not modify!
package com.henghao.parkland.activity.projectmanage;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ProjectSBDataSubmitActivity$$ViewInjector {
  public static void inject(Finder finder, final com.henghao.parkland.activity.projectmanage.ProjectSBDataSubmitActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624228, "field 'etSbName'");
    target.etSbName = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131624229, "field 'etSbSpec'");
    target.etSbSpec = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131624230, "field 'etSbNum'");
    target.etSbNum = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131624231, "field 'etSbPurpose'");
    target.etSbPurpose = (android.widget.EditText) view;
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
    view = finder.findRequiredView(source, 2131624232, "field 'rgSbSource'");
    target.rgSbSource = (android.widget.RadioGroup) view;
  }

  public static void reset(com.henghao.parkland.activity.projectmanage.ProjectSBDataSubmitActivity target) {
    target.etSbName = null;
    target.etSbSpec = null;
    target.etSbNum = null;
    target.etSbPurpose = null;
    target.tvSubmit = null;
    target.rgSbSource = null;
  }
}
