package com.fime.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.fime.dto.Medicine;

import net.miginfocom.swing.MigLayout;

public class Layout extends JFrame{

	private static final long serialVersionUID = 1L;
	
	//modelo de la tabla
	final InvTableModel model = new InvTableModel();
	JTable table;
	JScrollPane tableContainer;

	//botones de opciones
	JPanel buttonContainer;
	JButton addMedicine;
	
	public Layout(){
		
		//atributos del frame
		setTitle("Inventario de Medicamentos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(800,400);
        
		//se carga la tabla
        table = new JTable(model);
        tableContainer = new JScrollPane(table);
        
        //se crea el panel de botones
        buttonContainer = new JPanel(new MigLayout());
        addMedicine = new JButton("Agregar Medicamento");
        buttonContainer.add(addMedicine);
        
        //evento en el boton de agregar
        addMedicine.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent e){
            Medicine m = new Medicine(1L, "Paracetamol", 20, 25.5F, "10/10/2015");
            model.addRow(m);
            System.out.println("se agrego registro");
          }
        });
        

        //se agrega la tabla al componente
        add(buttonContainer, BorderLayout.NORTH);
        add(tableContainer, BorderLayout.CENTER);
	}
}
