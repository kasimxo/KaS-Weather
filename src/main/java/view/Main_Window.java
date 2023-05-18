package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import main.Main;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.JList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;

public class Main_Window extends JFrame {

	private int width=900;
	private int height=600;
	private JPanel contentPane;
	private JScrollPane screen;
	private JTable table;
	private JList<String> list;
	private List<String> tablas;
	private DefaultTableModel tableModel = new DefaultTableModel();
	private JLabel lbl_header;
	private JScrollPane tableContainer;
	private JComboBox mostrarMun = new JComboBox();
	private JButton btnInsertarMunicipio;
	private JButton btnPreferencias;

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
		try {
			setIconImage(Toolkit.getDefaultToolkit().getImage(Main_Window.class.getResource("/resources/icon.png")));
		} catch (Exception E) {
			System.out.println("Un error encontrando la imagen");
		}
		setTitle("KaS-Weather");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		int x = (int) (Main.width/2) - width/2;
		int y = (int) (Main.height/2)- height/2;
		setBounds(x, y, width, height);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		screen = new JScrollPane();
		screen.setBounds(10, 11, 826, 263);
		contentPane.add(screen);
		DefaultListModel model = new DefaultListModel();
		list = new JList(model);
		tablas = Main.mDB.showAllViews();
		for (String nombreVista : tablas) {
			model.addElement(nombreVista);
		}
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				String selectedView = tablas.get(list.getSelectedIndex()).replace(' ', '_');
				int indexMun = mostrarMun.getSelectedIndex();
				String selectedMunFromIndex = mostrarMun.getItemAt(indexMun).toString();
				if(selectedMunFromIndex!=null) {
					mostrarView(selectedView, selectedMunFromIndex);
				} else {
					mostrarView(selectedView);
				}
			}
		});
		screen.setRowHeaderView(list);
		
		lbl_header = new JLabel("Municipio:");
		screen.setColumnHeaderView(lbl_header);
		lbl_header.setHorizontalAlignment(SwingConstants.LEFT);
		
		tableContainer = new JScrollPane();
		screen.setViewportView(tableContainer);
		
		table = new JTable(tableModel);
		tableContainer.setViewportView(table);
		
		JButton btnDelMun = new JButton("Eliminar municipio");
		btnDelMun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.delMW.refreshMun();
				Main.delMW.setVisible(true);
			}
		});
		btnDelMun.setBounds(650, 350, 150, 25);
		contentPane.add(btnDelMun);
		
		
		actualizarMun();
		
		JLabel lblNewLabel = new JLabel("Seleccionar municipio");
		lblNewLabel.setBounds(10, 325, 169, 25);
		contentPane.add(lblNewLabel);
		
		btnInsertarMunicipio = new JButton("Insertar municipio");
		btnInsertarMunicipio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.insMW.setVisible(true);
			}
		});
		btnInsertarMunicipio.setBounds(650, 400, 150, 25);
		contentPane.add(btnInsertarMunicipio);
		
		btnPreferencias = new JButton("Preferencias");
		btnPreferencias.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.pW.setVisible(true);
			}
		});
		btnPreferencias.setBounds(650, 450, 150, 25);
		contentPane.add(btnPreferencias);
		
		
		
	}
	
	/**
	 * This method refreshes the JComboBox
	 */
	public void actualizarMun() {
		contentPane.remove(mostrarMun);
		List<String> municipiosList = Main.mDB.showTableColumn("CODES", "Nombre");
		String[] municipios = municipiosList.toArray(new String[municipiosList.size()]);
		mostrarMun = new JComboBox(municipios);
		mostrarMun.setBounds(10, 350, 250, 25);
		contentPane.add(mostrarMun);
	}
	
	public void deleteItemFromComboBox(int index) {
		mostrarMun.removeItemAt(index);
	}
	
	
	public void mostrarView(String selectedView, String mun) {
		List<String> viewContent = Main.mDB.showFromMun(selectedView, mun);
		String[] headers = viewContent.get(0).split(" ");
		tableModel.setRowCount(viewContent.size()-1);
		tableModel.setColumnCount(viewContent.get(0).split(" ").length);
		table = new JTable(tableModel);
		for(int col = 0; col<tableModel.getColumnCount(); col++) {
			for(int i = 0; i<headers.length; i++) {
				headers[i]=headers[i].replace('_', ' ');
			}
			tableModel.setColumnIdentifiers(headers);
		}
		
		for (int row = 1; row <= tableModel.getRowCount(); row++) {
			String[] linea = viewContent.get(row).split(" ");
			for(int i = 0; i<linea.length; i++) {
				tableModel.setValueAt(linea[i], row-1, i);
			}
		}
		lbl_header.setText("Municipio: "+mun);
	}

	private void mostrarView(String selectedView) {
		List<String> viewContent = Main.mDB.showViewContent(selectedView);
		String[] headers = viewContent.get(0).split(" ");
		tableModel.setRowCount(viewContent.size()-1);
		tableModel.setColumnCount(viewContent.get(0).split(" ").length);
		table = new JTable(tableModel);
		for(int col = 0; col<tableModel.getColumnCount(); col++) {
			for(int i = 0; i<headers.length; i++) {
				headers[i]=headers[i].replace('_', ' ');
			}
			tableModel.setColumnIdentifiers(headers);
		}
		
		for (int row = 1; row <= tableModel.getRowCount(); row++) {
			String[] linea = viewContent.get(row).split(" ");
			for(int i = 0; i<linea.length; i++) {
				tableModel.setValueAt(linea[i], row-1, i);
			}
		}
	}

}
