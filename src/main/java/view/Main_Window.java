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
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.JTable;

public class Main_Window extends JFrame {

	private JPanel contentPane;
	private JTextField txt_input;
	private JScrollPane screen;
	private JTable table;
	private JLabel lbl_output;
	private JLabel lbl_header;

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
		setIconImage(Toolkit.getDefaultToolkit().getImage(Main_Window.class.getResource("/resources/icon.png")));
		setTitle("KaS-Weather");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
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
								System.out.print(string);
							}
						} catch (FileNotFoundException e1) {
							e1.printStackTrace();
						}
					}
					
					txt_input.setText("");
					JsonHandler.toDataBase();
					lbl_output.setText(JsonHandler.getTemperatura());
					lbl_header.setText(JsonHandler.municipio);
				} else {
					lbl_output.setText("Introduce el nombre de un municipio.");
				}
			}
		});
		btn_send.setBounds(15, 226, 100, 25);
		contentPane.add(btn_send);
		
		txt_input = new JTextField();
		txt_input.setBounds(15, 170, 235, 30);
		contentPane.add(txt_input);
		txt_input.setColumns(10);
		
		JButton btn_cancel = new JButton("Cancelar");
		btn_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txt_input.setText("");
			}
		});
		btn_cancel.setBounds(150, 226, 100, 25);
		contentPane.add(btn_cancel);
		
		screen = new JScrollPane();
		screen.setBounds(15, 10, 699, 125);
		contentPane.add(screen);
		
		lbl_output = new JLabel("");
		screen.setViewportView(lbl_output);
		lbl_output.setOpaque(true);
		lbl_output.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_output.setBackground(Color.WHITE);
		
		lbl_header = new JLabel("");
		screen.setColumnHeaderView(lbl_header);
		
		JLabel lbl_municipio = new JLabel("Seleccionar municipio:");
		lbl_municipio.setBounds(15, 149, 180, 25);
		contentPane.add(lbl_municipio);
		
		table = new JTable();
		table.setBounds(0, 388, 450, 50);
		contentPane.add(table);
	}
	
	/**
	 * This method displays a string in the main window screen.
	 * @param s
	 */
	public void setScreen(String s) {
		lbl_output.setText(s);
	}
}
