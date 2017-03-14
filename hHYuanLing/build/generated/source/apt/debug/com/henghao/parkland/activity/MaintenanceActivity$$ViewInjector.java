// Generated code from Butter Knife. Do not modify!
package com.henghao.parkland.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class MaintenanceActivity$$ViewInjector {
  public static void inject(Finder finder, final com.henghao.parkland.activity.MaintenanceActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624138, "field 'tvTreeid'");
    target.tvTreeid = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624139, "field 'tvState'");
    target.tvState = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624140, "field 'tvTime'");
    target.tvTime = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624141, "field 'tvPlace'");
    target.tvPlace = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624142, "field 'btnConfirm' and method 'onClick'");
    target.btnConfirm = (android.widget.Button) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131624143, "field 'btnCancel' and method 'onClick'");
    target.btnCancel = (android.widget.Button) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
  }

  public static void reset(com.henghao.parkland.activity.MaintenanceActivity target) {
    target.tvTreeid = null;
    target.tvState = null;
    target.tvTime = null;
    target.tvPlace = null;
    target.btnConfirm = null;
    target.btnCancel = null;
  }
}
