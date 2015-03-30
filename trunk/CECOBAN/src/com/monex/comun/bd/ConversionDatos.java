package com.monex.comun.bd;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: Monex</p>
 *
 * @author Idalia Mora
 * @version 1.0
 */

import com.monex.comun.util.Fecha;

public class ConversionDatos {
  public ConversionDatos() {
    super();
  }

  public static java.sql.Date toSQLDate(String sFecha){
    java.util.Date dFecha;
    java.sql.Date jsqlD;

    // CONVERSION DE UNA CADENA A FECHA TIPO JAVA
    dFecha = Fecha.toDate(sFecha, true);

    // CONVERSION AL TIPO DE FECHA QUE ESPERA EL PASO DE PARAMETROS DEL PL
    jsqlD = new java.sql.Date(dFecha.getTime());

    return jsqlD;
  }

}
