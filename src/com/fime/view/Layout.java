package com.fime.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.fime.dto.Medicine;

import net.miginfocom.swing.MigLayout;

public class Layout extends JFrame{

	private static final long serialVersionUID = 1L;
	
	//contenedores principales de la ventana
	private CardLayout mainCardLayout = new CardLayout();
    private CardLayout actionsCardLayout = new CardLayout();
    private JPanel mainCard;
    private JPanel actionsCard;
	
    //panel para agregar registro
    JPanel addMedicinePanel;
    JTextField nameInput, totalInput, priceInput, expirationInput;
    JButton add;
    
	//modelo de la tabla
	final InvTableModel model = new InvTableModel();
	JTable table;
	JScrollPane tableContainer;

	//botones de opciones
	JPanel buttonContainer;
	JButton addMedicine;
	JButton addMedicineElements;
	JButton removeMedicineElements;
	
	public Layout(){
		
		//atributos del frame
		setTitle("Inventario de Medicamentos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(800,400);
        
        mainCard = new JPanel();
        mainCard.setLayout(mainCardLayout);
        actionsCard = new JPanel();
        actionsCard.setLayout(actionsCardLayout);
        
		//se carga la tabla
        table = new JTable(model);
        tableContainer = new JScrollPane(table);
        
        //se crea el panel de captura       
        nameInput = new JTextField(30);
        totalInput = new JTextField(30);
        priceInput = new JTextField(30);
        expirationInput = new JTextField(30);
        
        add = new JButton("Agregar");
        add.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
              Medicine m = new Medicine(1L, "Paracetamol", 20, 25.5F, "10/10/2015");
              model.addRow(m);
              System.out.println("Pantalla principal");
              mainCardLayout.show(mainCard, "1");
            }
          });
        
        addMedicinePanel = new JPanel(new MigLayout());
        addMedicinePanel.add(new JLabel("Nombre del medicamento"));
        addMedicinePanel.add(nameInput, "wrap");
        addMedicinePanel.add(new JLabel("Unidades Disponibles"));
        addMedicinePanel.add(totalInput, "wrap");
        addMedicinePanel.add(new JLabel("Precio por unidad"));
        addMedicinePanel.add(priceInput, "wrap");
        addMedicinePanel.add(new JLabel("Fecha de Caducidad"));
        addMedicinePanel.add(expirationInput, "wrap");
        addMedicinePanel.add(add);
        
        //se crea el panel de botones
        buttonContainer = new JPanel(new MigLayout());
        addMedicine = new JButton("Agregar Medicamento");
        addMedicineElements = new JButton("Modificar existencia de Medicamento");
        removeMedicineElements = new JButton("Registrar Salida de Medicamento");
        
        buttonContainer.add(addMedicine);
        buttonContainer.add(addMedicineElements);
        buttonContainer.add(removeMedicineElements);
              
        //evento en el boton de agregar
        addMedicine.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent e){
            System.out.println("moviendo a formulario");
            mainCardLayout.show(mainCard, "2");
          }
        });
        
        mainCard.add(tableContainer, "1");
        mainCard.add(addMedicinePanel, "2");
        
        actionsCard.add(buttonContainer, "1");
        
        //se agrega la tabla al componente
        add(actionsCard, BorderLayout.NORTH);
        add(mainCard, BorderLayout.CENTER);
	}
}
