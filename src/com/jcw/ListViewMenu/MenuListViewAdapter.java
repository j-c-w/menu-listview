package com.jcw.ListViewMenu;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jackson Woodruff on 4/16/2015.
 *
 *
 * This is a list view that is designed to have menu items added
 * to it.
 */
public class MenuListViewAdapter implements ExpandableListAdapter {
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

	public void itemClick(int index, int child) {
		MenuListItem item = items.get(index);

		if (child != -1) {
			if (item instanceof MenuListFolder) {
				MenuListItem subitem = ((MenuListFolder) item).subitems.get(child);
				MenuItemClickListener listener = subitem.listener;

				if (listener != null) {
					listener.onClick(subitem.text, subitem.subheader, false);
				}
			}
		}

		MenuItemClickListener thisListener = item.listener;
		if (thisListener != null) {
			thisListener.onClick(item.text, item.subheader, item instanceof MenuListFolder);
		}

		if (item instanceof MenuListFolder && child == -1) {
			MenuListFolder folder = (MenuListFolder) item;
			folder.isOpen = !folder.isOpen;
			if (dataSetObserver != null)
				dataSetObserver.onInvalidated();
		}
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
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public int getGroupCount() {
		return items.size();
	}

	@Override
	public int getChildrenCount(int i) {
		MenuListItem item = items.get(i);
		if (item instanceof MenuListFolder) {
			return ((MenuListFolder) item).subitems.size();
		}
		return 0;
	}

	@Override
	public Object getGroup(int i) {
		return items.get(i);
	}

	@Override
	public Object getChild(int i, int childIndex) {
		MenuListItem item = items.get(i);
		if (item instanceof MenuListFolder) {
			return ((MenuListFolder) item).subitems.get(childIndex);
		}

		throw new IndexOutOfBoundsException("Only MenuListFolders may have children, there isa  MenuListItem at the" +
				" index you requested");
	}

	@Override
	public long getGroupId(int i) {
		return i;
	}

	@Override
	public long getChildId(int i, int i2) {
		return i * 1000 + i2;
	}

	public boolean shouldExpandAt(int i) {
		return items.get(i) instanceof MenuListFolder;
	}

	@Override
	public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
		return getView(items.get(i));
	}

	@Override
	public View getChildView(int parentIndex, int childIndex, boolean b, View view, ViewGroup viewGroup) {
		if (!(items.get(parentIndex) instanceof MenuListFolder))
			throw new IllegalArgumentException("Only folders have children!");

		MenuListFolder data = (MenuListFolder) items.get(parentIndex);
		return getView(data.subitems.get(childIndex));
	}

	@Override
	public boolean isChildSelectable(int i, int i2) {
		return true;
	}

	@Override
	public void onGroupExpanded(int i) {

	}

	@Override
	public void onGroupCollapsed(int i) {

	}

	@Override
	public long getCombinedChildId(long l, long l2) {
		return l + l2;
	}

	@Override
	public long getCombinedGroupId(long l) {
		return l;
	}

	protected View getView(MenuListItem data) {
		if (data.hidden) {
			return null;
		}

		LinearLayout container = new LinearLayout(context);

		TextView mainText = new TextView(context);
		TextView subtext = new TextView(context);

		container.setOrientation(LinearLayout.VERTICAL);
		container.setPadding(25, 15, 15, 15);
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
		if (data.hasSubheader()) {
			// To preserve the position of the main text in the center,
			// only add this if it actually has text.
			container.addView(subtext);
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
	public boolean isEmpty() {
		return items.isEmpty();
	}
}
