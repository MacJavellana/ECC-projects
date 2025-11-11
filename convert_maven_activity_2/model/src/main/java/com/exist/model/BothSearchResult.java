package com.exist.model;

public class BothSearchResult {
    private SearchResult keyResults;
    private SearchResult valueResults;
    
    public BothSearchResult() {}
    
    public BothSearchResult(SearchResult keyResults, SearchResult valueResults) {
        this.keyResults = keyResults;
        this.valueResults = valueResults;
    }
    

    public SearchResult getKeyResults() { 
        return keyResults; 
    }
    public void setKeyResults(SearchResult keyResults) { 
        this.keyResults = keyResults; 
    }
    
    public SearchResult getValueResults() { 
        return valueResults; 
    }
    public void setValueResults(SearchResult valueResults) { 
        this.valueResults = valueResults; 
    }
}