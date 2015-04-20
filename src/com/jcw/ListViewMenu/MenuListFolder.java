package com.jcw.ListViewMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jackson Woodruff on 4/19/2015.
 *
 * This is a class used to contain sub items.
 * These sub items can be folders too, or they can
 * just be plain items.
 */
public class MenuListFolder extends MenuListItem {
	// This is protected because a call needs to be made to the
	// dataset observer in the adapter in the adapter for changes
	// to this to have any effect.
	protected boolean isOpen = false;
	protected List<MenuListItem> subitems = new ArrayList<MenuListItem>();

	public MenuListFolder(String text, List<MenuListItem> subitems) {
		super(text);
		this.subitems = subitems;
		initIndents();
	}

	public MenuListFolder(String text, MenuItemClickListener listener, List<MenuListItem> subitems) {
		super(text, listener);
		this.subitems = subitems;
		initIndents();
	}

	public MenuListFolder(String text, String subtext, List<MenuListItem> subitems) {
		super(text, subtext, null);
		this.subitems = subitems;
		initIndents();
	}

	public MenuListFolder(String text, String subtext, List<MenuListItem> subitems, MenuItemClickListener listener) {
		super(text, subtext, listener);
		this.subitems = subitems;
		initIndents();
	}

	private void initIndents() {
		for (int i = 0; i < subitems.size(); i ++) {
			subitems.get(i).setIndent(this.getIndent() + 1);
		}
	}

	@Override
	public void setIndent(int indent) {
		super.setIndent(indent);
		initIndents();
	}

	public void addItem(MenuListItem item) {
		item.setIndent(this.getIndent() + 1);
		this.subitems.add(item);
	}
}
