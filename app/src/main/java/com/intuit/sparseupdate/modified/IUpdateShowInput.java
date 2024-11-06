package com.intuit.sparseupdate.modified;

public interface IUpdateShowInput {

    String getId();

    void setId(String id);

    public Integer getReleaseYear();

    public void setReleaseYear(Integer releaseYear);

    public String getTitle();

    public void setTitle(String title);

    // Checkers for presence of data fields in customer input
    public Boolean isIdSet();
    public Boolean isTitleSet();
    public Boolean isReleaseYearSet() ;

}
