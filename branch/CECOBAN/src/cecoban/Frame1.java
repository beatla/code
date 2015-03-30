package cecoban;

import java.awt.*;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

import com.monex.bancoliquidador.bd.dao.SaldosBancosDAO;
import com.monex.bancoliquidador.cliente.view.PanelMonitorLiquidacionesCCUSD;

/**
 * <p>
 * Title:
 * </p>
 *
 * <p>
 * Description:
 * </p>
 *
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 *
 * <p>
 * Company:
 * </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class Frame1 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8273214558516542052L;
	JPanel contentPane;
	BorderLayout borderLayout1 = new BorderLayout();
	JRadioButton jRadioButton1 = new JRadioButton();
	JTextField jTextField1 = new JTextField();
	JFrame vista = null;

	public PanelMonitorLiquidacionesCCUSD win;

	public Frame1() {
		try {
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			jbInit();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private void conectar() throws Exception {

		String connect_string = "jdbc:oracle:thin:@200.77.225.106:1522:DCORPO";
		DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
		// DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());
		// new oracle.jdbc.OracleDriver
		// new oracle.jdbc.driver.OracleDriver
		Connection conn = DriverManager.getConnection(connect_string, "corpo",
				"dcorpo");
		// driver@machineName:port:SID , userid, password
		// URL="jdbc:oracle:thin:@200.77.225.106:1522:DCORPO"
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt
				.executeQuery("select f_operacion from corp_fecha_medio A where a.cve_entidad = 'MONEX' AND A.CVE_MEDIO = 'STORM'");

		while (rset.next()) {
			System.out.println(" eSTA ES LA FECHA STORM");
			System.out.println(rset.getString("f_operacion")); // Print col 1
		}
		stmt.close();
	}

	private void getFecha() {
		SaldosBancosDAO dao = new SaldosBancosDAO("MONEX");
		dao.validaFecha();
	}

	/**
	 * Component initialization.
	 *
	 * @throws java.lang.Exception
	 */
	private void jbInit() throws Exception {
		contentPane = (JPanel) getContentPane();
		contentPane.setLayout(borderLayout1);
		setSize(new Dimension(400, 300));
		setTitle("Hey !!! This is me");
		jRadioButton1.setText("jRadioButton1");
		jTextField1.setText("jTextField1");
		jTextField1
				.addActionListener(new Frame1_jTextField1_actionAdapter(this));
		contentPane.add(jRadioButton1, java.awt.BorderLayout.NORTH);
		contentPane.add(jTextField1, java.awt.BorderLayout.CENTER);
		getFecha();
		// conectar();

		// PruebaCliente cte = new PruebaCliente();
		abreVentana();

	}

	@SuppressWarnings("deprecation")
	public void abreVentana() {

		win = new PanelMonitorLiquidacionesCCUSD();
		win.construyeVista();
		vista = win.getVista();
		win.vista.show();
	}

	public void jTextField1_actionPerformed(ActionEvent actionEvent) {

	}
}

class Frame1_jTextField1_actionAdapter implements ActionListener {
	private Frame1 adaptee;

	Frame1_jTextField1_actionAdapter(Frame1 adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent actionEvent) {
		adaptee.jTextField1_actionPerformed(actionEvent);
	}
}
