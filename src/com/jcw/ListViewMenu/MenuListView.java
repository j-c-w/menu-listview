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
		this.setOnItemClickListener(null);
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		if (!(adapter instanceof MenuListViewAdapter))
			throw new IllegalArgumentException("You must use a MenuListViewAdapter with the MenuListView" +
					" " + adapter.getClass().toString() + " is not an instance of  MenuListViewAdapter");
		this.adapter = ((MenuListViewAdapter) adapter);
		this.setBackgroundColor(this.adapter.BACKGROUND_COLOR);

		super.setAdapter(adapter);
	}

	@Override
	public void setOnItemClickListener(final OnItemClickListener listener) {
		super.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				MenuListView.this.onItemClick(i);
				if (listener != null)
					listener.onItemClick(adapterView, view, i, l);
			}
		});
	}

	private void onItemClick(int index) {
		adapter.itemClick(index);
	}
}
