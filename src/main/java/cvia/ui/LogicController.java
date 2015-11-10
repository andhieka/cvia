package cvia.ui;

import cvia.analyzer.Matcher;
import cvia.analyzer.Report;
import cvia.model.CV;
import cvia.model.JobDescription;
import cvia.parser.PDFCVParser;
import cvia.reader_writer.PDFReadResult;
import cvia.reader_writer.PDFTextChunkReader;
import cvia.storage.CVManager;
import cvia.storage.JDManager;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Michael Limantara on 11/5/2015.
 */
public class LogicController {
    private static LogicController logicControllerInstance = null;
    private PDFCVParser pdfParser;
    private PDFTextChunkReader reader;
    private CVManager cvManager;
    private JDManager jdManager;

    private LogicController() {
        pdfParser = new PDFCVParser();
        reader = new PDFTextChunkReader();
        cvManager = new CVManager();
        jdManager = new JDManager();
    }

    public static LogicController getInstance() {
        if (logicControllerInstance == null) {
            logicControllerInstance = new LogicController();
        }

        return logicControllerInstance;
    }

    public List<Report> processMatchingAndGenerateReport(List<CV> cvList, JobDescription jobDescription) {
        Matcher matcher = new Matcher(cvList, jobDescription);
        matcher.run();
        return matcher.getReports();
    }

    public CV addCV(File file) throws IOException {
        CV addedCV = parseCVFromPdf(file);
        Long cvId = cvManager.save(addedCV);
        addedCV.setId(cvId);
        return addedCV;
    }

    public List<CV> listCV() {
        return cvManager.listCV();
    }

    public void editCV(Long id, CV cv) {
        cvManager.update(id, cv);
    }

    public void deleteCV(Long id) {
        cvManager.delete(id);
    }

    public JobDescription addJD(JobDescription jobDescription) {
        Long id = jdManager.save(jobDescription);
        jobDescription.setId(id);
        return jobDescription;
    }

    public List<JobDescription> listJobDescription() {
        return jdManager.listJD();
    }

    public void editJD(Long id, JobDescription jobDescription) {
        jdManager.update(id, jobDescription);
    }

    public void deleteJD(Long id) {
        jdManager.delete(id);
    }

    public void saveCV(CV cv) {
        Long id = cvManager.save(cv);
        cv.setId(id);
    }

    public void saveJD(JobDescription jd) {
        Long id = jdManager.save(jd);
        jd.setId(id);
    }

    private CV parseCVFromPdf(File file) throws IOException {
        // Read the PDF file
        PDFReadResult readResult = reader.readPDFFromFile(file);
        // Parse PDF into CV object
        CV cv = pdfParser.parse(readResult);

        return cv;
    }


}
