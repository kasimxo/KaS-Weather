package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import main.Main;
import utilities.ConfigFileHandler;

import javax.swing.JLabel;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Color;
import java.awt.Desktop;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JTextPane;

public class Preferences_Window extends JFrame {

	private int width=450;
	private int height=300;
	private JPanel contentPane;
	private JTextField textMod;
	private JButton btnModificar;
	private JLabel lblPrefered;
	private JLabel lblDisplayPrefered;
	private JButton btnGuardar;
	private JPanel displayWarning;
	private JButton btnNavegar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Preferences_Window frame = new Preferences_Window();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Preferences_Window() {
		setTitle("Preferencias");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Preferences_Window.class.getResource("/resources/icon.png")));
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		int x = (int) (Main.width/2) - width/2;
		int y = (int) (Main.height/2)- height/2;
		setBounds(x,y,width,height);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblDisplayPrefered = new JLabel("Municipio predeterminado:");
		lblDisplayPrefered.setBounds(20, 68, 130, 25);
		contentPane.add(lblDisplayPrefered);
		
		String configMun = ConfigFileHandler.readDefaultMun();
		lblPrefered = new JLabel(configMun);
		lblPrefered.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrefered.setOpaque(true);
		lblPrefered.setBackground(Color.WHITE);
		lblPrefered.setBounds(161, 68, 150, 25);
		contentPane.add(lblPrefered);
		
		btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/**
				 * Aqu� hacemos visibles los otros elementos
				 */
				textMod.setVisible(true);
				btnGuardar.setVisible(true);
			}
		});
		btnModificar.setBounds(321, 68, 89, 25);
		contentPane.add(btnModificar);
		
		textMod = new JTextField();
		textMod.setBounds(161, 104, 150, 20);
		contentPane.add(textMod);
		textMod.setColumns(10);
		textMod.setVisible(false);
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newMun = textMod.getText();
				textMod.setText("");
				lblPrefered.setText(newMun);
				ConfigFileHandler.setMunInConfigFile(Main.configFile, newMun);
				textMod.setVisible(false);
				btnGuardar.setVisible(false);
			}
		});
		btnGuardar.setBounds(321, 104, 89, 23);
		contentPane.add(btnGuardar);
		
		displayWarning = new JPanel();
		displayWarning.setBackground(new Color(249, 213, 213));
		displayWarning.setBounds(20, 10, 390, 49);
		contentPane.add(displayWarning);
		displayWarning.setVisible(false);
		if(Main.configFile==null) {
			displayWarning.setVisible(true);
		}
		
		JLabel lblMsg = new JLabel("AVISO: No se ha encontrado el archivo configuracion");
		lblMsg.setFont(new Font("Tahoma", Font.BOLD, 11));
		displayWarning.add(lblMsg);
		
		JButton btnCrear = new JButton("Crear");
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String mun = lblPrefered.getText();
				Main.configFile=ConfigFileHandler.createConfigFile();
				ConfigFileHandler.setMunInConfigFile(Main.configFile, mun);
				displayWarning.setVisible(false);
			}
		});
		displayWarning.add(btnCrear);
		
		btnNavegar = new JButton("GitHub");
		btnNavegar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
		            if (Desktop.isDesktopSupported()) {
		                Desktop desktop = Desktop.getDesktop();
		                if (desktop.isSupported(Desktop.Action.BROWSE)) {
		                	String url = "https://github.com/kasimxo/KaS-Weather";
		                    desktop.browse(URI.create(url));
		                }
		            }
		        } catch (IOException IOe) {
		            IOe.printStackTrace();
		        } catch (InternalError eI) {
		        	eI.printStackTrace();
		        }
				
			}
		});
		btnNavegar.setBounds(321, 135, 89, 90);
		contentPane.add(btnNavegar);
		
		JTextPane txtpnAsdfasdfasdfsadfasdfsadf = new JTextPane();
		txtpnAsdfasdfasdfsadfasdfsadf.setEditable(false);
		txtpnAsdfasdfasdfsadfasdfsadf.setText("KaS-Weather es un programa Open Source desarrollado por un estudiante de DAW.\n\nPuedes ver el codigo fuente en GitHub.");
		txtpnAsdfasdfasdfsadfasdfsadf.setBounds(20, 135, 274, 90);
		contentPane.add(txtpnAsdfasdfasdfsadfasdfsadf);
		btnGuardar.setVisible(false);
	}
}
