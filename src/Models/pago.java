/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import com.sun.security.auth.NTSidPrimaryGroupPrincipal;
import java.util.Vector;

/**
 *
 * @author ERIK RUBIO
 */
public class pago {
     private String idpago;
     private String ruc;
     private String tipodocumento;    
     private String serie;
     private String numeroserie;
     private String fecha;     
     private String idcita;
     private String idpaciente;
     private String nombre;
     
     
     private String idtipoconsulta;
     private String descripcion;
     private String cantidad;
     private String costo;
     private String acuenta;
     private String monto ;
     private String saldo;
     
     private String detallejson;
     private String subtotal;
     private String descuento;
     private String total;

    public String getJson() {
        return detallejson;
    }

    public void setJson(String json) {
        this.detallejson = json;
    }
     
     
 public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }
     
     
    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }
        
     
    public String getAcuenta() {
        return acuenta;
    }

    public void setAcuenta(String acuenta) {
        this.acuenta = acuenta;
    }
     

    public String getIdpago() {
        return idpago;
    }

    public void setIdpago(String idpago) {
        this.idpago = idpago;
    }

    public String getTipodocumento() {
        return tipodocumento;
    }

    public void setTipodocumento(String tipodocumento) {
        this.tipodocumento = tipodocumento;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getNumeroserie() {
        return numeroserie;
    }

    public void setNumeroserie(String numeroserie) {
        this.numeroserie = numeroserie;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getIdcita() {
        return idcita;
    }

    public void setIdcita(String idcita) {
        this.idcita = idcita;
    }

    public String getIdpaciente() {
        return idpaciente;
    }

    public void setIdpaciente(String idpaciente) {
        this.idpaciente = idpaciente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdtipoconsulta() {
        return idtipoconsulta;
    }

    public void setIdtipoconsulta(String idtipoconsulta) {
        this.idtipoconsulta = idtipoconsulta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getDescuento() {
        return descuento;
    }

    public void setDescuento(String descuento) {
        this.descuento = descuento;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
     
    
    // metodos vectores
    
 public void agregar(){
    ListaProducto.agregar(this);
    }
    public void eliminar(){
    ListaProducto.eliminar(0);
    }
    public Vector mostrar(){
    return ListaProducto.mostrar();
    }

    
    
     
    
    
    
    
    
    
}
