package com.jcw.ListViewMenu;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jackson Woodruff on 4/16/2015.
 *
 *
 * This is a list view that is designed to have menu items added
 * to it.
 */
public class MenuListViewAdapter implements ListAdapter {
	public int ENABLED_COLOR = Color.GRAY;
	public int DISABLED_COLOR = Color.LTGRAY;
	public int MAIN_TEXT_COLOR = Color.WHITE;
	public int SUB_TEXT_COLOR = Color.WHITE;

	public int LINE_SEPARATOR_COLOR = Color.BLACK;

	public int MAIN_TEXT_SIZE = 21;
	public int SUB_TEXT_SIZE = 12;


	Context context;

	List<MenuListItem> items;

	DataSetObserver dataSetObserver = null;

	public MenuListViewAdapter(Context context) {
		this.context = context;

		items = new ArrayList<MenuListItem>();
	}

	@Override
	public boolean areAllItemsEnabled() {
		for (MenuListItem item : items) {
			if (!item.enabled || item.hidden) {
				return false;
			}
		}
		return true;
	}

	public void itemClick(int index) {
		MenuListItem item = items.get(index);

		MenuItemClickListener thisListener = item.listener;
		if (thisListener != null) {
			thisListener.onClick(item.text, item.subheader, item.isFolder);
		}

		if (item.isFolder) {
			MenuListFolder folder = (MenuListFolder) item;
			folder.isOpen = !folder.isOpen;
		}
	}

	@Override
	public boolean isEnabled(int i) {
		return items.get(i).enabled && !items.get(i).hidden;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver dataSetObserver) {
		this.dataSetObserver = dataSetObserver;
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
		dataSetObserver = null;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int i) {
		return items.get(i);
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		MenuListItem data = items.get(i);

		return getView(data);
	}

	protected View getView(MenuListItem data) {
		if (data.hidden) {
			return null;
		}

		LinearLayout container = new LinearLayout(context);

		TextView mainText = new TextView(context);
		TextView subtext = new TextView(context);

		container.setOrientation(LinearLayout.VERTICAL);
		container.setPadding(15, 15, 15, 15);
		if (data.enabled) {
			container.setBackgroundColor(ENABLED_COLOR);
		} else {
			container.setBackgroundColor(DISABLED_COLOR);
		}

		mainText.setTextColor(MAIN_TEXT_COLOR);
		mainText.setText(data.text);
		mainText.setTextSize(MAIN_TEXT_SIZE);

		subtext.setTextColor(SUB_TEXT_COLOR);
		subtext.setText(data.subheader);
		subtext.setTextSize(SUB_TEXT_SIZE);

		container.addView(mainText);
		container.addView(subtext);

		if (data.isFolder && (((MenuListFolder)data).isOpen)) {
			// we need to display all the sub items.
			LinearLayout mainContainer = new LinearLayout(context);
			mainContainer.setOrientation(LinearLayout.VERTICAL);

			mainContainer.addView(container);
			mainContainer.addView(getListSeparator());
			for (MenuListItem item : ((MenuListFolder)data).subitems) {
				mainContainer.addView(getView(item));
			}
		}

		LinearLayout mainContainer = new LinearLayout(context);
		mainContainer.setOrientation(LinearLayout.VERTICAL);

		mainContainer.addView(container);
		mainContainer.addView(getListSeparator());

		return mainContainer;
	}

	/*
	 * This returns a separator that is basically the same as a separator
	 * on an Android list view.
	 */
	public View getListSeparator() {
		View separator = new View(context);
		separator.setLayoutParams(new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, 3
		));

		separator.setBackgroundColor(LINE_SEPARATOR_COLOR);
		return separator;
	}

	public void addItem(MenuListItem item) {
		this.items.add(item);
		if (dataSetObserver != null) {
			dataSetObserver.onChanged();
		}
	}

	@Override
	public int getItemViewType(int i) {
		return 0;
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public boolean isEmpty() {
		return items.isEmpty();
	}
}
