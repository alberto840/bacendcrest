package com.project.pet_veteriana.dto;

public class SubSubCategoriaDto {

    private Integer subSubCategoriaId;
    private String nameSubSubCategoria;
    private Integer subCategoriaId; // Relación con SubCategoria representada por su ID

    public SubSubCategoriaDto() {
    }

    public SubSubCategoriaDto(Integer subSubCategoriaId, String nameSubSubCategoria, Integer subCategoriaId) {
        this.subSubCategoriaId = subSubCategoriaId;
        this.nameSubSubCategoria = nameSubSubCategoria;
        this.subCategoriaId = subCategoriaId;
    }

    public Integer getSubSubCategoriaId() {
        return subSubCategoriaId;
    }

    public void setSubSubCategoriaId(Integer subSubCategoriaId) {
        this.subSubCategoriaId = subSubCategoriaId;
    }

    public String getNameSubSubCategoria() {
        return nameSubSubCategoria;
    }

    public void setNameSubSubCategoria(String nameSubSubCategoria) {
        this.nameSubSubCategoria = nameSubSubCategoria;
    }

    public Integer getSubCategoriaId() {
        return subCategoriaId;
    }

    public void setSubCategoriaId(Integer subCategoriaId) {
        this.subCategoriaId = subCategoriaId;
    }
}
