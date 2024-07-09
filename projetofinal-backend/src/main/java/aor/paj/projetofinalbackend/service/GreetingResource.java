package aor.paj.projetofinalbackend.service;

import aor.paj.projetofinalbackend.utils.GeoLocationService;
import aor.paj.projetofinalbackend.utils.LocaleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Locale;

/**
 * Service class for generating greetings based on client's country.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@Path("/greetings")
public class GreetingResource {

    @Context
    private HttpServletRequest request;

    /**
     * Retrieves the greeting message based on the client's IP address.
     *
     * @return Response containing the client's locale in JSON format.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGreeting() {
        try {
            // Teste com um endereço IP real
            String ipAddress =   "95.94.55.210"; // request.getRemoteAddr();//"95.94.55.210";    //"1.1.1.14" // Endereço IP público para teste

            String countryCode = GeoLocationService.getCountryCode(ipAddress);

            Locale locale = LocaleService.getLocaleForCountry(countryCode);

            return Response.ok("{\"locale\": \"" + locale.toLanguageTag() + "\"}").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"Error determining locale\"}")
                    .build();
        }
    }
}
