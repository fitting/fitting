package org.fitting;

public interface Element extends SearchContext {
    String getName();

    String getValue();

    void click();

    void sendKeys(CharSequence... characters);

    void clear();

    String getAttributeValue(String attributeName);

    boolean isActive();

    boolean isDisplayed();

    Point getLocation();

    Dimension getSize();
}
