package com.example.coffee;

public class MenuItemData
{
    public String title;
    public int iconResDefault;
    public int iconResSelected;
    public boolean isHeader;

    public MenuItemData(String title, int iconResDefault, int iconResSelected)
    {
        this.title = title;
        this.iconResDefault = iconResDefault;
        this.iconResSelected = iconResSelected;
        this.isHeader = isHeader;
    }
}
