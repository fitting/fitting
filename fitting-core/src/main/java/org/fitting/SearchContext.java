package org.fitting;

import java.util.List;

public interface SearchContext {
    List<Element> findElementsBy(By byClause);

    Element findElementBy(By byClause);
}
