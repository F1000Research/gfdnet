package org.cytoscape.gfdnet.controller.tasks;

import org.cytoscape.gfdnet.controller.CoreController;
import org.cytoscape.gfdnet.controller.ToolBarController;
import org.cytoscape.gfdnet.controller.utils.CySwing;
import org.cytoscape.gfdnet.model.businessobjects.GFDnetResult;
import org.cytoscape.gfdnet.model.businessobjects.Graph;
import org.cytoscape.gfdnet.model.businessobjects.go.Organism;
import org.cytoscape.gfdnet.model.logic.GFDnet;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

/**
 *
 * @author Juan José Díaz Montaña
 */
public class ExecuteGFDnetTask extends AbstractTask{
    private final Graph<String> network;
    private final Organism organism;
    private final String ontology;
    private CoreController core;
    private Thread taskThread;
    
    public ExecuteGFDnetTask(Graph<String> network, Organism organism, String ontology, CoreController core){
        this.network = network;
        this.organism = organism;
        this.ontology = ontology; 
        this.core = core;
    }
    
    @Override
    public void run(TaskMonitor tm){
        taskThread = Thread.currentThread();
        final ToolBarController toolbar = core.getToolbar();
        try{
            toolbar.configDBButtonEnabled(false);
            toolbar.setOntologyButtonEnabled(false);
            toolbar.setOrganismButtonEnabled(false);
            toolbar.executeButtonEnabled(false);
            toolbar.refreshButtonEnabled(false);
            final GFDnetResult result = GFDnet.evaluateGeneNames(network, organism, ontology, 1, tm);
            // TODO Change to observable task callback in 3.1
            if (!taskThread.isInterrupted()){
                core.showResults(result);
                CySwing.displayPopUpMessage("GFD-Net succesfully finished!");
            }
            else{
                toolbar.executeButtonEnabled(true);
            }
        }
        catch (Exception ex){
            toolbar.executeButtonEnabled(true);
            CySwing.displayPopUpMessage(ex.getMessage());
        }
        finally{
            toolbar.configDBButtonEnabled(true);
            toolbar.setOntologyButtonEnabled(true);
            toolbar.setOrganismButtonEnabled(true);
            toolbar.refreshButtonEnabled(true);
        }
    } 
    
    @Override
    public void cancel(){
        taskThread.interrupt();
        ToolBarController toolbar = core.getToolbar();
        toolbar.configDBButtonEnabled(true);
        toolbar.setOntologyButtonEnabled(true);
        toolbar.setOrganismButtonEnabled(true);
        toolbar.refreshButtonEnabled(true); 
        super.cancel();
        CySwing.displayPopUpMessage("GFD-Net execution was cancelled");
    }
}