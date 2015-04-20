package com.jcw.ListViewMenu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.*;

/**
 * Created by Jackson Woodruff on 4/16/2015.
 *
 * This is an extension of the listview
 * that uses the MenuListViewAdapter to display
 * the menu.
 *
 * Note -- you must use the MenuListViewAdapter with this code
 */
public class MenuListView extends ExpandableListView {
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
		this.setOnChildClickListener(null);
		this.setOnGroupClickListener(null);
		this.setGroupIndicator(null);
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		throw new IllegalArgumentException("You must use a MenuListViewAdapter with the MenuListView" +
				" " + adapter.getClass() + " is not an instance of MenuListViewAdapter");
	}

	@Override
	public void setAdapter(ExpandableListAdapter adapter) {
		if (!(adapter instanceof MenuListViewAdapter))
			throw new IllegalArgumentException("You must use a MenuListViewAdapter with the MenuListView" +
					" " + adapter.getClass().toString() + " is not an instance of  MenuListViewAdapter");
		this.adapter = ((MenuListViewAdapter) adapter);

		super.setAdapter(adapter);
	}

	@Override
	public void setOnChildClickListener(final OnChildClickListener listener) {
		super.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int child, long l) {
				MenuListView.this.onItemClick(i, child);
				if (listener != null)
					listener.onChildClick(expandableListView, view, i, child, l);
				return true;
			}
		});
	}

	@Override
	public void setOnGroupClickListener(OnGroupClickListener listener) {
		super.setOnGroupClickListener(new OnGroupClickListener() {
			@Override
			public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
				return adapter.shouldExpandAt(i);
			}
		});
	}

	private void onItemClick(int index, int child) {
		adapter.itemClick(index, child);
	}
}
