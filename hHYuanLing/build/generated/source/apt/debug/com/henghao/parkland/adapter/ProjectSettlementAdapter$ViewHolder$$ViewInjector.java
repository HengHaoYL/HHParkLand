// Generated code from Butter Knife. Do not modify!
package com.henghao.parkland.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ProjectSettlementAdapter$ViewHolder$$ViewInjector {
  public static void inject(Finder finder, final com.henghao.parkland.adapter.ProjectSettlementAdapter.ViewHolder target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624517, "field 'checkBox'");
    target.checkBox = (android.widget.CheckBox) view;
    view = finder.findRequiredView(source, 2131624161, "field 'tvName'");
    target.tvName = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624068, "field 'tvDates'");
    target.tvDates = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624540, "field 'ivImg'");
    target.ivImg = (android.widget.ImageView) view;
  }

  public static void reset(com.henghao.parkland.adapter.ProjectSettlementAdapter.ViewHolder target) {
    target.checkBox = null;
    target.tvName = null;
    target.tvDates = null;
    target.ivImg = null;
  }
}
