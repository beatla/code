package com.monex.bancoliquidador.cliente.view;

import java.awt.Color;
import java.util.LinkedList;
import java.util.Iterator;

import javax.swing.JButton;

import diamondedge.swing.grid.DsGrid;

import com.jgoodies.swing.application.ResourceManager;
import com.monex.aplic.admon.catalogo.Registro;
import com.monex.bancoliquidador.bd.dao.SaldoCCUSD;
import com.monex.bancoliquidador.cliente.util.ConstantesI;
import com.monex.comun.bd.MatrizDatos;
import com.monex.comun.util.FormatoNumero;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GridSaldosCCUSD {
	// Interfaz de acceso a la bd
	MonitorLiquidacionesCCUSDImpl implementacion = null;

	// declaración e Inicialización de Variables para recuperación de datos
	@SuppressWarnings("rawtypes")
	LinkedList lista = null;
	Registro encabezado = null;
	Registro totales = null;

	// COLUMNAS DEL GRID
	public static final int COLUMNA_CVE_BANXICO = 0;
	public static final int COLUMNA_CONTRATO = 1;
	public static final int COLUMNA_BANCO = 2;
	public static final int COLUMNA_SDO_CORTO = 3;
	public static final int COLUMNA_SDO_LARGO = 4;
	public static final int COLUMNA_SDO_PREVIO = 5;
	public static final int COLUMNA_B_SDO_APLICADO = 6;
	public static final int COLUMNA_SDO_INICIAL = 7;
	public static final int COLUMNA_CARGOS = 8;
	public static final int COLUMNA_ABONOS = 9;
	public static final int COLUMNA_SDO_FINAL = 10;
	public static final int COLUMNA_LIN_CRED = 11;
	public static final int COLUMNA_LIN_CRED_UTIL = 12;
	public static final int COLUMNA_LIN_CRED_DISP = 13;
	public static final int COLUMNA_DET_LIQUID = 14;

	public ActionListener btnLiquidacionesListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out
						.println("ejecuta el action listener del detalle de liq");
			}
		};
	}

	public void limpiaTabla(DsGrid tabla) {
		tabla.setColumnHeaderVisible(false);
		for (int i = tabla.getRowCount() - 1; i >= 0; i--) {
			tabla.removeRow(i);
		}
	}

	public void agregaHeader(DsGrid tabla) {
		tabla.setColumnHeaderVisible(false);
		// Encabezados en la primera fila
		tabla.addRow();
		tabla.setValueAt("Banxico", 0, 0);
		tabla.setValueAt("Contrato", 0, 1);
		tabla.setValueAt("Banco", 0, 2);
		tabla.setValueAt("Saldos para Liquidacion", 0, 3);
		tabla.setValueAt("Saldos Cuenta Digital", 0, 7);
		tabla.setValueAt("Linea de Credito", 0, 11);
		tabla.setValueAt("Liquidaciones", 0, 14);
		// Encabezados en la segunda fila
		tabla.addRow();
		tabla.setValueAt("Corto", 1, COLUMNA_SDO_CORTO);
		tabla.setValueAt("Largo", 1, COLUMNA_SDO_LARGO);
		tabla.setValueAt("Previo", 1, COLUMNA_SDO_PREVIO);
		tabla.setValueAt("Aplicado", 1, COLUMNA_B_SDO_APLICADO);
		tabla.setValueAt("Inicial", 1, COLUMNA_SDO_INICIAL);
		tabla.setValueAt("Cargo", 1, COLUMNA_CARGOS);
		tabla.setValueAt("Abono", 1, COLUMNA_ABONOS);
		tabla.setValueAt("Final", 1, COLUMNA_SDO_FINAL);
		tabla.setValueAt("$ Total", 1, COLUMNA_LIN_CRED);
		tabla.setValueAt("$ Utilizado", 1, COLUMNA_LIN_CRED_UTIL);
		tabla.setValueAt("$ Disponible", 1, COLUMNA_LIN_CRED_DISP);
		// Expansión de celdas de encabezado de primera fila
		tabla.spanCells(0, 0, 2, 1);
		tabla.spanCells(0, 1, 2, 1);
		tabla.spanCells(0, 2, 2, 1);
		tabla.spanCells(0, 3, 1, 4);
		tabla.spanCells(0, 7, 1, 4);
		tabla.spanCells(0, 11, 1, 3);
		tabla.spanCells(0, 14, 2, 1);
		// Propiedades de las celdas del enzabezado
		for (int col = 0; col <= 1; col++) {
			for (int row = 0; row < tabla.getColumnCount(); row++) {
				setLookCell(tabla, col, row, ConstantesI.VERDAD);
			}
		}
	}

	public void setLookCell(DsGrid tabla, int row, int col, String isNormal) {
		tabla.getCellAt(row, col).setFontBold(true);
		tabla.setColumnHorizontalAlignment(col, DsGrid.CENTER);
		if (isNormal.equals(ConstantesI.VERDAD)) {
			tabla.getCellAt(row, col).setForeground(Color.decode("#FFFFFF"));
			tabla.getCellAt(row, col).setBackground(Color.decode("#0066CC"));
		} else {
			tabla.getCellAt(row, col).setForeground(Color.decode("#FFFFFF"));
			tabla.getCellAt(row, col).setBackground(Color.RED);

		}
	}

	public void setLookCellError(DsGrid tabla, int row, int col) {
		tabla.getCellAt(row, col).setForeground(Color.RED);
		// tabla.getCellAt(row, col).setFontItalic(true);
		tabla.getCellAt(row, col).setFontBold(true);
	}

	public void agregaRenglonSaldos(DsGrid tabla, Registro saldo, String isTotal) {
		// System.out.ln("se agregará un renglón");
		// System.out.println(saldo.getDefCampo(Integer.toString(SaldoCCUSD.COL_ID_BANXICO)));
		tabla.addRow();
		// Agrega las columnas de importes
		setImporte(tabla, saldo, SaldoCCUSD.COL_SDO_CORTO, COLUMNA_SDO_CORTO);
		setImporte(tabla, saldo, SaldoCCUSD.COL_SDO_LARGO, COLUMNA_SDO_LARGO);
		setImporte(tabla, saldo, SaldoCCUSD.COL_SDO_INICIAL,
				COLUMNA_SDO_INICIAL);
		setImporte(tabla, saldo, SaldoCCUSD.COL_CARGOS, COLUMNA_CARGOS);
		setImporte(tabla, saldo, SaldoCCUSD.COL_ABONOS, COLUMNA_ABONOS);
		setImporte(tabla, saldo, SaldoCCUSD.COL_SDO_FINAL, COLUMNA_SDO_FINAL);
		setImporte(tabla, saldo, SaldoCCUSD.COL_LIN_CRED, COLUMNA_LIN_CRED);
		setImporte(tabla, saldo, SaldoCCUSD.COL_LIN_CRED_UTIL,
				COLUMNA_LIN_CRED_UTIL);
		setImporte(tabla, saldo, SaldoCCUSD.COL_LIN_CRED_DISP,
				COLUMNA_LIN_CRED_DISP);

		if (isPositivo(saldo, SaldoCCUSD.COL_SDO_PREVIO).equals(
				ConstantesI.VERDAD))
			setImporte(tabla, saldo, SaldoCCUSD.COL_SDO_PREVIO,
					COLUMNA_SDO_PREVIO);
		else {
			setImporte(tabla, saldo, SaldoCCUSD.COL_SDO_PREVIO,
					COLUMNA_SDO_PREVIO);
			setLookCellError(tabla, tabla.getRowCount() - 1, COLUMNA_SDO_PREVIO);
		}

		if (isTotal.equals(ConstantesI.FALSO)) {

			tabla.setValueAt(saldo.getDefCampo(Integer
					.toString(SaldoCCUSD.COL_ID_BANXICO)),
					tabla.getRowCount() - 1, COLUMNA_CVE_BANXICO);
			tabla.setValueAt(saldo.getDefCampo(Integer
					.toString(SaldoCCUSD.COL_REF_NUM_CONTRATO)), tabla
					.getRowCount() - 1, COLUMNA_CONTRATO);
			tabla.setValueAt(
					saldo.getDefCampo(Integer.toString(SaldoCCUSD.COL_BANCO)),
					tabla.getRowCount() - 1, COLUMNA_BANCO);
			tabla.setValueAt(ResourceManager.readImageIcon(saldo.getDefCampo(
					Integer.toString(SaldoCCUSD.COL_B_SDO_APLICADO)).equals(
					ConstantesI.FALSO) ? "imagenes/imgCancelar.gif"
					: "imagenes/imgSeleccionar.gif"), tabla.getRowCount() - 1,
					COLUMNA_B_SDO_APLICADO);

			JButton btnLiquidaciones = new JButton();
			btnLiquidaciones.setText("Detalle");
			btnLiquidaciones.addActionListener(btnLiquidacionesListener());
			// vista.dsOper.setValueAt(botonRespuesta,
			// vista.dsOper.getRowCount()-1, COLUMNA_CLIENTE);
			tabla.setValueAt(btnLiquidaciones, tabla.getRowCount() - 1,
					COLUMNA_DET_LIQUID);

			// Valida que el banco tenga una cuenta en monex
			if (saldo.getDefCampo(Integer.toString(SaldoCCUSD.COL_ID_CONTRATO)) == null) {
				for (int col = 0; col < tabla.getColumnCount(); col++) {
					setLookCellError(tabla, tabla.getRowCount() - 1, col);
				}
			}
		} else {
			// Renglón de totales
			tabla.setValueAt("TOTALES", tabla.getRowCount() - 1, 0);
			tabla.spanCells(tabla.getRowCount() - 1, 0, 1, 3);

			// Look del renglón igual que encabezado

			// Columnas que se imprimen siempre de manera normal
			setLookCell(tabla, tabla.getRowCount() - 1, COLUMNA_CVE_BANXICO,
					ConstantesI.VERDAD);
			setLookCell(tabla, tabla.getRowCount() - 1, COLUMNA_CONTRATO,
					ConstantesI.VERDAD);
			setLookCell(tabla, tabla.getRowCount() - 1, COLUMNA_BANCO,
					ConstantesI.VERDAD);
			setLookCell(tabla, tabla.getRowCount() - 1, COLUMNA_CVE_BANXICO,
					ConstantesI.VERDAD);
			setLookCell(tabla, tabla.getRowCount() - 1, COLUMNA_B_SDO_APLICADO,
					ConstantesI.VERDAD);
			setLookCell(tabla, tabla.getRowCount() - 1, COLUMNA_SDO_INICIAL,
					ConstantesI.VERDAD);
			setLookCell(tabla, tabla.getRowCount() - 1, COLUMNA_DET_LIQUID,
					ConstantesI.VERDAD);
			setLookCell(tabla, tabla.getRowCount() - 1, COLUMNA_LIN_CRED_UTIL,
					ConstantesI.VERDAD);
			setLookCell(tabla, tabla.getRowCount() - 1, COLUMNA_LIN_CRED_DISP,
					ConstantesI.VERDAD);
			setLookCell(tabla, tabla.getRowCount() - 1, COLUMNA_LIN_CRED,
					ConstantesI.VERDAD);
			setLookCell(tabla, tabla.getRowCount() - 1, COLUMNA_CARGOS,
					ConstantesI.VERDAD);
			setLookCell(tabla, tabla.getRowCount() - 1, COLUMNA_ABONOS,
					ConstantesI.VERDAD);
			setLookCell(tabla, tabla.getRowCount() - 1, COLUMNA_SDO_FINAL,
					ConstantesI.VERDAD);

			// Columnas que se validan para remarcarse en su caso
			setLookCell(
					tabla,
					tabla.getRowCount() - 1,
					COLUMNA_SDO_CORTO,
					isDiferenciaCero(saldo, SaldoCCUSD.COL_SDO_CORTO,
							SaldoCCUSD.COL_SDO_LARGO));
			setLookCell(
					tabla,
					tabla.getRowCount() - 1,
					COLUMNA_SDO_LARGO,
					isDiferenciaCero(saldo, SaldoCCUSD.COL_SDO_CORTO,
							SaldoCCUSD.COL_SDO_LARGO));
			// setLookCell(tabla, tabla.getRowCount()-1, COLUMNA_CARGOS,
			// isDiferenciaCero(saldo, SaldoCCUSD.COL_ABONOS,
			// SaldoCCUSD.COL_CARGOS));
			// setLookCell(tabla, tabla.getRowCount()-1, COLUMNA_ABONOS,
			// isDiferenciaCero(saldo, SaldoCCUSD.COL_ABONOS,
			// SaldoCCUSD.COL_CARGOS));
			setLookCell(tabla, tabla.getRowCount() - 1, COLUMNA_SDO_PREVIO,
					isPositivo(saldo, SaldoCCUSD.COL_SDO_PREVIO));
			// setLookCell(tabla, tabla.getRowCount()-1, COLUMNA_SDO_FINAL,
			// isPositivo(saldo, SaldoCCUSD.COL_SDO_FINAL));

			/*
			 * for (int col = 0; col < tabla.getColumnCount(); col++) { // Si la
			 * suma de los cargos es diferente a los abonos resaltar en rojo los
			 * datos setLookCell(tabla, tabla.getRowCount()-1, col,
			 * isDiferenciaCero(saldo, SaldoCCUSD.COL_ABONOS,
			 * SaldoCCUSD.COL_CARGOS)); }
			 */
		}
		// Apaga la bandera de columnas editables
		for (int col = 0; col < tabla.getColumnCount() - 1; col++) {
			tabla.getCellAt(tabla.getRowCount() - 1, col).setEditable(false);
		}
	}

	/**
	 * Valida si la diferencia entre dos columnas es cero
	 * 
	 * @param saldo
	 *            Registro
	 * @param iCol1
	 *            int
	 * @param iCol2
	 *            int
	 * @return String si la diferencia es cero regresa V, sino F
	 */
	public String isDiferenciaCero(Registro saldo, int iCol1, int iCol2) {
		double dDiferencia = Double.parseDouble((String) saldo
				.getDefCampo(Integer.toString(iCol1)))
				- Double.parseDouble((String) saldo.getDefCampo(Integer
						.toString(iCol2)));

		if (Math.rint(dDiferencia * 100) / 100 == 0)
			return ConstantesI.VERDAD;
		else
			return ConstantesI.FALSO;
	}

	public String isPositivo(Registro saldo, int iCol) {
		if (Math.rint(Double.parseDouble((String) saldo.getDefCampo(Integer
				.toString(iCol))) * 1) / 1 >= 0)
			return ConstantesI.VERDAD;
		else
			return ConstantesI.FALSO;
	}

	public void setImporte(DsGrid tabla, Registro saldo, int columnaBd,
			int columnaGrid) {
		// System.out.println(FormatoNumero.numeroDecimales(Math.rint(Double.parseDouble((String)saldo.getDefCampo(Integer.toString(columnaBd)))
		// * 100 / 100), 0, true));

		tabla.setValueAt(FormatoNumero.numeroDecimales( // Math.round(
				Double.parseDouble((String) saldo.getDefCampo(Integer
						.toString(columnaBd))) // )
				, 2, true),
		// FormatoNumero.numeroDecimales(Double.parseDouble((String)saldo.getDefCampo(Integer.toString(columnaBd))),0,true),
				tabla.getRowCount() - 1, columnaGrid);
		tabla.getCellAt(tabla.getRowCount() - 1, columnaGrid)
				.setHorizontalAlignment(DsGrid.RIGHT);
	}

	public String actualizaSaldos(String sFecha, DsGrid tabla,
			String sNombreConexion) {
		String sMensaje1 = null;
		String sMensaje2 = null;
		String sMensaje = null;

		sMensaje1 = consultaSaldos(sFecha, sNombreConexion);
		sMensaje2 = imprimeSaldos(tabla);

		System.out.print("sMensaje1[");
		System.out.print(sMensaje1);
		System.out.print("]sMensaje2[");
		System.out.print(sMensaje2);
		System.out.println("]");

		if (sMensaje1 != null && !sMensaje1.trim().equals("")) {
			if (sMensaje2 != null && !sMensaje2.trim().equals("")) {
				sMensaje = sMensaje1 + '\n' + sMensaje2;
			} else {
				sMensaje = sMensaje1.trim();
			}
		} else {
			sMensaje = null;
		}

		return sMensaje;
	}

	public String consultaSaldos(String sFecha, String sNombreConexion) {
		MatrizDatos saldos = null;

		System.out.println("consultaSaldos");

		implementacion = new MonitorLiquidacionesCCUSDImpl(sNombreConexion);

		System.out.println("Consulta los datos para el monitor");

		// Consulta los datos para el monitor
		saldos = implementacion.getMonitorSaldos(ConstantesI.ENTIDAD, sFecha);
		System.out.println("recupera los datos de la matriz");
		// Recupera los datos de la matriz
		if (saldos != null) {
			encabezado = saldos.getEncabezado();
			lista = saldos.getRegistros();
			totales = saldos.getTotales();
		}
		System.out.println("termina consultaSaldos");
		return implementacion.getDescripcionError();
	}

	@SuppressWarnings("rawtypes")
	public String imprimeSaldos(DsGrid tabla) {
		Iterator listaSaldos = null;
		String isRenglonTotal = null;

		limpiaTabla(tabla);
		// System.out.println("la lista e s " + lista);
		if (lista != null) {
			listaSaldos = lista.iterator();
			// Impresión de Encabezados
			agregaHeader(tabla);
			// Impresión del detalle de saldos
			isRenglonTotal = ConstantesI.FALSO;

			while (listaSaldos.hasNext()) {
				// System.out.println("itera sobre matriz de saldos");
				Registro saldo = (Registro) listaSaldos.next();
				agregaRenglonSaldos(tabla, saldo, isRenglonTotal);
			}
			// System.out.println("termina iterar la lista");
			// Impresión de saldos totales
			isRenglonTotal = ConstantesI.VERDAD;
			agregaRenglonSaldos(tabla, totales, isRenglonTotal);
		}
		// tabla.setColumnHeaderVisible(false);
		return implementacion.getDescripcionError();
	}

	public String consultaDetalle(String sFecha, String sNombreConexion,
			long idContrato, String sCveDivisa) {
		MatrizDatos detalle = null;

		implementacion = new MonitorLiquidacionesCCUSDImpl(sNombreConexion);

		// Consulta los datos
		detalle = implementacion.getLiquidaciones(ConstantesI.ENTIDAD, sFecha,
				idContrato, sCveDivisa);

		// Recupera los datos de la matriz
		if (detalle != null) {
			encabezado = detalle.getEncabezado();
			lista = detalle.getRegistros();
			totales = detalle.getTotales();
		}
		return implementacion.getDescripcionError();
	}

	/*
	 * public String getImporte(Registro saldo, int columna){ String sCol = "";
	 * Object campo = null; double dCampo = 0d; String sCampoFormateado = "";
	 * 
	 * sCol = Integer.toString(columna); campo = saldo.getDefCampo(sCol);
	 * System.out.println(campo); System.out.println(campo.getClass());
	 * 
	 * //dCampo = ((java.math.BigDecimal)campo).doubleValue(); dCampo =
	 * Double.parseDouble((String)campo); sCampoFormateado =
	 * FormatoNumero.numeroDecimales( dCampo, 0, true );
	 * 
	 * return sCampoFormateado;
	 * 
	 * return
	 * FormatoNumero.numeroDecimales(Double.parseDouble((String)saldo.getDefCampo
	 * (Integer.toString(columna))),0,true); }
	 */
}
