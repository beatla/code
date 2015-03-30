/**
 * Title:        Administración Corporativa
 * Description:  Clase de excepción personalizada para la conexion.
 * Copyright:    Copyright (c) 2002
 * Company:      Monex Institución Cambiaria
 * @version      1.0
 * $Revision:    1.0 $
 * $Date: 2002/02/19 $
 *
 * ====================================================================
 *
 * Sistemas J2EE Corporativo Monex, Version 1.0
 *
 * Copyright (c) 2002 Monex Institución Cambiaria.  Todos los derechos
 * reservados.
 *
 * <ul>
 * <li>Creación 2002/02/19 Eleuterio Arellano.
 * </ul>
 */
package com.monex.comun.bd;

/**
 * Clase Bean que lanza una excepción personalizada sobre alguna operación
 * comúin de la conexión.
 */
public class ConexionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6551878789884056239L;

	public ConexionException() {
		super();
	}

	/**
	 * Constructor que genera una excepción a partir de un mensaje
	 * personalizado.
	 * 
	 * @param sExcepcion
	 *            Mensaje de la excepción.
	 */
	public ConexionException(String sExcepcion) { // Constructor con mensaje
		super(sExcepcion);
	}
}
