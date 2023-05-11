package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import controller.Request;
import main.Main;
import utilities.CSVreader;
import utilities.Format;
import utilities.JsonHandler;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.Iterator;
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
import javax.swing.JList;
import javax.swing.JMenuItem;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Main_Window extends JFrame {

	private JPanel contentPane;
	private JTextField txt_input;
	private JScrollPane screen;
	private JTable table;
	private JLabel lbl_output;
	private JLabel lbl_header;
	private JList<String> list;
	private List<String> tablas;
	private DefaultTableModel tableModel = new DefaultTableModel();

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
		setBounds(100, 100, 900, 600);
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
							lbl_output.setText("Estos datos no est√°n disponbles en este momento.");
						}
					}
					
					txt_input.setText("");
					JsonHandler.toDataBase();
					lbl_header.setText(JsonHandler.municipio);
				} else {
					lbl_output.setText("Introduce el nombre de un municipio.");
				}
			}
		});
		btn_send.setBounds(10, 294, 100, 25);
		contentPane.add(btn_send);
		
		txt_input = new JTextField();
		txt_input.setBounds(10, 238, 235, 30);
		contentPane.add(txt_input);
		txt_input.setColumns(10);
		
		JButton btn_cancel = new JButton("Cancelar");
		btn_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txt_input.setText("");
			}
		});
		btn_cancel.setBounds(145, 294, 100, 25);
		contentPane.add(btn_cancel);
		
		screen = new JScrollPane();
		screen.setBounds(10, 50, 826, 125);
		contentPane.add(screen);
		
		lbl_header = new JLabel("");
		screen.setColumnHeaderView(lbl_header);
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
				mostrarView(selectedView);
				System.out.println("El index seleccionado es: " + list.getSelectedIndex() + " que corresponde con el Ìtem: " + tablas.get(list.getSelectedIndex()));
			}
		});
		screen.setRowHeaderView(list);
		
		table = new JTable(tableModel);
		screen.setViewportView(table);
		
		JLabel lbl_municipio = new JLabel("Seleccionar municipio:");
		lbl_municipio.setBounds(10, 213, 180, 25);
		contentPane.add(lbl_municipio);
		
		lbl_output = new JLabel("");
		lbl_output.setBounds(10, 177, 699, 25);
		contentPane.add(lbl_output);
		lbl_output.setOpaque(true);
		lbl_output.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_output.setBackground(Color.WHITE);
		
		
		
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
		
		//header.getColumnModel().getColumn(ABORT);
		
		for (int row = 1; row < tableModel.getRowCount(); row++) {
			String[] linea = viewContent.get(row).split(" ");
			for(int i = 0; i<linea.length; i++) {
				tableModel.setValueAt(linea[i], row-1, i);
			}
		}
	}

	/**
	 * This method displays a string in the main window screen.
	 * @param s
	 */
	public void setScreen(String s) {
		lbl_output.setText(s);
	}
}
