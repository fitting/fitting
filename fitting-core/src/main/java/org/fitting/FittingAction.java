package org.fitting;

import java.util.List;

public interface FittingAction {
    Element getElement(SearchContext searchContext, By byClause) throws NoSuchElementException;

    Element getElement(SearchContext searchContext, By byClause, NoSuchElementCallback noSuchElementCallback);

    List<Element> getElements(SearchContext searchContext, By byClause);

    void waitXSeconds(int duration);

    void waitForElement(SearchContext searchContext, By byClause, int timeout) throws NoSuchElementException;

    void waitForElementWithContent(SearchContext searchContext, By byClause, String content, int timeout) throws NoSuchElementException;

    String getAttributeValue(SearchContext searchContext, By byClause, String attributeName) throws NoSuchElementException;

    boolean elementAttributeValueContains(SearchContext searchContext, By byClause, String attributeName, String text) throws NoSuchElementException;

    String getTextValue(SearchContext searchContext, By byClause) throws NoSuchElementException;

    boolean elementTextValueContains(SearchContext searchContext, By byClause, String text) throws NoSuchElementException;

    void clickElement(SearchContext searchContext, By byClause) throws NoSuchElementException;

    void refresh(ElementContainer elementContainer);

    boolean isContainerPresent(SearchContext searchContext, By byClause);

    ElementContainer getContainer(SearchContext searchContext, By byClause) throws NoSuchElementException;

    ElementContainer getContainer(SearchContext searchContext, By byClause, NoSuchElementCallback noSuchElementCallback);

    boolean isModalContainerPresent();

    String getModalContainerText();

    void acceptModalContainer();

    void dismissModalContainer();

    ElementContainer getModalContainer();

    void isElementValueSelectable(SearchContext searchContext, By byClause, String value) throws NoSuchElementException;

    void selectElementValue(SearchContext searchContext, By byClause, String value) throws NoSuchElementException;

    void selectElementValue(SearchContext searchContext, By byClause, String value, NoSuchElementCallback noSuchElementCallback);

    void isElementCheckbox(SearchContext searchContext, By byClause) throws NoSuchElementException;

    boolean isElementValueSelected(SearchContext searchContext, By byClause, String value) throws NoSuchElementException;

    boolean isElementValueSelected(SearchContext searchContext, By byClause, String value, NoSuchElementCallback noSuchElementCallback);

    boolean isElementValueSettable(SearchContext searchContext, By byClause) throws NoSuchElementException;

    void setValueForElement(SearchContext searchContext, By byClause, String value);

    boolean isElementPresent(SearchContext searchContext, By byClause);

    boolean isElementDisplayed(SearchContext searchContext, By byClause);
}
