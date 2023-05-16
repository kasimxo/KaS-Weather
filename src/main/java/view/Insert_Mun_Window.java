package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.Request;
import main.Main;
import utilities.CSVreader;
import utilities.Format;
import utilities.JsonHandler;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class Insert_Mun_Window extends JFrame {

	private int width=450;
	private int height=300;
	private JPanel contentPane;
	private JTextField txt_input;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Insert_Mun_Window frame = new Insert_Mun_Window();
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
	public Insert_Mun_Window() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Insert_Mun_Window.class.getResource("/resources/icon.png")));
		setTitle("Insertar municipio");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		int x = (int) (Main.width/2) - width/2;
		int y = (int) (Main.height/2)- height/2;
		setBounds(x,y,width,height);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btn_send = new JButton("Aceptar");
		btn_send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!txt_input.getText().isEmpty()) {
					String mun = txt_input.getText();
					int munCode = -1;
					
					try {
						munCode = CSVreader.munCode(mun);
					} catch (Exception e1) {
						Main.mW.setlbl_Output("No se ha encontrado la ciudad " + mun);
						e1.printStackTrace();
					}
					
					if(munCode!=-1) {
						String url = Request.getUrlConsulta(munCode);
						String s = Request.getRawData(url);
						if(s!=null) {
							try {
								List<String> formato = Format.rawDataToList(s);
								for (String string : formato) {
									System.out.print(string);
								}
								
							} catch (FileNotFoundException e1) {
								e1.printStackTrace();
							}
							
						} else {
							Main.mW.setlbl_Output("Estos datos no estan disponbles en este momento.");
						}
					}
					
					txt_input.setText("");
					JsonHandler.toDataBase();
					Main.mW.actualizarMun();
				} else {
					Main.mW.setlbl_Output("Introduce el nombre de un municipio.");
				}
				setVisible(false);
			}
		});
		btn_send.setBounds(40, 137, 100, 25);
		contentPane.add(btn_send);
		
		txt_input = new JTextField();
		txt_input.setColumns(10);
		txt_input.setBounds(40, 81, 235, 30);
		contentPane.add(txt_input);
		
		JButton btn_cancel = new JButton("Cancelar");
		btn_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txt_input.setText("");
				setVisible(false);
			}
		});
		btn_cancel.setBounds(175, 137, 100, 25);
		contentPane.add(btn_cancel);
		
		JLabel lbl_municipio = new JLabel("Insertar municipio");
		lbl_municipio.setBounds(40, 55, 180, 25);
		contentPane.add(lbl_municipio);
	}
}
