package com.jcw.ListViewMenu;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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

		List<MenuListItem> items = new ArrayList<MenuListItem>();
		items.add(new MenuListItem("Sub item 1", "", new MenuItemClickListener() {
			@Override
			public void onClick(String text, String subheader, boolean isFolder) {
				Toast.makeText(MenuListViewExampleActivity.this, "Item in a folder of the menu", Toast.LENGTH_LONG).show();
			}
		}));

		MenuListFolder container = new MenuListFolder("Folder", "Click to enlarge", items);

		adapter.addItem(container);
		adapter.addItem(new MenuListItem("Exit", "Quit the demo", new MenuItemClickListener() {
			@Override
			public void onClick(String text, String subheader, boolean isFolder) {
				MenuListViewExampleActivity.this.finish();
			}
		}));

		exampleList.setAdapter(adapter);
		this.setContentView(exampleList);
	}
}
