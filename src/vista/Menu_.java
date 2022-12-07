package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import modelo.Coleccion;
import modelo.Numero;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Font;

public class Menu_ extends JFrame implements Runnable {

	private JPanel contentPane;
	public static String Log = "";
	private ServerSocket servidor = null;
	private int puerto = 9999;
	private Socket sc = null;
	private JTextArea txtLogs;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu_ frame = new Menu_();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Menu_() {
		setTitle("Logs Servidor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		txtLogs = new JTextArea();
		txtLogs.setFont(new Font("Arial Black", Font.PLAIN, 15));
		txtLogs.setEditable(false);
		scrollPane.setViewportView(txtLogs);

		Thread miHilo = new Thread(this);
		miHilo.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			servidor = new ServerSocket(puerto);

			System.out.println("Servidor Iniciado");
			DataInputStream in;
			String codigo = "";
			Log = Log + "Estoy a la escucha \n";

			while (true) {
				System.out.println("Entrando al bucle" + servidor.getLocalSocketAddress());

				sc = servidor.accept();

				System.out.println("Despues del accept");

				escribirLog();

				in = new DataInputStream(sc.getInputStream());

				System.out.println("Antes de leer");
				codigo = in.readUTF();

				System.out.println("C�digo: " + codigo);

				switch (codigo) {

					case "1":
						ObjectOutputStream objectOut = new ObjectOutputStream(sc.getOutputStream());
						ArrayList<Coleccion> listaColecciones = manejo_bd.base_datos.ObtenerColecciones();
						objectOut.writeObject(listaColecciones);
						objectOut.flush();

						Log = Log + "Colecciones Cargadas. \n \n";
						escribirLog();

						break;
					case "2":

						Coleccion colecc;
						ObjectInputStream objectIn = new ObjectInputStream(sc.getInputStream());

						colecc = (Coleccion) objectIn.readObject();
						System.out.println("Objeto creado");

						DataInputStream entrada_bytes = new DataInputStream(sc.getInputStream());
						int longitud_array = entrada_bytes.readInt();

						byte[] bytes = new byte[longitud_array];
						for (int i = 0; i < bytes.length; i++) {
							bytes[i] = in.readByte();
						}

						int id = manejo_bd.base_datos.Crear_Coleccion(colecc, bytes);

						DataOutputStream outp = new DataOutputStream(sc.getOutputStream());
						outp.writeInt(id);

						Log = Log + "Coleccion creada. \n";
						escribirLog();

						break;
					case "3":

						DataInputStream entrada = new DataInputStream(sc.getInputStream());
						int id_buscar = entrada.readInt();

						Coleccion coleccion = manejo_bd.base_datos.buscar_id(id_buscar);
						ObjectOutputStream salida_objeto = new ObjectOutputStream(sc.getOutputStream());
						salida_objeto.writeObject(coleccion);
						salida_objeto.flush();

						Log = Log + "Coleccion cargada. \n";
						escribirLog();

						break;
					case "4":

						ObjectInputStream entrada_objeto = new ObjectInputStream(sc.getInputStream());
						Coleccion Coleccion_actualizada = (Coleccion) entrada_objeto.readObject();

						DataInputStream entrada_imagen = new DataInputStream(sc.getInputStream());
						int longitud_Array = entrada_imagen.readInt();

						byte[] array_bytes = new byte[longitud_Array];
						for (int i = 0; i < array_bytes.length; i++) {
							array_bytes[i] = in.readByte();
						}

						boolean modificado = manejo_bd.base_datos.Modificar_coleccion(Coleccion_actualizada,
								array_bytes);

						DataOutputStream salida_dato = new DataOutputStream(sc.getOutputStream());
						salida_dato.writeBoolean(modificado);
						salida_dato.flush();

						Log = Log + "Colecci�n modificada. \n";
						escribirLog();

						break;
					case "5":

						DataInputStream entradaTitulo = new DataInputStream(sc.getInputStream());
						String Titulo = entradaTitulo.readUTF();

						int Id_coleccion = manejo_bd.base_datos.ObtenerID(Titulo);

						DataOutputStream salidaID = new DataOutputStream(sc.getOutputStream());
						salidaID.writeInt(Id_coleccion);

						Log = Log + "Id de coleccion cargado \n";
						escribirLog();

						break;
					case "6":
						ObjectOutputStream objectOutNumeros = new ObjectOutputStream(sc.getOutputStream());
						ArrayList<Numero> listanumeros = manejo_bd.base_datos.ObtenerNumeros();
						objectOutNumeros.writeObject(listanumeros);
						objectOutNumeros.flush();

						Log = Log + "Numeros Cargados.  \n";
						escribirLog();

						break;
					case "7":

						Numero num;
						ObjectInputStream objectInputNumero = new ObjectInputStream(sc.getInputStream());

						num = (Numero) objectInputNumero.readObject();
						System.out.println("Objeto creado");

						DataInputStream entrada_bytes_numero = new DataInputStream(sc.getInputStream());
						int longitud_array_numero = entrada_bytes_numero.readInt();

						byte[] bytes_num = new byte[longitud_array_numero];
						for (int i = 0; i < bytes_num.length; i++) {
							bytes_num[i] = in.readByte();
						}

						int id_num = manejo_bd.base_datos.CrearNumero(num, bytes_num);

						DataOutputStream output_num = new DataOutputStream(sc.getOutputStream());
						output_num.writeInt(id_num);

						Log = Log + "Numero creado. \n";
						escribirLog();

						break;
					case "8":

						DataInputStream entradaidNum = new DataInputStream(sc.getInputStream());
						int idNumeroABuscar = entradaidNum.readInt();

						Numero NumeroEncontrado = manejo_bd.base_datos.numero_buscar_id(idNumeroABuscar);

						ObjectOutputStream salidaNumero = new ObjectOutputStream(sc.getOutputStream());
						salidaNumero.writeObject(NumeroEncontrado);

						Log = Log + "N�mero Cargago. \n";
						escribirLog();

						break;
					case "9":

						ObjectInputStream entrada_numero = new ObjectInputStream(sc.getInputStream());
						Numero numero = (Numero) entrada_numero.readObject();

						DataInputStream entrada_imagen_numero = new DataInputStream(sc.getInputStream());
						int longitud_Array_numero = entrada_imagen_numero.readInt();

						byte[] array_bytes_numero = new byte[longitud_Array_numero];
						for (int i = 0; i < array_bytes_numero.length; i++) {
							array_bytes_numero[i] = in.readByte();
						}

						boolean num_modificado = manejo_bd.base_datos.Modificar_numero(numero, array_bytes_numero);
						DataOutputStream salida_num_mod = new DataOutputStream(sc.getOutputStream());
						salida_num_mod.writeBoolean(num_modificado);

						Log = Log + "N�mero Modificado. \n";
						escribirLog();

						break;
					case "10":

						DataInputStream input = new DataInputStream(sc.getInputStream());
						int id_eliminar = input.readInt();

						int resultado = manejo_bd.base_datos.eliminar_coleccion(id_eliminar);

						DataOutputStream salida = new DataOutputStream(sc.getOutputStream());
						salida.writeInt(resultado);

						Log = Log + "Colecci�n Eliminada. \n";
						escribirLog();

						break;
					case "11":
						DataInputStream input_Numero = new DataInputStream(sc.getInputStream());
						int id_eliminar_Numero = input_Numero.readInt();

						int resultado_Numero = manejo_bd.base_datos.eliminar_numero(id_eliminar_Numero);

						DataOutputStream salida_Numero = new DataOutputStream(sc.getOutputStream());
						salida_Numero.writeInt(resultado_Numero);

						Log = Log + "Numero Eliminado. \n";
						escribirLog();

						break;
					case "15":

						System.out.println("Entra al caso 15");

						String informe = ".\\informes\\colecciones.jrxml";
						File f = new File(informe);
						System.out.println(f.exists());
						JasperPrint visor = null;
						try {
							JasperReport report = JasperCompileManager.compileReport(informe);

							visor = JasperFillManager.fillReport(report, null, vista.Inicio.miConexion);

						} catch (Exception e1) {
							System.out.println("Fallo al acceder al informe.");
							JOptionPane.showMessageDialog(this, "No se ha podido encontrar el archivo del informe.");
							e1.printStackTrace();
						}

						ObjectOutputStream salida_informe = new ObjectOutputStream(sc.getOutputStream());
						salida_informe.writeObject(visor);

						Log = Log + "Informe Colecciones Cargado. \n";
						escribirLog();

						break;
					case "16":

						String informe_numeros = ".\\informes\\numeros.jrxml";
						File fNum = new File(informe_numeros);
						System.out.println(fNum.exists());
						JasperPrint visorNumeros = null;
						try {
							JasperReport report = JasperCompileManager.compileReport(informe_numeros);

							visorNumeros = JasperFillManager.fillReport(report, null, vista.Inicio.miConexion);

						} catch (Exception e1) {
							System.out.println("Fallo al acceder al informe.");
							JOptionPane.showMessageDialog(this, "No se ha podido encontrar el archivo del informe.");
							e1.printStackTrace();
						}

						ObjectOutputStream salida_informe_numeros = new ObjectOutputStream(sc.getOutputStream());
						salida_informe_numeros.writeObject(visorNumeros);

						Log = Log + "Informe N�meros Cargado. \n";
						escribirLog();

						break;
					case "17":

						DataInputStream entradaIdCol = new DataInputStream(sc.getInputStream());

						int id_colecc = entradaIdCol.readInt();

						String informenum_col = ".\\informes\\numeros_por_coleccion.jrxml";
						HashMap hm = new HashMap();
						hm.put("Colecci�n", id_colecc);

						JasperPrint visornum_col = null;

						try {
							JasperReport reportnum_col = JasperCompileManager.compileReport(informenum_col);

							visornum_col = JasperFillManager.fillReport(reportnum_col, hm, vista.Inicio.miConexion);

						} catch (Exception e1) {

							e1.printStackTrace();
						}

						ObjectOutputStream salidaInforme = new ObjectOutputStream(sc.getOutputStream());
						salidaInforme.writeObject(visornum_col);

						Log = Log + "Informe N�meros Por Colecci�n Cargado. \n";
						escribirLog();
						break;
					case "20":
						DataOutputStream output = new DataOutputStream(sc.getOutputStream());
						output.writeBoolean(true);

						Log = Log + "Cliente Conectado";
						escribirLog();
						break;

					case "23":
						DataInputStream entradaEstadoCom = new DataInputStream(sc.getInputStream());

						String estado = entradaEstadoCom.readUTF();

						String informe_estado = ".\\informes\\comic_estados.jrxml";

						HashMap hm2 = new HashMap();
						hm2.put("Estado", estado);

						JasperPrint visorestado = null;

						try {
							JasperReport informeEstado = JasperCompileManager.compileReport(informe_estado);

							visorestado = JasperFillManager.fillReport(informeEstado, hm2, vista.Inicio.miConexion);

						} catch (Exception e1) {

							e1.printStackTrace();
						}

						ObjectOutputStream outInforme = new ObjectOutputStream(sc.getOutputStream());
						outInforme.writeObject(visorestado);

						Log = Log + "Informe Comics por Estado Cargado. \n";
						escribirLog();
				}

				sc.close();

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void escribirLog() {
		txtLogs.setText(Log);
	}

}
