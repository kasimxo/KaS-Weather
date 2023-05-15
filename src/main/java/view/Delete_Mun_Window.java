package view;

import java.awt.EventQueue;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.Main;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JButton;

public class Delete_Mun_Window extends JFrame {

	private JPanel contentPane;
	private JList list;
	private List<String> municipios;
	private DefaultListModel model;

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
		
		model = new DefaultListModel();
		list = new JList(model);
		
		list.setBounds(60, 90, 250, 50);
		contentPane.add(list);
		
		JButton btnDelete = new JButton("Borrar");
		btnDelete.setBounds(12, 207, 117, 25);
		contentPane.add(btnDelete);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(267, 207, 117, 25);
		contentPane.add(btnCancelar);
	}
	
	/**
	 * This method makes sure to add every mun to the list.
	 */
	public void refreshMun() {
		municipios = Main.mDB.showTableContent("CODES");
		municipios.remove(0);
		for (String string : municipios) {
			String[] splited = string.split(" ");
			model.addElement(splited[2]);
		}

	}
}
