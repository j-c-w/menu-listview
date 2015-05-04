package com.jcw.ListViewMenu;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.Spanned;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Jackson Woodruff on 4/16/2015.
 *
 *
 * This is a list view that is designed to have menu items added
 * to it.
 */
public class MenuListViewAdapter implements ListAdapter {
	public static final int BOLD_FALG = Typeface.BOLD;
	public static final int ITALIC_FLAG = Typeface.ITALIC;

	public int MAIN_TEXT_STYLE_FLAGS = 0;
	public int SUB_TEXT_STYLE_FLAGS = 0;

	// To set the enabled color, you need to change the XML files... Sorry!
	public int DISABLED_COLOR = Color.LTGRAY;
	public int MAIN_TEXT_COLOR = Color.BLACK;
	public int SUB_TEXT_COLOR = Color.BLACK;

	public int LINE_SEPARATOR_COLOR = Color.WHITE;
	public int BACKGROUND_COLOR = Color.WHITE;

	public int MAIN_TEXT_SIZE = 21;
	public int SUB_TEXT_SIZE = 12;

	public int TEXT_PADDING = 12;


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
			if (!item.enabled) {
				return false;
			}
		}
		return true;
	}

	public void itemClick(int index) {
		MenuListItem item = items.get(index);

		MenuItemClickListener thisListener = item.listener;
		if (thisListener != null) {
			thisListener.onClick(item.getText(), item.getSubheader(), item instanceof MenuListFolder);
		}

		if (item instanceof MenuListFolder) {
			MenuListFolder folder = (MenuListFolder) item;
			folder.isOpen = !folder.isOpen;

			if (folder.isOpen) {
				items.addAll(index + 1, folder.subitems);
			} else {
				removeFolder(index);
			}

			if (dataSetObserver != null)
				dataSetObserver.onInvalidated();
		}
	}

	public void addAll(Collection<MenuListItem> items) {
		items.addAll(items);
	}

	public boolean remove(MenuListItem item) {
		return items.remove(item);
	}

	/*
	 * This recursively remvoes all sub-folders
	 * along with the main folder from the list.
	 */
	private void removeFolder(int index) {
		MenuListFolder folder = (MenuListFolder) items.get(index);
		for (int i = 0; i < folder.subitems.size(); i ++) {
			MenuListItem item = folder.subitems.get(i);
			if (item instanceof MenuListFolder) {
				removeFolder(index + i + 1);
			}
		}
		items.removeAll(folder.subitems);
	}

	@Override
	public boolean isEnabled(int i) {
		return items.get(i).enabled;
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

		return getView(data, i);
	}

	public void invalidate() {
		if (dataSetObserver != null) {
			dataSetObserver.onInvalidated();
		}
	}

	protected View getView(MenuListItem data, final int index) {
		final LinearLayout container = new LinearLayout(context);

		TextView mainText = new TextView(context);
		TextView subtext = new TextView(context);

		container.setOrientation(LinearLayout.VERTICAL);
		container.setPadding(15 * data.getIndentFactor() + TEXT_PADDING, 15, 15, 15);
		if (data.enabled) {
			container.setBackgroundResource(R.drawable.enabled_background);
		} else {
			container.setBackgroundColor(DISABLED_COLOR);
		}

		container.setClickable(true);

		styleTextView(MAIN_TEXT_STYLE_FLAGS, mainText);
		mainText.setTextColor(MAIN_TEXT_COLOR);
		mainText.setText(data.getText());
		mainText.setTextSize(MAIN_TEXT_SIZE);

		styleTextView(SUB_TEXT_STYLE_FLAGS, subtext);
		subtext.setTextColor(SUB_TEXT_COLOR);
		subtext.setText(data.getSubheader());
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
	 * this parses the style flags and applies them to the text view
	 */
	private void styleTextView(int flags, TextView text) {
		text.setTypeface(null, flags);

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
