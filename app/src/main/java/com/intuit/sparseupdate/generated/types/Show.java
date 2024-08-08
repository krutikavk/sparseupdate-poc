package com.intuit.sparseupdate.generated.types;

import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;

public class Show {
  private String id;

  private String title = "test";

  private Integer releaseYear;

  public Show() {
  }

  public Show(String id, String title, Integer releaseYear) {
    this.id = id;
    this.title = title;
    this.releaseYear = releaseYear;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Integer getReleaseYear() {
    return releaseYear;
  }

  public void setReleaseYear(Integer releaseYear) {
    this.releaseYear = releaseYear;
  }

  @Override
  public String toString() {
    return "Show{" + "id='" + id + "'," +"title='" + title + "'," +"releaseYear='" + releaseYear + "'" +"}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Show that = (Show) o;
    return
            java.util.Objects.equals(id, that.id) &&
                    java.util.Objects.equals(title, that.title) &&
                    java.util.Objects.equals(releaseYear, that.releaseYear) ;
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(id, title, releaseYear);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {
    private String id;

    private String title;

    private Integer releaseYear;

    public com.intuit.sparseupdate.generated.types.Show build() {
      com.intuit.sparseupdate.generated.types.Show result = new com.intuit.sparseupdate.generated.types.Show();
      result.id = this.id;
      result.title = this.title;
      result.releaseYear = this.releaseYear;
      return result;
    }

    public Builder id(String id) {
      this.id = id;
      return this;
    }

    public Builder title(String title) {
      this.title = title;
      return this;
    }

    public Builder releaseYear(Integer releaseYear) {
      this.releaseYear = releaseYear;
      return this;
    }
  }
}

