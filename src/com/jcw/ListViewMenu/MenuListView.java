package com.jcw.ListViewMenu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by Jackson Woodruff on 4/16/2015.
 *
 * This is an extension of the listview
 * that uses the MenuListViewAdapter to display
 * the menu.
 *
 * Note -- you must use the MenuListViewAdapter with this code
 */
public class MenuListView extends ListView {
	MenuListViewAdapter adapter;

	public MenuListView(Context context) {
		super(context);
		init();
	}

	public MenuListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public MenuListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		// This is done because the wa that the adapter generates
		// the sub-items of folders leads to some very large items
		// in the list. This would lead to the scroll bar jumping
		// around all over the place (i.e. it would be horribly
		// inaccurate).
		this.setVerticalScrollBarEnabled(false);
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		if (!(adapter instanceof MenuListViewAdapter))
			throw new IllegalArgumentException("You must use a MenuListViewAdapter with the MenuListView" +
					" " + adapter.getClass().toString() + " is not an instance of  MenuListViewAdapter");
		this.adapter = ((MenuListViewAdapter) adapter);

		super.setAdapter(adapter);
	}

	@Override
	public void setOnItemClickListener(final OnItemClickListener listener) {
		super.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				MenuListView.this.onItemClick(i);
				listener.onItemClick(adapterView, view, i, l);
			}
		});
	}

	private void onItemClick(int index) {
		adapter.itemClick(index);
	}
}
