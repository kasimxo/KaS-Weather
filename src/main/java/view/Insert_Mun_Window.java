package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Insert_Mun_Window extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
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
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btn_send = new JButton("Aceptar");
		btn_send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
