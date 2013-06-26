package org.fitting;

public interface ByProvider {
    By getBy(String byTag, String query);

    String[] getAvailableTags();
}
