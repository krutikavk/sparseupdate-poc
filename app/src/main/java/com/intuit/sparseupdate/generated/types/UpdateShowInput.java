package com.intuit.sparseupdate.generated.types;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;

public class UpdateShowInput {
  private String id;

  private Integer releaseYear;

  private boolean isReleaseYearSet = false;

  private boolean isTitleSet = false;

  private String title;

  public UpdateShowInput() {

  }

  public UpdateShowInput(String id, Integer releaseYear, String title) {
    System.out.println("UpdateShowInput 1 hit");
    this.id = id;
    this.releaseYear = releaseYear;
    this.isReleaseYearSet = true;
    this.title = title;
    this.isTitleSet = true;
  }

  public UpdateShowInput(String id, Integer releaseYear) {
    System.out.println("UpdateShowInput Constructor 2 hit");
    this.id = id;
    this.releaseYear = releaseYear;
    this.isReleaseYearSet = true;
    this.title = "test";
    this.isTitleSet = true;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Integer getReleaseYear() {
    return releaseYear;
  }

  public void setReleaseYear(Integer releaseYear) {
    System.out.println("setTitle called");
    this.releaseYear = releaseYear;
    this.isReleaseYearSet = true;
  }

  @JsonIgnore
  public boolean isReleaseYearSet() {
    return isReleaseYearSet;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    System.out.println("setTitle called");
    this.title = title;
    this.isTitleSet = true;
  }

  @JsonIgnore
  public boolean isTitleSet() {
    return isTitleSet;
  }

  @Override
  public String toString() {
    return "UpdateShowInput{" + "id='" + id + "'," +"releaseYear='" + releaseYear + "'," +"title='" + title + "'" +"}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UpdateShowInput that = (UpdateShowInput) o;
    return
            java.util.Objects.equals(id, that.id) &&
                    java.util.Objects.equals(releaseYear, that.releaseYear) &&
                    java.util.Objects.equals(title, that.title) ;
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(id, releaseYear, isReleaseYearSet, title, isTitleSet);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {
    private String id;

    private Integer releaseYear;

    private boolean isReleaseYearSet = false;

    private boolean isTitleSet = false;

    private String title = "test";

    public UpdateShowInput build() {
      com.intuit.sparseupdate.generated.types.UpdateShowInput result = new com.intuit.sparseupdate.generated.types.UpdateShowInput();
      result.id = this.id;
      result.releaseYear = this.releaseYear;
      result.isReleaseYearSet = this.isReleaseYearSet;
      result.title = this.title;
      result.isTitleSet = this.isTitleSet;
      return result;
    }

    public Builder id(String id) {
      this.id = id;
      return this;
    }

    public Builder releaseYear(Integer releaseYear) {
      this.releaseYear = releaseYear;
      this.isReleaseYearSet = true;
      return this;
    }

    public Builder isReleaseYearSet(boolean isReleaseYearSet) {
      this.isReleaseYearSet = isReleaseYearSet;
      return this;
    }

    public Builder title(String title) {
      this.title = title;
      this.isTitleSet = true;
      return this;
    }

    public Builder isTitleSet(boolean isTitleSet) {
      this.isTitleSet = isTitleSet;
      return this;
    }
  }
}

