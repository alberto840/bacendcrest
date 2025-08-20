package com.project.pet_veteriana.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "SubCategoria")
public class SubCategoria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sub_categoria_id", nullable = false)
    private Integer subCategoriaId;

    @Column(name = "name_sub_categoria", nullable = false, length = 150)
    private String nameSubCategoria;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category; // Relación con Category

    public SubCategoria() {
    }

    public SubCategoria(Integer subCategoriaId, String nameSubCategoria, Category category) {
        this.subCategoriaId = subCategoriaId;
        this.nameSubCategoria = nameSubCategoria;
        this.category = category;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
