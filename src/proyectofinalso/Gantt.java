/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinalso;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

/**
 *
 * @author asus
 */
public class Gantt extends JTable {

    public Gantt(TableModel tm) {
        super(tm);
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int rowIndex, int columnIndex) {

        Component componente = super.prepareRenderer(renderer, rowIndex, columnIndex);
        if (getValueAt(rowIndex, columnIndex) != null && !getValueAt(rowIndex, columnIndex).equals("")) {
            if (getValueAt(rowIndex, columnIndex).getClass().equals(String.class)) {
                String valor = this.getValueAt(rowIndex, columnIndex).toString();
                if (valor.equals("Ej")) {
                    componente.setBackground(Color.GREEN);
                    componente.setForeground(Color.BLACK);
                }else if(valor.equals("*")){
                    componente.setBackground(Color.BLUE);
                    componente.setForeground(Color.WHITE);
                }else if(valor.equals("B")){
                    componente.setBackground(Color.DARK_GRAY);
                    componente.setForeground(Color.WHITE);
                }
                

            } else {
                componente.setBackground(Color.WHITE);
            }
        }

        return componente;

    }

}

