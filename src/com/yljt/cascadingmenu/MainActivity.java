package com.yljt.cascadingmenu;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.yljt.cascadingmenu.entity.MenuItem;
import com.yljt.cascadingmenu.interfaces.CascadingMenuViewOnSelectListener;

public class MainActivity extends FragmentActivity implements OnClickListener {
   private ArrayList<MenuItem> menuItems=new ArrayList<MenuItem>();
   //两级联动菜单数据
   private CascadingMenuFragment cascadingMenuFragment=null;
   private CascadingMenuPopWindow cascadingMenuPopWindow=null;
   private LinearLayout linear = null;
   private ListView mainMenu = null;
   private Button menuViewPopWindow ;
   private Button menuViewFragment ;
   private float beginX = 0;
   private float endX = 0;
   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		menuViewPopWindow=(Button)findViewById(R.id.menuViewPopWindow);
		menuViewFragment=(Button)findViewById(R.id.menuViewFragment);
		ArrayList<MenuItem> tempMenuItems =null;
		for (int j = 0; j < 7; j++) {
			tempMenuItems =new ArrayList<MenuItem>();
			for (int i = 0; i <15; i++) {
				tempMenuItems.add(new MenuItem(false,"子菜单"+j+""+i,null));
			}
			menuItems.add(new MenuItem(true,"主菜单"+j,tempMenuItems));
		}
		menuViewPopWindow.setOnClickListener(this);
		menuViewFragment.setOnClickListener(this);
		linear = (LinearLayout) findViewById(R.id.liner);
		linear.setOnTouchListener(mOnTouchListener);
	}
	// 滑动
		private OnTouchListener mOnTouchListener = new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				System.out.println("触摸了" + event.getAction() + " , " + event.getX());
				if(event.getAction() == MotionEvent.ACTION_DOWN){
					beginX = event.getX();
					return true;
				} else if (event.getAction() == MotionEvent.ACTION_UP){
					endX = event.getY();
					System.out.println("滑动距离 = " + Math.abs(beginX - endX));
					if(Math.abs(beginX - endX) > 50){
						showFragmentMenu();						
					}
				}
				return false;
			}
		};
	public void showFragmentMenu(){
		FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
		fragmentTransaction.setCustomAnimations(R.anim.short_menu_pop_in,R.anim.short_menu_pop_out);
		
		if(cascadingMenuFragment==null){
			cascadingMenuFragment=CascadingMenuFragment.getInstance();
			cascadingMenuFragment.setMenuItems(menuItems);
			cascadingMenuFragment.setMenuViewOnSelectListener(new NMCascadingMenuViewOnSelectListener());
			fragmentTransaction.replace(R.id.liner, cascadingMenuFragment);
		}else{
			fragmentTransaction.remove(cascadingMenuFragment);
			cascadingMenuFragment=null;
		}
		fragmentTransaction.commit();
		mainMenu = (ListView) findViewById(R.id.listView);
		if(mainMenu != null){
			mainMenu.setOnTouchListener(mOnTouchListener);
		}
	}
	private void showPopMenu(){
		if(cascadingMenuPopWindow==null){
			cascadingMenuPopWindow=new CascadingMenuPopWindow(getApplicationContext(),menuItems);
			cascadingMenuPopWindow.setMenuViewOnSelectListener(new NMCascadingMenuViewOnSelectListener());
			cascadingMenuPopWindow.showAsDropDown(menuViewPopWindow,5,5);
		}
	     else if(cascadingMenuPopWindow!=null&&cascadingMenuPopWindow.isShowing()){
			cascadingMenuPopWindow.dismiss();
		}else{
			cascadingMenuPopWindow.showAsDropDown(menuViewPopWindow,5,5);
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
    //级联菜单选择回调接口
	class NMCascadingMenuViewOnSelectListener implements CascadingMenuViewOnSelectListener{

		@Override
		public void getValue(MenuItem menuItem) {
			cascadingMenuFragment=null;
			Toast.makeText(getApplicationContext(), ""+menuItem.toString(), 1000).show();
		}
		
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.menuViewPopWindow:
			showPopMenu();
			break;
		case R.id.menuViewFragment:
			showFragmentMenu();
			break;
		}
	}
}
