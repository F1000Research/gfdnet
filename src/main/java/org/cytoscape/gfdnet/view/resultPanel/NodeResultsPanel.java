package org.cytoscape.gfdnet.view.resultPanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import org.cytoscape.gfdnet.model.businessobjects.GeneInput;
import org.cytoscape.gfdnet.model.businessobjects.Representation;

/**
 * @license Apache License V2 <http://www.apache.org/licenses/LICENSE-2.0.html>
 * @author Juan José Díaz Montaña
 */
public class NodeResultsPanel extends javax.swing.JPanel {
    private String[] representationNames;
    private int cont;
    private final String ontology;
    
    /**
     * Creates new form DefaultResultsPanel
     */
    public NodeResultsPanel(String ontology) {
        initComponents();
        this.ontology = ontology;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GeneLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        RepresentationList = new javax.swing.JList();

        GeneLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        GeneLabel.setText("Gene");

        RepresentationList.setCellRenderer(new MyListCellRenderer());
        RepresentationList.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RepresentationList.setDragEnabled(true);
        RepresentationList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RepresentationListMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(RepresentationList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(GeneLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(GeneLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void RepresentationListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RepresentationListMouseClicked
        if (evt.getClickCount()%2 == 0){
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                String SelectedValue = RepresentationList.getSelectedValue().toString();
                String URL = "http://amigo.geneontology.org/cgi-bin/amigo/term_details?term=" + SelectedValue.substring(0, SelectedValue.indexOf(" "));
                try {
                    URI uri = new URI(URL);
                    desktop.browse(uri);
                } catch (IOException ex) {
                    
                } catch (URISyntaxException ex) {
                    
                }
            }
        }
    }//GEN-LAST:event_RepresentationListMouseClicked

    public void updateView(GeneInput gene){
        GeneLabel.setText(gene.getName());
        Representation selectedRepresentation = gene.getRepresentationSelected();
        List<Representation> representations = new ArrayList<Representation>(gene.getRepresentations(ontology));
        representations.remove(selectedRepresentation);
        representationNames = new String[representations.size()];
        representationNames[0] = selectedRepresentation.getGoTerm().getName() + " " + selectedRepresentation.getGoTerm().getDescription();
        cont = 1;
        for (Representation representation : representations){
            String element = representation.getGoTerm().getName() + " " + representation.getGoTerm().getDescription();
            if (!(Arrays.asList(representationNames)).contains(element))
            {
                representationNames[cont] = element;
                cont++;
            }
        }
        RepresentationList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = Arrays.copyOfRange(representationNames, 0, cont);
            @Override
            public int getSize() { return strings.length; }
            @Override
            public Object getElementAt(int i) { return strings[i]; }
        });
    }
    
    class MyListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
        {
            Component c = super.getListCellRendererComponent(list,value,index,isSelected,cellHasFocus);
            if (index == 0 && list.getSelectedIndex() != 0){
                c.setBackground(Color.lightGray);
            }
            return c;
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel GeneLabel;
    private javax.swing.JList RepresentationList;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
