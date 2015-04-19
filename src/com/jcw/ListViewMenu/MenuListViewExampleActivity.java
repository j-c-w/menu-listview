package com.jcw.ListViewMenu;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by Jackson Woodruff on 4/18/2015.
 *
 * This is an example of using the MenuListView
 * and its adapter in an activity.
 */
public class MenuListViewExampleActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstace) {
		super.onCreate(savedInstace);

		MenuListView exampleList = new MenuListView(this);
		MenuListViewAdapter adapter = new MenuListViewAdapter(this);

		adapter.addItem(new MenuListItem("Show a Toast", new MenuItemClickListener() {
			@Override
			public void onClick(String text, String subheader, boolean isFolder) {
				Toast.makeText(MenuListViewExampleActivity.this, "Toast", Toast.LENGTH_LONG).show();
			}
		}));

		this.setContentView(exampleList);
	}
}
