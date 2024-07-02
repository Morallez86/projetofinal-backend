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

@Path("/greetings")
public class GreetingResource {

    @Context
    private HttpServletRequest request;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGreeting() {
        try {
            // Teste com um endereço IP real
            String ipAddress =  "95.94.55.210";  // request.getRemoteAddr();//"95.94.55.210";    //"1.1.1.14" // Endereço IP público para teste
            System.out.println("IP Address: " + ipAddress);

            String countryCode = GeoLocationService.getCountryCode(ipAddress);
            System.out.println("Country Code: " + countryCode);

            Locale locale = LocaleService.getLocaleForCountry(countryCode);
            System.out.println("Locale: " + locale);

            return Response.ok("{\"locale\": \"" + locale.toLanguageTag() + "\"}").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"Error determining locale\"}")
                    .build();
        }
    }
}
