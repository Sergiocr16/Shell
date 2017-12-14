package com.lighthouse.shell.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Sprint entity.
 */
public class SprintDTO implements Serializable {

    private Long id;

    private String numero;

    private String status;

    private Integer puntosPlaneados;

    private Integer puntosRealizados;

    private String spi;

    private String cpi;

    private String sv;

    private String vac;

    private String pe;

    private String bac;

    private String pv;

    private String ac;

    private String etc;

    private String eac;

    private String cv;

    private String vc;

    private String ev;

    private String comentario;

    private Long lanzamientoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getPuntosPlaneados() {
        return puntosPlaneados;
    }

    public void setPuntosPlaneados(Integer puntosPlaneados) {
        this.puntosPlaneados = puntosPlaneados;
    }

    public Integer getPuntosRealizados() {
        return puntosRealizados;
    }

    public void setPuntosRealizados(Integer puntosRealizados) {
        this.puntosRealizados = puntosRealizados;
    }

    public String getSpi() {
        return spi;
    }

    public void setSpi(String spi) {
        this.spi = spi;
    }

    public String getCpi() {
        return cpi;
    }

    public void setCpi(String cpi) {
        this.cpi = cpi;
    }

    public String getSv() {
        return sv;
    }

    public void setSv(String sv) {
        this.sv = sv;
    }

    public String getVac() {
        return vac;
    }

    public void setVac(String vac) {
        this.vac = vac;
    }

    public String getPe() {
        return pe;
    }

    public void setPe(String pe) {
        this.pe = pe;
    }

    public String getBac() {
        return bac;
    }

    public void setBac(String bac) {
        this.bac = bac;
    }

    public String getPv() {
        return pv;
    }

    public void setPv(String pv) {
        this.pv = pv;
    }

    public String getAc() {
        return ac;
    }

    public void setAc(String ac) {
        this.ac = ac;
    }

    public String getEtc() {
        return etc;
    }

    public void setEtc(String etc) {
        this.etc = etc;
    }

    public String getEac() {
        return eac;
    }

    public void setEac(String eac) {
        this.eac = eac;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    public String getVc() {
        return vc;
    }

    public void setVc(String vc) {
        this.vc = vc;
    }

    public String getEv() {
        return ev;
    }

    public void setEv(String ev) {
        this.ev = ev;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Long getLanzamientoId() {
        return lanzamientoId;
    }

    public void setLanzamientoId(Long lanzamientoId) {
        this.lanzamientoId = lanzamientoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SprintDTO sprintDTO = (SprintDTO) o;
        if(sprintDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sprintDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SprintDTO{" +
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
