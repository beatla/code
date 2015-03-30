package com.monex.comun;

public class Formato {
  /**
   * Convierte un objeto en una cadena con formato fecha
   * @param fecha Object
   * @return String
   */
  public static String toFormaFechaBD(Object fecha){
    return (fecha == null || fecha.equals("")) ? "NULL" : "TO_DATE('" + (String)fecha + "',  'dd/MM/yyyy')";
  }

  /**
   * Convierte un objeto en cadena
   * @param data Object
   * @return String
   */
  public static String toStringData(Object data){
    return (data==null || data.equals(""))?"NULL":"'" + (String)data + "'";
  }

  /**
   * Convierte un objeto cualquiera en un objeto no nulo
   * @param data Object
   * @return Object
   */
  public static Object notEmptyData(Object data){
    return (data==null || data.equals(""))?"NULL":data;
  }

  /**
   * Conviere un objeto que puede contener un número en una cadena
   * @param data Object
   * @return String
   */
  public static String toStringNumberData(Object data){
    return (data==null || data.equals(""))?"0":"'" + (String)data + "'";
  }

  /**
   * Convierte una cadena con fecha tipo dd/mm/yyyy a
   * una cadena en formato yyyymmdd
   * @param sFecha String
   * @return String
   */
  public static String toFormatoYYYYMMDD(String sFecha){
    StringBuffer sbFecha = new StringBuffer();
    // año
    sbFecha.append(sFecha.substring(6,10));
    // mes
    sbFecha.append(sFecha.substring(3,5));
    // dia
    sbFecha.append(sFecha.substring(0,2));
    System.out.println(sbFecha);
    return sbFecha.toString();
  }
}
