package org.fitting;

import java.util.List;

public interface By {
    public abstract List<Element> findElements(SearchContext context);

    public abstract Element findElement(SearchContext context);
}
