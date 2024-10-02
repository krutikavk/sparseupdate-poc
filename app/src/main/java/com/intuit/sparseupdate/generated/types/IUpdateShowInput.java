package com.intuit.sparseupdate.generated.types;

public interface IUpdateShowInput {

    String getId();

    void setId(String id);

    public Integer getReleaseYear();

    public void setReleaseYear(Integer releaseYear);

    public String getTitle();

    public void setTitle(String title);

    // Presence Fields are static and final in interfaces--cannot update them in UpdateShowInput setters
    // So, these fields should be added to UpdateShowInput instead
    public Boolean isReleaseYearSet = false;
    public Boolean isTitleSet = false;

    // Boolean variables are final and static: NO use for getters/setters for these fields. Use reflection to set these fields
    public Boolean isReleaseYearSet();
    public Boolean isTitleSet();

    public void setIsReleaseYearSet();
    public void setIsTitleSet();
}
