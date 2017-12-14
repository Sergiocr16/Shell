package com.lighthouse.shell.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Medida entity.
 */
public class MedidaDTO implements Serializable {

    private Long id;

    private String contencion;

    private String mitigacion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContencion() {
        return contencion;
    }

    public void setContencion(String contencion) {
        this.contencion = contencion;
    }

    public String getMitigacion() {
        return mitigacion;
    }

    public void setMitigacion(String mitigacion) {
        this.mitigacion = mitigacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MedidaDTO medidaDTO = (MedidaDTO) o;
        if(medidaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), medidaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MedidaDTO{" +
            "id=" + getId() +
            ", contencion='" + getContencion() + "'" +
            ", mitigacion='" + getMitigacion() + "'" +
            "}";
    }
}
