package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.Request;
import utilities.CSVreader;
import utilities.Format;
import utilities.JsonHandler;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.ScrollPaneConstants;

public class Main_Window extends JFrame {

	private JPanel contentPane;
	private JTextField txt_input;
	private JScrollPane screen;
	private JLabel lbl_output;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main_Window frame = new Main_Window();
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
	public Main_Window() {
		setTitle("KaS-Weather");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
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
						lbl_output.setText("No se ha encontrado la ciudad " + mun);
						e1.printStackTrace();
					}
					
					if(munCode!=-1) {
						String url = Request.getUrlConsulta(munCode);
						String s = Request.getRawData(url);
						try {
							List<String> formato = Format.rawDataToList(s);
							for (String string : formato) {
								System.out.println(string + "nepe\n");
							}
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					
					txt_input.setText("");
					lbl_output.setText(JsonHandler.getTemperatura());
				} else {
					lbl_output.setText("Introduce el nombre de un municipio.");
				}
			}
		});
		btn_send.setBounds(12, 226, 117, 25);
		contentPane.add(btn_send);
		
		txt_input = new JTextField();
		txt_input.setBounds(133, 160, 233, 30);
		contentPane.add(txt_input);
		txt_input.setColumns(10);
		
		JButton btn_cancel = new JButton("Cancelar");
		btn_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txt_input.setText("");
			}
		});
		btn_cancel.setBounds(282, 214, 117, 25);
		contentPane.add(btn_cancel);
		
		screen = new JScrollPane();
		screen.setBounds(15, 10, 420, 125);
		contentPane.add(screen);
		
		lbl_output = new JLabel("");
		lbl_output.setOpaque(true);
		screen.setViewportView(lbl_output);
		lbl_output.setBackground(Color.WHITE);
	}
}
