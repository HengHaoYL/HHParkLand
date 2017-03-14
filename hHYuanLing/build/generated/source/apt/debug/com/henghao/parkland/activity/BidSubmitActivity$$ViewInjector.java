// Generated code from Butter Knife. Do not modify!
package com.henghao.parkland.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class BidSubmitActivity$$ViewInjector {
  public static void inject(Finder finder, final com.henghao.parkland.activity.BidSubmitActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624065, "field 'etCompanyName'");
    target.etCompanyName = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131624066, "field 'etCompanyAdd'");
    target.etCompanyAdd = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131624067, "field 'etContacts'");
    target.etContacts = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131624068, "field 'etTel'");
    target.etTel = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131624069, "field 'tvDates'");
    target.tvDates = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624201, "field 'etXmAdd'");
    target.etXmAdd = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131624070, "field 'btnSubmitProjectinfo' and method 'onClick'");
    target.btnSubmitProjectinfo = (android.widget.TextView) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onClick();
        }
      });
  }

  public static void reset(com.henghao.parkland.activity.BidSubmitActivity target) {
    target.etCompanyName = null;
    target.etCompanyAdd = null;
    target.etContacts = null;
    target.etTel = null;
    target.tvDates = null;
    target.etXmAdd = null;
    target.btnSubmitProjectinfo = null;
  }
}
