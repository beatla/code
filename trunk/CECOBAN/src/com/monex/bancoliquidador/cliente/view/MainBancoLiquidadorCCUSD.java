package com.monex.bancoliquidador.cliente.view;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * <p>
 * Title: Banco Liquidador de la Cámara de Compensación en Dólares
 * </p>
 * <p>
 * Description: El objetivo de la aplicación es monitorear y gestionar las
 * liquidaciones de los bancos por instrucción de CECOBAN
 * </p>
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * <p>
 * Company: Grupo Financiero Monex
 * </p>
 * 
 * @author Idalia Mora
 * @version 1.0
 */
public class MainBancoLiquidadorCCUSD {

	public PanelLogIn frameLogIn;

	/**
	 * Construct and show the application.
	 */
	@SuppressWarnings("deprecation")
	public MainBancoLiquidadorCCUSD() {

		frameLogIn = new PanelLogIn();
		frameLogIn.construyeVista();
		frameLogIn.setSize(450, 300);
		frameLogIn.setLocationRelativeTo(300, 300);
		frameLogIn.getVista().show();
	}

	/**
	 * Application entry point.
	 *
	 * @param args String[]
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception exception) {
					exception.printStackTrace();
				}
				new MainBancoLiquidadorCCUSD();
			}
		});
	}
}
