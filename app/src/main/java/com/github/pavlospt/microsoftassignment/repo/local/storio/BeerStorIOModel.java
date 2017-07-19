package com.github.pavlospt.microsoftassignment.repo.local.storio;

import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

@StorIOSQLiteType(table = BeersTable.TABLE_NAME)
public class BeerStorIOModel {
  @StorIOSQLiteColumn(name = BeersTable.COLUMN_ID, key = true)
  Long _id;

  @StorIOSQLiteColumn(name = BeersTable.COLUMN_REMOTE_ID)
  private int id;

  @StorIOSQLiteColumn(name = BeersTable.COLUMN_NAME)
  private String name;

  @StorIOSQLiteColumn(name = BeersTable.COLUMN_TAGLINE)
  private String tagline;

  @StorIOSQLiteColumn(name = BeersTable.COLUMN_BREWER_TIPS)
  private String brewerTips;

  @StorIOSQLiteColumn(name = BeersTable.COLUMN_DESCRIPTION)
  private String description;

  @StorIOSQLiteColumn(name = BeersTable.COLUMN_FIRST_BREWED)
  private String firstBrewed;

  @StorIOSQLiteColumn(name = BeersTable.COLUMN_IMAGE_URL)
  private String imageUrl;

  @StorIOSQLiteColumn(name = BeersTable.COLUMN_CONTRIBUTED_BY)
  private String contributedBy;

  public BeerStorIOModel() {
  }

  public BeerStorIOModel(int id, String name, String tagline, String brewerTips, String description,
      String firstBrewed, String imageUrl, String contributedBy) {
    this.id = id;
    this.name = name;
    this.tagline = tagline;
    this.brewerTips = brewerTips;
    this.description = description;
    this.firstBrewed = firstBrewed;
    this.imageUrl = imageUrl;
    this.contributedBy = contributedBy;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTagline() {
    return tagline;
  }

  public void setTagline(String tagline) {
    this.tagline = tagline;
  }

  public String getBrewerTips() {
    return brewerTips;
  }

  public void setBrewerTips(String brewerTips) {
    this.brewerTips = brewerTips;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getFirstBrewed() {
    return firstBrewed;
  }

  public void setFirstBrewed(String firstBrewed) {
    this.firstBrewed = firstBrewed;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getContributedBy() {
    return contributedBy;
  }

  public void setContributedBy(String contributedBy) {
    this.contributedBy = contributedBy;
  }
}
