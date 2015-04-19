package com.jcw.ListViewMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jackson on 4/19/2015.
 *
 * This is a class used to contain sub items.
 * These sub items can be folders too, or they can
 * just be plain items.
 */
public class MenuListFolder extends MenuListItem {
	protected final boolean isFolder = true;
	protected List<MenuListItem> subitems = new ArrayList<MenuListItem>();
	protected boolean isOpen = false;

	public MenuListFolder(String text, List<MenuListItem> subitems) {
		super(text, null);
		this.subitems = subitems;
	}

	public MenuListFolder(String text, MenuItemClickListener listener, List<MenuListItem> subitems) {
		super(text, listener);
		this.subitems = subitems;
	}

	public MenuListFolder(String text, String subtext, List<MenuListItem> subitems) {
		super(text, subtext, null);
		this.subitems = subitems;
	}

	public MenuListFolder(String text, String subtext, List<MenuListItem> subitems, MenuItemClickListener listener) {
		super(text, subtext, listener);
		this.subitems = subitems;
	}

	public void addItem(MenuListItem item) {
		this.subitems.add(item);
	}
}
