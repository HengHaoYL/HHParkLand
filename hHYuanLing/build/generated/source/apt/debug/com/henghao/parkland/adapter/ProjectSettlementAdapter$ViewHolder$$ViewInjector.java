// Generated code from Butter Knife. Do not modify!
package com.henghao.parkland.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ProjectSettlementAdapter$ViewHolder$$ViewInjector {
  public static void inject(Finder finder, final com.henghao.parkland.adapter.ProjectSettlementAdapter.ViewHolder target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624151, "field 'tvName'");
    target.tvName = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624069, "field 'tvDates'");
    target.tvDates = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624538, "field 'ivImage'");
    target.ivImage = (android.widget.ImageView) view;
  }

  public static void reset(com.henghao.parkland.adapter.ProjectSettlementAdapter.ViewHolder target) {
    target.tvName = null;
    target.tvDates = null;
    target.ivImage = null;
  }
}
