package com.monex.bancoliquidador.cliente.util;

public interface ConstantesI {
  //public static final char[] ACCESO_PWD = {'M','0','n','x','C','c','0','b','a','n'};
  public static final char[] ACCESO_PWD = {'M'};
  public static final int REP_ACUSE_RECIBO = 1;
  public static final int REP_DETALLE_TRANSMITIDO = 2;
  public static final int REP_BALANCE = 3;
  public static final int ERROR = -1;
  public static final int WARNING = 0;
  public static final int OK = 0;
  public static final String ARCHIVO_CONEXION = "conexion_bd.xml";
  public static final String ABONO = "A";
  public static final String CARGO = "C";
  public static final String CONFIRMACION = "¿Está seguro de continuar?";
  public static final String ENTIDAD = "MONEX";
  public static final String FALSO = "F";
  public static final String VERDAD = "V";

  //public static final String NOM_REP_DETALLE_CARGOS = "RA_CARGOS_000001";
  //public static final String NOM_REP_DETALLE_ABONOS = "RA_ABONOS_000002";
  //public static final String NOM_REP_BALANCE = "BALANCE_CUENTA";
  //public static final String NOM_REP_ACUSE_CARGOS = "ACK_1CARGOSD";
  //public static final String NOM_REP_ACUSE_ABONOS = "ACK_1ABONOSD";

  // CONSTANTES PARA FORMAR EL NOMBRE DEL REPORTE
  public static final String TIPO_ARCHIVO_CARGOS = "CARGOSD";
  public static final String TIPO_ARCHIVO_ABONOS = "ABONOSD";

  public static final String PREFIJO_REP_ACUSE_RECIBO = "ACK_";
  public static final String PREFIJO_REP_DET_TRANSMITIDO = "RA_";
  public static final String PREFIJO_REP_BALANCE = "BALANCE_";

}
