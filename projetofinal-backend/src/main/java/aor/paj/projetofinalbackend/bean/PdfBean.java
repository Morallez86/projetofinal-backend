package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.dao.*;
import aor.paj.projetofinalbackend.pojo.WorkplaceProjectCount;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Stateless session bean responsible for generating PDF reports containing project statistics.
 * @see ProjectDao
 * @see ProjectBean
 * @see WorkplaceBean
 * @see ComponentBean
 * @see ResourceBean
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@Stateless
public class PdfBean {

    @EJB
    ProjectDao projectDao;

    @Inject
    private ProjectBean projectBean;

    @Inject
    private WorkplaceBean workplaceBean;

    @Inject
    private ComponentBean componentBean;

    @Inject
    private ResourceBean resourceBean;

    /**
     * Generates a PDF report containing various project statistics.
     *
     * @return A byte array representing the PDF document.
     * @throws DocumentException if there is an error creating the PDF document.
     */
    public byte[] generatePdf() throws DocumentException {

        String projectName = "Projeto Final AOR";
        long projectsSubmitted = projectBean.getTotalProjectCount();
        long approvedProjects = projectDao.getApprovedProjectCount();
        long finishedProjects = projectDao.getFinishedProjectCount();
        long canceledProjects = projectDao.getCanceledProjectCount();
        double avgExecutionTime = projectDao.getAverageExecutionTime();

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, out);
        document.open();
        document.add(new Paragraph("PROJECT STATISTICS SUMMARY"));
        document.add(new Paragraph("Project Name: " + projectName));
        document.add(new Paragraph("\n"));

        document.add(new Paragraph("Global Statistics: "));
        document.add(new Paragraph("Total of Projects Submitted: " + projectsSubmitted));
        document.add(new Paragraph("Number of Approved Projects: " + approvedProjects));
        document.add(new Paragraph("Average number of members per project: " + String.format("%.2f", projectBean.getAverageUsersPerProject())));
        document.add(new Paragraph("Percentage of Approved Projects: " + String.format("%.2f", projectBean.getPercentage(approvedProjects, projectsSubmitted)) + "%"));
        document.add(new Paragraph("Number of Finished Projects: " + finishedProjects));
        document.add(new Paragraph("Percentage of Finished Projects: " + String.format("%.2f", projectBean.getPercentage(finishedProjects, projectsSubmitted)) + "%"));
        document.add(new Paragraph("Number of Canceled Projects: " + canceledProjects));
        document.add(new Paragraph("Percentage of Canceled Projects: " + String.format("%.2f", projectBean.getPercentage(canceledProjects, projectsSubmitted)) + "%"));
        document.add(new Paragraph("Average Project Execution Time: " + String.format("%.2f", avgExecutionTime) + " days"));
        document.add(new Paragraph("\n"));

        // Fetch and add project count per workplace
        List<WorkplaceProjectCount> projectCountPerWorkplace = workplaceBean.getProjectCountPerWorkplace();
        document.add(new Paragraph("Statistics By Workplace:"));

        // Use com.itextpdf.text.List for PDF content
        com.itextpdf.text.List pdfList = new com.itextpdf.text.List(com.itextpdf.text.List.ORDERED);
        for (WorkplaceProjectCount dto : projectCountPerWorkplace) {
            pdfList.add(new ListItem(dto.getWorkplaceName() + ":" + "\n" + "Projects(" + dto.getProjectCount() + ") Percentage:"
                    + String.format("%.2f", dto.getPercentage()) + "%" + "\n" + "Percentage of available components: "
                    + componentBean.getTotalPercentageOfComponentsByWorkplace(dto.getWorkplaceName()) + "%"));
        }
        document.add(pdfList);

        document.add(new Paragraph("\n"));
        document.add(new Paragraph("Unused Resources:"));
        String unusedResourcesNames = resourceBean.getUnusedResourcesNames();
        document.add(new Paragraph(unusedResourcesNames));

        document.close();

        return out.toByteArray();
    }
}
