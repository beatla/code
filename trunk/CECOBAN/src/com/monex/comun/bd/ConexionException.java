/**
 * Title:        Administraci�n Corporativa
 * Description:  Clase de excepci�n personalizada para la conexion.
 * Copyright:    Copyright (c) 2002
 * Company:      Monex Instituci�n Cambiaria
 * @version      1.0
 * $Revision:    1.0 $
 * $Date: 2002/02/19 $
 *
 * ====================================================================
 *
 * Sistemas J2EE Corporativo Monex, Version 1.0
 *
 * Copyright (c) 2002 Monex Instituci�n Cambiaria.  Todos los derechos
 * reservados.
 *
 * <ul>
 * <li>Creaci�n 2002/02/19 Eleuterio Arellano.
 * </ul>
 */
package com.monex.comun.bd;

/**
 * Clase Bean que lanza una excepci�n personalizada sobre alguna operaci�n
 * com�in de la conexi�n.
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
	 * Constructor que genera una excepci�n a partir de un mensaje
	 * personalizado.
	 * 
	 * @param sExcepcion
	 *            Mensaje de la excepci�n.
	 */
	public ConexionException(String sExcepcion) { // Constructor con mensaje
		super(sExcepcion);
	}
}
