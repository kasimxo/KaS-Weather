package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import main.Main;

import javax.swing.JLabel;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class Preferences_Window extends JFrame {

	private int width=450;
	private int height=300;
	private JPanel contentPane;
	private JTextField textMod;
	private JButton btnModificar;
	private JLabel lblPrefered;
	private JLabel lblDisplayPrefered;
	private JButton btnGuardar;

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
		
		lblPrefered = new JLabel("Ninguno");
		lblPrefered.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrefered.setOpaque(true);
		lblPrefered.setBackground(Color.WHITE);
		lblPrefered.setBounds(161, 68, 150, 25);
		contentPane.add(lblPrefered);
		
		btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/**
				 * Aquí hacemos visibles los otros elementos
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
				textMod.setVisible(false);
				btnGuardar.setVisible(false);
				/* 
				 * Aquí tenemos que ir al archivo config y sobreescribir el valor
				 * */
			}
		});
		btnGuardar.setBounds(321, 104, 89, 23);
		contentPane.add(btnGuardar);
		
		JPanel displayWarning = new JPanel();
		displayWarning.setBackground(new Color(249, 213, 213));
		displayWarning.setBounds(20, 10, 390, 49);
		contentPane.add(displayWarning);
		displayWarning.setVisible(false);
		if(Main.configFile==null) {
			displayWarning.setVisible(true);
		}
		
		JLabel lblMsg = new JLabel("AVISO: No se ha encontrado el archivo configuraci\u00F3n");
		lblMsg.setFont(new Font("Tahoma", Font.BOLD, 11));
		displayWarning.add(lblMsg);
		
		JButton btnNewButton = new JButton("Buscar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jf = new JFileChooser(new File("").getAbsolutePath()+"/src/main/java/");
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos config", "txt", ".txt");
				jf.setFileFilter(filter);
				jf.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				jf.setDialogTitle("Seleccionar archivo preferencias");
				jf.setAcceptAllFileFilterUsed(false);
				if (jf.showOpenDialog(Main.pW) == JFileChooser.APPROVE_OPTION) { 
				      System.out.println("getCurrentDirectory(): " 
				         +  jf.getCurrentDirectory());
				      System.out.println("getSelectedFile() : " 
				         +  jf.getSelectedFile());
			    } else {
				      System.out.println("No Selection ");
			    }
			   
			}
		});
		displayWarning.add(btnNewButton);
		btnGuardar.setVisible(false);
	}
}
