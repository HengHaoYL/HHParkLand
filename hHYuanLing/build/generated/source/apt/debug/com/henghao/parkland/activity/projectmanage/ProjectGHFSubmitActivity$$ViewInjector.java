// Generated code from Butter Knife. Do not modify!
package com.henghao.parkland.activity.projectmanage;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ProjectGHFSubmitActivity$$ViewInjector {
  public static void inject(Finder finder, final com.henghao.parkland.activity.projectmanage.ProjectGHFSubmitActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624174, "field 'etEpName'");
    target.etEpName = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131624175, "field 'etEpAdd'");
    target.etEpAdd = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131624172, "field 'tvEpDate' and method 'onClick'");
    target.tvEpDate = (android.widget.TextView) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131624176, "field 'etEpTel'");
    target.etEpTel = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131624177, "field 'tvHehong' and method 'onClick'");
    target.tvHehong = (android.widget.TextView) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131624178, "field 'tvJianyan' and method 'onClick'");
    target.tvJianyan = (android.widget.TextView) view;
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

  public static void reset(com.henghao.parkland.activity.projectmanage.ProjectGHFSubmitActivity target) {
    target.etEpName = null;
    target.etEpAdd = null;
    target.tvEpDate = null;
    target.etEpTel = null;
    target.tvHehong = null;
    target.tvJianyan = null;
    target.tvSubmit = null;
  }
}
