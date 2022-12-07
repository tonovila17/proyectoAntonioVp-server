package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Component;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.DriverManager;
import java.util.Base64;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.Color;
import javax.swing.JComboBox;

public class Config_conexion extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtIP;
	private JTextField txtPuerto;
	private JTextField txtNombreBD;
	private JTextField txtUsuario;
	private JPasswordField txtContrasenha;
	private String[] idiomas = { "ES", "En" };
	private JLabel lbldatos;
	private JLabel lblPuerto;
	private JLabel lblNombreBD;
	private JLabel lblUsuario;
	private JLabel lblContrasenha;
	private JButton btnDefecto;
	private JButton btnGuardar;
	private JButton btnCancelar;

	public static void main(String[] args) {
		try {
			Config_conexion dialog = new Config_conexion();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Config_conexion() {
		setResizable(false);
		setTitle("Configurar Conexi\u00F3n");
		setBounds(100, 100, 458, 350);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.DARK_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			panel.setBackground(Color.DARK_GRAY);
			contentPanel.add(panel, BorderLayout.NORTH);
			{
				lbldatos = new JLabel("Indica los datos sobre tu conexi\u00F3n :");
				lbldatos.setForeground(Color.WHITE);
				lbldatos.setBackground(Color.DARK_GRAY);
				panel.add(lbldatos);
			}
		}
		{
			JPanel panel = new JPanel();
			panel.setBackground(Color.DARK_GRAY);
			contentPanel.add(panel, BorderLayout.CENTER);
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
			gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
					Double.MIN_VALUE };
			gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
			panel.setLayout(gbl_panel);
			{
				Component horizontalStrut = Box.createHorizontalStrut(20);
				GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
				gbc_horizontalStrut.insets = new Insets(0, 0, 5, 5);
				gbc_horizontalStrut.gridx = 4;
				gbc_horizontalStrut.gridy = 1;
				panel.add(horizontalStrut, gbc_horizontalStrut);
			}
			{
				Component horizontalStrut = Box.createHorizontalStrut(20);
				GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
				gbc_horizontalStrut.insets = new Insets(0, 0, 5, 5);
				gbc_horizontalStrut.gridx = 5;
				gbc_horizontalStrut.gridy = 1;
				panel.add(horizontalStrut, gbc_horizontalStrut);
			}
			{
				Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
				GridBagConstraints gbc_rigidArea = new GridBagConstraints();
				gbc_rigidArea.insets = new Insets(0, 0, 5, 5);
				gbc_rigidArea.gridx = 6;
				gbc_rigidArea.gridy = 1;
				panel.add(rigidArea, gbc_rigidArea);
			}
			{
				JLabel lblIP = new JLabel("IP:");
				lblIP.setForeground(Color.WHITE);
				GridBagConstraints gbc_lblIP = new GridBagConstraints();
				gbc_lblIP.anchor = GridBagConstraints.EAST;
				gbc_lblIP.insets = new Insets(0, 0, 5, 5);
				gbc_lblIP.gridx = 7;
				gbc_lblIP.gridy = 1;
				panel.add(lblIP, gbc_lblIP);
			}
			{
				txtIP = new JTextField();
				GridBagConstraints gbc_txtIP = new GridBagConstraints();
				gbc_txtIP.gridwidth = 2;
				gbc_txtIP.insets = new Insets(0, 0, 5, 5);
				gbc_txtIP.fill = GridBagConstraints.HORIZONTAL;
				gbc_txtIP.gridx = 8;
				gbc_txtIP.gridy = 1;
				panel.add(txtIP, gbc_txtIP);
				txtIP.setColumns(10);
			}
			{
				Component horizontalStrut = Box.createHorizontalStrut(20);
				GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
				gbc_horizontalStrut.insets = new Insets(0, 0, 5, 5);
				gbc_horizontalStrut.gridx = 6;
				gbc_horizontalStrut.gridy = 2;
				panel.add(horizontalStrut, gbc_horizontalStrut);
			}
			{
				lblPuerto = new JLabel("Puerto:");
				lblPuerto.setForeground(Color.WHITE);
				GridBagConstraints gbc_lblPuerto = new GridBagConstraints();
				gbc_lblPuerto.anchor = GridBagConstraints.EAST;
				gbc_lblPuerto.insets = new Insets(0, 0, 5, 5);
				gbc_lblPuerto.gridx = 7;
				gbc_lblPuerto.gridy = 2;
				panel.add(lblPuerto, gbc_lblPuerto);
			}
			{
				txtPuerto = new JTextField();
				GridBagConstraints gbc_txtPuerto = new GridBagConstraints();
				gbc_txtPuerto.gridwidth = 2;
				gbc_txtPuerto.insets = new Insets(0, 0, 5, 5);
				gbc_txtPuerto.fill = GridBagConstraints.HORIZONTAL;
				gbc_txtPuerto.gridx = 8;
				gbc_txtPuerto.gridy = 2;
				panel.add(txtPuerto, gbc_txtPuerto);
				txtPuerto.setColumns(10);
			}
			{
				lblNombreBD = new JLabel("Nombre B.Datos:");
				lblNombreBD.setForeground(Color.WHITE);
				GridBagConstraints gbc_lblNombreBD = new GridBagConstraints();
				gbc_lblNombreBD.anchor = GridBagConstraints.EAST;
				gbc_lblNombreBD.insets = new Insets(0, 0, 5, 5);
				gbc_lblNombreBD.gridx = 7;
				gbc_lblNombreBD.gridy = 3;
				panel.add(lblNombreBD, gbc_lblNombreBD);
			}
			{
				txtNombreBD = new JTextField();
				GridBagConstraints gbc_txtNombreBD = new GridBagConstraints();
				gbc_txtNombreBD.gridwidth = 2;
				gbc_txtNombreBD.insets = new Insets(0, 0, 5, 5);
				gbc_txtNombreBD.fill = GridBagConstraints.HORIZONTAL;
				gbc_txtNombreBD.gridx = 8;
				gbc_txtNombreBD.gridy = 3;
				panel.add(txtNombreBD, gbc_txtNombreBD);
				txtNombreBD.setColumns(10);
			}
			{
				lblUsuario = new JLabel("Usuario");
				lblUsuario.setForeground(Color.WHITE);
				GridBagConstraints gbc_lblUsuario = new GridBagConstraints();
				gbc_lblUsuario.anchor = GridBagConstraints.EAST;
				gbc_lblUsuario.insets = new Insets(0, 0, 5, 5);
				gbc_lblUsuario.gridx = 7;
				gbc_lblUsuario.gridy = 4;
				panel.add(lblUsuario, gbc_lblUsuario);
			}
			{
				txtUsuario = new JTextField();
				GridBagConstraints gbc_txtUsuario = new GridBagConstraints();
				gbc_txtUsuario.gridwidth = 2;
				gbc_txtUsuario.insets = new Insets(0, 0, 5, 5);
				gbc_txtUsuario.fill = GridBagConstraints.HORIZONTAL;
				gbc_txtUsuario.gridx = 8;
				gbc_txtUsuario.gridy = 4;
				panel.add(txtUsuario, gbc_txtUsuario);
				txtUsuario.setColumns(10);
			}
			{
				lblContrasenha = new JLabel("Contrase\u00F1a:");
				lblContrasenha.setForeground(Color.WHITE);
				GridBagConstraints gbc_lblContrasenha = new GridBagConstraints();
				gbc_lblContrasenha.anchor = GridBagConstraints.EAST;
				gbc_lblContrasenha.insets = new Insets(0, 0, 5, 5);
				gbc_lblContrasenha.gridx = 7;
				gbc_lblContrasenha.gridy = 5;
				panel.add(lblContrasenha, gbc_lblContrasenha);
			}
			{
				txtContrasenha = new JPasswordField();
				GridBagConstraints gbc_txtContrasenha = new GridBagConstraints();
				gbc_txtContrasenha.gridwidth = 2;
				gbc_txtContrasenha.insets = new Insets(0, 0, 5, 5);
				gbc_txtContrasenha.fill = GridBagConstraints.HORIZONTAL;
				gbc_txtContrasenha.gridx = 8;
				gbc_txtContrasenha.gridy = 5;
				panel.add(txtContrasenha, gbc_txtContrasenha);
			}
			{
				btnDefecto = new JButton("Probar Conexi\u00F3n Con Valores Por Defecto");
				btnDefecto.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						boolean conectado = false;

						try {

							vista.Inicio.miConexion = DriverManager.getConnection(
									"jdbc:mysql://localhost:3306/comics?serverTimezone=UTC", "root", "root");
							vista.Inicio.miConexion.setAutoCommit(false);
							Menu_ dialogo = new Menu_();
							dialogo.setVisible(true);
							dispose();
						} catch (Exception e1) {
							System.out.print("no se pudo conectar a base de datos");
							e1.printStackTrace();

							JOptionPane.showMessageDialog(btnDefecto, "La conexion no ha podido ser establecida.");

						}

					}
				});
				GridBagConstraints gbc_btnDefecto = new GridBagConstraints();
				gbc_btnDefecto.insets = new Insets(0, 0, 0, 5);
				gbc_btnDefecto.fill = GridBagConstraints.HORIZONTAL;
				gbc_btnDefecto.gridwidth = 3;
				gbc_btnDefecto.gridx = 7;
				gbc_btnDefecto.gridy = 6;
				panel.add(btnDefecto, gbc_btnDefecto);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.DARK_GRAY);
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnGuardar = new JButton("Guardar");
				btnGuardar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String ip = txtIP.getText();
						String puerto = txtPuerto.getText();
						String NombreBD = txtNombreBD.getText();
						String usuario = txtUsuario.getText();
						String contrasenha = new String(txtContrasenha.getPassword());
						String mensaje = "";
						boolean validado = true;

						if (ip.isEmpty()) {
							mensaje = mensaje + "El campo IP est� vac�o. \n";
							validado = false;
						}

						if (puerto.isEmpty()) {
							mensaje = mensaje + "El campo Puerto est� vac�o. \n";
							validado = false;
						}

						if (NombreBD.isEmpty()) {
							mensaje = mensaje + "El campo NombreBD est� vac�o. \n";
							validado = false;
						}

						if (usuario.isEmpty()) {
							mensaje = mensaje + "El campo Usuario est� vac�o. \n";
							validado = false;
						}

						if (contrasenha.isEmpty()) {
							mensaje = mensaje + "El campo Contrase�a est� vac�o. \n";
							validado = false;
						}

						if (validado) {
							Properties prop;

							try (OutputStream out = new FileOutputStream("./conexion.properties")) {
								prop = new Properties();
								prop.setProperty("IP", ip);
								prop.setProperty("Puerto", puerto);
								prop.setProperty("NombreBD", NombreBD);
								prop.setProperty("Usuario", usuario);
								String cifrada = Base64.getEncoder().encodeToString(contrasenha.getBytes());
								prop.setProperty("Contrasenha", cifrada);
								prop.store(out, null);
								out.close();
								JOptionPane.showMessageDialog(btnGuardar, "Datos guardados con �xito.");
								dispose();
							} catch (Exception e4) {
								System.out.print("No se encont� el archivo");
								e4.printStackTrace();
							}
						} else {
							JOptionPane.showMessageDialog(btnGuardar, mensaje
									+ "No se han podido guardar los par�metros en el archivo de configuraci�n. \n Revisa los campos de datos y prueba de nuevo.");
							return;
						}

					}
				});
				btnGuardar.setActionCommand("OK");
				buttonPane.add(btnGuardar);
				getRootPane().setDefaultButton(btnGuardar);
			}
			{
				Component horizontalStrut = Box.createHorizontalStrut(20);
				buttonPane.add(horizontalStrut);
			}
			{
				btnCancelar = new JButton("Cancelar");
				btnCancelar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				btnCancelar.setActionCommand("Cancel");
				buttonPane.add(btnCancelar);
			}
		}
		traducir();
	}

	public void traducir() {

		ResourceBundle rb = ResourceBundle.getBundle("traduccion.config");

		lbldatos.setText(rb.getString("lbldatos"));
		lblNombreBD.setText(rb.getString("lblNombreBD"));
		lblUsuario.setText(rb.getString("lblUsuario"));
		lblContrasenha.setText(rb.getString("lblContrasenha"));
		btnDefecto.setText(rb.getString("btnDefecto"));
		btnGuardar.setText(rb.getString("btnGuardar"));
		btnCancelar.setText(rb.getString("btnCancelar"));

	}

}
