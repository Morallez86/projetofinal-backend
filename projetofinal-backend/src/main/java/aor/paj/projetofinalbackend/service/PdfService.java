package aor.paj.projetofinalbackend.service;

import aor.paj.projetofinalbackend.bean.PdfBean;
import com.itextpdf.text.DocumentException;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/pdf")
public class PdfService {

    @Inject
    private PdfBean pdfBean;

    @GET
    @Path("/generate")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response generatePdf(@HeaderParam("Authorization") String authorizationHeader) {
        try {
            byte[] pdfContents = pdfBean.generatePdf();
            return Response.ok(pdfContents)
                    .header("Content-Disposition", "attachment; filename=\"application_statistics.pdf\"")
                    .build();
        } catch (DocumentException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}
