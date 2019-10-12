package com.mymoonapplab.oxfirat.model;

public class model_menu {

    public String menuName;
    public boolean hasChildren, isGroup;

    public model_menu(String menuName, boolean isGroup, boolean hasChildren) {

        this.menuName = menuName;
        this.isGroup = isGroup;
        this.hasChildren = hasChildren;
    }

}
