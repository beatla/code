package com.monex.bancoliquidador.bd.dao;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: Monex Casa de Bolsa, S.A. de C.V.</p>
 * @author Idalia Mora
 * @version 1.0
 */

import com.monex.comun.bd.*;

import java.sql.Types;
import java.sql.CallableStatement;
import java.sql.ResultSet;

import com.monex.bancoliquidador.vo.DiaInhabilEUAVO;

public class SaldosBancosDAO extends ConexionBean {
	public SaldosBancosDAO() {
		super();
	}

	public SaldosBancosDAO(String sIdEntidad) {
		super(sIdEntidad);
	}

	private StringBuffer buferMensaje = null;

	/**
	 * Aplicación de saldos cortos y largos por instrucción de CECOBAN
	 * 
	 * @param sFecha
	 *            String
	 * @param sTipoMovimiento
	 *            String
	 * @return long
	 */
	public int aplicaSaldos(String sFecha, String sTipoMovimiento) {
		int idGpoTransac = 0;
		this.sDetalleError = "";
		try {
			abreConexionCliente();
			CallableStatement cs = this.conn
					.prepareCall("{ call ? := PkgBancoLiquidadorCCUSD.AplicaCargosAbonos(?,?,?,?) }");
			// 1) Resultado del proceso
			cs.registerOutParameter(1, Types.NUMERIC);
			// 2) Entidad
			cs.setString(2, this.sIdEntidad);
			// 3) Fecha de Liquidación
			cs.setDate(3, ConversionDatos.toSQLDate(sFecha));
			System.out.println(sFecha);
			// 4) Tipo de Movimiento (C/A)
			cs.setString(4, sTipoMovimiento);
			System.out.println(sTipoMovimiento);
			// 5) Mensaje de salida
			cs.registerOutParameter(5, Types.VARCHAR);

			// Execute and retrieve the returned value
			cs.execute();

			// Si se aplico correctamente el proceso el resultado es el
			// id_grupo_transac, sino, -1
			idGpoTransac = Integer.parseInt(cs.getObject(1).toString());
			System.out.println("El idgrupotransac es");
			System.out.println(idGpoTransac);

			// Agrega el mensaje de salida al detalle de errores
			setDetalleError((String) cs.getObject(5));

			// Cierra la sentencia
			cs.close();
		} catch (Exception e) {
			System.out.println("Error en aplicaSaldos");
			System.out.println(e.toString());
			e.printStackTrace();

			setDetalleError("Problemas en la aplicacion de");
			setDetalleError(sTipoMovimiento.equals("C") ? "Saldos Cortos"
					: "Saldos Largos [");
			setDetalleError(e.toString());
			setDetalleError("]");
		} finally {
			cierraConexion();
		}

		return idGpoTransac;
	}

	public int cargaArchivo(String sFecha, String sTipoMovimiento) {
		int idResultado = 0;
		this.sDetalleError = "";
		try {

			System.out.print("cargaArchivo de . . ");
			System.out.println(sTipoMovimiento);
			abreConexionCliente();
			// CallableStatement cs =
			// this.conn.prepareCall("{ call ? := PkgBancoLiquidadorCCUSD.AltaArchivoCecoban(?,?,?) }");

			CallableStatement cs = this.conn
					.prepareCall("{ call ? := PKG_CECOBAN.AltaArchivoCecoban(?,?) }");

			// 1) Resultado del proceso
			cs.registerOutParameter(1, Types.NUMERIC);

			// 2) Nombre del archivo
			// if ( sTipoMovimiento.equals("A") ) {
			// cs.setString(2, "1ABONOSD.TXT");
			// }
			// else {
			// cs.setString(2, "1CARGOSD.TXT");
			// }

			// 2) Tipo de Movimiento (C/A)
			cs.setString(2, sTipoMovimiento);
			System.out.println(sTipoMovimiento);

			// 3) Mensaje de salida
			cs.registerOutParameter(3, Types.VARCHAR);

			// Execute and retrieve the returned value
			cs.execute();

			// Si se aplico correctamente el proceso el resultado es 0, sino, -1
			idResultado = Integer.parseInt(cs.getObject(1).toString());
			System.out.println("El resultado es");
			System.out.println(idResultado);

			// Agrega el mensaje de salida al detalle de errores
			setDetalleError((String) cs.getObject(3));

			System.out.println("resultado de la carga");
			System.out.println(cs.getObject(3));

			// Cierra la sentencia
			cs.close();
		} catch (Exception e) {
			System.out.println("Error en carga archivo");
			System.out.println(e.toString());
			e.printStackTrace();

			setDetalleError("Problemas en la carga de");
			setDetalleError(sTipoMovimiento.equals("C") ? "Saldos Cortos"
					: "Saldos Largos [");
			setDetalleError(e.toString());
			setDetalleError("]");
		} finally {
			cierraConexion();
		}

		return idResultado;
	}

	public int finLiquidacionDiaInhabilEUA(String sFecha) {
		int idResultado = 0;
		this.sDetalleError = "";
		try {
			abreConexionCliente();
			CallableStatement cs = this.conn
					.prepareCall("{ call ? := PkgBancoLiquidadorCCUSD.FinLiquidacionDiaInhabil(?,?,?) }");
			// 1) Resultado del proceso
			cs.registerOutParameter(1, Types.NUMERIC);
			// 2) Entidad
			cs.setString(2, this.sIdEntidad);
			// 3) Fecha de Liquidación
			cs.setDate(3, ConversionDatos.toSQLDate(sFecha));
			// 4) Mensaje de salida
			cs.registerOutParameter(4, Types.VARCHAR);

			// Execute and retrieve the returned value
			cs.execute();

			// Si se aplico correctamente el proceso el resultado es 0, sino, -1
			idResultado = Integer.parseInt(cs.getObject(1).toString());

			// Agrega el mensaje de salida al detalle de errores
			setDetalleError((String) cs.getObject(4));

			// Cierra la sentencia
			cs.close();
		} catch (Exception e) {
			System.out.println("Error en finLiquidacionDiaInhabilEUA");
			System.out.println(e.toString());
			e.printStackTrace();

			setDetalleError("Problemas al aplicar Fin a la Liquidación" + "n");
			setDetalleError("[");
			setDetalleError(e.toString());
			setDetalleError("]");
		} finally {
			cierraConexion();
		}

		return idResultado;
	}

	@SuppressWarnings("unused")
	public DiaInhabilEUAVO getDiaInhabilEUA(String sFechaLiquidacion) {
		String sFecha = "";
		DiaInhabilEUAVO inhabilVO = null;
		StringBuffer sbSql = null;

		this.sDetalleError = "";

		sbSql = new StringBuffer();
		sbSql.append("SELECT TO_CHAR(f_liquidacion, 'dd/mm/yyyy') AS f_liquidacion, TO_CHAR(f_inhabil, 'dd/mm/yyyy') AS f_inhabil, NVL(desc_inhabil, '') AS desc_inhabil, b_inhabil_liquidado");
		sbSql.append("\n  FROM cecoban_dia_inhabil_eua");
		sbSql.append("\n WHERE f_liquidacion = TO_DATE('")
				.append(sFechaLiquidacion).append("', 'dd/mm/yyyy')");

		try {
			abreConexionCliente();
			ejecutaSql(sbSql.toString());
			if (rs.next()) {
				inhabilVO = new DiaInhabilEUAVO();
				inhabilVO.setFLiquidacion(rs.getString("f_liquidacion"));
				inhabilVO.setFInhabil(rs.getString("f_inhabil"));
				inhabilVO.setDescInhabil(rs.getString("desc_inhabil"));
				inhabilVO.setIsInhabilLiquidado(rs
						.getString("b_inhabil_liquidado"));

				System.out.println("la fecha inhabil es ");
				System.out.println(inhabilVO.getFInhabil());
			}
			rs.close();
			rs = null;
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
			System.out.println("en sqlexception");
			System.out.println(this.getSDetalleError());
			setDetalleError(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("en Exception");
			System.out.println(this.getSDetalleError());
			setDetalleError(e.getMessage());
		} finally {
			cierraConexion();
		}
		return inhabilVO;
	}

	/**
	 * Método para consultar la fecha de la base de datos
	 * 
	 * @return String
	 */
	public String getFecha() {
		String sFecha = "";
		this.sDetalleError = "";
		StringBuffer sbSql = new StringBuffer();

		/*
		 * sbSql.append(
		 * "select TO_CHAR(f_operacion, 'dd/mm/yyyy') as f_operacion from corp_fecha_medio@corpo"
		 * );
		 * sbSql.append(" where cve_entidad = '").append(sIdEntidad).append("'"
		 * ); sbSql.append("   and cve_medio   = 'STORM'");
		 */

		sbSql.append("select TO_CHAR(f_liquidacion, 'dd/mm/yyyy') as f_operacion from cecoban_fecha");

		try {
			System.out.println("dao...:");
			System.out.println(this.getNombreConexion());
			abreConexionCliente();
			ejecutaSql(sbSql.toString());
			if (rs.next()) {
				System.out.println("la fecha consultada es ");
				sFecha = rs.getString("f_operacion");
				System.out.println(sFecha);
			}
			rs.close();
			rs = null;
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
			System.out.println("en sqlexception");
			System.out.println(this.getSDetalleError());
			setDetalleError(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("en Exception");
			System.out.println(this.getSDetalleError());
			setDetalleError(e.getMessage());
		} finally {
			cierraConexion();
		}
		return sFecha;
	}

	public int getIdLiquidacion(String sFecha) {

		int idResultado = 0;
		this.sDetalleError = "";
		try {
			abreConexionCliente();
			CallableStatement cs = this.conn
					.prepareCall("{ call ? := GetIdLiquidacion(?) }");
			// 1) Resultado del proceso
			cs.registerOutParameter(1, Types.NUMERIC);
			// 2) Fecha de Liquidación
			cs.setDate(2, ConversionDatos.toSQLDate(sFecha));

			// Execute and retrieve the returned value
			cs.execute();

			// Si se aplico correctamente el proceso el resultado es 0, sino, -1
			idResultado = Integer.parseInt(cs.getObject(1).toString());

			System.out.println("Id_Liquidacion =");
			System.out.println(idResultado);

			// Cierra la sentencia
			cs.close();
		} catch (Exception e) {
			System.out.println("Error en finLiquidacionDiaInhabilEUA");
			System.out.println(e.toString());
			e.printStackTrace();

			setDetalleError("Problemas en GetIdLiquidacion");
			setDetalleError("[");
			setDetalleError(e.toString());
			setDetalleError("]");
		} finally {
			cierraConexion();
		}

		return idResultado;

	}

	public MatrizDatos getLiquidaciones(String sFecha, long idContrato,
			String sCveDivisa) {
		MatrizDatos matriz = null;
		this.sDetalleError = "";

		try {
			abreConexionCliente();
			CallableStatement cs = this.conn
					.prepareCall("{ call ? := PkgBancoLiquidadorCCUSD.ObtieneDetalleLiquidaciones(?,?,?,?,?) }");
			// 1) Resultset
			cs.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
			// 2) Entidad
			cs.setString(2, this.sIdEntidad);
			// 3) Fecha de Liquidación
			cs.setDate(3, ConversionDatos.toSQLDate(sFecha));
			// 4) Contrato
			cs.setLong(4, idContrato);
			// 5) Divisa
			cs.setString(5, sCveDivisa);
			// 6) Mensaje de salida
			cs.registerOutParameter(6, Types.VARCHAR);

			// Execute and retrieve the returned value
			cs.execute();
			ResultSet rs = (ResultSet) cs.getObject(1);

			matriz = new MatrizDatos(rs);

			cs.close();
			rs.close();

		} catch (Exception e) {
			System.out.println("Error al obtener las liquidaciones");
			System.out.println(e.getMessage());
			e.printStackTrace();
			setDetalleError(e.getMessage());
		} finally {
			cierraConexion();
		}

		return matriz;

	}

	/**
	 * Método para consultar los datos del monitor de saldos
	 * 
	 * @param sFOperacion
	 *            String
	 * @return LinkedList
	 */
	public MatrizDatos getSaldos(String sFOperacion) {
		MatrizDatos matriz = null;
		this.sDetalleError = "";
		try {
			abreConexionCliente();
			CallableStatement cs = this.conn
					.prepareCall("{ call ? := PkgBancoLiquidadorCCUSD.ObtieneMonitorSaldos(?,?) }");
			cs.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);

			// Parametros de la función
			// 1) Entidad
			cs.setString(2, this.sIdEntidad);

			// System.out.println("Fecha");
			// System.out.println(sFOperacion);
			// System.out.println(ConversionDatos.toSQLDate(sFOperacion));

			// 2) Fecha de Operacion
			cs.setDate(3, ConversionDatos.toSQLDate(sFOperacion));

			// Execute and retrieve the returned value
			cs.execute();
			ResultSet rs = (ResultSet) cs.getObject(1);

			matriz = new MatrizDatos(rs);

			cs.close();
			rs.close();
		} catch (Exception e) {
			System.out.println("Error en el monitor");
			setDetalleError(e.getMessage());
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			cierraConexion();
		}

		return matriz;

	}

	/**
	 * Envía un mensaje a la variable pública sDetalleError
	 * 
	 * @param sMensaje
	 *            String
	 */
	public void setDetalleError(String sMensaje) {
		if (sMensaje != null && !sMensaje.trim().equals("")) {
			buferMensaje = new StringBuffer();
			buferMensaje.append(this.sDetalleError).append(" ");
			buferMensaje.append(sMensaje).append(" ");
			this.sDetalleError = buferMensaje.toString();
		}
	}

	/**
	 * Este método sirve para probar la conectividad a la base de datos
	 */
	public void validaFecha() {
		StringBuffer sbSql = new StringBuffer();
		this.sDetalleError = "";

		sbSql.append("select f_liquidacion from cecoban_fecha A");

		/*
		 * sbSql.append("select f_operacion from corp_fecha_medio@corpo A");
		 * sbSql.append(" where a.cve_entidad = 'MONEX'");
		 * sbSql.append(" AND A.CVE_MEDIO = 'STORM'");
		 */
		try {
			abreConexionCliente();
			System.out.println("Nombre de la conexion");
			System.out.println(this.getNombreConexion());
			ejecutaSql(sbSql.toString());
			if (rs.next()) {
				System.out.println("Resultado desde saldos bancos");
				System.out.println(rs.getString("f_operacion"));
			}
			rs.close();
			rs = null;
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
			setDetalleError(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			setDetalleError(e.getMessage());
		} finally {
			cierraConexion();
		}
	}

	public int ReversaArchivo(String sFecha, String sTipoMovimiento,
			String sTxtMotivo) {
		int idResult = 0;
		this.sDetalleError = "";
		try {
			abreConexionCliente();
			// CallableStatement cs =
			// this.conn.prepareCall("{ call ? := PkgBancoLiquidadorCCUSD.CancelaArchivoCecoban(?,?,?,?) }");
			CallableStatement cs = this.conn
					.prepareCall("{ call ? := PKG_CECOBAN.ReversaCarga(?,?,?,?) }");
			// 1) Resultado del proceso
			cs.registerOutParameter(1, Types.NUMERIC);

			// 2) Fecha de Liquidación
			cs.setDate(2, ConversionDatos.toSQLDate(sFecha));
			System.out.println(sFecha);

			// 3) Tipo de Movimiento (C/A)
			cs.setString(3, sTipoMovimiento);
			System.out.println(sTipoMovimiento);

			// 4) Motivo de cancelación de archivo
			cs.setString(4, sTxtMotivo);

			// 5) Mensaje de salida
			cs.registerOutParameter(5, Types.VARCHAR);

			// Execute and retrieve the returned value
			cs.execute();

			// Si se aplico correctamente el proceso el resultado es 0, sino, -1
			idResult = Integer.parseInt(cs.getObject(1).toString());
			System.out.println("El resultado es");
			System.out.println(idResult);

			// Agrega el mensaje de salida al detalle de errores
			setDetalleError((String) cs.getObject(5));

			// Cierra la sentencia
			cs.close();
		} catch (Exception e) {
			System.out.println("Error en ReversaArchivo");
			System.out.println(e.toString());
			e.printStackTrace();

			setDetalleError("Problemas en reversar los cargos [");
			setDetalleError(e.toString());
			setDetalleError("]");
		} finally {
			cierraConexion();
		}

		return idResult;
	}

	/**
	 * Desde el sistema default valida si la contingencia está activa
	 * 
	 * @return int
	 */
	public String isContingenciaActiva() {
		String isContingenciaActiva = "";
		this.sDetalleError = "";
		StringBuffer sbSql = new StringBuffer();

		sbSql.append("select isContingenciaActiva@CECOBAN as b_activa from dual");

		try {
			System.out.println(this.getNombreConexion());
			abreConexionCliente();
			ejecutaSql(sbSql.toString());
			if (rs.next()) {
				System.out.println("Contingencia Activa ");
				isContingenciaActiva = rs.getString("b_activa");
			}
			rs.close();
			rs = null;
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
			System.out.println("en sqlexception");
			System.out.println(this.getSDetalleError());
			setDetalleError(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("en Exception");
			System.out.println(this.getSDetalleError());
			setDetalleError(e.getMessage());
		} finally {
			cierraConexion();
		}
		return isContingenciaActiva;

	}

	/**
	 * Este método sirve para probar la conectividad a la base de datos
	 */
	public void pruebaAdminTransac() {
		StringBuffer sbSql = new StringBuffer();

		long startTime = 0;

		this.sDetalleError = "";

		sbSql.append("SELECT pkgadministradortransacciones.recuperafechahoy@corpo('MONEX') AS F_HOY FROM DUAL");

		try {
			abreConexionCliente();
			System.out.println("Tiempo transcurrido hasta la conexion"
					+ (System.currentTimeMillis() - startTime)
					+ " milli seconds");
			ejecutaSql(sbSql.toString());
			System.out.println("Tiempo transcurrido hasta la ejecución"
					+ (System.currentTimeMillis() - startTime)
					+ " milli seconds");
			if (rs.next()) {
				System.out.println(rs.getString("F_HOY"));
				System.out.println("Tiempo transcurrido hasta obtener el dato"
						+ (System.currentTimeMillis() - startTime)
						+ " milli seconds");
			}
			rs.close();
			rs = null;
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
			setDetalleError(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			setDetalleError(e.getMessage());
		} finally {
			cierraConexion();
		}
	}

}
