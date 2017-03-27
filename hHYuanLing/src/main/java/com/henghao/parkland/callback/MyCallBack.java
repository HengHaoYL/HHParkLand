package com.henghao.parkland.callback;

/**
 * Created by 晏琦云 on 2017/3/14.
 * 删除list_item所用到的回调接口
 */

public interface MyCallBack {
    /**
     * 添加一个选中的item的ID
     *
     * @param id
     */
    public void addId(int id);

    /**
     * 清除没有选中的item的ID
     *
     * @param id
     */
    public void removeId(int id);

    /**
     * 设置全选按钮的状态
     */
    public void setChecked();
}
