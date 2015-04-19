package com.jcw.ListViewMenu;

/**
 * Created by Jackson on 4/19/2015.
 *
 * This is triggered when there is an
 * item click in the menu list.
 */
public interface MenuItemClickListener {
	public void onClick(String text, String subheader, boolean isFolder);
}
