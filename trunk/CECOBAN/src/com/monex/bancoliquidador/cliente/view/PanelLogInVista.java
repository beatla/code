/*
 * Created by JFormDesigner on Mon Aug 31 13:14:34 GMT 2009
 */

package com.monex.bancoliquidador.cliente.view;

import java.awt.*;
import javax.swing.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;

/**
 * @author Eleuterio Arellano Saldaña
 */
public class PanelLogInVista extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6229001950109521311L;

	public PanelLogInVista() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY
		// //GEN-BEGIN:initComponents
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		label2 = new JLabel();
		textCveUsuario = new JTextField();
		label3 = new JLabel();
		pwdContrasenia = new JPasswordField();
		label4 = new JLabel();
		cboTipoConexion = new JComboBox();
		label1 = new JLabel();
		textDirectorio = new JTextField();
		btnAceptar = new JButton();
		buttonBar = new JPanel();
		lblMensaje = new JLabel();
		label6 = new JLabel();
		CellConstraints cc = new CellConstraints();

		// ======== this ========
		setTitle("Monitor CECOBAN");
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		// ======== dialogPane ========
		{
			dialogPane.setBorder(Borders.DIALOG_BORDER);
			dialogPane.setLayout(new BorderLayout());

			// ======== contentPanel ========
			{
				contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC }, new RowSpec[] {
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.LINE_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.LINE_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.LINE_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.LINE_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.LINE_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.LINE_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.LINE_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.LINE_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC }));

				// ---- label2 ----
				label2.setText("Usuario");
				contentPanel.add(label2, cc.xywh(1, 5, 1, 1,
						CellConstraints.RIGHT, CellConstraints.DEFAULT));
				contentPanel.add(textCveUsuario, cc.xy(3, 5));

				// ---- label3 ----
				label3.setText("Contrase\u00f1a");
				contentPanel.add(label3, cc.xywh(1, 7, 1, 1,
						CellConstraints.RIGHT, CellConstraints.DEFAULT));
				contentPanel.add(pwdContrasenia, cc.xy(3, 7));

				// ---- label4 ----
				label4.setText("Tipo de conexi\u00f3n");
				contentPanel.add(label4, cc.xywh(1, 9, 1, 1,
						CellConstraints.RIGHT, CellConstraints.DEFAULT));

				// ---- cboTipoConexion ----
				cboTipoConexion.setModel(new DefaultComboBoxModel(new String[] {
						"Default", "Contingencia" }));
				contentPanel.add(cboTipoConexion, cc.xy(3, 9));

				// ---- label1 ----
				label1.setText("Directorio CECOBAN");
				contentPanel.add(label1, cc.xy(1, 11));
				contentPanel.add(textDirectorio, cc.xywh(3, 11, 3, 1));

				// ---- btnAceptar ----
				btnAceptar.setText("Aceptar");
				contentPanel.add(btnAceptar, cc.xy(3, 15));
			}
			dialogPane.add(contentPanel, BorderLayout.CENTER);

			// ======== buttonBar ========
			{
				buttonBar.setBorder(Borders.BUTTON_BAR_GAP_BORDER);
				buttonBar.setLayout(new FormLayout(new ColumnSpec[] {
						FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC, FormFactory.GLUE_COLSPEC,
						FormFactory.BUTTON_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.BUTTON_COLSPEC }, RowSpec
						.decodeSpecs("pref")));

				// ---- lblMensaje ----
				lblMensaje.setText("...");
				lblMensaje.setBackground(new Color(0, 204, 255));
				lblMensaje.setForeground(Color.red);
				lblMensaje.setFont(new Font("Tahoma", Font.BOLD, 11));
				buttonBar.add(lblMensaje, cc.xy(2, 1));
			}
			dialogPane.add(buttonBar, BorderLayout.SOUTH);

			// ---- label6 ----
			label6.setText("Acceso a Monitor de Gestion Liquidaci\u00f3n de Bancos CECOBAN");
			label6.setVerticalAlignment(SwingConstants.TOP);
			dialogPane.add(label6, BorderLayout.NORTH);
		}
		contentPane.add(dialogPane, BorderLayout.CENTER);
		// JFormDesigner - End of component initialization
		// //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY
	// //GEN-BEGIN:variables
	public JPanel dialogPane;
	public JPanel contentPanel;
	public JLabel label2;
	public JTextField textCveUsuario;
	public JLabel label3;
	public JPasswordField pwdContrasenia;
	public JLabel label4;
	public JComboBox cboTipoConexion;
	public JLabel label1;
	public JTextField textDirectorio;
	public JButton btnAceptar;
	public JPanel buttonBar;
	public JLabel lblMensaje;
	public JLabel label6;
	// JFormDesigner - End of variables declaration //GEN-END:variables
}
