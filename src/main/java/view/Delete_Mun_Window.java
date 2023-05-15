package view;

import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JList;

public class Delete_Mun_Window extends JFrame {

	private JPanel contentPane;
	private JList list;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Delete_Mun_Window frame = new Delete_Mun_Window();
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
	public Delete_Mun_Window() {
		setTitle("Borrar municipio");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbl_Selection = new JLabel("Selecciona el municipio para borrar:");
		lbl_Selection.setBounds(50, 63, 257, 15);
		contentPane.add(lbl_Selection);
		
		DefaultListModel model = new DefaultListModel();
		list = new JList();
		list.setBounds(60, 90, 250, 50);
		contentPane.add(list);
	}
}
