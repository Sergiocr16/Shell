package com.lighthouse.shell.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Lanzamiento.
 */
@Entity
@Table(name = "lanzamiento")
public class Lanzamiento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero")
    private String numero;

    @ManyToOne
    private Proyecto proyecto;

    @OneToMany(mappedBy = "lanzamiento")
    @JsonIgnore
    private Set<Sprint> sprints = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public Lanzamiento numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public Lanzamiento proyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
        return this;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public Set<Sprint> getSprints() {
        return sprints;
    }

    public Lanzamiento sprints(Set<Sprint> sprints) {
        this.sprints = sprints;
        return this;
    }

    public Lanzamiento addSprint(Sprint sprint) {
        this.sprints.add(sprint);
        sprint.setLanzamiento(this);
        return this;
    }

    public Lanzamiento removeSprint(Sprint sprint) {
        this.sprints.remove(sprint);
        sprint.setLanzamiento(null);
        return this;
    }

    public void setSprints(Set<Sprint> sprints) {
        this.sprints = sprints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Lanzamiento lanzamiento = (Lanzamiento) o;
        if (lanzamiento.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lanzamiento.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Lanzamiento{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            "}";
    }
}
