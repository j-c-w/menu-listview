This is a library for Android that makes use of a menu in a list view much easier.

It lets you add items to the list and link them up with listeners in ways that are easier to understand that having to deal with indexes of each individual element. Additionally, you can add sub-menus, where clicking on a menu item expands the list.

See the MenuListViewExampleActivity for a quick example.

Putting this in your project
---------
To put this project into your own, there are two steps that you need to take.

First, copy all classes from the package com.jcw.ListViewMenu into a package in your project,
and change the package names in each file to match their new location. Of these copied classes,
delete the MenuListViewExampleActivity, as it is not useful during the use of this library.

If you do not already have a colors.xml file, simply copy and paste the res/values/mlv_colors.xml to
the res/values/mlv_colors.xml in your project.

After this, you will need to copy the res/drawable folder into your project. Again, if you already have such a
folder, the contents can just be pasted in. All these files have been prefixed with 'mlv' to try and keep naming
conflicts to a minimum.

Finally, you may need to go back to the java files you copied and fix the imports by
replacing the package com.jcw.ListViewMenu with the new package where the files are located.

-----------

Documentation
--------------
A MenuListView is just an extension of a ListView that is better suited to displaying your menu.

It uses a MenuListViewAdapter to display the contents of a page.

To use a MenuListViewAdapter, you should first create a list of menu items that will be added to this adapter (in the constructor)
This is done with a List<MenuListItem>. Fill this up with new items. Creating a new item is modeled after
the dialog button creation process. So, for a simple menu with a 'Home' button and a 'Quit' button, you would do the following

	List<MenuListItem> items = new ArrayList<MenuListItem>(2);
	items.add(new MenuListItem("Home", new MenuItemClickListener() {
		@Override
		public void onClick(String text, String subheader, boolean isFolder) {
			// go home
		}
	});

	items.add(new MenuListItem("Quit", new MenuItemClickListener() {
		@Override
		public void onClick(String text, String subheader, boolean isFolder) {
			// quit
		}
	});

To add that list to a view, you would simply use an intermediate MenuListViewAdapter as follows

	MenuListView listView = new MenuListView(context);
	MenuListViewAdapter adapter = new MenuListViewAdapter(context);

	adapter.addViews(items);
	listView.setAdapter(adapter);

And that does the trick!

**Customization**

To change the colors of the list view, just edit the mlv_colors.xml file to your liking. This contains the
default background colors for the unclicked state (defaultItemBackground), the clicked state (defaultClicked),
the background of the list (which will show if the total height of the list is ever greater than that of items),
defaultListBackground, the disabled color (disabledColor) and the color of the divider between list items (defaultDividerColor).

To change the color of the text in the list view, you should use the adapter. Setting this color is as easy as calling: adapter.MAIN_TEXT_COLOR = Color.parseColor("your color");.
The same can be done for the sub text, with adapter.SUB_TEXT_COLOR = ...

Additionally, style flags (like italic, bold etc.) can be set on both the main text and the sub text using adapter.MAIN_TEXT_FLAGS = ..., in the same way that these flags are set on
TextViews.


------------------------

Method by Method
-------------
**MenuListItem**

These are the basic building blocks of the menu. They can have both a text and a sub text (which appears as a subnote in a menu),
either via the constructor or via the methods:

	setText(String text);

and

	setSubheader(String sub);

The MenuListItem also requires a click listener in the constructor, which fires when the item is clicked. This listener passes back three
arguments: String text, String subheader, boolean isFolder. 'text' contains the text that is displayed by the item, 'subheader' contains the subheader
in the item, and isFolder tells you whether the items that was clicked is actually a folder.

The other two methods that are worth considering for the MenuListItem are setEnabled and setClickable, which work as they normally do (e.g. for buttons).

**MenuListFolder**

This is a class used for creating items that have sub items within. The important fields for this class are the text for the folder, and the subitems.

These can both be set in either the constructor or via various methods. It is also worth remembering the MenuListFolder is a subclass of MenuListItem,
and can thus have both a text and a sub text.

The folding action is all dealt with internally, and thus, the items passed to the Folder will be hidden and shown without any further effort.

The only method of note that is added in this class is the addItem(MenuListItem item), which adds an item to the folder.

**MenuListViewAdapter**

The styling for the MenuListViewAdapter is all done through constants, which are all public, and thus can all be set as adapter.CONSTANT = value;


	int MAIN_TEXT_STYLE_FLAGS - This contains the text style flags (such as Bold, Italic) for the style of the main text view

	int SUB_TEXT_STYLE_FLAGS - This contains the text style flags for the style of teh sub text view

	int MAIN_TEXT_COLOR - This is the color of the main text

	int SUB_TEXT_COLOR - This is the color of any sub text

	int MAIN_TEXT_SIZE - This is the size of the main text (in DP), default of 18

	int SUB_TEXT_SIZE - This is the size of the sub text (also in DP), default of 12

Other useful methods of the MneuListViewAdapter are:

	void remove(MenuListItem item);

This removes a single item from the list. DO NOT DO THIS WITH FOLDERS

	void removeFolder(MenuListItem item);

This removes a folder and all of it's sub items. This is safe to use for both folders and individual items.

	void addItem(MenuListItem item);

This adds an item to the list view.

** MenuListView **

Since most of the work is done by the MnuListViewAdapter, this is a smaller and simpler class.

The only method of note here is the setAdapter method. This method requires a MenuListViewAdapter to be passed to it, of you will get an error.


------------------------

** Common Errors **
-------------------
An IllegalArgumentException stating that "You must use a MenuListViewAdapter with a MenuListView" -
Most likely, you passed a normal ListAdapter as an argument. Just use a MenuListViewAdapter instead.
