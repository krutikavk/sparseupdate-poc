package com.intuit.sparseupdate.generated.types;

import java.util.BitSet;

public class UpdateShowInput {
  private String id;
  private String title;
  private Integer releaseYear;

  private final transient BitSet fieldsPresent = new BitSet();

  private void setField(Field field) {
    fieldsPresent.set(field.getOrdinal());
  }

  public boolean isSet(Field field) {
    return fieldsPresent.get(field.getOrdinal());
  }

  public UpdateShowInput() {
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
    setField(Field.ID);
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
    setField(Field.TITLE);
  }

  public Integer getReleaseYear() {
    return releaseYear;
  }

  public void setReleaseYear(Integer releaseYear) {
    this.releaseYear = releaseYear;
    setField(Field.RELEASE_YEAR);
  }

  @Override
  public String toString() {
    return "UpdateShowInput{" + "id='" + id + "'," +"title='" + title + "'," +"releaseYear='" + releaseYear + "'" +"}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateShowInput that = (UpdateShowInput) o;
        return java.util.Objects.equals(id, that.id) &&
                            java.util.Objects.equals(title, that.title) &&
                            java.util.Objects.equals(releaseYear, that.releaseYear);
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(id, title, releaseYear);
  }

  public static com.intuit.sparseupdate.generated.types.UpdateShowInput.Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {
    private String id;

    private String title;

    private Integer releaseYear;

    private final transient BitSet fieldsPresent = new BitSet();

    private void setField(Field field) {
      fieldsPresent.set(field.getOrdinal());
    }

    private boolean isSet(Field field) {
      return fieldsPresent.get(field.getOrdinal());
    }

    public UpdateShowInput build() {
      com.intuit.sparseupdate.generated.types.UpdateShowInput result = new com.intuit.sparseupdate.generated.types.UpdateShowInput();
      result.id = this.id;
      result.title = this.title;
      result.releaseYear = this.releaseYear;
      for (Field field: Field.values()) {
        if (this.isSet(field)) {
          result.setField(field);
        }
      }
      return result;
    }

    public com.intuit.sparseupdate.generated.types.UpdateShowInput.Builder id(String id) {
      this.id = id;
      setField(Field.ID);
      return this;
    }

    public com.intuit.sparseupdate.generated.types.UpdateShowInput.Builder title(String title) {
      this.title = title;
      setField(Field.TITLE);
      return this;
    }

    public com.intuit.sparseupdate.generated.types.UpdateShowInput.Builder releaseYear(
        Integer releaseYear) {
      this.releaseYear = releaseYear;
      setField(Field.RELEASE_YEAR);
      return this;
    }
  }

  public enum Field {
    ID(0),
    TITLE(1),
    RELEASE_YEAR(2);

    int ordinal = -1;

    Field(int ordinal) {
      this.ordinal = ordinal;
    }

    public int getOrdinal() {
      return ordinal;
    }
  }
}
