package org.fitting.documentation;

/**
 * Renderer for fixture documentation.
 */
public interface FixtureDocumentationRenderer {
    /**
     * Render the fixture documentation.
     *
     * @param documentation The documentation.
     *
     * @return The rendered result.
     *
     * @throws FittingRenderingException When rendering failed.
     */
    String render(FixtureDocumentation documentation) throws FittingRenderingException;
}
