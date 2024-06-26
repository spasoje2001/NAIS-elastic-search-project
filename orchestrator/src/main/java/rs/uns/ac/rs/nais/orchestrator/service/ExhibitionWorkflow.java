package rs.uns.ac.rs.nais.orchestrator.service;

import java.util.List;

public class ExhibitionWorkflow implements Workflow {
    
    private final List<WorkflowStep> steps;

    public ExhibitionWorkflow(List<WorkflowStep> steps) {
        super();
        this.steps = steps;
    }

    @Override
    public List<WorkflowStep> getSteps() {
        return steps;
    }

}
