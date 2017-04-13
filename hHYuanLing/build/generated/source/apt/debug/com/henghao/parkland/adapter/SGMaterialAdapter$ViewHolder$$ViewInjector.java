// Generated code from Butter Knife. Do not modify!
package com.henghao.parkland.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class SGMaterialAdapter$ViewHolder$$ViewInjector {
  public static void inject(Finder finder, final com.henghao.parkland.adapter.SGMaterialAdapter.ViewHolder target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624553, "field 'ivWorkImg'");
    target.ivWorkImg = (android.widget.ImageView) view;
    view = finder.findRequiredView(source, 2131624552, "field 'tvWorkContent'");
    target.tvWorkContent = (android.widget.TextView) view;
  }

  public static void reset(com.henghao.parkland.adapter.SGMaterialAdapter.ViewHolder target) {
    target.ivWorkImg = null;
    target.tvWorkContent = null;
  }
}
