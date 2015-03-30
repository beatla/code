package com.monex.bancoliquidador.cliente.view;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import com.jgoodies.swing.application.ResourceManager;

import com.monex.bancoliquidador.cliente.util.ConstantesI;
import com.monex.bancoliquidador.cliente.view.MonitorLiquidacionesCCUSDImpl;
import com.monex.bancoliquidador.vo.DiaInhabilEUAVO;
import com.monex.cliente.bd.DatoConexionXML;
import com.monex.comun.Formato;
import com.monex.comun.ReporteMonex;
import com.monex.comun.util.Fecha;


public class PanelMonitorLiquidacionesCCUSD {
  public PanelMonitorLiquidacionesCCUSDVista vista = null;
  public GridSaldosCCUSD gridSaldos = null;
  public String nombreConexion = null;
  public String directorio = null;


  // Interfaz de acceso a la bd
  MonitorLiquidacionesCCUSDImpl  implementacion = null;

  ReporteMonex reporte = null;

  // barra de progreso
  Thread hilo;
  Object objeto = new Object();
  boolean pideParar = false;
  int iProcesados = 0;

  // Fecha del día en formato dd/mm/yyyy
  String sFecha = "";

  // Fecha del día en formato yyyymmdd
  String sFechaYYYYMMDD = "";

  // Número de Liquidación en el día
  int idLiquidacion = 1;


  public void construyeVista(){
      vista =  new PanelMonitorLiquidacionesCCUSDVista();
      vista.btnAcuse.addActionListener(btnAcuseListener());
      vista.btnAplicaCA.addActionListener(btnAplicaCAListener());
      vista.btnArchivo.addActionListener(btnArchivoListener());
      vista.btnBalance.addActionListener(btnBalanceListener());
      vista.btnCancela.addActionListener(btnCancelaListener());
      vista.btnCarga.addActionListener(btnCargaListener());
      vista.btnComisiones.addActionListener(btnComisionesListener());
      vista.btnConsulta.addActionListener(btnConsultaListener());
      vista.btnDetalleTrans.addActionListener(btnDetalleTransListener());
      vista.btnFinLiquidacion.addActionListener(btnFinLiquidacionListener());
      //vista.btnLiquidaciones.addActionListener(btnLiquidacionesListener());
      vista.cbReporte.addActionListener(cbReporteListener());
      vista.lblSistema.setText("SISTEMA " + this.nombreConexion.toUpperCase());
      gridSaldos = new GridSaldosCCUSD();
      implementacion = new MonitorLiquidacionesCCUSDImpl(this.nombreConexion);
      actualizaFecha();
      cargaInhabilEUA();
      cargaIdLiquidacion();
      actualizaSaldos();
  }


  public ActionListener btnAcuseListener() {
      return new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            reportes(ConstantesI.REP_ACUSE_RECIBO);
          }
      };
  }

  public ActionListener btnAplicaCAListener() {
      return new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              aplicaSaldos();
              actualizaSaldos();
          }
      };
  }

  public ActionListener btnArchivoListener() {
      return new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              reportes(0);
          }
      };
  }

  public ActionListener btnBalanceListener() {
    return new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            reportes(ConstantesI.REP_BALANCE);
        }
    };

  }

  public ActionListener btnCancelaListener() {
    return new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          reversaArchivo();
          actualizaSaldos();
        }
    };
  }

  public ActionListener btnCargaListener() {
      return new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              cargaArchivo();
              actualizaSaldos();
          }
      };
  }


  public ActionListener btnComisionesListener() {
      return new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            if( JOptionPane.showConfirmDialog(null, ConstantesI.CONFIRMACION, "Generación de comisiones de CECOBAN",
                                  JOptionPane.YES_NO_OPTION , JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
              vista.lblMensaje.setText("Esta opción se encuentra en construcción");
            } else {
                vista.lblMensaje.setText("Desistio de la operacion");
              }

          }
      };
  }

  public ActionListener btnConsultaListener() {
      return new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            actualizaSaldos();
          }
      };
  }

  public ActionListener btnDetalleTransListener() {
    return new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          reportes(ConstantesI.REP_DETALLE_TRANSMITIDO);
        }
    };
  }

  public ActionListener btnFinLiquidacionListener() {
    return new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          terminaLiquidacion();
          actualizaSaldos();
        }
    };

  }

/*
  public ActionListener btnLiquidacionesListener() {
    return new ActionListener() {
        public void actionPerformed(ActionEvent e) {
               System.out.println("ejecuta el action listener del detalle de liq");
        }
    };
  }
*/
  public ActionListener cbReporteListener() {
    return new ActionListener() {
        public void actionPerformed(ActionEvent e) {

        }
    };
  }

  public JFrame getVista(){
      return vista;
  }

  public void setSize(int i, int j){
    vista.setSize(i, j);
  }

  public void setNombreConexion(String sNombreConexion){
    this.nombreConexion = sNombreConexion;
  }

  public void setDirectorio(String sDirectorio){
    this.directorio = sDirectorio;
  }


  public void iniciaCarga() {
        if( hilo == null ) {
          hilo = new ThreadConsulta();
          pideParar = false;
          hilo.start();
        }
      }

      public void detieneCarga() {
        synchronized( objeto ) {
          pideParar = true;
          objeto.notify();
        }
      }


    //Hilo de analisis de la carga
    class ThreadConsulta extends Thread {
        public void run() {
          try{
            int min = 0;
            int max = 100;
            iProcesados = 0;

            vista.lblMensaje.setText("Consultando...");
            vista.lblMensaje.setForeground(Color.decode("#336600"));
            vista.lblMensaje.setIcon( ResourceManager.readImageIcon("images/imgEnProcesoAnimado.gif") );

            vista.btnConsulta.setEnabled(false);
            //vista.btnDetener.setEnabled(true);

            vista.barra.setValue(min);
            vista.barra.setMinimum(min);
            vista.barra.setMaximum(max);

            actualizaSaldos();
            //consultaSaldos();
            //imprimeSaldos();

            /*
            LinkedList saldos;

            saldos = implementacion.getMonitorSaldos("MONEX", "29-07-2009");

            if(saldos != null){

                int iNumElementos = saldos.getSeguroCount();
                //System.out.println("iNumElementos "+iNumElementos);

                for (int iActual=0; iActual < iNumElementos; iActual++) {
                  vista.lblMensaje.setText("Analizando "+iActual+" de "+iNumElementos);

                  validaRenglonSeguro(saldos.getSeguro(iActual));

                  int iAvance =Math.round((iActual*100)/iNumElementos);
                  vista.barra.setValue( iAvance );

                  synchronized( objeto ) {
                    if (pideParar)
                      break;
                    try {
                      objeto.wait(100);
                    }
                    catch (InterruptedException e) {
                      vista.btnConsulta.setEnabled(true);
                      vista.lblMensaje.setText("");
                      vista.lblMensaje.setForeground(Color.decode("#336600"));
                      vista.lblMensaje.setIcon( null );

                      vista.barra.setValue( 0 );

                      e.printStackTrace();
                      // Se ignoran las excepciones
                    }
                  }

                }
                hilo = null;
                vista.btnConsulta.setEnabled(true);
                vista.lblMensaje.setText("");
                vista.lblMensaje.setForeground(Color.decode("#336600"));
                vista.lblMensaje.setIcon( null );
                vista.barra.setValue( 0 );
                vista.lblMensaje.setText("Se encontraron "+iProcesados+" seguro(s) con este criterio.");
             }*/
          }catch(Exception e){
            vista.btnConsulta.setEnabled(true);
            vista.lblMensaje.setText("");
            vista.lblMensaje.setForeground(Color.decode("#336600"));
            vista.lblMensaje.setIcon( null );
            vista.barra.setValue( 0 );

            e.printStackTrace();
          }
        }
      }

    public void actualizaFecha(){
      consultaFecha();
      sFechaYYYYMMDD = Formato.toFormatoYYYYMMDD(sFecha);
      vista.lblFecha.setText("Fecha de Liquidación: " + sFecha);
      vista.dateFechaSaldos.setEditable(new SimpleDateFormat("dd/MM/yyyy"));
      vista.dateFechaSaldos.setDate(Fecha.toDate(sFecha, true));
      System.out.println("actualiza fecha");
    }

    public void actualizaSaldos(){
      String sFechaSaldos = "";
      String sMensaje = null;
      sFechaSaldos = Fecha.FormatoHtml(vista.dateFechaSaldos.getDate(), true);
      System.out.println("actualiza saldos xxx ....");
      System.out.println(this.nombreConexion);
      sMensaje = gridSaldos.actualizaSaldos(sFechaSaldos, vista.dsSaldos, this.nombreConexion);
      if (sMensaje != null && sMensaje.trim().length() > 0 ) {
        JOptionPane.showMessageDialog(null, sMensaje, "Error de Consulta", JOptionPane.ERROR_MESSAGE);
      }
      System.out.println("regresa de actualiza saldos");

      System.out.println("prueba del administrador de transacciones");
      implementacion.pruebaAdminTransac("MONEX");
      System.out.println("continua ...");

    }

    public void consultaFecha(){
      // Consulta la fecha del día
      sFecha = implementacion.getFecha(ConstantesI.ENTIDAD);
      vista.lblMensaje.setText(implementacion.getDescripcionError());
    }

    public void cargaArchivo(){
      int iResultado = 0;
      String sTipoMovimiento = "";
      String sMensajeReporte = "";
      StringBuffer sTextoConfirmacion = null;

      sTipoMovimiento = getTipoMovimiento();
      if (!sTipoMovimiento.equals("")){

        sTextoConfirmacion = new StringBuffer();
        sTextoConfirmacion.append("Carga de Archivos de CECOBAN de ").append(sTipoMovimiento.equals(ConstantesI.CARGO) ? "CARGOS" : "ABONOS");
        sTextoConfirmacion.append("\ndel día ").append(sFecha).append("\n");
        sTextoConfirmacion.append(ConstantesI.CONFIRMACION);

        if( JOptionPane.showConfirmDialog(null, sTextoConfirmacion, "Carga de archivo de CECOBAN",
                                          JOptionPane.YES_NO_OPTION , JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){


          Container contenedor = vista.btnAplicaCA.getParent();
          //COLOCA EL RELOJ DE ARENA
          contenedor.getParent().setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.WAIT_CURSOR));

          cargaIdLiquidacion();

          iResultado = implementacion.cargaArchivoCECOBAN(ConstantesI.ENTIDAD, sFecha, sTipoMovimiento);

          System.out.println("termino la carga ");
          System.out.println("resultado");
          System.out.println(iResultado);

          // Generación automática de reportes de salida
          // 1) Acuse de Recibo
          sMensajeReporte = generaArchivo(ConstantesI.REP_ACUSE_RECIBO, sFecha, sTipoMovimiento);

          //QUITA EL RELOJ DE ARENA Y VUELVE AL PUNTERO POR DEFAULT
          contenedor.getParent().setCursor(java.awt.Cursor.getDefaultCursor());

          switch (iResultado) {
            case ConstantesI.ERROR:
              JOptionPane.showMessageDialog(null,"Se presentó el siguiente error en el proceso\n"+implementacion.getDescripcionError() + "\n" + sMensajeReporte,"Error del proceso",JOptionPane.ERROR_MESSAGE);
              break;
            case ConstantesI.OK:
              JOptionPane.showMessageDialog(null,implementacion.getDescripcionError() + "\n" + sMensajeReporte,"Proceso Concluído",JOptionPane.INFORMATION_MESSAGE);
              break;
          }
        }
      }
    }

    public void reversaArchivo(){
      String sTipoMovimiento = "";
      int iResultado = 0;

      //sTipoMovimiento = ConstantesI.CARGO;
      sTipoMovimiento = getTipoMovimiento();
      if ( !sTipoMovimiento.equals("") ) {
        if( JOptionPane.showConfirmDialog(null, "Desea continuar con la reversa de " + (sTipoMovimiento.equals(ConstantesI.CARGO) ? "CARGOS" : "ABONOS"), "Reversa Cargos/Abonos",
                                          JOptionPane.YES_NO_OPTION , JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){

          Container contenedor = vista.btnCancela.getParent();
          //COLOCA EL RELOJ DE ARENA
          contenedor.getParent().setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.WAIT_CURSOR));

          iResultado = implementacion.reversaArchivoCECOBAN(ConstantesI.ENTIDAD, sFecha, sTipoMovimiento);

          //QUITA EL RELOJ DE ARENA Y VUELVE AL PUNTERO POR DEFAULT
          contenedor.getParent().setCursor(java.awt.Cursor.getDefaultCursor());

          switch (iResultado) {
            case ConstantesI.ERROR:
              JOptionPane.showMessageDialog(null,"Se presentó el siguiente error\n"+implementacion.getDescripcionError(),"Error del proceso",JOptionPane.ERROR_MESSAGE);
              break;
            case ConstantesI.OK:
              JOptionPane.showMessageDialog(null,implementacion.getDescripcionError(),"Cancelación Concluída",JOptionPane.INFORMATION_MESSAGE);
              break;
          }
        }
      }
    }

    public void aplicaSaldos() {
      String sTipoMovimiento = "";
      String sMensajeReporte = "";
      StringBuffer sTextoConfirmacion = null;
      int iResultado = 0;

      sTipoMovimiento = getTipoMovimiento();
      if ( !sTipoMovimiento.equals("") ) {

        sTextoConfirmacion = new StringBuffer();
        sTextoConfirmacion.append("Aplicación de ").append(sTipoMovimiento.equals(ConstantesI.CARGO) ? "CARGOS" : "ABONOS");
        sTextoConfirmacion.append("\ndel día ").append(sFecha).append("\n");
        sTextoConfirmacion.append(ConstantesI.CONFIRMACION);

        if( JOptionPane.showConfirmDialog(null, sTextoConfirmacion, "Aplicación de Cargos/Abonos",
                                          JOptionPane.YES_NO_OPTION , JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){


          Container contenedor = vista.btnAplicaCA.getParent();
          //COLOCA EL RELOJ DE ARENA
          contenedor.getParent().setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.WAIT_CURSOR));

          iResultado = implementacion.aplicaSaldosCECOBAN(ConstantesI.ENTIDAD, sFecha, sTipoMovimiento);

          // Solo genera el reporte cuando la aplicación se llevó a cabo
          if ( iResultado > 0 ) {
            // Generación automática de reportes de salida
            // 2) Detalle de lo transmitido
            sMensajeReporte = generaArchivo(ConstantesI.REP_DETALLE_TRANSMITIDO, sFecha, sTipoMovimiento);
            System.out.println("termina de guardar detalle transmitido");
            System.out.println(sMensajeReporte);
          }
          //QUITA EL RELOJ DE ARENA Y VUELVE AL PUNTERO POR DEFAULT
          contenedor.getParent().setCursor(java.awt.Cursor.getDefaultCursor());

          switch (iResultado) {
            case ConstantesI.ERROR:
              JOptionPane.showMessageDialog(null,"No se aplicaron los saldos.\n"+implementacion.getDescripcionError() + "\n" + sMensajeReporte,"Error del proceso",JOptionPane.ERROR_MESSAGE);
              break;
            case ConstantesI.WARNING:
              JOptionPane.showMessageDialog(null,implementacion.getDescripcionError() + "\n" + sMensajeReporte,"Aviso",JOptionPane.WARNING_MESSAGE);
              break;
            default:
              JOptionPane.showMessageDialog(null,implementacion.getDescripcionError() + "\nFolio del proceso: " + iResultado  + "\n" + sMensajeReporte,"Proceso Concluído",JOptionPane.INFORMATION_MESSAGE);
              break;
          }

        }
      }
    }

    public String getTipoMovimiento(){
      String sTipoMovimiento = "";
      sTipoMovimiento = vista.radioAplicaCortos.isSelected() ? ConstantesI.CARGO : (
                        vista.radioAplicaLargos.isSelected() ? ConstantesI.ABONO : "");
      if (sTipoMovimiento.equals("")){
        JOptionPane.showMessageDialog(null,"Por favor,\nseleccione Cargos o Abonos","Error de selección",JOptionPane.ERROR_MESSAGE);
      }
      return sTipoMovimiento;
    }

    public String getURLReporte(int idReporte, String sFecha, String sTipoMovimiento) throws Exception{
      StringBuffer bufUrlReporte = null;
      DatoConexionXML datoConexionXML = null;

      //System.out.println("geturlrep: ");
      //System.out.println(this.nombreConexion);
      bufUrlReporte = new StringBuffer();

      try {
        // Obtiene el nombre de la base de datos
        datoConexionXML = new DatoConexionXML(ConstantesI.ARCHIVO_CONEXION, this.nombreConexion);
        if (!datoConexionXML.getServiceName().equals("")){
          bufUrlReporte.append(ReporteMonex.sReporteador);
          switch (idReporte) {
            case ConstantesI.REP_ACUSE_RECIBO:
              bufUrlReporte.append("Param1Parametro=pBUltimo&Param1Tipo=String&Param1Valor=V&Param2Parametro=pFecha&Param2Tipo=String&Param2Valor=").append(sFecha).append("&Param3Parametro=pTipo&Param3Tipo=String&Param3Valor=").append(sTipoMovimiento).append("&PDF=T&idActivo=3&NombreArchivo=CECOBAN_Recibo.rpt&DB=").append(datoConexionXML.getServiceName()).append("&NombreTablaDinamica=&Propietario=&RefreshMensual=N");
              break;
            case ConstantesI.REP_DETALLE_TRANSMITIDO:
              bufUrlReporte.append("Param1Parametro=pFecha&Param1Tipo=String&Param1Valor=").append(sFecha).append("&Param2Parametro=pTipo&Param2Tipo=String&Param2Valor=").append(sTipoMovimiento).append("&PDF=S&idActivo=2&NombreArchivo=CECOBAN_Detalle_transmitido.rpt&DB=").append(datoConexionXML.getServiceName()).append("&NombreTablaDinamica=&Propietario=&RefreshMensual=N");
              break;
            case ConstantesI.REP_BALANCE:
              bufUrlReporte.append("Param1Parametro=fCreacion&Param1Tipo=String&Param1Valor=").append(sFecha).append("&PDF=S&idActivo=1&NombreArchivo=CECOBAN_BalanceCuentaConcentradora.rpt&DB=").append(datoConexionXML.getServiceName()).append("&NombreTablaDinamica=&Propietario=&RefreshMensual=N");
              break;
          }
        }
        else{
          throw new Exception("Falta la configuración de la conexion a la base de datos");
        }
      } finally {
        datoConexionXML = null;
      }
      System.out.println(bufUrlReporte.toString());
      return bufUrlReporte.toString();
    }

    public void reportes(int idReporte){
      String sTipoMovimiento = null;
      String sURL = null;
      try {
        switch (idReporte) {
          case ConstantesI.REP_ACUSE_RECIBO:
            sTipoMovimiento = getTipoMovimiento();
            if (!sTipoMovimiento.equals("")) {
              sURL = getURLReporte(ConstantesI.REP_ACUSE_RECIBO, sFecha, sTipoMovimiento);
              ReporteMonex.lanzadorWeb(sURL);
            }
            break;
          case ConstantesI.REP_DETALLE_TRANSMITIDO:
            sTipoMovimiento = getTipoMovimiento();
            if (!sTipoMovimiento.equals("")) {
              sURL = getURLReporte(ConstantesI.REP_DETALLE_TRANSMITIDO, sFecha, sTipoMovimiento);
              ReporteMonex.lanzadorWeb(sURL);
            }
            break;
          case ConstantesI.REP_BALANCE:
            sURL = getURLReporte(ConstantesI.REP_BALANCE, sFecha, "");
            generaArchivo(ConstantesI.REP_BALANCE, sFecha, "");
            ReporteMonex.lanzadorWeb(sURL);
            break;
        }
      } catch(Exception e) {
        JOptionPane.showMessageDialog(null, e.getMessage(),"Error del reporte", JOptionPane.ERROR_MESSAGE);
      }
    }


    public String generaArchivo(int idReporte, String sFecha, String sTipoMovimiento) {
      //String sNombreReporte = null;
      String sMensaje = null;
      String sURLReporte = null;
      StringBuffer sbNombreReporte = new StringBuffer();

      if (reporte == null){
        reporte = new ReporteMonex();
      }
      sMensaje = "";
      try {
        // Obtiene la url del reporte
        sURLReporte = getURLReporte(idReporte, sFecha, sTipoMovimiento);

        // Arma el nombre del reporte
        // Nombre del archivo = Prefijo ||idLiquidacion	|| TipoArchivoCarga || Fecha
        switch (idReporte) {
          case ConstantesI.REP_ACUSE_RECIBO:
            sbNombreReporte.append(ConstantesI.PREFIJO_REP_ACUSE_RECIBO);
            sbNombreReporte.append(idLiquidacion);
            sbNombreReporte.append(sTipoMovimiento.equals(ConstantesI.CARGO) ? ConstantesI.TIPO_ARCHIVO_CARGOS : ConstantesI.TIPO_ARCHIVO_ABONOS);
            sbNombreReporte.append(sFechaYYYYMMDD);
            //sNombreReporte = (sTipoMovimiento.equals(ConstantesI.CARGO) ? ConstantesI.NOM_REP_ACUSE_CARGOS : ConstantesI.NOM_REP_ACUSE_ABONOS);
            break;
          case ConstantesI.REP_DETALLE_TRANSMITIDO:
            sbNombreReporte.append(ConstantesI.PREFIJO_REP_DET_TRANSMITIDO);
            sbNombreReporte.append(idLiquidacion);
            sbNombreReporte.append(sTipoMovimiento.equals(ConstantesI.CARGO) ? ConstantesI.TIPO_ARCHIVO_CARGOS : ConstantesI.TIPO_ARCHIVO_ABONOS);
            sbNombreReporte.append(sFechaYYYYMMDD);
            //sNombreReporte = (sTipoMovimiento.equals(ConstantesI.CARGO) ? ConstantesI.NOM_REP_DETALLE_CARGOS : ConstantesI.NOM_REP_DETALLE_ABONOS);
            break;
          case ConstantesI.REP_BALANCE:
            sbNombreReporte.append(ConstantesI.PREFIJO_REP_BALANCE);
            sbNombreReporte.append(idLiquidacion);
            sbNombreReporte.append(sFechaYYYYMMDD);
            //sNombreReporte = ConstantesI.NOM_REP_BALANCE;
            break;
        }

        // Guarda el archivo
        sMensaje = reporte.guardaURLenArchivo(sURLReporte, this.directorio, sbNombreReporte.toString());

      } catch (Exception e) {
        sMensaje = sMensaje + "\n" + e.getMessage();
      }
      return sMensaje;
    }


    public void cargaInhabilEUA() {
      DiaInhabilEUAVO inhabilVO = null;

      inhabilVO = implementacion.getInhabilEUA(ConstantesI.ENTIDAD, sFecha);
      // Si existe día inhabil en EUA y si no ha sido liquidado el inhabil
      if ( inhabilVO != null && inhabilVO.getIsInhabilLiquidado().equals(ConstantesI.FALSO) ) {
        vista.textFechaOperacion.setText(inhabilVO.getFInhabil());
        vista.lblDescInhabil.setText(inhabilVO.getDescInhabil());
        vista.panelFechaInhabil.setVisible(true);
      } else {
        vista.panelFechaInhabil.setVisible(false);
      }
      System.out.println("carga inhabil");
    }

    public void terminaLiquidacion() {
      int iResultado = 0;
      Container contenedor = null;

      if( JOptionPane.showConfirmDialog(null, "Conclusión de la liquidación del día " + vista.textFechaOperacion.getText(), "Fin Liquidación",
                                        JOptionPane.YES_NO_OPTION , JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
        //QUITA EL RELOJ DE ARENA Y VUELVE AL PUNTERO POR DEFAULT
        contenedor = vista.btnAplicaCA.getParent();
        contenedor.getParent().setCursor(java.awt.Cursor.getDefaultCursor());
        iResultado = implementacion.finLiquidacionDiaInhabilEUA(ConstantesI.ENTIDAD, sFecha);

        switch (iResultado) {
          case ConstantesI.ERROR:
            JOptionPane.showMessageDialog(null,"Se presentó el siguiente error\n"+implementacion.getDescripcionError(),"Error del proceso",JOptionPane.ERROR_MESSAGE);
            break;
          case ConstantesI.OK:
            vista.panelFechaInhabil.setVisible(false);
            JOptionPane.showMessageDialog(null,implementacion.getDescripcionError(),"Fin de Liquidacion Concluída",JOptionPane.INFORMATION_MESSAGE);
            break;
        }
      }
      contenedor.setCursor(java.awt.Cursor.getDefaultCursor());
    }

    public void cargaIdLiquidacion() {
        idLiquidacion = implementacion.getIdLiquidacion(ConstantesI.ENTIDAD, sFecha);
    }


/*
       public void iniciaComponentes(){
        matrizMercadoPanelVista = new MatrizMercadoGenericoVista();
        matrizMercadoPanelVista.checkRefrescar.addItemListener(getRefrescarListener());
        matrizMercadoPanelVista.lblTipoSeguro.setText(vistaMercado.getDesc());
        matrizMercadoPanelVista.scrollMatriz.setPreferredSize(new Dimension(50,vistaMercado.getAlto()));
        matrizMercadoPanelVista.btnRefrescar.addActionListener(getAccionRefrescar());
    }

      public JComponent getVista(){
          //verifica si la vista esta configurada para autorefrescarse
          if(vistaMercado.getBRfresk()){
              matrizMercadoPanelVista.checkRefrescar.setSelected(true);
          }else{
              refrescaMatriz();
          }
          return matrizMercadoPanelVista;
      }

      public void refrescaMatriz(){
        //System.out.println("La matris es de "+vistaMercado.listaColumnas.size()+" por "+vistaMercado.listaPlazos.size());
        matrizMercadoPanelVista.tablaMatriz.setColumnCount(vistaMercado.getColumnasVistaMercado().getColumnaVistaMercadoCount()+1);
        matrizMercadoPanelVista.tablaMatriz.setRowCount(vistaMercado.getPlazosVistaMercado().getPlazoVistaMercadoCount()+1);
        matrizMercadoPanelVista.tablaMatriz.getColumnHeader().setPreferredSize(new Dimension(0,0));
        matrizMercadoPanelVista.setFont(new Font("Tahoma", Font.TRUETYPE_FONT, 8));
        Enumeration enumeradorColumnas=vistaMercado.getColumnasVistaMercado().enumerateColumnaVistaMercado();
        int iColumna=0;
        matrizMercadoPanelVista.tablaMatriz.getCellAt( 0, 0).setBackground(Color.decode("#62AAF2"));
        while(enumeradorColumnas.hasMoreElements()){
            iColumna++;
            ColumnaVistaMercado columna = (ColumnaVistaMercado)enumeradorColumnas.nextElement();
            matrizMercadoPanelVista.tablaMatriz.setColumnWidth( iColumna, columna.getTamano());
            matrizMercadoPanelVista.tablaMatriz.getCellAt( 0, iColumna).setFont(new Font("Tahoma", Font.TRUETYPE_FONT, 9));
            matrizMercadoPanelVista.tablaMatriz.getCellAt( 0, iColumna).setHorizontalAlignment(matrizMercadoPanelVista.tablaMatriz.CENTER);
            matrizMercadoPanelVista.tablaMatriz.getCellAt( 0, iColumna).setFontBold(true);
            matrizMercadoPanelVista.tablaMatriz.getCellAt( 0, iColumna).setWordWrap(true);
            matrizMercadoPanelVista.tablaMatriz.getCellAt( 0, iColumna).setBackground(Color.decode("#62AAF2"));
            matrizMercadoPanelVista.tablaMatriz.setValueAt(columna.getDescTipoCotiza(), 0, iColumna);
        }

        //renglon 1 a 30
        matrizMercadoPanelVista.tablaMatriz.setRowHeight(0,30);

        Enumeration enumeradorPlazos=vistaMercado.getPlazosVistaMercado().enumeratePlazoVistaMercado();
        int iRenglon=0;
        while(enumeradorPlazos.hasMoreElements()){
            iRenglon++;
            matrizMercadoPanelVista.tablaMatriz.setRowHeight(iRenglon,11);
            PlazoVistaMercado plazo = (PlazoVistaMercado)enumeradorPlazos.nextElement();
            matrizMercadoPanelVista.tablaMatriz.setValueAt(plazo.getTxDescripcion(), iRenglon, 0 );
            enumeradorColumnas=vistaMercado.getColumnasVistaMercado().enumerateColumnaVistaMercado();
            if(plazo.getTxDescripcion().trim().equals("")){
                matrizMercadoPanelVista.tablaMatriz.setColumnWidth( 0, 0);
            }
            iColumna=0;
            while(enumeradorColumnas.hasMoreElements()){
                iColumna++;
                ColumnaVistaMercado columna = (ColumnaVistaMercado)enumeradorColumnas.nextElement();

                //System.out.println("Buscando columna "+iColumna+" "+columna.sCveTipoCotizacion);
                Enumeration enumeradorValores=plazo.getValoresVistaMercado().enumerateValorVistaMercado();
                while(enumeradorValores.hasMoreElements()){
                    ValorVistaMercado valorCM = (ValorVistaMercado) enumeradorValores.nextElement();
                    if(valorCM.getCveTipoCotiza().equals(columna.getCveTipoCotiza())){
                        matrizMercadoPanelVista.tablaMatriz.getCellAt( iRenglon, iColumna).setFont(new Font("Tahoma", Font.TRUETYPE_FONT, 9));
                        matrizMercadoPanelVista.tablaMatriz.getCellAt( iRenglon, iColumna).setHorizontalAlignment(matrizMercadoPanelVista.tablaMatriz.RIGHT);
                        formatoCampo = new DecimalFormat(columna.getFormato());
                        if(valorCM.getValorCompra()==0){
                            matrizMercadoPanelVista.tablaMatriz.setValueAt(null, iRenglon, iColumna);
                        }else{
                            matrizMercadoPanelVista.tablaMatriz.setValueAt(formatoCampo.format(valorCM.getValorCompra()), iRenglon, iColumna);
                        }
                        formatoCampo=null;
                    }
                }


            }
        }
        matrizMercadoPanelVista.scrollMatriz.setPreferredSize(new Dimension(50,vistaMercado.getAlto()));
    }


      public void refrescarDatosVista(){
          System.out.println("Refrescando la vista de mercado "+vistaMercado.getDesc()+" "+java.util.Calendar.getInstance().getTime());
          // CONSTRUIMOS LA VISTAS DE MERCADO CON VALORES
          VistasMercado vistasMercado= AccesoWebservice.getVistasMercado(System.getProperty("cliente.promocion.entidad_usuario"),
                                                          vistaMercado.getCveGpo(), //GRUPO DE LA VISTA
                                                          vistaMercado.getCve(), //LA VISTA ACTUAL
                                                          "S",
                                                          "S");
          if(vistasMercado.getGrupoVistaMercadoCount()>0 && vistasMercado.getGrupoVistaMercado(0).getVistaMercadoCount()>0){
            vistaMercado= (VistaMercado)vistasMercado.getGrupoVistaMercado(0).getVistaMercado(0);
            refrescaMatriz();
          }else{
              matrizMercadoPanelVista.lblTipoSeguro.setForeground(Color.red);
              matrizMercadoPanelVista.lblTipoSeguro.setText(matrizMercadoPanelVista.lblTipoSeguro.getText()+" (Cancelada o fuera de horario)");
              matrizMercadoPanelVista.tablaMatriz.setColumnCount(0);
              matrizMercadoPanelVista.tablaMatriz.setRowCount(0);
              matrizMercadoPanelVista.tablaMatriz.getColumnHeader().setPreferredSize(new Dimension(0,0));
              if(matrizMercadoPanelVista.checkRefrescar.isSelected()){
                  matrizMercadoPanelVista.checkRefrescar.setSelected(false);
              }
              matrizMercadoPanelVista.checkRefrescar.setEnabled(false);
              matrizMercadoPanelVista.btnRefrescar.setEnabled(false);

          }
      }

      public boolean refrescaDatosAutomaticamente(String sAccion) {
          boolean bResultado=false;
          if(sAccion.equals("No Refrescar")){
            if(timer!=null){
                timer.cancel();
                timer=null;
                System.out.println("Tarea de refresque \"Cancelado\" para "+vistaMercado.getDesc());
            }else{
                System.out.println("No se ha podido parar el refresque \"NO ESTA ACTIVO\" para "+vistaMercado.getDesc());
            }
             bResultado=true;
          }else if(sAccion.equals("Refrescar")){
             if(timer==null){
                EjecutaTimer();
                System.out.println("Tarea de refresque \"Iniciado\" para "+vistaMercado.getDesc());
             }else{
                System.out.println("La solicitud de ejecución de la tarea de refresque \"YA SE ENCUENTRA CORRIENDO\" para "+vistaMercado.getDesc());
             }
             bResultado=true;
          }
          return bResultado;
        }

        public int EjecutaTimer()
          {
              timer = new Timer();
              //configurado par cotizar conforme esta configurado
              timer.scheduleAtFixedRate(new RemindTask(), 1L, (vistaMercado.getRfresk()*1000));
              return 1;
          }
        public int getEstadoActual() {
          int iRegreso=0;
          if(timer!=null){
             iRegreso=1;
          }
          return iRegreso;
        }

        class RemindTask extends TimerTask
        {

          public void run()
          {
                try{
                    refrescarDatosVista();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                finally{

                }
          }

          RemindTask()
          {
          }
      }

      private Action getAccionRefrescar() {
            return new AbstractAction() {
              public void actionPerformed(ActionEvent e) {
                JButton boton = (JButton)e.getSource();
                Container contenedor =boton.getParent();
                contenedor.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.WAIT_CURSOR));
                refrescarDatosVista();
                contenedor.setCursor(java.awt.Cursor.getDefaultCursor());
              }
            };
      }
      public ItemListener getRefrescarListener(){
        return new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.DESELECTED){
                    matrizMercadoPanelVista.btnRefrescar.setEnabled(true);
                    matrizMercadoPanelVista.lblIcono.setIcon( ResourceManager.readImageIcon("images/imgRefrescandoEstatico.gif") );
                    refrescaDatosAutomaticamente("No Refrescar");
                }else{
                    matrizMercadoPanelVista.btnRefrescar.setEnabled(false);
                    matrizMercadoPanelVista.lblIcono.setIcon( ResourceManager.readImageIcon("images/imgRefrescandoAnimado.gif") );
                    refrescaDatosAutomaticamente("Refrescar");
                }
            }
        };
     }

*/

}
