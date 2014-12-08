package com.yljt.cascadingmenu;
import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yljt.cascadingmenu.entity.MenuItem;
import com.yljt.cascadingmenu.interfaces.CascadingMenuViewOnSelectListener;


/**
 * 级联菜单碎片
 * @author LILIN
 * 上午11:01:04
 */
public class CascadingMenuFragment extends Fragment {

	private CascadingMenuView cascadingMenuView;
	private ArrayList<MenuItem> menuItems=null;
	private int isShowing = 0;
    //提供给外的接口
	private CascadingMenuViewOnSelectListener menuViewOnSelectListener;
	private static CascadingMenuFragment instance=null;
	//单例模式
	public static CascadingMenuFragment getInstance(){
		if(instance==null){
			instance=new CascadingMenuFragment();
		}
		return instance;
	}
	
	public void setMenuItems(ArrayList<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}
	public void setMenuViewOnSelectListener(
			CascadingMenuViewOnSelectListener menuViewOnSelectListener) {
		this.menuViewOnSelectListener = menuViewOnSelectListener;
	}
     
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//实例化级联菜单
		cascadingMenuView=new CascadingMenuView(getActivity(),menuItems);
		//设置回调接口
		cascadingMenuView.setCascadingMenuViewOnSelectListener(new MCascadingMenuViewOnSelectListener());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return cascadingMenuView;
	}
	 public int getIsShowing() {
		return isShowing;
	}

	public void setIsShowing(int isShowing) {
		this.isShowing = isShowing;
	}
		//级联菜单选择回调接口
		class MCascadingMenuViewOnSelectListener implements CascadingMenuViewOnSelectListener{

			@Override
			public void getValue(MenuItem menuItem) {
				if(menuViewOnSelectListener!=null){
					menuViewOnSelectListener.getValue(menuItem);
				}
			}
			
		}
}
