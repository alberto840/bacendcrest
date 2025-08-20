package com.project.pet_veteriana.dto;

public class SubCategoriaDto {

    private Integer subCategoriaId;
    private String nameSubCategoria;
    private Integer categoryId;

    public SubCategoriaDto() {
    }

    public SubCategoriaDto(Integer subCategoriaId, String nameSubCategoria, Integer categoryId) {
        this.subCategoriaId = subCategoriaId;
        this.nameSubCategoria = nameSubCategoria;
        this.categoryId = categoryId;
    }

    public Integer getSubCategoriaId() {
        return subCategoriaId;
    }

    public void setSubCategoriaId(Integer subCategoriaId) {
        this.subCategoriaId = subCategoriaId;
    }

    public String getNameSubCategoria() {
        return nameSubCategoria;
    }

    public void setNameSubCategoria(String nameSubCategoria) {
        this.nameSubCategoria = nameSubCategoria;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
