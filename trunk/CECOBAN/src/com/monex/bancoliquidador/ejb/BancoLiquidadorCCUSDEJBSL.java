package com.monex.bancoliquidador.ejb;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;
import com.monex.comun.bd.MatrizDatos;

public interface BancoLiquidadorCCUSDEJBSL
    extends EJBObject {
  public MatrizDatos getMonitorSaldos(String sCveEntidad, String sFecha) throws
      RemoteException;
}
