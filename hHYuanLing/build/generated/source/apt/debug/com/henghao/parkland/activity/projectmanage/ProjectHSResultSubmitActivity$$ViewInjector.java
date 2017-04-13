// Generated code from Butter Knife. Do not modify!
package com.henghao.parkland.activity.projectmanage;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ProjectHSResultSubmitActivity$$ViewInjector {
  public static void inject(Finder finder, final com.henghao.parkland.activity.projectmanage.ProjectHSResultSubmitActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624161, "field 'tvName'");
    target.tvName = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624195, "field 'etHsDeparment'");
    target.etHsDeparment = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131624196, "field 'tvHsImg' and method 'onClick'");
    target.tvHsImg = (android.widget.TextView) view;
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

  public static void reset(com.henghao.parkland.activity.projectmanage.ProjectHSResultSubmitActivity target) {
    target.tvName = null;
    target.etHsDeparment = null;
    target.tvHsImg = null;
    target.tvSubmit = null;
  }
}
