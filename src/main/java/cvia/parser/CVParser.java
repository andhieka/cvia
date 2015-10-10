package cvia.parser;

import cvia.model.CVParseResult;

/**
 * Created by Michael Limantara on 9/23/2015.
 */
public interface CVParser {
    public CVParseResult parse(String cv);
}
