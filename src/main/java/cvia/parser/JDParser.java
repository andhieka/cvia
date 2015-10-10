package cvia.parser;

import cvia.model.JDParseResult;

/**
 * Created by Michael Limantara on 9/23/2015.
 */
public interface JDParser {
    public JDParseResult parse(String jobDescription);
}
