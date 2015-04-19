package com.jcw.ListViewMenu;

/**
 * Created by Jackson on 4/19/2015.
 *
 * This is the calss that is used to store the
 * information that is actually displayed in the
 * menu.
 */
public class MenuListItem {
	protected String text;
	protected String subheader;

	protected MenuItemClickListener listener;

	protected final boolean isFolder = false;
	protected boolean enabled = true;
	protected boolean hidden = false;

	public MenuListItem(String text, MenuItemClickListener listener) {
		this.text = text;
		this.listener = listener;
	}

	public MenuListItem(String text, String subheader, MenuItemClickListener listener) {
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
