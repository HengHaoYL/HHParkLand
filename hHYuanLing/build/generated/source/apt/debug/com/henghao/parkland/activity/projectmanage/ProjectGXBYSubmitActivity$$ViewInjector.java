// Generated code from Butter Knife. Do not modify!
package com.henghao.parkland.activity.projectmanage;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ProjectGXBYSubmitActivity$$ViewInjector {
  public static void inject(Finder finder, final com.henghao.parkland.activity.projectmanage.ProjectGXBYSubmitActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624174, "field 'tvGxName'");
    target.tvGxName = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624179, "field 'etGxProcedure'");
    target.etGxProcedure = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131624185, "field 'etWorkPost'");
    target.etWorkPost = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131624178, "field 'tvGxTime' and method 'onClick'");
    target.tvGxTime = (android.widget.TextView) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131624186, "field 'tvUploadImage' and method 'onClick'");
    target.tvUploadImage = (android.widget.TextView) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131624148, "field 'tvSubmit' and method 'onClick'");
    target.tvSubmit = (android.widget.TextView) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131624180, "field 'rgPersonnelType'");
    target.rgPersonnelType = (android.widget.RadioGroup) view;
    view = finder.findRequiredView(source, 2131624176, "field 'tvPersonnelType' and method 'onClick'");
    target.tvPersonnelType = (android.widget.TextView) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
  }

  public static void reset(com.henghao.parkland.activity.projectmanage.ProjectGXBYSubmitActivity target) {
    target.tvGxName = null;
    target.etGxProcedure = null;
    target.etWorkPost = null;
    target.tvGxTime = null;
    target.tvUploadImage = null;
    target.tvSubmit = null;
    target.rgPersonnelType = null;
    target.tvPersonnelType = null;
  }
}
