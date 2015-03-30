/**
 *   Copyright (c) 2001 Casa de Cambio Monex.
 *   Todos los derechos reservados.
 */

package com.monex.comun.bd;

import java.sql.*;
import java.util.*;
import javax.naming.*;
import javax.sql.*;

import com.monex.cliente.bd.*;

/**
 * Esta clase es la encargada de realizar la  conexion a la base de datos, de tal forma que todos
 * los JavaBeans o servlets que accesen a la base de datos deber�n heredar esta clase. La clase
 * proporciona al programador un statemet y resulset ya configurados, de tal forma que el programador
 * solo tenga que enviarle la consulta al resulset.
 * <p>
 * La clase que herede esta, deber� llamar al m�todo abreConexion() para preparar la conexion a la
 * base de datos (ver diferentes implementaciones para este m�todo.) Cuando se haya terminado de
 * utilizar la conexi�n a la base se datos el usuario deber� llamar al m�todo cierraConexi�n() para
 * eliminar la conexi�n y as� evitar dejar conexiones abiertas.
 * <p>
 * Cuando se desarrollen objetos que accesen a las bases de datos utilizando p�ginas jsp, se
 * deber� utilizar el m�todo abreConexion() pero con conexi�n a un pool.
 *
 * Para mayor informaci�n relacionada de como utilizar esta clase ver la
 * <a href="package-summary.html#package_description">descripci�n del package com.omnex.comun.bd</a>
 *
 * @author Joaqu�n Ponte D�az
 * @version 1.2     24/Ene/2001
 * <p>
 * <ul>
 *   <li> Eleuterio Arellano Salda�a, 19/02/2002, anexo propiedad para manejo de entidades.
 * <ul>
 */

/**
 * @version 1.3 07/07/2009
 * @author Idalia Mora
 *         <p>
 *         Description: Se agrega la opci�n de abrir una conexi�n para un modelo
 *         de dos capas (cliente - bd)
 *         </p>
 */

public class ConexionBean {

	/**
	 * Bandera que puede ser utilizada para indicar alta de datos
	 */
	protected static final int CON_MODO_ALTA = 1;
	/**
	 * Bandera que puede ser utilizada para indicar cambio de datos
	 */
	protected static final int CON_MODO_CAMBIO = 2;
	/**
	 * Bandera que puede ser utilizada para indicar eliminacion de datos
	 */
	protected static final int CON_MODO_BAJA = 3;
	/**
	 * Bandera que puede ser utilizada para indicar selecci�n de datos
	 */
	protected static final int CON_MODO_SELECCION = 3;
	/**
	 * Bandera que puede ser utilizada para indicar que no se han realizado
	 * cambio en los datos
	 */
	protected static final int CON_MODO_SIN_CAMBIO = 0;

	// CONFIGURACION PARA UTILIZAR LOS POOLS DE WEBLOGIC
	private boolean bConexionHeredada = false;
	/**
	 * Esta variable se utilizar� para construir las consultas a la base de
	 * datos.
	 */
	protected String sSql;
	/**
	 * Esta variable se utilizar� para saber el estado de la operaci�n (insert,
	 * delete, update). Para determinar tambi�n la ejecuci�n de las sentencias
	 * en otras capas.
	 */
	protected int iEstadoActual = CON_MODO_SIN_CAMBIO;
	/**
	 * Id del Error
	 */
	protected int iIdError = 0;
	/**
	 * Esta variable almecena el detalle del error.
	 */
	protected String sDetalleError = "";
	/**
	 * Resultset a la base de datos. El Resultset se podr� utilizar en cualquier
	 * clase que herede esta. El Resultset se abrir� automaticamente cuando la
	 * clase que hereda llame al m�todo abreConexion().
	 */
	protected ResultSet rs = null;
	/**
	 * Statement a la base de datos. El Statement se podr� utilizar en cualquier
	 * clase que herede esta. El Statemente se abrir� automaticamente cuando la
	 * clase que hereda llame al m�todo abreConexion().
	 */
	protected Statement st = null;

	/**
	 * Statement a la base de datos para procedimientos almacenados. El
	 * Statement se podr� utilizar en cualquier clase que herede esta. El
	 * Statemente se abrir� automaticamente cuando la clase que hereda llame al
	 * m�todo abreConexion().
	 */
	protected CallableStatement sto = null;

	/** Contiene la conexion a la base de datos */
	protected Connection conn;

	/** Contiene el contexto Inicial del JNDI */
	protected Context ic;

	/** Contiene el DataSource de la base de datos */
	protected DataSource ds;

	/**
	 * Esta variable se utilizar� para construir las consultas en base a la
	 * entidad definida a en esta.
	 */
	protected String sIdEntidad = null;

	/**
	 * Usuario firmado para las consultas.
	 */
	protected String sCveUsuario = null;

	// Propiedades para su uso en el cliente
	protected String nombreConexion = null;

	/**
	 * Constructor de la clase
	 */
	public ConexionBean() {
	}

	/**
	 * Constructor de la clase con opcion de trabajar con una entidad.
	 * Recomendado para realizar todos los accesos corporativos.
	 *
	 * @param sIdEntidad
	 *            Identificador de Entidad.
	 */
	public ConexionBean(String sIdEntidad) {
		this.sIdEntidad = sIdEntidad;
	}

	/**
	 * Obtiene directamente la conexion a la base de datos que se esta
	 * utilizando
	 *
	 * @return Apuntador a la base de datos
	 */
	public Connection getConexion() {
		return conn;
	}

	/**
	 * Asigna la conexion a la base de datos tomando una abierta por otro objeto
	 *
	 * @param cconn
	 *            Conexi�n a la base da datos
	 */
	public void setConexion(Connection cconn) {
		try {
			this.conn = cconn;
			bConexionHeredada = true;
			// CREA EL STATEMENT
			st = conn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Abre una conexion a la base de datos utilizando un pool de conexiones
	 *
	 * @param sPool
	 *            Nombre del Pool de conexiones
	 */
	public void abreConexion(String sPool) throws Exception {
		Class.forName("weblogic.jdbc.pool.Driver").newInstance();
		conn = DriverManager.getConnection("jdbc:weblogic:pool:" + sPool, null);
		// CREA EL STATEMENT
		st = conn.createStatement();
	}

	/**
	 * Abre una conexion a la base de datos utilizando un Datasource configurado
	 * para utilizar transacciones
	 *
	 * @param sDataSource
	 *            Nombre del DataSource en el servidor de aplicaciones
	 */
	public void abreConexionJts(String sDataSource) {
		try {
			ic = new InitialContext();
			ds = (DataSource) ic.lookup(sDataSource);
			conn = ds.getConnection();
			// CREA EL STATEMENT
			st = conn.createStatement();
		} catch (Exception ne) {
			ne.printStackTrace();
		}
	}

	/**
	 * Abre una conexion a la base de datos utilizando un Datasource configurado
	 * para utilizar transacciones por default.
	 * <p>
	 * Primero busca sobre el archivo
	 * [WLSERVER]/config/mydomain/applications/DBPoolMonex.xml Si no encuentra
	 * un Datasource definido, entonces, toma el Datasource default.
	 */
	/*
	 * public void abreConexionJts(){ //Observer que el archivo debe estar en la
	 * direcci�n config/mydomain/applications/DBPoolMonex.xml Properties p =
	 * System.getProperties(); Enumeration enu = p.elements();
	 * while(enu.hasMoreElements()){ System.out.println(enu.nextElement()); }
	 * 
	 * 
	 * //System.out.println("Usando el dominio="+System.getProperty("java.Domain"
	 * )+".xml"); DatoConexionXML datoConexionXML = new
	 * DatoConexionXML(System.getProperty("java.Domain")+".xml"); try{ if
	 * (datoConexionXML.getNombre().equals(""))
	 * abreConexionJts("corp.DSCorpTransaccional"); else
	 * abreConexionJts(datoConexionXML.getSValorPool()); }catch(Exception e){
	 * abreConexionJts("corp.DSCorpTransaccional"); } finally{ datoConexionXML =
	 * null; } }
	 */

	/**
	 * Abre una conexion a la base de datos utilizando un archivo de
	 * configuraci�n de conexiones
	 */
	public void abreConexionCliente() throws Exception {
		DatoConexionXML datoConexionXML = new DatoConexionXML(
				"conexion_bd.xml", this.nombreConexion);
		// datoConexionXML.setNombre();
		System.out.println("conexion bean: ");
		System.out.println(this.nombreConexion);
		try {
			if (!datoConexionXML.getNombre().equals("")) {
				this.nombreConexion = datoConexionXML.getNombre();
				abreConexionCliente(datoConexionXML.getHost(),
						datoConexionXML.getPort(),
						datoConexionXML.getServiceName(),
						datoConexionXML.getUsuario(),
						datoConexionXML.getContrasenia());
			} else {
				throw new Exception(
						"Falta la configuraci�n de la conexion a la base de datos");
			}
		} finally {
			datoConexionXML = null;
		}
	}

	public void abreConexionCliente(String sHost, String sPort,
			String sServiceName, String sUsuario, String sContrasenia) {
		try {
			StringBuffer sURL = new StringBuffer();
			sURL.append("jdbc:oracle:thin:@");
			sURL.append(sHost).append(":");
			sURL.append(sPort).append(":");
			sURL.append(sServiceName);

			System.out.println("url de conexion");
			System.out.println(sURL);

			DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
			conn = DriverManager.getConnection(sURL.toString(), sUsuario,
					sContrasenia);
			st = conn.createStatement();
		} catch (Exception ne) {
			System.out.println("excepcion en abreConexionCliente");
			this.iIdError = ne.hashCode();
			this.sDetalleError = ne.getMessage();
			System.out.println("Codigo de Error");
			System.out.println(this.iIdError);
			System.out.println("Detalle del error:");
			System.out.println(sDetalleError);

			ne.printStackTrace();
		}
	}

	/**
	 * Abre una nueva conexion a la base de datos utilizando el pool de
	 * desarrollo por default "CORP"
	 */
	public void abreConexion() throws Exception {
		abreConexion("CORP");
	}

	/**
	 * Abre una conexion directa a la base de datos
	 *
	 * @param sUsuario
	 *            Nombre del usuario
	 * @param sContrase�a
	 *            Password del usuario
	 * @param sServidor
	 *            Nombre del servidor de base de datos
	 */
	public void abreConexion(String sUsuario, String sContrase�a,
			String sServidor) throws Exception {
		Properties props = new Properties();
		props.put("user", sUsuario);
		props.put("password", sContrase�a);
		props.put("server", sServidor);
		Driver myDriver = (Driver) Class.forName("weblogic.jdbc.oci.Driver")
				.newInstance();
		conn = myDriver.connect("jdbc:weblogic:oracle", props);
		// CREA EL STATEMENT
		st = conn.createStatement();
	}

	/**
	 * Cierra la conexion a la base de datos. Si la conexion fue abierta por
	 * otro objeto y pasada a la clase por el metodo setConexion(), entonces
	 * solo se cerrar�n los objetos statement y resulset
	 */
	public void cierraConexion() {
		try {
			st.close();
			// VERIFICA SI LA CONEXION NO FUE HEREDADA POR OTRO OBJETO
			if (!bConexionHeredada) {
				// CIERRA LA CONEXION A LA BASE DE DATOS
				if (isConectado()) {
					conn.close();
				} else {
					System.out.println("La conexion ya no existe");
				}
			}
			conn = null;

		} catch (Exception e) {
			e.printStackTrace();
		}
		bConexionHeredada = false;
	}

	/**
	 * Verifica si la clase tiene hecha una conexion a la base de datos
	 */
	public boolean isConectado() {
		if (conn != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Ejecuta un procedimiento almacenado. La llamada a la base de datos se
	 * construye en la variable sSql y los car�cteres que se utilizan para
	 * indicar la naturaleza de los parametros son los siguientes:
	 * <ul type=disc>
	 * <li class=MsoNormal style='mso-margin-top-alt:auto;mso-margin-bottom-alt:auto; * mso-list:l1 level1 lfo2;tab-stops:list 36.0pt'>
	 * <span lang=ES-MX style='mso-ansi-language:ES-MX'>C= Char,
	 * VarChar<o:p></o:p></span></li>
	 * <li class=MsoNormal style='mso-margin-top-alt:auto;mso-margin-bottom-alt:auto; * mso-list:l1 level1 lfo2;tab-stops:list 36.0pt'>
	 * <span lang=ES-MX style='mso-ansi-language:ES-MX'>R=
	 * Cursor<o:p></o:p></span></li>
	 * <li class=MsoNormal style='mso-margin-top-alt:auto;mso-margin-bottom-alt:auto; * mso-list:l1 level1 lfo2;tab-stops:list 36.0pt'>
	 * I= Integer</li>
	 * <li class=MsoNormal style='mso-margin-top-alt:auto;mso-margin-bottom-alt:auto; * mso-list:l1 level1 lfo2;tab-stops:list 36.0pt'>
	 * F= Float</li>
	 * <li class=MsoNormal style='mso-margin-top-alt:auto;mso-margin-bottom-alt:auto; * mso-list:l1 level1 lfo2;tab-stops:list 36.0pt'>
	 * B= Double</li>
	 * <li class=MsoNormal style='mso-margin-top-alt:auto;mso-margin-bottom-alt:auto; * mso-list:l1 level1 lfo2;tab-stops:list 36.0pt'>
	 * D= Date</li>
	 * <li class=MsoNormal style='mso-margin-top-alt:auto;mso-margin-bottom-alt:auto; * mso-list:l1 level1 lfo2;tab-stops:list 36.0pt'>
	 * <span lang=ES-MX style='mso-ansi-language:ES-MX'>T=
	 * Time<o:p></o:p></span></li>
	 * </ul>
	 *
	 * La ejecuci�n del procedimiento almacenado se deposita en la variable sto.
	 * La variable sSql se modifica y se eliminan los caracteres utilizados para
	 * identificar la naturaleza de los par�metros
	 */
	public void ejecutaSp() throws Exception {
		int iIndice = 0;
		int[] iTipos = new int[10];
		String sTipo = "";
		String sTemp = "";
		int iTipo = 0;
		int iParametros = 0;
		int iTama�o = 0;

		// OBTIENE LA LISTA DE PARAMETROS DE SALIDA DE LA CADNA sSQL
		iTama�o = sSql.length();
		iIndice = sSql.indexOf("?");
		while (iIndice != -1) {
			sTipo = sSql.substring(iIndice + 1, iIndice + 2);

			/*
			 * VERIFICA QUE TIPO DE DATO ES EL DEL PARAMETRO. LOS PARAMETROS
			 * IMPLEMTADOS SON LOS SIGUIENTES: C= Char, VarChar R= Cursor I=
			 * Integer F= Float B= Double D= Date T= Time
			 */
			if (sTipo.equals("C")) {
				iTipo = java.sql.Types.CHAR;
			} else {
				if (sTipo.equals("R")) {
					iTipo = java.sql.Types.OTHER;
				} else {
					if (sTipo.equals("I")) {
						iTipo = java.sql.Types.INTEGER;
					} else {
						if (sTipo.equals("F")) {
							iTipo = java.sql.Types.FLOAT;
						} else {
							if (sTipo.equals("B")) {
								iTipo = java.sql.Types.DOUBLE;
							} else {
								if (sTipo.equals("T")) {
									iTipo = java.sql.Types.TIME;
								} else {
									iTipo = java.sql.Types.OTHER;
								}
							}
						}
					}
				}
			}
			sTemp = sTemp + sSql.substring(0, iIndice + 1);
			sSql = sSql.substring(iIndice + 2, iTama�o);

			iTipos[iParametros] = iTipo;
			iParametros++;

			iTama�o = sSql.length();
			iIndice = sSql.indexOf("?");
		}

		// COMPLETA LA LLAMADA AL PROCEDIMIENTO ALMACENADO
		if (iParametros > 0) {
			sSql = sTemp + sSql;
		}
		// PREPARA LA LLAMADA AL PROCEDIMIENTO ALMACENADO
		sto = conn.prepareCall(sSql);

		// REGISTRA LOS PARAMETROS DE SALIDA
		for (int iParametro = 0; iParametro < iParametros; iParametro++) {
			sto.registerOutParameter(iParametro + 1, iTipos[iParametro]);
		}
		// EJECUTA EL PROCEDIMIENTO ALMACENADO
		sto.execute();
	}

	/**
	 * Ejecuta un procedimiento almacenado. A diferencia del m�todo anterior
	 * este recibe como par�metro la llamada al procedimiento almacenado. Una
	 * vez ejecutado el m�todo la variable sSql queda con la cadena que se
	 * utiliz� para llamar al procedimiento almacenado
	 *
	 * @param sProcedimiento
	 *            Cadena que representa la llamada al procedimiento almacenado
	 */
	public void ejecutaSp(String sProcedimiento) throws Exception {
		sSql = sProcedimiento;
		ejecutaSp();
	}

	/**
	 * Ejecuta una setencia sql. Toma el valor de la variable sSql y ejecuta la
	 * consulta sobre la base de datos. El resultado de la consulta se podr�
	 * procesar a trav�s de la variable rs, la cual es de tipo ResultSet. Este
	 * m�todo emplea la variable st que es de tipo Statement para realizar la
	 * conexion a la base de datos
	 */
	public void ejecutaSql() throws Exception {
		try {
			ConexionException conexionException = new ConexionException(
					"\n** No se ha definido aun la entidad -- Utilice el constructor ConexionBean(String sIdEntidad)**\n");
			if (sIdEntidad == null) {
				throw conexionException;
			} else if (sIdEntidad.trim().equals("")) {
				throw conexionException;
			}

			rs = st.executeQuery(sSql);
			conexionException = null;
			this.iEstadoActual = ConexionBean.CON_MODO_SELECCION;
		} catch (ConexionException eCon) {
			System.out.println("\n\nError al ejecutar la consulta:");
			eCon.printStackTrace();
			this.iIdError = -999999;
		} catch (SQLException sqlE) {
			this.iIdError = sqlE.getErrorCode();
			this.sDetalleError = sqlE.getMessage();
			System.out
					.println("Error al ejecutar la siguiente actualizacion de datos:");
			System.out.println(sSql);
			System.out.println("Detalle del error:");
			System.out.println(sDetalleError);
		} catch (Exception e) {
			this.iIdError = e.hashCode();
			this.sDetalleError = e.getMessage();
			e.printStackTrace();
		}
	}

	/**
	 * Ejecuta una sentencia sql. A diferencia del m�todo anterior este recibe
	 * como par�metro la consulta SQL.
	 *
	 * @param sConsulta
	 *            Consulta SQL
	 */
	public void ejecutaSql(String sConsulta) throws Exception {
		try {
			ConexionException conexionException = new ConexionException(
					"\n** No se ha definido aun la entidad -- Utilice el constructor ConexionBean(String sIdEntidad)**\n");
			if (sIdEntidad == null) {
				throw conexionException;
			} else if (sIdEntidad.trim().equals("")) {
				throw conexionException;
			}

			rs = st.executeQuery(sConsulta);
			conexionException = null;
			this.iEstadoActual = ConexionBean.CON_MODO_SELECCION;
		} catch (ConexionException eCon) {
			System.out.println("\n\nError al ejecutar la consulta:");
			eCon.printStackTrace();
			this.iIdError = -999999;
		} catch (SQLException sqlE) {
			this.iIdError = sqlE.getErrorCode();
			this.sDetalleError = sqlE.getMessage();
			System.out
					.println("Error al ejecutar la siguiente actualizacion de datos:");
			System.out.println(sSql);
			System.out.println("Detalle del error:");
			System.out.println(sDetalleError);
		} catch (Exception e) {
			this.iIdError = e.hashCode();
			this.sDetalleError = e.getMessage();
			e.printStackTrace();
		}
	}

	/**
	 * Ejecuta una setencia sql. Toma el valor de la variable sSql y ejecuta la
	 * consulta sobre la base de datos, regresando un ResultSet. Este m�todo
	 * emplea la variable st que es de tipo Statement para realizar la conexion
	 * a la base de datos
	 */
	public ResultSet ejecutaSqlRs() throws Exception {
		try {
			ConexionException conexionException = new ConexionException(
					"\n** No se ha definido aun la entidad -- Utilice el constructor ConexionBean(String sIdEntidad)**\n");
			if (sIdEntidad == null) {
				throw conexionException;
			} else if (sIdEntidad.trim().equals("")) {
				throw conexionException;
			}

			conexionException = null;
			this.iEstadoActual = ConexionBean.CON_MODO_CAMBIO;
			return st.executeQuery(sSql);
		} catch (ConexionException eCon) {
			System.out.println("\n\nError al ejecutar la consulta:");
			eCon.printStackTrace();
			this.iIdError = -999999;
			return null;
		} catch (SQLException sqlE) {
			this.iIdError = sqlE.getErrorCode();
			this.sDetalleError = sqlE.getMessage();
			System.out
					.println("Error al ejecutar la siguiente actualizacion de datos:");
			System.out.println(sSql);
			System.out.println("Detalle del error:");
			System.out.println(sDetalleError);
			return null;
		} catch (Exception e) {
			this.iIdError = e.hashCode();
			this.sDetalleError = e.getMessage();
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Ejecuta una setencia sql de INSERT, UPDATE o DELETE. Toma el valor de la
	 * variable sSql y ejecuta la consulta sobre la base de datos. El resultado
	 * de la consulta ser� el n�mero de registros procesados por las sentencias
	 * INSERT, UPDATE o DELETE, o 0 para una setentencia que no regresa alg�n
	 * valor cual es de tipo ResultSet. Este m�todo emplea la variable st que es
	 * de tipo Statement para realizar la conexion a la base de datos
	 */
	public int ejecutaUpdate() {
		int iNumRegistros = 0;
		try {
			ConexionException conexionException = new ConexionException(
					"\n** No se ha definido aun la entidad -- Utilice el constructor ConexionBean(String sIdEntidad)**\n");
			if (sIdEntidad == null) {
				throw conexionException;
			} else if (sIdEntidad.trim().equals("")) {
				throw conexionException;
			}

			conexionException = null;
			iNumRegistros = st.executeUpdate(sSql);
			this.iEstadoActual = ConexionBean.CON_MODO_CAMBIO;
		} catch (ConexionException eCon) {
			System.out.println("\n\nError al ejecutar la consulta:");
			eCon.printStackTrace();
			this.iIdError = -999999;
		} catch (SQLException sqlE) {
			this.iIdError = sqlE.getErrorCode();
			this.sDetalleError = sqlE.getMessage();
			System.out
					.println("Error al ejecutar la siguiente actualizacion de datos:");
			System.out.println(sSql);
			System.out.println("Detalle del error:");
			System.out.println(sDetalleError);
		} catch (Exception e) {
			this.iIdError = e.hashCode();
			this.sDetalleError = e.getMessage();
			e.printStackTrace();
		}
		return iNumRegistros;
	}

	/**
	 * Obtener el estado actual de la ejecuci�n de un query.
	 * 
	 * @return Estado Actual
	 */
	public int getIEstadoActual() {
		return iEstadoActual;
	}

	/**
	 * Retorna el numero de error SQL o generico en una ejecuci�n anormal del
	 * query.
	 * 
	 * @return Numero de Error de la BD o Generico
	 */
	public int getIIdError() {
		return iIdError;
	}

	public String getNombreConexion() {
		return nombreConexion;
	}

	/**
	 * Obtiene el detalle del error en caso de no mostrarlo en la consola
	 * 
	 * @return
	 */
	public String getSDetalleError() {
		return sDetalleError;
	}

	/**
	 * Obtiene la clave de usuario que actualiza
	 * 
	 * @return
	 */
	public String getSCveUsuario() {
		return sCveUsuario;
	}

	/**
	 * ACtualiza la clave de usuario
	 */
	public void setSCveUsuario(String sCveUsuario) {
		this.sCveUsuario = sCveUsuario;
	}

	public void setNombreConexion(String sNombreConexion) {
		this.nombreConexion = sNombreConexion;
	}

}
