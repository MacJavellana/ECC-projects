package com.exist.model;

public class SearchResult {
    private int occurrences;
    private String locations;
    
    // Constructors
    public SearchResult() {}
    
    public SearchResult(int occurrences, String locations) {
        this.occurrences = occurrences;
        this.locations = locations;
    }
    
    // Getters and Setters
    public int getOccurrences() { 
        return occurrences; 
    }
    public void setOccurrences(int occurrences) { 
        this.occurrences = occurrences; 
    }
    
    public String getLocations() { 
        return locations; 
    }
    public void setLocations(String locations) { 
        this.locations = locations; 
    }
    
    @Override
    public String toString() {
        return occurrences + " occurrence(s) at " + locations;
    }
}