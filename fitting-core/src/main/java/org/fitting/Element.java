package org.fitting;

/**
 * Generic screen element.
 */
public interface Element extends SearchContext {
    /**
     * Get the name of the element.
     *
     * @return The name.
     */
    String getName();

    /**
     * Get the value of the element.
     *
     * @return The value.
     */
    String getValue();

    /**
     * Click the element.
     */
    void click();

    /**
     * Send a sequence of keys to the element.
     *
     * @param characters The keys to send.
     */
    void sendKeys(CharSequence... characters);

    /**
     * Clear the element.
     */
    void clear();

    /**
     * Get the value of an attribute on the element.
     *
     * <p>
     * The implementation can either decide to return <code>null</code> or throw one of the {@link FittingException}-exceptions when there is no attribute with the given name.
     * </p>
     *
     * @param attributeName The name of the attribute.
     *
     * @return The value.
     */
    String getAttributeValue(String attributeName);

    /**
     * Check if the element is currently active (e.g. selected, etc.).
     *
     * @return <code>true</code> if the element is active or <code>false</code> if not or the element can't be active.
     */
    boolean isActive();

    /**
     * Check if the element is displayed (this also includes being visible, etc.).
     *
     * @return <code>true</code> if the element is displayed.
     */
    boolean isDisplayed();

    /**
     * Get the location of the element within the container.
     *
     * @return The location or <code>null</code> if no location information was available.
     */
    Point getLocation();

    /**
     * Get the size of the element.
     *
     * @return The size or <code>null</code> if no size information was available.
     */
    Dimension getSize();
}
