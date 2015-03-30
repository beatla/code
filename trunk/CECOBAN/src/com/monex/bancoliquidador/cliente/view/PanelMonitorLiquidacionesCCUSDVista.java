/*
 * Created by JFormDesigner on Tue Jul 21 01:59:59 GMT 2009
 */

package com.monex.bancoliquidador.cliente.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;
import diamondedge.swing.grid.*;
import lt.monarch.swing.*;

/**
 * @author Eleuterio Arellano Saldaña
 */
public class PanelMonitorLiquidacionesCCUSDVista extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6982672393507944391L;

	public PanelMonitorLiquidacionesCCUSDVista() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY
		// //GEN-BEGIN:initComponents
		DefaultComponentFactory compFactory = DefaultComponentFactory
				.getInstance();
		panel3 = new JPanel();
		frmTituloLogo = compFactory.createTitle("");
		panelTitulo = new JPanel();
		lblTituloForma = new JLabel();
		lblSistema = new JLabel();
		panelFechaInhabil = new JPanel();
		lblFechaOperacion = new JLabel();
		textFechaOperacion = new JTextField();
		btnFinLiquidacion = new JButton();
		lblDescInhabil = new JLabel();
		lblFecha = new JLabel();
		panelArchivos2 = new JPanel();
		label5 = new JLabel();
		textImpComision = new JTextField();
		btnComisiones = new JButton();
		panelArchivos = new JPanel();
		cbReporte = new JComboBox();
		btnArchivo = new JButton();
		panel1 = new JPanel();
		radioAplicaCortos = new JRadioButton();
		radioAplicaLargos = new JRadioButton();
		btnCarga = new JButton();
		btnAcuse = new JButton();
		btnDetalleTrans = new JButton();
		btnAplicaCA = new JButton();
		btnCancela = new JButton();
		btnBalance = new JButton();
		panel2 = new JPanel();
		dateFechaSaldos = new JDateField();
		checkRefresh = new JCheckBox();
		btnConsulta = new JButton();
		btnLiquidaciones = new JButton();
		scrollPane1 = new JScrollPane();
		dsSaldos = new DsGrid(0, 12);
		lblMensaje = new JLabel();
		barra = new JProgressBar();
		CellConstraints cc = new CellConstraints();

		// ======== this ========
		setBackground(new Color(0, 137, 207));
		setTitle("Monitor de Gestion y Control de Liquidaciones en Dolares de la Camara de Compensacion");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Container contentPane = getContentPane();
		contentPane.setLayout(new FormLayout("default:grow", "default"));

		// ======== panel3 ========
		{
			panel3.setBackground(new Color(0, 137, 207));
			panel3.setBorder(new EtchedBorder());
			panel3.setLayout(new FormLayout(new ColumnSpec[] {
					new ColumnSpec(ColumnSpec.LEFT, Sizes.DEFAULT,
							FormSpec.DEFAULT_GROW),
					FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
					new ColumnSpec(ColumnSpec.LEFT, Sizes.DEFAULT,
							FormSpec.DEFAULT_GROW),
					new ColumnSpec(ColumnSpec.LEFT, Sizes.DLUX3,
							FormSpec.NO_GROW),
					new ColumnSpec(ColumnSpec.CENTER, Sizes.DEFAULT,
							FormSpec.DEFAULT_GROW),
					new ColumnSpec(ColumnSpec.LEFT, Sizes.DLUX3,
							FormSpec.NO_GROW),
					new ColumnSpec(ColumnSpec.RIGHT, Sizes.DEFAULT,
							FormSpec.DEFAULT_GROW),
					FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
					new ColumnSpec(ColumnSpec.RIGHT, Sizes.DEFAULT,
							FormSpec.DEFAULT_GROW) }, new RowSpec[] {
					FormFactory.DEFAULT_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
					FormFactory.DEFAULT_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
					FormFactory.DEFAULT_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
					FormFactory.DEFAULT_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
					FormFactory.DEFAULT_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
					FormFactory.DEFAULT_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
					FormFactory.LINE_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC }));

			// ---- frmTituloLogo ----
			frmTituloLogo.setIcon(new ImageIcon(getClass().getResource(
					"/imagenes/logomonex_popw2.jpg")));
			panel3.add(frmTituloLogo, cc.xy(5, 1));

			// ======== panelTitulo ========
			{
				panelTitulo.setBackground(Color.white);
				panelTitulo.setForeground(new Color(0, 137, 207));
				panelTitulo
						.setLayout(new FormLayout(
								new ColumnSpec[] { FormFactory.GROWING_BUTTON_COLSPEC },
								RowSpec.decodeSpecs("default")));

				// ---- lblTituloForma ----
				lblTituloForma
						.setText("Liquidaciones en D\u00f3lares de la C\u00e1mara de Compensaci\u00f3n");
				lblTituloForma.setForeground(new Color(0, 137, 207));
				lblTituloForma.setFont(new Font("Tahoma", Font.BOLD, 12));
				lblTituloForma.setHorizontalAlignment(SwingConstants.CENTER);
				panelTitulo.add(lblTituloForma, cc.xy(1, 1));
			}
			panel3.add(panelTitulo, cc.xywh(1, 2, 9, 1));

			// ---- lblSistema ----
			lblSistema.setText("Sistema");
			lblSistema.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblSistema.setForeground(Color.white);
			lblSistema.setHorizontalAlignment(SwingConstants.CENTER);
			panel3.add(lblSistema, cc.xywh(5, 3, 1, 1, CellConstraints.FILL,
					CellConstraints.DEFAULT));

			// ======== panelFechaInhabil ========
			{
				panelFechaInhabil.setBackground(new Color(0, 137, 207));
				panelFechaInhabil.setBorder(new TitledBorder(null,
						"Dia inhabil en EUA", TitledBorder.LEADING,
						TitledBorder.TOP, new Font("Tahoma", Font.BOLD, 10),
						Color.white));
				panelFechaInhabil.setLayout(new FormLayout(new ColumnSpec[] {
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC }, RowSpec
						.decodeSpecs("default")));

				// ---- lblFechaOperacion ----
				lblFechaOperacion.setText("Fecha de Operaci\u00f3n");
				lblFechaOperacion.setFont(new Font("Tahoma", Font.PLAIN, 10));
				lblFechaOperacion.setForeground(Color.white);
				lblFechaOperacion.setHorizontalAlignment(SwingConstants.CENTER);
				panelFechaInhabil.add(lblFechaOperacion, cc.xy(1, 1));

				// ---- textFechaOperacion ----
				textFechaOperacion.setEditable(false);
				textFechaOperacion.setEnabled(false);
				textFechaOperacion.setFont(new Font("Tahoma", Font.BOLD, 10));
				textFechaOperacion.setForeground(new Color(0, 137, 206));
				panelFechaInhabil.add(textFechaOperacion, cc.xy(3, 1));

				// ---- btnFinLiquidacion ----
				btnFinLiquidacion.setText("Fin Liquidaci\u00f3n");
				btnFinLiquidacion.setFont(new Font("Tahoma", Font.BOLD, 9));
				btnFinLiquidacion.setForeground(new Color(0, 137, 206));
				btnFinLiquidacion.setIcon(new ImageIcon(getClass().getResource(
						"/imagenes/images/imgMovimientos.gif")));
				btnFinLiquidacion
						.setToolTipText("Reporte Balance de Cuenta Concentradora");
				panelFechaInhabil.add(btnFinLiquidacion, cc.xy(5, 1));

				// ---- lblDescInhabil ----
				lblDescInhabil.setText("Dia ...");
				lblDescInhabil.setFont(new Font("Tahoma", Font.PLAIN, 10));
				lblDescInhabil.setForeground(Color.white);
				lblDescInhabil.setHorizontalAlignment(SwingConstants.CENTER);
				panelFechaInhabil.add(lblDescInhabil, cc.xy(7, 1));
			}
			panel3.add(panelFechaInhabil, cc.xywh(1, 4, 9, 1,
					CellConstraints.FILL, CellConstraints.DEFAULT));

			// ---- lblFecha ----
			lblFecha.setText("Fecha de Liquidaci\u00f3n: ");
			lblFecha.setFont(new Font("Tahoma", Font.BOLD, 10));
			lblFecha.setForeground(Color.white);
			lblFecha.setHorizontalAlignment(SwingConstants.CENTER);
			panel3.add(lblFecha, cc.xywh(5, 5, 1, 1, CellConstraints.FILL,
					CellConstraints.DEFAULT));

			// ======== panelArchivos2 ========
			{
				panelArchivos2.setBorder(new TitledBorder(null, "Comisiones",
						TitledBorder.LEADING, TitledBorder.TOP, new Font(
								"Tahoma", Font.BOLD, 10), Color.white));
				panelArchivos2.setBackground(new Color(0, 137, 207));
				panelArchivos2.setLayout(new FormLayout(new ColumnSpec[] {
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC }, RowSpec
						.decodeSpecs("fill:default")));

				// ---- label5 ----
				label5.setText(" $ Comisi\u00f3n");
				label5.setFont(new Font("Tahoma", Font.PLAIN, 9));
				label5.setForeground(Color.white);
				label5.setToolTipText("Importe de comisi\u00f3n unitaria por transferencia");
				panelArchivos2.add(label5, cc.xywh(1, 1, 1, 1,
						CellConstraints.FILL, CellConstraints.DEFAULT));

				// ---- textImpComision ----
				textImpComision.setFont(new Font("Tahoma", Font.PLAIN, 10));
				textImpComision.setText("4.50");
				textImpComision.setMinimumSize(new Dimension(30, 18));
				panelArchivos2.add(textImpComision, cc.xy(3, 1));

				// ---- btnComisiones ----
				btnComisiones.setText("Aceptar");
				btnComisiones.setFont(new Font("Tahoma", Font.BOLD, 9));
				btnComisiones.setForeground(new Color(0, 137, 206));
				panelArchivos2.add(btnComisiones, cc.xywh(7, 1, 1, 1,
						CellConstraints.CENTER, CellConstraints.DEFAULT));
			}
			panel3.add(panelArchivos2, cc.xywh(1, 6, 9, 1,
					CellConstraints.FILL, CellConstraints.FILL));

			// ======== panelArchivos ========
			{
				panelArchivos.setBorder(new TitledBorder(null, "Reportes",
						TitledBorder.LEADING, TitledBorder.TOP, new Font(
								"Tahoma", Font.BOLD, 10), Color.white));
				panelArchivos.setBackground(new Color(0, 137, 207));
				panelArchivos.setForeground(Color.white);
				panelArchivos.setLayout(new FormLayout(new ColumnSpec[] {
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC }, RowSpec
						.decodeSpecs("default")));

				// ---- cbReporte ----
				cbReporte.setModel(new DefaultComboBoxModel(new String[] {
						"Acuse de Recibo", "Detalle de lo Transmitido",
						"Estatus de Fondos", "Balance de Cuenta Liquidadora",
						"Detalle de Transaccionalidad", "Historico de Saldos",
						"Seguimiento de Lineas de Credito Intradia" }));
				cbReporte.setFont(new Font("Tahoma", Font.PLAIN, 9));
				panelArchivos.add(cbReporte, cc.xywh(1, 1, 1, 1,
						CellConstraints.CENTER, CellConstraints.DEFAULT));

				// ---- btnArchivo ----
				btnArchivo.setText("Aceptar");
				btnArchivo.setFont(new Font("Tahoma", Font.BOLD, 9));
				btnArchivo.setForeground(new Color(0, 137, 206));
				panelArchivos.add(btnArchivo, cc.xywh(3, 1, 1, 1,
						CellConstraints.CENTER, CellConstraints.DEFAULT));
			}
			panel3.add(panelArchivos, cc.xywh(1, 7, 9, 1, CellConstraints.FILL,
					CellConstraints.FILL));

			// ======== panel1 ========
			{
				panel1.setBorder(new TitledBorder(null,
						"Saldos para Liquidacion", TitledBorder.LEADING,
						TitledBorder.TOP, new Font("Tahoma", Font.BOLD, 10),
						Color.white));
				panel1.setBackground(new Color(0, 137, 207));
				panel1.setLayout(new FormLayout(new ColumnSpec[] {
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC }, RowSpec
						.decodeSpecs("default")));

				// ---- radioAplicaCortos ----
				radioAplicaCortos.setText("Cargos");
				radioAplicaCortos.setFont(new Font("Tahoma", Font.PLAIN, 9));
				radioAplicaCortos.setForeground(Color.white);
				radioAplicaCortos.setBackground(new Color(0, 137, 207));
				radioAplicaCortos.setHorizontalAlignment(SwingConstants.LEFT);
				panel1.add(radioAplicaCortos, cc.xywh(1, 1, 1, 1,
						CellConstraints.LEFT, CellConstraints.DEFAULT));

				// ---- radioAplicaLargos ----
				radioAplicaLargos.setText("Abonos");
				radioAplicaLargos.setFont(new Font("Tahoma", Font.PLAIN, 9));
				radioAplicaLargos.setForeground(Color.white);
				radioAplicaLargos.setBackground(new Color(0, 137, 207));
				radioAplicaLargos.setHorizontalAlignment(SwingConstants.LEFT);
				panel1.add(radioAplicaLargos, cc.xywh(3, 1, 1, 1,
						CellConstraints.LEFT, CellConstraints.DEFAULT));

				// ---- btnCarga ----
				btnCarga.setText("Carga");
				btnCarga.setFont(new Font("Tahoma", Font.BOLD, 9));
				btnCarga.setForeground(new Color(0, 137, 206));
				btnCarga.setIcon(new ImageIcon(getClass().getResource(
						"/imagenes/package$o.gif")));
				btnCarga.setToolTipText("Carga de Archivos");
				panel1.add(btnCarga, cc.xywh(5, 1, 1, 1,
						CellConstraints.CENTER, CellConstraints.DEFAULT));

				// ---- btnAcuse ----
				btnAcuse.setText("Acuse");
				btnAcuse.setFont(new Font("Tahoma", Font.BOLD, 9));
				btnAcuse.setForeground(new Color(0, 137, 206));
				btnAcuse.setToolTipText("Acuse de Recibo");
				btnAcuse.setIcon(new ImageIcon(getClass().getResource(
						"/imagenes/imgLocalizar.gif")));
				panel1.add(btnAcuse, cc.xywh(7, 1, 1, 1,
						CellConstraints.CENTER, CellConstraints.DEFAULT));

				// ---- btnDetalleTrans ----
				btnDetalleTrans.setText("Detalle");
				btnDetalleTrans.setFont(new Font("Tahoma", Font.BOLD, 9));
				btnDetalleTrans.setForeground(new Color(0, 137, 206));
				btnDetalleTrans.setToolTipText("Detalle de lo Transmitido");
				btnDetalleTrans.setIcon(new ImageIcon(getClass().getResource(
						"/imagenes/imgTableGo.gif")));
				panel1.add(btnDetalleTrans, cc.xywh(9, 1, 1, 1,
						CellConstraints.CENTER, CellConstraints.DEFAULT));

				// ---- btnAplicaCA ----
				btnAplicaCA.setText("Aplicaci\u00f3n");
				btnAplicaCA.setFont(new Font("Tahoma", Font.BOLD, 9));
				btnAplicaCA.setForeground(new Color(0, 137, 206));
				btnAplicaCA.setIcon(new ImageIcon(getClass().getResource(
						"/imagenes/aceptar.gif")));
				btnAplicaCA
						.setToolTipText("Aplicaci\u00f3n de Saldos en Cuenta Digital");
				panel1.add(btnAplicaCA, cc.xywh(11, 1, 1, 1,
						CellConstraints.CENTER, CellConstraints.DEFAULT));

				// ---- btnCancela ----
				btnCancela.setText("Cancela");
				btnCancela.setFont(new Font("Tahoma", Font.BOLD, 9));
				btnCancela.setForeground(new Color(0, 137, 206));
				btnCancela.setIcon(new ImageIcon(getClass().getResource(
						"/imagenes/images/imgErrorCaptura.gif")));
				btnCancela.setToolTipText("Reversa de archivo de cargos");
				panel1.add(btnCancela, cc.xywh(13, 1, 1, 1,
						CellConstraints.CENTER, CellConstraints.DEFAULT));

				// ---- btnBalance ----
				btnBalance.setText("Balance");
				btnBalance.setFont(new Font("Tahoma", Font.BOLD, 9));
				btnBalance.setForeground(new Color(0, 137, 206));
				btnBalance.setIcon(new ImageIcon(getClass().getResource(
						"/imagenes/images/ico_derecho.gif")));
				btnBalance
						.setToolTipText("Reporte Balance de Cuenta Concentradora");
				panel1.add(btnBalance, cc.xy(15, 1));
			}
			panel3.add(panel1, cc.xywh(1, 8, 9, 1));

			// ======== panel2 ========
			{
				panel2.setBorder(new TitledBorder(null, "Consulta de Saldos",
						TitledBorder.LEADING, TitledBorder.TOP, new Font(
								"Tahoma", Font.BOLD, 10), Color.white));
				panel2.setBackground(new Color(0, 137, 207));
				panel2.setLayout(new FormLayout(new ColumnSpec[] {
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC }, RowSpec
						.decodeSpecs("default")));
				panel2.add(dateFechaSaldos, cc.xy(1, 1));

				// ---- checkRefresh ----
				checkRefresh.setText("Actualiza Autom\u00e1ticamente");
				checkRefresh.setFont(new Font("Tahoma", Font.PLAIN, 9));
				checkRefresh.setBackground(new Color(0, 137, 206));
				checkRefresh.setForeground(Color.white);
				checkRefresh.setBorderPaintedFlat(true);
				panel2.add(checkRefresh, cc.xywh(5, 1, 1, 1,
						CellConstraints.CENTER, CellConstraints.DEFAULT));

				// ---- btnConsulta ----
				btnConsulta.setText("Actualiza");
				btnConsulta.setFont(new Font("Tahoma", Font.BOLD, 9));
				btnConsulta.setForeground(new Color(0, 137, 206));
				panel2.add(btnConsulta, cc.xywh(7, 1, 1, 1,
						CellConstraints.CENTER, CellConstraints.DEFAULT));

				// ---- btnLiquidaciones ----
				btnLiquidaciones.setFont(new Font("Tahoma", Font.BOLD, 9));
				btnLiquidaciones.setIcon(null);
				btnLiquidaciones.setText("...");
				btnLiquidaciones.setForeground(new Color(0, 137, 206));
				btnLiquidaciones.setVisible(false);
				panel2.add(btnLiquidaciones, cc.xy(9, 1));
			}
			panel3.add(panel2, cc.xywh(1, 9, 9, 1));

			// ======== scrollPane1 ========
			{

				// ---- dsSaldos ----
				dsSaldos.setColumnCount(12);
				dsSaldos.setRowCount(0);
				dsSaldos.setColumnHeaderBackground(new Color(0, 137, 207));
				dsSaldos.setColumnHeaderFont(new Font("Tahoma", Font.BOLD, 9));
				dsSaldos.setColumnHeaderForeground(Color.white);
				dsSaldos.setFont(new Font("Tahoma", Font.PLAIN, 10));
				dsSaldos.setCellValue(null);

				// INICIALIZA LA TABLA DE DATOS
				dsSaldos.setShowGrid(true);
				dsSaldos.setFont(new Font("Tahoma", Font.TRUETYPE_FONT, 9));
				dsSaldos.setColumnHeaderVisible(false);
				dsSaldos.getColumnHeader().getCellAt(0).setValue("Banco");
				dsSaldos.getColumnHeader().getCellAt(1).setValue("Sdo.Corto");
				dsSaldos.getColumnHeader().getCellAt(2).setValue("Sdo.Largo");
				dsSaldos.getColumnHeader().getCellAt(3)
						.setValue("Sdo.Aplicado");
				dsSaldos.getColumnHeader().getCellAt(4).setValue("Sdo.Inicial");
				dsSaldos.getColumnHeader().getCellAt(5).setValue("Cargos");
				dsSaldos.getColumnHeader().getCellAt(6).setValue("Abonos");
				dsSaldos.getColumnHeader().getCellAt(7).setValue("Sdo.Final");
				dsSaldos.getColumnHeader().getCellAt(8)
						.setValue("Lin.Cred.Tot.");
				dsSaldos.getColumnHeader().getCellAt(9)
						.setValue("Lin.Cred.Uti.");
				dsSaldos.getColumnHeader().getCellAt(10)
						.setValue("Lin.Cred.Dis.");
				dsSaldos.getColumnHeader().getCellAt(11)
						.setValue("Det.Liquid.");
				dsSaldos.setAutoStyle(DsGrid.ALTERNATING_ROW_COLOR, null,
						Color.white, null, Color.decode("#ECECF0"));
				dsSaldos.setColumnWidth(0, 100);
				dsSaldos.setColumnWidth(1, 20);
				dsSaldos.setColumnWidth(2, 20);
				dsSaldos.setColumnWidth(3, 20);
				dsSaldos.setColumnWidth(4, 20);
				dsSaldos.setColumnWidth(5, 20);
				dsSaldos.setColumnWidth(6, 20);
				dsSaldos.setColumnWidth(7, 20);
				dsSaldos.setColumnWidth(8, 20);
				dsSaldos.setColumnWidth(9, 20);
				dsSaldos.setColumnWidth(10, 20);
				dsSaldos.setColumnWidth(11, 20);
				dsSaldos.setColumnHorizontalAlignment(0, DsGrid.LEFT);
				dsSaldos.setColumnHorizontalAlignment(1, DsGrid.RIGHT);
				dsSaldos.setColumnHorizontalAlignment(2, DsGrid.RIGHT);
				dsSaldos.setColumnHorizontalAlignment(3, DsGrid.RIGHT);
				dsSaldos.setColumnHorizontalAlignment(4, DsGrid.RIGHT);
				dsSaldos.setColumnHorizontalAlignment(5, DsGrid.RIGHT);
				dsSaldos.setColumnHorizontalAlignment(6, DsGrid.RIGHT);
				dsSaldos.setColumnHorizontalAlignment(7, DsGrid.RIGHT);
				dsSaldos.setColumnHorizontalAlignment(8, DsGrid.RIGHT);
				dsSaldos.setColumnHorizontalAlignment(9, DsGrid.RIGHT);
				dsSaldos.setColumnHorizontalAlignment(10, DsGrid.CENTER);
				dsSaldos.setColumnHorizontalAlignment(11, DsGrid.CENTER);

				dsSaldos.getColumnHeader().getCellAt(0)
						.setHorizontalAlignment(DsGrid.CENTER);
				dsSaldos.getColumnHeader().getCellAt(1)
						.setHorizontalAlignment(DsGrid.CENTER);
				dsSaldos.getColumnHeader().getCellAt(2)
						.setHorizontalAlignment(DsGrid.CENTER);
				dsSaldos.getColumnHeader().getCellAt(3)
						.setHorizontalAlignment(DsGrid.CENTER);
				dsSaldos.getColumnHeader().getCellAt(4)
						.setHorizontalAlignment(DsGrid.CENTER);
				dsSaldos.getColumnHeader().getCellAt(5)
						.setHorizontalAlignment(DsGrid.CENTER);
				dsSaldos.getColumnHeader().getCellAt(6)
						.setHorizontalAlignment(DsGrid.CENTER);
				dsSaldos.getColumnHeader().getCellAt(7)
						.setHorizontalAlignment(DsGrid.CENTER);
				dsSaldos.getColumnHeader().getCellAt(8)
						.setHorizontalAlignment(DsGrid.CENTER);
				dsSaldos.getColumnHeader().getCellAt(9)
						.setHorizontalAlignment(DsGrid.CENTER);
				dsSaldos.getColumnHeader().getCellAt(10)
						.setHorizontalAlignment(DsGrid.CENTER);
				dsSaldos.getColumnHeader().getCellAt(11)
						.setHorizontalAlignment(DsGrid.CENTER);

				dsSaldos.getColumnHeader().getProperties().setWordWrap(true);
				dsSaldos.getColumnHeader().setPreferredSize(
						new Dimension(800, 30));

				dsSaldos.getColumnHeader().getCellAt(0)
						.setFont(new Font("Tahoma", Font.TRUETYPE_FONT, 9));
				dsSaldos.getColumnHeader().getCellAt(1)
						.setFont(new Font("Tahoma", Font.TRUETYPE_FONT, 9));
				dsSaldos.getColumnHeader().getCellAt(2)
						.setFont(new Font("Tahoma", Font.TRUETYPE_FONT, 9));
				dsSaldos.getColumnHeader().getCellAt(3)
						.setFont(new Font("Tahoma", Font.TRUETYPE_FONT, 9));
				dsSaldos.getColumnHeader().getCellAt(4)
						.setFont(new Font("Tahoma", Font.TRUETYPE_FONT, 9));
				dsSaldos.getColumnHeader().getCellAt(5)
						.setFont(new Font("Tahoma", Font.TRUETYPE_FONT, 9));
				dsSaldos.getColumnHeader().getCellAt(6)
						.setFont(new Font("Tahoma", Font.TRUETYPE_FONT, 9));
				dsSaldos.getColumnHeader().getCellAt(7)
						.setFont(new Font("Tahoma", Font.TRUETYPE_FONT, 9));
				dsSaldos.getColumnHeader().getCellAt(8)
						.setFont(new Font("Tahoma", Font.TRUETYPE_FONT, 9));
				dsSaldos.getColumnHeader().getCellAt(9)
						.setFont(new Font("Tahoma", Font.TRUETYPE_FONT, 9));
				dsSaldos.getColumnHeader().getCellAt(10)
						.setFont(new Font("Tahoma", Font.TRUETYPE_FONT, 9));
				dsSaldos.getColumnHeader().getCellAt(11)
						.setFont(new Font("Tahoma", Font.TRUETYPE_FONT, 9));
				scrollPane1.setViewportView(dsSaldos);
			}
			panel3.add(scrollPane1, cc.xywh(1, 10, 9, 1));

			// ---- lblMensaje ----
			lblMensaje.setText("*** Zona de Mensajes ***");
			lblMensaje.setFont(new Font("Tahoma", Font.BOLD, 10));
			lblMensaje.setForeground(Color.white);
			lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
			panel3.add(lblMensaje, cc.xywh(1, 11, 9, 1));
			panel3.add(barra, cc.xywh(1, 12, 9, 1));
		}
		contentPane.add(panel3, cc.xy(1, 1));

		// ---- buttonGroup2 ----
		ButtonGroup buttonGroup2 = new ButtonGroup();
		buttonGroup2.add(radioAplicaCortos);
		buttonGroup2.add(radioAplicaLargos);
		// JFormDesigner - End of component initialization
		// //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY
	// //GEN-BEGIN:variables
	public JPanel panel3;
	public JLabel frmTituloLogo;
	public JPanel panelTitulo;
	public JLabel lblTituloForma;
	public JLabel lblSistema;
	public JPanel panelFechaInhabil;
	public JLabel lblFechaOperacion;
	public JTextField textFechaOperacion;
	public JButton btnFinLiquidacion;
	public JLabel lblDescInhabil;
	public JLabel lblFecha;
	public JPanel panelArchivos2;
	public JLabel label5;
	public JTextField textImpComision;
	public JButton btnComisiones;
	public JPanel panelArchivos;
	public JComboBox cbReporte;
	public JButton btnArchivo;
	public JPanel panel1;
	public JRadioButton radioAplicaCortos;
	public JRadioButton radioAplicaLargos;
	public JButton btnCarga;
	public JButton btnAcuse;
	public JButton btnDetalleTrans;
	public JButton btnAplicaCA;
	public JButton btnCancela;
	public JButton btnBalance;
	public JPanel panel2;
	public JDateField dateFechaSaldos;
	public JCheckBox checkRefresh;
	public JButton btnConsulta;
	public JButton btnLiquidaciones;
	public JScrollPane scrollPane1;
	public DsGrid dsSaldos;
	public JLabel lblMensaje;
	public JProgressBar barra;
	// JFormDesigner - End of variables declaration //GEN-END:variables
}
