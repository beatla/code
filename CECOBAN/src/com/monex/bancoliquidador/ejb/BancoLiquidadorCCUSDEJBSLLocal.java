package com.monex.bancoliquidador.ejb;

import javax.ejb.EJBLocalObject;
import com.monex.comun.bd.MatrizDatos;

public interface BancoLiquidadorCCUSDEJBSLLocal
    extends EJBLocalObject {
  public MatrizDatos getMonitorSaldos(String sCveEntidad, String sFecha);
}
