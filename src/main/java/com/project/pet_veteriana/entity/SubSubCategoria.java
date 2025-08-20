package com.project.pet_veteriana.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "SubSubCategoria")
public class SubSubCategoria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sub_sub_categoria_id", nullable = false)
    private Integer subSubCategoriaId;

    @Column(name = "name_sub_sub_categoria", nullable = false, length = 150)
    private String nameSubSubCategoria;

    @ManyToOne
    @JoinColumn(name = "sub_categoria_id", nullable = false)
    private SubCategoria subCategoria; // Relación con SubCategoria

    public SubSubCategoria() {
    }

    public SubSubCategoria(Integer subSubCategoriaId, String nameSubSubCategoria, SubCategoria subCategoria) {
        this.subSubCategoriaId = subSubCategoriaId;
        this.nameSubSubCategoria = nameSubSubCategoria;
        this.subCategoria = subCategoria;
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

    public SubCategoria getSubCategoria() {
        return subCategoria;
    }

    public void setSubCategoria(SubCategoria subCategoria) {
        this.subCategoria = subCategoria;
    }
}
