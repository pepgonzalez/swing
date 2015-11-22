package com.fime.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.fime.db.DBManager;
import com.fime.dto.Medicine;
import com.fime.utils.Utilery;

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
    JButton add, clean;
    
	//modelo de la tabla
	final InvTableModel model = new InvTableModel();
	JTable table;
	JScrollPane tableContainer;

	//botones de opciones
	JPanel buttonContainer;
	JButton addMedicine;
	JButton addMedicineElements;
	JButton removeMedicineElements;
	JTextField search;
	
	//boton y panel para opcion de regresar
	JPanel returnButtonContainer;
	JButton returnButton;
	
	//contenido para pantalla de retiro de medicamento
	JPanel substractMedicinePanel;
	JComboBox<String> substractMedicineCombo;
	JTextField substractMedicineInput;
	JButton substractMedicineButton;
	
	/*contenido para pantalla de captura de medicamento*/
	JPanel addMedicinePanel2;
	JComboBox<String> addMedicineCombo;
	JTextField addMedicineInput;
	JButton addMedicineButton;
	
	
	//gestor de base de datos
	DBManager db = new DBManager();
	
	JFrame innerFrame;
	
	public Layout() throws Exception{
		
		innerFrame = this;
		//atributos del frame
		setTitle("Inventario de Medicamentos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(700,400);
        
        mainCard = new JPanel();
        mainCard.setLayout(mainCardLayout);
        actionsCard = new JPanel();
        actionsCard.setLayout(actionsCardLayout);
        
		//se carga la tabla
        table = new JTable(model);
        tableContainer = new JScrollPane(table);
        model.reloadModel();
        
        //se crea el panel de captura       
        nameInput = new JTextField(30);
        totalInput = new JTextField(30);
        priceInput = new JTextField(30);
        expirationInput = new JTextField(30);
        
        clean = new JButton("Limpiar Campos");
        clean.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	System.out.println("Limpiando campos");
            	nameInput.setText("");
            	totalInput.setText("");
            	priceInput.setText("");
            	expirationInput.setText("");
            }
        });
        
        add = new JButton("Agregar");
        add.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	
              String name = nameInput.getText();
              String total = totalInput.getText();
              String price = priceInput.getText();
              String expirationDate = expirationInput.getText();
              
              if(name.length() <= 0 || total.length() <= 0 || price.length() <= 0 || expirationDate.length() <= 0){
            	  System.out.println("Informacion incompleta");
            	  
            	  JOptionPane.showMessageDialog(innerFrame,"Complete todos los campos para poder continuar","Error de captura de información",JOptionPane.ERROR_MESSAGE);
            	  
              }else{
            	  
            	  //validacion de campos
            	  
            	  if(!Utilery.isInteger(total)){
            		  JOptionPane.showMessageDialog(innerFrame,"Error en Cantidad. " + total + " No es un valor numerico. Verifique.","Error de captura de información",JOptionPane.ERROR_MESSAGE);
            		  return;
            	  }
            	  
            	  if(!Utilery.isFloat(price)){
            		  JOptionPane.showMessageDialog(innerFrame,"Error en Precio. " + price + " No es un valor numerico. Verifique.","Error de captura de información",JOptionPane.ERROR_MESSAGE);
            		  return;
            	  }
            	  
            	  if(!Utilery.isDate(expirationDate)){
            		  JOptionPane.showMessageDialog(innerFrame,"Error en Fecha de caducidad. " + expirationDate + " No es una fecha válida (dd/mm/yyyy). Verifique.","Error de captura de información",JOptionPane.ERROR_MESSAGE);
            		  return;
            	  }
            	  
				try {
					List<Medicine> list = db.getMedicineList();
					for(Medicine m : list){
						String currentName = m.getName().toUpperCase();
						System.out.println("registro actual: " + currentName);
						System.out.println("capturado: " + name.toUpperCase());
						if (currentName.equals(name.toUpperCase())){
							JOptionPane.showMessageDialog(innerFrame,
									"Error. " + name + " ya existe en la base de datos. Verifique.","Error de captura de información",
									JOptionPane.ERROR_MESSAGE);
		            		return;
						}
	            	}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
            	  
            	  
            	  try {
            		System.out.println("Informacion completa, guardando");
                	//se agrega la medicina a la base de datos
                	Medicine m = new Medicine(1L, name, new Integer(total) , new Float(price), expirationDate);
					db.insertMedicine(m);
					//se manda a recargar el modelo
	                model.reloadModel();
	                
	                addMedicineCombo.addItem(m.getName().toUpperCase());
	                substractMedicineCombo.addItem(m.getName().toUpperCase());
	                
	                //se retorna a la pantall del listado
	                System.out.println("Pantalla principal");
	                mainCardLayout.show(mainCard, "1");
	                actionsCardLayout.show(actionsCard,"1");
            	  } catch (Exception e1) {
					e1.printStackTrace();
            	  }
              }
            }
          });
        
        addMedicinePanel = new JPanel(new MigLayout());
        addMedicinePanel.add(new JLabel("Nombre del medicamento:"));
        addMedicinePanel.add(nameInput, "wrap");
        addMedicinePanel.add(new JLabel("Unidades Disponibles:"));
        addMedicinePanel.add(totalInput, "wrap");
        addMedicinePanel.add(new JLabel("Precio por unidad:"));
        addMedicinePanel.add(priceInput, "wrap");
        addMedicinePanel.add(new JLabel("Fecha de Caducidad:"));
        addMedicinePanel.add(expirationInput, "wrap");
        
        JPanel bPanel = new JPanel(new MigLayout());
        bPanel.add(add);
        bPanel.add(clean);
        addMedicinePanel.add(bPanel, "span");
        
        //se crea el panel de botones
        buttonContainer = new JPanel(new MigLayout());
        addMedicine = new JButton("Agregar Medicamento");
        addMedicineElements = new JButton("Modificar existencia de Medicamento");
        removeMedicineElements = new JButton("Registrar Salida de Medicamento");
        search = new JTextField(45);
        
        search.addKeyListener(new KeyAdapter() {
            
        	public void keyReleased(KeyEvent e) {
                JTextField textField = (JTextField) e.getSource();
                String text = textField.getText();
                if (text.length() <= 0){
                	model.reloadModel();
                }else{
                	model.reloadModel(text);
                }
            }
        });
        
        buttonContainer.add(addMedicine);
        buttonContainer.add(addMedicineElements);
        buttonContainer.add(removeMedicineElements, "wrap");
        buttonContainer.add(new JLabel("Buscar Medicamento:"));
        buttonContainer.add(search, "span 2");
              
        //evento en el boton de agregar
        addMedicine.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent e){
            System.out.println("moviendo a formulario");
            mainCardLayout.show(mainCard, "2");
            actionsCardLayout.show(actionsCard,"2");
          }
        });
        
        //evento en el boton de agregar unidades a un medicament
        removeMedicineElements.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent e){
            System.out.println("moviendo a retirar unidades");
            mainCardLayout.show(mainCard, "3");
            actionsCardLayout.show(actionsCard,"2");
          }
        });
        
        addMedicineElements.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.out.println("moviendo a agregar unidades");
                mainCardLayout.show(mainCard, "4");
                actionsCardLayout.show(actionsCard,"2");
              }
            });
        
        //panel para boton de regresar
        returnButtonContainer = new JPanel(new MigLayout());
        returnButton = new JButton("Regresar");
        returnButtonContainer.add(returnButton);
        
        returnButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.out.println("moviendo a listado principal");
                mainCardLayout.show(mainCard, "1");
                actionsCardLayout.show(actionsCard,"1");
            }
        });
        
        
        /* --------------------------------------------------------------*/
        // panel de contenido para retirar medicina
        substractMedicinePanel = new JPanel(new MigLayout());
        List<Medicine> list = db.getMedicineList();
        String[] comboList = new String[list.size()];
        
        for(int i = 0; i < list.size(); i++){
        	comboList[i] = list.get(i).getName();
        }
        substractMedicineCombo = new JComboBox<String>(comboList);
        substractMedicineInput = new JTextField(10);
        substractMedicineButton = new JButton("Retirar medicamento");
        
        substractMedicineButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String selectedMedicine = (String) substractMedicineCombo.getSelectedItem();
                String total = substractMedicineInput.getText();
                System.out.println("retirando " + total + " unidades de: " + selectedMedicine);
                
                if((total.length() <= 0) || (!Utilery.isInteger(total))){
          		  JOptionPane.showMessageDialog(innerFrame,"Error en Cantidad. El valor capturado en unidades no es correcto. Verifique.","Error de captura de información",JOptionPane.ERROR_MESSAGE);
          		  return;
          	  	}else{
          	  		try {
						Integer currentTotal = db.getAvailableMedicine(selectedMedicine);
						if (currentTotal < Integer.parseInt(total)){
							JOptionPane.showMessageDialog(innerFrame,
									"Error. Solo existen " + currentTotal + " unidades disponibles del medicamento: " + selectedMedicine,
									"Error de captura de información",JOptionPane.ERROR_MESSAGE);
						}else{
							db.updateMedicineTotal(selectedMedicine, currentTotal - Integer.parseInt(total));
							db.addDownMovement(selectedMedicine);
							JOptionPane.showMessageDialog(innerFrame,
									"Información de Medicamento: " + selectedMedicine + " actualizada.",
									"Operación exitosa",JOptionPane.WARNING_MESSAGE);
							 model.reloadModel();
				             //se retorna a la pantall del listado
				             System.out.println("Pantalla principal");
				             mainCardLayout.show(mainCard, "1");
				             actionsCardLayout.show(actionsCard,"1");
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
          	  	}
            }
        });
        
        substractMedicinePanel.add(new JLabel(""),"span 3");
        substractMedicinePanel.add(new JLabel(""),"wrap");
        substractMedicinePanel.add(new JLabel("Seleccione Medicamento y Unidades a retirar:"), "span 3");
        substractMedicinePanel.add(new JLabel(""),"wrap");
        
        substractMedicinePanel.add(new JLabel("Medicamento:"));
        substractMedicinePanel.add(substractMedicineCombo, "wrap");
        substractMedicinePanel.add(new JLabel("Unidades a retirar:"));
        substractMedicinePanel.add(substractMedicineInput, "wrap");
        substractMedicinePanel.add(substractMedicineButton);
        
        
        /*------------------------------------------------------------------------------------------*/
     // panel de contenido para retirar medicina
        addMedicinePanel2 = new JPanel(new MigLayout());
        
        addMedicineCombo = new JComboBox<String>(comboList);
        addMedicineInput = new JTextField(10);
        addMedicineButton = new JButton("Agregar medicamento");
        
        addMedicineButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String selectedMedicine = (String) addMedicineCombo.getSelectedItem();
                String total = addMedicineInput.getText();
                System.out.println("retirando " + total + " unidades de: " + selectedMedicine);
                
                if((total.length() <= 0) || (!Utilery.isInteger(total))){
          		  JOptionPane.showMessageDialog(innerFrame,"Error en Cantidad. El valor capturado en unidades no es correcto. Verifique.","Error de captura de información",JOptionPane.ERROR_MESSAGE);
          		  return;
          	  	}else{
          	  		try {
						Integer currentTotal = db.getAvailableMedicine(selectedMedicine);
						db.updateMedicineTotal(selectedMedicine, currentTotal + Integer.parseInt(total));
						db.addUpMovement(selectedMedicine);
						JOptionPane.showMessageDialog(innerFrame,
									"Información de Medicamento: " + selectedMedicine + " actualizada.",
									"Operación exitosa",JOptionPane.WARNING_MESSAGE);
						model.reloadModel();
				        //se retorna a la pantall del listado
				        System.out.println("Pantalla principal");
				        mainCardLayout.show(mainCard, "1");
				        actionsCardLayout.show(actionsCard,"1");
					} catch (Exception e1) {
						e1.printStackTrace();
					}
          	  	}
            }
        });
        
        addMedicinePanel2.add(new JLabel(""),"span 3");
        addMedicinePanel2.add(new JLabel(""),"wrap");
        addMedicinePanel2.add(new JLabel("Seleccione Medicamento y unidades a Agregar a Inventario:"), "span 3");
        addMedicinePanel2.add(new JLabel(""),"wrap");
        
        addMedicinePanel2.add(new JLabel("Medicamento:"));
        addMedicinePanel2.add(addMedicineCombo, "wrap");
        addMedicinePanel2.add(new JLabel("Unidades a Agregar:"));
        addMedicinePanel2.add(addMedicineInput, "wrap");
        addMedicinePanel2.add(addMedicineButton);
        
        
        /*------------------------------------------------------------------------------------------*/
        
        mainCard.add(tableContainer, "1");
        mainCard.add(addMedicinePanel, "2");
        mainCard.add(substractMedicinePanel, "3");
        mainCard.add(addMedicinePanel2, "4");
        
        
        actionsCard.add(buttonContainer, "1");
        actionsCard.add(returnButtonContainer, "2");
        
        //se agrega la tabla al componente
        add(actionsCard, BorderLayout.NORTH);
        add(mainCard, BorderLayout.CENTER);
	}
}
