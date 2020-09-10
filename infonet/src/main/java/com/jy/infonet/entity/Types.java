package com.jy.infonet.entity;

public class Types {
    private Integer typeId;

    private String typeName;

    private String typeCover;

    private Integer deleted;

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    public String getTypeCover() {
        return typeCover;
    }

    public void setTypeCover(String typeCover) {
        this.typeCover = typeCover == null ? null : typeCover.trim();
    }

    @Override
    public String toString() {
        return "Types{" +
                "typeId=" + typeId +
                ", typeName='" + typeName + '\'' +
                ", typeCover='" + typeCover + '\'' +
                '}';
    }
}