package com.monex.bancoliquidador.cliente.view;

import com.monex.bancoliquidador.bd.dao.SaldosBancosDAO;
import com.monex.comun.bd.MatrizDatos;
import com.monex.bancoliquidador.vo.DiaInhabilEUAVO;

public class MonitorLiquidacionesCCUSDImpl {
  SaldosBancosDAO dao = null;
  String nombreConexion = null;

  public MonitorLiquidacionesCCUSDImpl() {
    super();
  }

  public MonitorLiquidacionesCCUSDImpl(String sNombreConexion) {
    super();
    this.nombreConexion = sNombreConexion;
  }

  public int aplicaSaldosCECOBAN(String sCveEntidad, String sFecha, String sTipoMovimiento) {
    int idGrupoTransac = 0;
    if ( dao == null ){
      dao = new SaldosBancosDAO(sCveEntidad);
      dao.setNombreConexion(this.nombreConexion);

    }
    idGrupoTransac = dao.aplicaSaldos(sFecha, sTipoMovimiento);
    return idGrupoTransac;
  }

  public int cargaArchivoCECOBAN(String sCveEntidad, String sFecha, String sTipoMovimiento) {
    int idResultado = 0;
    if ( dao == null ){
      dao = new SaldosBancosDAO(sCveEntidad);
      dao.setNombreConexion(this.nombreConexion);
    }
    idResultado = dao.cargaArchivo(sFecha, sTipoMovimiento);
    return idResultado;
  }

  public int finLiquidacionDiaInhabilEUA(String sCveEntidad, String sFecha) {
    int idResultado = 0;
    if ( dao == null ){
      dao = new SaldosBancosDAO(sCveEntidad);
      dao.setNombreConexion(this.nombreConexion);
    }
    idResultado = dao.finLiquidacionDiaInhabilEUA(sFecha);
    return idResultado;
  }

  public int reversaArchivoCECOBAN(String sCveEntidad, String sFecha, String sTipoMovimiento){
    int idResultado = 0;
    if ( dao == null ){
      dao = new SaldosBancosDAO(sCveEntidad);
      dao.setNombreConexion(this.nombreConexion);
    }
    idResultado = dao.ReversaArchivo(sFecha, sTipoMovimiento, "Cancelación de Archivo de Cargos");
    return idResultado;
  }

  public String getDescripcionError(){
    return dao.getSDetalleError();
  }

  public String getFecha(String sCveEntidad){
    String sFecha = "";
    if ( dao == null ){
      dao = new SaldosBancosDAO(sCveEntidad);
      dao.setNombreConexion(this.nombreConexion);
    }
    sFecha = dao.getFecha();
    return sFecha;
  }

  public int getIdLiquidacion(String sCveEntidad, String sFecha){
    int idLiquidacion = 1;
    if ( dao == null ){
      dao = new SaldosBancosDAO(sCveEntidad);
      dao.setNombreConexion(this.nombreConexion);
    }
    idLiquidacion = dao.getIdLiquidacion(sFecha);
    return idLiquidacion;
  }

  public DiaInhabilEUAVO getInhabilEUA(String sCveEntidad, String sFecha){
    DiaInhabilEUAVO inhabilEUAVO = null;
    if ( dao == null ){
      dao = new SaldosBancosDAO(sCveEntidad);
      dao.setNombreConexion(this.nombreConexion);
    }
    inhabilEUAVO = dao.getDiaInhabilEUA(sFecha);
    return inhabilEUAVO;
  }

  public MatrizDatos getLiquidaciones(String sCveEntidad, String sFecha, long idContrato, String sCveDivisa) {
    MatrizDatos liquidaciones = null;
    if ( dao == null ){
      dao = new SaldosBancosDAO(sCveEntidad);
      dao.setNombreConexion(this.nombreConexion);
    }
    liquidaciones = dao.getLiquidaciones(sFecha, idContrato, sCveDivisa);
    return liquidaciones;
  }

  public MatrizDatos getMonitorSaldos(String sCveEntidad, String sFecha) {
    MatrizDatos saldos = null;
    if ( dao == null ){
      dao = new SaldosBancosDAO(sCveEntidad);
      dao.setNombreConexion(this.nombreConexion);
    }
    saldos = dao.getSaldos(sFecha);
    return saldos;
  }

  public String isContingenciaActiva(String sCveEntidad){
    String sResultado = "F";
        if ( dao == null ){
          dao = new SaldosBancosDAO(sCveEntidad);
          dao.setNombreConexion(this.nombreConexion);
        }
        sResultado = dao.isContingenciaActiva();
    return sResultado;
  }

  public void pruebaAdminTransac(String sCveEntidad){
    if ( dao == null ){
      dao = new SaldosBancosDAO(sCveEntidad);
      dao.pruebaAdminTransac();
    }
  }

}
