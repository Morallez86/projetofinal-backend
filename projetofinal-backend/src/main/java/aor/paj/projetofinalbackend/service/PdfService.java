package aor.paj.projetofinalbackend.service;

import aor.paj.projetofinalbackend.bean.PdfBean;
import aor.paj.projetofinalbackend.dao.TokenDao;
import aor.paj.projetofinalbackend.entity.UserEntity;
import aor.paj.projetofinalbackend.utils.LoggerUtil;
import com.itextpdf.text.DocumentException;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;

/**
 * Service endpoint for handling PDF generation requests.
 * This service allows authenticated users to generate a PDF document.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@Path("/pdf")
public class PdfService {

    @Inject
    private PdfBean pdfBean;

    @Inject
    TokenDao tokenDao;

    /**
     * Generates a PDF document and returns it as an octet-stream response.
     *
     * @param authorizationHeader the Authorization header containing the bearer token
     * @return a Response object containing the generated PDF as octet-stream
     */
    @GET
    @Path("/generate")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response generatePdf(@HeaderParam("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring("Bearer".length()).trim();
        UserEntity user = tokenDao.findUserByTokenValue(token);
        try {
            byte[] pdfContents = pdfBean.generatePdf();
            LoggerUtil.logInfo("GENERATE PDF", "at " + LocalDateTime.now(),user.getEmail(), token);
            return Response.ok(pdfContents)
                    .header("Content-Disposition", "attachment; filename=\"application_statistics.pdf\"")
                    .build();
        } catch (DocumentException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}
