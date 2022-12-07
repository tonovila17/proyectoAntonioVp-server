package vista;

import java.awt.BorderLayout;

import java.awt.EventQueue;
import java.net.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import modelo.Coleccion;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.help.HelpSetException;
import javax.swing.Box;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

import java.awt.Component;
import java.awt.Color;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class Inicio extends JFrame {

	public static Connection miConexion;
	private JPanel contentPane;
	private JFrame proyecto;
	private String[] idiomas = { "ES", "GL" };
	private JLabel lblBienvenido;
	private JButton btnConectar;
	private JButton btnConfigConexion;
	private JButton btnAyuda;
	private JButton btnSalir;
	private HelpSet helpset;
	private HelpBroker browser;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Inicio frame = new Inicio();
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Inicio() {

		setTitle("Comics - SERVER -Inicio");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel_norte_label = new JPanel();
		panel_norte_label.setForeground(Color.WHITE);
		panel_norte_label.setBackground(Color.DARK_GRAY);
		contentPane.add(panel_norte_label, BorderLayout.NORTH);

		lblBienvenido = new JLabel("\u00A1Bienvenido! Vamos a establecer conexi\u00F3n con las Base de Datos:");
		lblBienvenido.setForeground(Color.WHITE);
		panel_norte_label.add(lblBienvenido);

		JPanel panel_center = new JPanel();
		contentPane.add(panel_center, BorderLayout.CENTER);
		panel_center.setLayout(new BorderLayout(0, 0));

		JPanel panel_center2 = new JPanel();
		panel_center.add(panel_center2);
		panel_center2.setLayout(new BorderLayout(0, 0));

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.DARK_GRAY);
		panel_center2.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		btnConectar = new JButton("Conectar");
		btnConectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String ip;
				String puerto;
				String nombreBD;
				String usuario;
				String contrasenha;
				String descifrada;
				Properties prop = null;

				try (InputStream is = new FileInputStream("./conexion.properties")) {
					prop = new Properties();
					prop.load(is);
					ip = prop.getProperty("IP");
					System.out.print("IP: " + ip);
					puerto = prop.getProperty("Puerto");
					nombreBD = prop.getProperty("NombreBD");
					usuario = prop.getProperty("Usuario");
					contrasenha = prop.getProperty("Contrasenha");
					byte[] decoder = Base64.getDecoder().decode(contrasenha);
					descifrada = new String(decoder);
					is.close();

				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(btnConectar,
							"No se encontr� el archivo con su configuraci�n. Si es la primera conexi�n que realiza \n "
									+ "Vaya al apartado de configuraci�n y guarde la suya o \n"
									+ "pruebe con los datos por defecto.");
					System.out.print("No se encontr� el archivo properties");
					return;
				}

				try {

					String conexion;
					conexion = "jdbc:mysql://" + ip + ":" + puerto + "/" + nombreBD + "?serverTimezone=UTC";

					miConexion = DriverManager.getConnection(conexion, usuario, descifrada);
					miConexion.setAutoCommit(false);
					Menu_ dialogo = new Menu_();
					dialogo.setVisible(true);

				} catch (Exception e2) {
					e2.printStackTrace();
					System.out.println("No se pudo conectar a la Base de datos");
					JOptionPane.showMessageDialog(btnConectar,
							"No se ha podido establecer conexi�n. Revise su configuraci�n de Conexi�n.");
				}

			}
		});
		panel_2.add(btnConectar);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.DARK_GRAY);
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		panel_center.add(panel_1, BorderLayout.SOUTH);

		btnConfigConexion = new JButton("Configurar Conexi\u00F3n");
		btnConfigConexion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Config_conexion dia = new Config_conexion();
				dia.setVisible(true);
			}
		});

		JComboBox cmbIdiomas = new JComboBox();
		panel_1.add(cmbIdiomas);
		cmbIdiomas.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				if (cmbIdiomas.getSelectedItem().equals("ES")) {
					Locale.setDefault(new Locale("es", "ES"));

					try {
						URL helpURL = this.getClass().getResource("/ayuda/helpset.hs");

						helpset = new HelpSet(null, helpURL);

						browser = helpset.createHelpBroker();
						browser.enableHelpOnButton(btnAyuda, "inicio", helpset);

					} catch (HelpSetException e2) {
						System.out.print("No se pudo cargar la ayuda.");
						JOptionPane.showMessageDialog(btnAyuda, "No se ha podido encontrar el archivo de ayuda.");
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					traducir();
				}

				if (cmbIdiomas.getSelectedItem().equals("GL")) {
					Locale.setDefault(new Locale("es", "GL"));

					try {
						URL helpURL = this.getClass().getResource("/ayuda_ingles/helpset.hs");

						helpset = new HelpSet(null, helpURL);

						browser = helpset.createHelpBroker();
						browser.enableHelpOnButton(btnAyuda, "inicio", helpset);

					} catch (HelpSetException e3) {
						System.out.print("No se pudo cargar la ayuda.");
						JOptionPane.showMessageDialog(btnAyuda, "No se ha podido encontrar el archivo de ayuda.");
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}

					traducir();

				}

			}
		});
		cmbIdiomas.setModel(new DefaultComboBoxModel(idiomas));
		panel_1.add(btnConfigConexion);

		btnAyuda = new JButton("Ayuda");
		panel_1.add(btnAyuda);

		btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (Inicio.miConexion == null) {
						System.exit(0);
						;
						return;
					}

					Inicio.miConexion.close();
					JOptionPane.showMessageDialog(btnSalir, "Desconectado de la Base de Datos.");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				System.exit(0);

			}
		});
		panel_1.add(btnSalir);

		try {
			URL helpURL = this.getClass().getResource("/ayuda/helpset.hs");

			helpset = new HelpSet(null, helpURL);

			browser = helpset.createHelpBroker();
			browser.enableHelpOnButton(btnAyuda, "inicio", helpset);

		} catch (HelpSetException e) {
			System.out.print("No se pudo cargar la ayuda.");
			JOptionPane.showMessageDialog(btnAyuda, "No se ha podido encontrar el archivo de ayuda.");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void traducir() {

		ResourceBundle rb = ResourceBundle.getBundle("traduccion.config");

		lblBienvenido.setText(rb.getString("lblBienvenido"));

		btnConectar.setText(rb.getString("btnConectar"));
		btnConfigConexion.setText(rb.getString("btnConfigConexion"));
		btnAyuda.setText(rb.getString("btnAyuda"));
		btnSalir.setText(rb.getString("btnSalir"));

	}

	public Socket ObtenerSocket() {
		Properties prop = null;
		String ip;
		ArrayList<Coleccion> ListaColecciones = null;

		try (InputStream is = new FileInputStream("./conexion.properties")) {
			prop = new Properties();
			prop.load(is);
			ip = prop.getProperty("IP");
			is.close();

		} catch (Exception e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(btnConectar,
					"No se encontr� el archivo con su configuraci�n. Si es la primera conexi�n que realiza \n "
							+ "Vaya al apartado de configuraci�n y guarde la suya o \n"
							+ "pruebe con los datos por defecto.");
			System.out.print("No se encontr� el archivo properties");
			return null;
		}

		DataInputStream in;
		DataOutputStream out;
		ObjectOutputStream objectOut;
		ObjectInputStream objectInput;
		Socket sc = null;
		try {
			sc = new Socket(ip, 9999);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sc;
	}

}
