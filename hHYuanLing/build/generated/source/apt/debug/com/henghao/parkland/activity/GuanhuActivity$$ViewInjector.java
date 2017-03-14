// Generated code from Butter Knife. Do not modify!
package com.henghao.parkland.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class GuanhuActivity$$ViewInjector {
  public static void inject(Finder finder, final com.henghao.parkland.activity.GuanhuActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624086, "field 'tvTreeId'");
    target.tvTreeId = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624515, "field 'tv_title'");
    target.tv_title = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624087, "field 'tvYhSite'");
    target.tvYhSite = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624088, "field 'tvYhTime'");
    target.tvYhTime = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624089, "field 'etYhWorkder'");
    target.etYhWorkder = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131624091, "field 'etYhDetails'");
    target.etYhDetails = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131624106, "field 'etComment'");
    target.etComment = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131624107, "field 'btnSubmit' and method 'onClick'");
    target.btnSubmit = (android.widget.Button) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131624108, "field 'btnCancel' and method 'onClick'");
    target.btnCancel = (android.widget.Button) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131624098, "field 'rgClean'");
    target.rgClean = (android.widget.RadioGroup) view;
    view = finder.findRequiredView(source, 2131624102, "field 'rgTreegrowup'");
    target.rgTreegrowup = (android.widget.RadioGroup) view;
    view = finder.findRequiredView(source, 2131624092, "field 'rgQuestion'");
    target.rgQuestion = (com.henghao.parkland.views.FlowRadioGroup) view;
  }

  public static void reset(com.henghao.parkland.activity.GuanhuActivity target) {
    target.tvTreeId = null;
    target.tv_title = null;
    target.tvYhSite = null;
    target.tvYhTime = null;
    target.etYhWorkder = null;
    target.etYhDetails = null;
    target.etComment = null;
    target.btnSubmit = null;
    target.btnCancel = null;
    target.rgClean = null;
    target.rgTreegrowup = null;
    target.rgQuestion = null;
  }
}
