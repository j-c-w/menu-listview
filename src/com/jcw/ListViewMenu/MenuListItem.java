package com.jcw.ListViewMenu;

/**
 * Created by Jackson on 4/19/2015.
 *
 * This is the calss that is used to store the
 * information that is actually displayed in the
 * menu.
 */
public class MenuListItem {
	private String text = "";
	private String subheader = "";

	private int indent = 0;

	protected MenuItemClickListener listener = null;

	public boolean enabled = true;
	public boolean clickable = true;

	// This needs to be set if the content of the item is changed.
	// it signals to the list view that the item needs to be redrawn.
	private boolean invalidated = true;

	public MenuListItem(String text, MenuItemClickListener listener) {
		this.text = text;
		this.listener = listener;
	}

	public MenuListItem(String text, String subheader, MenuItemClickListener listener) {
		this.text = text;
		this.subheader = subheader;
		this.listener = listener;
	}

	public MenuListItem(String text) {
		this.text = text;
	}

	public MenuListItem(String text, String subheader) {
		this.text = text;
		this.subheader = subheader;
	}

	public void setSubheader(String subheader) {
		this.subheader = subheader;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void invalidate() {
		this.invalidated = true;
	}

	public boolean isInvalidated() {
		return invalidated;
	}

	public void setMenuItemClickListener(MenuItemClickListener listener) {
		this.listener = listener;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setClickable(boolean clickable) {
		this.clickable = clickable;
	}

	public boolean hasSubheader() {
		return !subheader.equals("");
	}

	public String getText() {
		return text;
	}

	public String getSubheader() {
		return subheader;
	}

	public int getIndentFactor() {
		return indent;
	}

	public int getIndent() {
		return indent;
	}

	public void setIndent(int indent) {
		this.indent = indent;
	}
}
