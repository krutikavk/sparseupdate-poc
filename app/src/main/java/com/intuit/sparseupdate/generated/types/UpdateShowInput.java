package com.intuit.sparseupdate.generated.types;

import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Objects;

public class UpdateShowInput{
  private String id;

  private Integer releaseYear;

  private String title;

  public UpdateShowInput() {
  }

  public UpdateShowInput(String id, Integer releaseYear, String title) {
    this.id = id;
    this.releaseYear = releaseYear;
    this.title = title;
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
    this.releaseYear = releaseYear;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String toString() {
    return "UpdateShowInput{id='" + id + "', releaseYear='" + releaseYear + "', title='" + title + "'}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UpdateShowInput that = (UpdateShowInput) o;
    return Objects.equals(id, that.id) &&
            Objects.equals(releaseYear, that.releaseYear) &&
            Objects.equals(title, that.title);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, releaseYear, title);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {
    private String id;

    private Integer releaseYear;

    private String title;

    public UpdateShowInput build() {
      UpdateShowInput result = new UpdateShowInput();
      result.id = this.id;
      result.releaseYear = this.releaseYear;
      result.title = this.title;
      return result;
    }

    public Builder id(String id) {
      this.id = id;
      return this;
    }

    public Builder releaseYear(Integer releaseYear) {
      this.releaseYear = releaseYear;
      return this;
    }

    public Builder title(String title) {
      this.title = title;
      return this;
    }
  }
}
