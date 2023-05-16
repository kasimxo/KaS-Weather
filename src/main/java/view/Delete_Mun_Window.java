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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.Toolkit;

public class Delete_Mun_Window extends JFrame {

	private int width=450;
	private int height=300;
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
		setIconImage(Toolkit.getDefaultToolkit().getImage(Delete_Mun_Window.class.getResource("/resources/icon.png")));
		setTitle("Borrar municipio");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		int x = (int) (Main.width/2) - width/2;
		int y = (int) (Main.height/2)- height/2;
		setBounds(x,y,width,height);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbl_Selection = new JLabel("Selecciona el municipio para borrar:");
		lbl_Selection.setBounds(50, 30, 257, 15);
		contentPane.add(lbl_Selection);
		
		model = new DefaultListModel();
		
		JButton btnDelete = new JButton("Borrar");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*Aquí es donde metemos el coger el municipio de la lista y borrarlo de la base de datos*/
				
				String[] selectedMunArr = municipios.get(list.getSelectedIndex()).split(" ");
				Main.mDB.deleteMun(selectedMunArr[2]);
				Main.mW.deleteItemFromComboBox(list.getSelectedIndex());
				setVisible(false);
			}
		});
		btnDelete.setBounds(50, 150, 100, 25);
		contentPane.add(btnDelete);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnCancelar.setBounds(200, 150, 100, 25);
		contentPane.add(btnCancelar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(50, 50, 250, 80);
		contentPane.add(scrollPane);
		list = new JList(model);
		scrollPane.setViewportView(list);
	}
	
	/**
	 * This method makes sure to add every mun to the list.
	 */
	public void refreshMun() {
		model.removeAllElements();
		municipios = Main.mDB.showTableContent("CODES");
		municipios.remove(0);
		for (String string : municipios) {
			String[] splited = string.split(" ");
			model.addElement(splited[2]);
		}

	}
}
