package com.monex.cliente.bd;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */

import java.io.*;

import javax.xml.parsers.*;

import org.xml.sax.*;

@SuppressWarnings("deprecation")
public class DatoConexionXML extends HandlerBase {
	private String nombre = "";
	private String host = "";
	private String port = "";
	private String serviceName = "";
	private String usuario = "";
	private String contrasenia = "";
	private String prioridad = "";

	public DatoConexionXML() {
		super();
	}

	/**
	 * Realiza la lectura de un archivo XML que contiene la definición de la
	 * conexion
	 * 
	 * @param sArchivoXML
	 *            Nombre y ruta del archivo XML.
	 */
	public DatoConexionXML(String sArchivoXML, String sNombreConexion) {
		nombre = sNombreConexion;
		// Uso del Validador
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser saxParser = factory.newSAXParser();
			saxParser.parse(new File(sArchivoXML), this);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	/**
	 * Realiza una busqueda sobre cada elemento del archivo XML
	 * <p>
	 * Este método es implementado del parser SAX.
	 */
	public void startElement(String name, AttributeList attrs)
			throws SAXException {
		System.out.println("DatoConexionXML: " + nombre);
		if (name.equals(nombre)) {
			if (attrs.getName(0).equals("nombre")) {
				this.nombre = attrs.getValue(0);
			}
			if (attrs.getName(1).equals("host")) {
				this.host = attrs.getValue(1);
			}
			if (attrs.getName(2).equals("port")) {
				this.port = attrs.getValue(2);
			}
			if (attrs.getName(3).equals("service-name")) {
				this.serviceName = attrs.getValue(3);
			}
			if (attrs.getName(4).equals("usuario")) {
				this.usuario = attrs.getValue(4);
			}
			if (attrs.getName(5).equals("contrasenia")) {
				this.contrasenia = attrs.getValue(5);
			}
			if (attrs.getName(6).equals("prioridad")) {
				this.prioridad = attrs.getValue(1);
			}
		}

	}

	public String getContrasenia() {
		return contrasenia;
	}

	public String getHost() {
		return host;
	}

	public String getNombre() {
		return nombre;
	}

	public String getPort() {
		return port;
	}

	public String getPrioridad() {
		return prioridad;
	}

	public String getUsuario() {
		return usuario;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setNombre(String sNombre) {
		nombre = sNombre;
	}

}
