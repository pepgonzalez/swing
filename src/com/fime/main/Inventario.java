/**
 * 
 */
package com.fime.main;

import com.fime.view.Layout;

/**
 * @author pepgonzalez
 *
 */
public class Inventario {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("Hola Mundo");
		
		Layout screen;
		try {
			screen = new Layout();
			screen.setVisible(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
