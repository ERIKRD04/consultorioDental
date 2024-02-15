/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import com.fasterxml.jackson.annotation.*;
public class Citajson {
    private String medico;
    private String idconsultorio;

    @JsonProperty("medico")
    public String getMedico() { return medico; }
    @JsonProperty("medico")
    public void setMedico(String value) { this.medico = value; }

    @JsonProperty("idconsultorio")
    public String getIdconsultorio() { return idconsultorio; }
    @JsonProperty("idconsultorio")
    public void setIdconsultorio(String value) { this.idconsultorio = value; }
}
