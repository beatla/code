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

import java.io.*;
import java.sql.*;
import java.util.*;

import com.monex.aplic.admon.catalogo.*;

@SuppressWarnings("rawtypes")
public class MatrizDatos implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4966397369131350099L;

	LinkedList matriz = null;
	LinkedList registros = null;
	Registro encabezado = null;
	Registro totales = null;
	int numColumnas = 0;

	@SuppressWarnings("unchecked")
	public MatrizDatos(ResultSet rs) throws SQLException {
		matriz = new LinkedList();
		encabezado = new Registro();
		registros = new LinkedList();
		totales = new Registro();
		Registro registro = null;
		// implementa un arreglo que pueda almacenar hasta veinte valores dobles
		double[] dbTotales = new double[20];

		// Recupera el número de columnas del result set
		numColumnas = rs.getMetaData().getColumnCount();

		// Recuper los datos del resulset para regresar una lista
		if (rs != null) {

			// Encabezado
			for (int j = 1; j <= numColumnas; j++) {
				// Utiliza el nombre de las columnas del resulset como
				// encabezado
				encabezado.addDefCampo(Integer.toString(j), rs.getMetaData()
						.getColumnName(j));
				// System.out.println(rs.getMetaData().getColumnName(j));
				dbTotales[j] = 0;
			}

			// Campos
			while (rs.next()) {
				registro = new Registro();
				for (int i = 1; i <= numColumnas; i++) {
					// Los campos se identifican por el número de columna
					// registro.addDefCampo(Integer.toString(i),
					// rs.getObject(i));
					registro.addDefCampo(Integer.toString(i), rs.getString(i));
					// Actualización de acumulados por columna (solo si el tipo
					// de dato es número)
					if (rs.getMetaData().getColumnType(i) == java.sql.Types.DECIMAL
							|| rs.getMetaData().getColumnType(i) == java.sql.Types.NUMERIC
							|| rs.getMetaData().getColumnType(i) == java.sql.Types.FLOAT
							|| rs.getMetaData().getColumnType(i) == java.sql.Types.DOUBLE
							|| rs.getMetaData().getColumnType(i) == java.sql.Types.INTEGER) {
						dbTotales[i] += rs.getDouble(i);
					} else {
						dbTotales[i] += 1;
						// System.out.println(rs.getObject(i));
					}
				}
				registros.add(registro);
			}

			// Totales
			for (int k = 1; k <= numColumnas; k++) {
				totales.addDefCampo(Integer.toString(k),
						Double.toString(dbTotales[k]));
			}
			System.out
					.println("Termina con conversion de resulset en MatrizDatos");
		}

		matriz.add(0, encabezado);
		matriz.add(1, registros);
		matriz.add(2, totales);

	}

	public Registro getEncabezado() {
		return encabezado;
	}

	public LinkedList getMatriz() {
		return matriz;
	}

	public int getNumColumnas() {
		return numColumnas;
	}

	public LinkedList getRegistros() {
		return registros;
	}

	public Registro getTotales() {
		return totales;
	}

}
