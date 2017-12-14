package com.lighthouse.shell.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Sprint.
 */
@Entity
@Table(name = "sprint")
public class Sprint implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero")
    private String numero;

    @Column(name = "status")
    private String status;

    @Column(name = "puntos_planeados")
    private Integer puntosPlaneados;

    @Column(name = "puntos_realizados")
    private Integer puntosRealizados;

    @Column(name = "spi")
    private String spi;

    @Column(name = "cpi")
    private String cpi;

    @Column(name = "sv")
    private String sv;

    @Column(name = "vac")
    private String vac;

    @Column(name = "pe")
    private String pe;

    @Column(name = "bac")
    private String bac;

    @Column(name = "pv")
    private String pv;

    @Column(name = "ac")
    private String ac;

    @Column(name = "etc")
    private String etc;

    @Column(name = "eac")
    private String eac;

    @Column(name = "cv")
    private String cv;

    @Column(name = "vc")
    private String vc;

    @Column(name = "ev")
    private String ev;

    @Column(name = "comentario")
    private String comentario;

    @ManyToOne
    private Lanzamiento lanzamiento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public Sprint numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getStatus() {
        return status;
    }

    public Sprint status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getPuntosPlaneados() {
        return puntosPlaneados;
    }

    public Sprint puntosPlaneados(Integer puntosPlaneados) {
        this.puntosPlaneados = puntosPlaneados;
        return this;
    }

    public void setPuntosPlaneados(Integer puntosPlaneados) {
        this.puntosPlaneados = puntosPlaneados;
    }

    public Integer getPuntosRealizados() {
        return puntosRealizados;
    }

    public Sprint puntosRealizados(Integer puntosRealizados) {
        this.puntosRealizados = puntosRealizados;
        return this;
    }

    public void setPuntosRealizados(Integer puntosRealizados) {
        this.puntosRealizados = puntosRealizados;
    }

    public String getSpi() {
        return spi;
    }

    public Sprint spi(String spi) {
        this.spi = spi;
        return this;
    }

    public void setSpi(String spi) {
        this.spi = spi;
    }

    public String getCpi() {
        return cpi;
    }

    public Sprint cpi(String cpi) {
        this.cpi = cpi;
        return this;
    }

    public void setCpi(String cpi) {
        this.cpi = cpi;
    }

    public String getSv() {
        return sv;
    }

    public Sprint sv(String sv) {
        this.sv = sv;
        return this;
    }

    public void setSv(String sv) {
        this.sv = sv;
    }

    public String getVac() {
        return vac;
    }

    public Sprint vac(String vac) {
        this.vac = vac;
        return this;
    }

    public void setVac(String vac) {
        this.vac = vac;
    }

    public String getPe() {
        return pe;
    }

    public Sprint pe(String pe) {
        this.pe = pe;
        return this;
    }

    public void setPe(String pe) {
        this.pe = pe;
    }

    public String getBac() {
        return bac;
    }

    public Sprint bac(String bac) {
        this.bac = bac;
        return this;
    }

    public void setBac(String bac) {
        this.bac = bac;
    }

    public String getPv() {
        return pv;
    }

    public Sprint pv(String pv) {
        this.pv = pv;
        return this;
    }

    public void setPv(String pv) {
        this.pv = pv;
    }

    public String getAc() {
        return ac;
    }

    public Sprint ac(String ac) {
        this.ac = ac;
        return this;
    }

    public void setAc(String ac) {
        this.ac = ac;
    }

    public String getEtc() {
        return etc;
    }

    public Sprint etc(String etc) {
        this.etc = etc;
        return this;
    }

    public void setEtc(String etc) {
        this.etc = etc;
    }

    public String getEac() {
        return eac;
    }

    public Sprint eac(String eac) {
        this.eac = eac;
        return this;
    }

    public void setEac(String eac) {
        this.eac = eac;
    }

    public String getCv() {
        return cv;
    }

    public Sprint cv(String cv) {
        this.cv = cv;
        return this;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    public String getVc() {
        return vc;
    }

    public Sprint vc(String vc) {
        this.vc = vc;
        return this;
    }

    public void setVc(String vc) {
        this.vc = vc;
    }

    public String getEv() {
        return ev;
    }

    public Sprint ev(String ev) {
        this.ev = ev;
        return this;
    }

    public void setEv(String ev) {
        this.ev = ev;
    }

    public String getComentario() {
        return comentario;
    }

    public Sprint comentario(String comentario) {
        this.comentario = comentario;
        return this;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Lanzamiento getLanzamiento() {
        return lanzamiento;
    }

    public Sprint lanzamiento(Lanzamiento lanzamiento) {
        this.lanzamiento = lanzamiento;
        return this;
    }

    public void setLanzamiento(Lanzamiento lanzamiento) {
        this.lanzamiento = lanzamiento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Sprint sprint = (Sprint) o;
        if (sprint.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sprint.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Sprint{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", status='" + getStatus() + "'" +
            ", puntosPlaneados='" + getPuntosPlaneados() + "'" +
            ", puntosRealizados='" + getPuntosRealizados() + "'" +
            ", spi='" + getSpi() + "'" +
            ", cpi='" + getCpi() + "'" +
            ", sv='" + getSv() + "'" +
            ", vac='" + getVac() + "'" +
            ", pe='" + getPe() + "'" +
            ", bac='" + getBac() + "'" +
            ", pv='" + getPv() + "'" +
            ", ac='" + getAc() + "'" +
            ", etc='" + getEtc() + "'" +
            ", eac='" + getEac() + "'" +
            ", cv='" + getCv() + "'" +
            ", vc='" + getVc() + "'" +
            ", ev='" + getEv() + "'" +
            ", comentario='" + getComentario() + "'" +
            "}";
    }
}
