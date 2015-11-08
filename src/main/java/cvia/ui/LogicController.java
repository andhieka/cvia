package cvia.ui;

import cvia.model.CV;
import cvia.model.JobDescription;

import java.io.File;

/**
 * Created by Michael Limantara on 11/5/2015.
 */
public class LogicController {
    private static LogicController logicControllerInstance = null;

    private LogicController() { }

    public static LogicController getInstance() {
        if (logicControllerInstance == null) {
            logicControllerInstance = new LogicController();
        }

        return logicControllerInstance;
    }

    public CV addCV(File file) {
        //TODO: AddCV Logic
        return null;
    }

    public void editCV(CV cv) {
        //TODO: EditCV Logic
    }

    public void deleteCV(Long id) {
        //TODO: DeleteCV Logic
    }

    public void deleteJD(JobDescription jobDescription) {
        //TODO: DeleteJD Logic
    }
}
