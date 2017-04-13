// Generated code from Butter Knife. Do not modify!
package com.henghao.parkland.activity.projectmanage;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ProjectBGManageSubmitActivity$$ViewInjector {
  public static void inject(Finder finder, final com.henghao.parkland.activity.projectmanage.ProjectBGManageSubmitActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624164, "field 'spConfirmingParty'");
    target.spConfirmingParty = (android.widget.Spinner) view;
    view = finder.findRequiredView(source, 2131624163, "field 'tvTimes' and method 'onClick'");
    target.tvTimes = (android.widget.TextView) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131624165, "field 'tvFiles' and method 'onClick'");
    target.tvFiles = (android.widget.TextView) view;
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

  public static void reset(com.henghao.parkland.activity.projectmanage.ProjectBGManageSubmitActivity target) {
    target.spConfirmingParty = null;
    target.tvTimes = null;
    target.tvFiles = null;
    target.tvSubmit = null;
  }
}
