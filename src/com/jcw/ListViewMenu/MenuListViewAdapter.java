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

	List<Item> items;

	DataSetObserver dataSetObserver = null;

	public MenuListViewAdapter(Context context) {
		this.context = context;

		items = new ArrayList<Item>();
	}

	@Override
	public boolean areAllItemsEnabled() {
		for (Item item : items) {
			if (!item.enabled || item.hidden) {
				return false;
			}
		}
		return true;
	}

	public void itemClick(int index) {
		Item item = items.get(index);

		ItemClickListener thisListener = item.listener;
		if (thisListener != null) {
			thisListener.onClick(item.text, item.subheader, item.isFolder);
		}

		if (item.isFolder) {
			Folder folder = (Folder) item;
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
		Item data = items.get(i);

		return getView(data);
	}

	protected View getView(Item data) {
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
		container.addView(getListSeparator());

		if (data.isFolder && (((Folder)data).isOpen)) {
			// we need to display all the sub items.
			LinearLayout mainContainer = new LinearLayout(context);
			mainContainer.setOrientation(LinearLayout.VERTICAL);

			mainContainer.addView(container);
			for (Item item : ((Folder)data).subitems) {
				mainContainer.addView(getView(item));
			}
	}

		return container;
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

	public void addItem(Item item) {
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
		return 0;
	}

	@Override
	public boolean isEmpty() {
		return items.isEmpty();
	}

	/*
	 * This is used to store information about each item
	 */
	public class Item {
		protected String text;
		protected String subheader;

		protected ItemClickListener listener;

		protected final boolean isFolder = false;
		protected boolean enabled = true;
		protected boolean hidden = false;

		public Item(String text, ItemClickListener listener) {
			this.text = text;
			this.listener = listener;
		}

		public Item(String text, String subheader, ItemClickListener listener) {
			this.text = text;
			this.subheader = subheader;
			this.listener = listener;
		}

		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}

		public void setHidden(boolean hidden) {
			this.hidden = hidden;
		}
	}

	protected class Folder extends Item {
		protected final boolean isFolder = true;
		protected List<Item> subitems = new ArrayList<Item>();
		protected boolean isOpen = false;

		public Folder(String text, List<Item> subitems) {
			super(text, null);
			this.subitems = subitems;
		}

		public Folder(String text, ItemClickListener listener, List<Item> subitems) {
			super(text, listener);
			this.subitems = subitems;
		}

		public Folder(String text, String subtext, List<Item> subitems) {
			super(text, subtext, null);
			this.subitems = subitems;
		}

		public Folder(String text, String subtext, List<Item> subitems, ItemClickListener listener) {
			super(text, subtext, listener);
			this.subitems = subitems;
		}

		public void addItem(Item item) {
			this.subitems.add(item);
		}
	}

	public interface ItemClickListener {
		public void onClick(String text, String subheader, boolean isFolder);
	}
}
