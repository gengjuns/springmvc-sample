package com.saas.dynamic.runtime.search;

import org.apache.lucene.analysis.KeywordTokenizer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.ReusableAnalyzerBase;
import org.apache.lucene.util.Version;

import java.io.Reader;

/**
 * @author 
 * @since 07/03/2013 3:28 PM
 */
public class KeyWordLowerCaseAnalyzer extends ReusableAnalyzerBase {

    private final Version matchVersion;


    public KeyWordLowerCaseAnalyzer(Version matchVersion) {
        this.matchVersion = matchVersion;
    }


    @Override
    protected TokenStreamComponents createComponents(String fieldName, Reader reader) {
        final KeywordTokenizer source = new KeywordTokenizer(reader);
        return new TokenStreamComponents(source, new LowerCaseFilter(matchVersion, source));
    }
}
