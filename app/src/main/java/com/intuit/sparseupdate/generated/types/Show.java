package com.intuit.sparseupdate.generated.types;

public class Show {
  private String id;

  private String title;

  private Integer releaseYear;

  private Boolean isIdDefined = false;
  private Boolean isTitleDefined = false;
  private Boolean isReleaseYearDefined = false;

  public Show() {
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
    this.isIdDefined = true;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
    this.isTitleDefined = true;
  }

  public Integer getReleaseYear() {
    return releaseYear;
  }

  public void setReleaseYear(Integer releaseYear) {
    this.releaseYear = releaseYear;
    this.isReleaseYearDefined = true;
  }

  public boolean getIsIdDefined(){
    return isIdDefined;
  }

  public boolean getIsTitleDefined(){
    return isTitleDefined;
  }

  public boolean getIsReleaseYearDefined(){
    return isReleaseYearDefined;
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
        return java.util.Objects.equals(id, that.id) &&
                            java.util.Objects.equals(title, that.title) &&
                            java.util.Objects.equals(releaseYear, that.releaseYear);
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(id, title, releaseYear);
  }

  public static com.intuit.sparseupdate.generated.types.Show.Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {
    private String id;

    private String title;

    private Integer releaseYear;

    private boolean isIdDefined = false;

    private boolean isTitleDefined = false;

    private boolean isReleaseYearDefined = false;

    public Show build() {
      com.intuit.sparseupdate.generated.types.Show result = new com.intuit.sparseupdate.generated.types.Show();
      if(this.isIdDefined) {
        result.setId(this.id);
      }
      if(this.isTitleDefined) {
        result.setTitle(this.title);
      }
      if(this.isReleaseYearDefined) {
        result.setReleaseYear(this.releaseYear);
      }
      return result;
    }

    public com.intuit.sparseupdate.generated.types.Show.Builder id(String id) {
      this.id = id;
      this.isIdDefined = true;
      return this;
    }

    public com.intuit.sparseupdate.generated.types.Show.Builder title(String title) {
      this.title = title;
      this.isTitleDefined = true;
      return this;
    }

    public com.intuit.sparseupdate.generated.types.Show.Builder releaseYear(Integer releaseYear) {
      this.releaseYear = releaseYear;
      this.isReleaseYearDefined = true;
      return this;
    }

    public boolean getIsIdDefined(){
      return isIdDefined;
    }

    public boolean getIsTitleDefined(){
      return isTitleDefined;
    }

    public boolean getIsReleaseYearDefined(){
      return isReleaseYearDefined;
    }
  }
}
