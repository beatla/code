package com.monex.bancoliquidador.cliente.view;

import com.monex.bancoliquidador.cliente.util.ConstantesI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JFrame;

public class PanelLogIn extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1719442409171875549L;
	public PanelLogInVista vista = null;
	public PanelMonitorLiquidacionesCCUSD frame;

	public void construyeVista() {
		vista = new PanelLogInVista();
		vista.btnAceptar.addActionListener(btnAceptarListener());
		vista.cboTipoConexion.addActionListener(cboTipoConexionListener());
	}

	public ActionListener btnAceptarListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (validaUsuario()) {
					abreMonitor();
				}
			}
		};
	}

	/*
	 * public ActionListener btnCancelarListener() { return new ActionListener()
	 * { public void actionPerformed(ActionEvent e) { vista.btnCancelar. } }; }
	 */

	public ActionListener cboTipoConexionListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		};
	}

	public void actionPerformed(ActionEvent ae) {
		abreMonitor();
	}

	@SuppressWarnings("deprecation")
	public void abreMonitor() {
		System.out.println("directorio seleccionado");
		System.out.println(vista.textDirectorio.getText());

		System.out.println("conexion seleccionada");
		System.out.println(vista.cboTipoConexion
				.getItemAt(vista.cboTipoConexion.getSelectedIndex()));

		MonitorLiquidacionesCCUSDImpl implementacion = new MonitorLiquidacionesCCUSDImpl(
				vista.cboTipoConexion.getItemAt(
						vista.cboTipoConexion.getSelectedIndex()).toString());

		if (vista.cboTipoConexion.getSelectedIndex() == 0) {
			String bContingenciaActiva = implementacion
					.isContingenciaActiva("MONEX");
			if (bContingenciaActiva.equals("F")) {
				frame = new PanelMonitorLiquidacionesCCUSD();
				frame.setDirectorio(vista.textDirectorio.getText());
				frame.setNombreConexion(vista.cboTipoConexion.getItemAt(
						vista.cboTipoConexion.getSelectedIndex()).toString());
				frame.construyeVista();
				frame.setSize(1000, 600);
				// Esconde la vista de login
				vista.setVisible(false);
				// Muestra el monitor
				frame.getVista().show();
			} else {
				vista.lblMensaje
						.setText("No puede accesar al sistema default,\n la contingencia está activa!");
			}
		} else {
			frame = new PanelMonitorLiquidacionesCCUSD();
			frame.setDirectorio(vista.textDirectorio.getText());
			frame.setNombreConexion(vista.cboTipoConexion.getItemAt(
					vista.cboTipoConexion.getSelectedIndex()).toString());
			frame.construyeVista();
			frame.setSize(1000, 600);
			// Esconde la vista de login
			vista.setVisible(false);
			// Muestra el monitor
			frame.getVista().show();

		}
	}

	public JFrame getVista() {
		return vista;
	}

	public void setSize(int i, int j) {
		vista.setSize(i, j);
	}

	public void setLocationRelativeTo(int i, int j) {
		vista.setLocation(i, j);
		// vista.setLocationRelativeTo(Component);
	}

	public boolean validaUsuario() {
		boolean isValido;
		char[] input = vista.pwdContrasenia.getPassword();
		if (isPasswordCorrect(input)) {
			vista.lblMensaje.setText("");
			isValido = true;
		} else {
			vista.lblMensaje
					.setText("Clave de Acceso Incorrecta!. Intente de nuevo.");
			isValido = false;
		}
		Arrays.fill(input, '0');
		vista.pwdContrasenia.selectAll();
		return isValido;
	}

	private static boolean isPasswordCorrect(char[] input) {
		boolean isCorrect = true;
		char[] correctPassword = ConstantesI.ACCESO_PWD;
		if (input.length != correctPassword.length) {
			isCorrect = false;
		} else {
			isCorrect = Arrays.equals(input, correctPassword);
		}
		Arrays.fill(correctPassword, '0');
		return isCorrect;
	}

}
