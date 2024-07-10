package aor.paj.projetofinalbackend.utils;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.ThreadContext;

import java.io.IOException;

/**
 * Servlet filter to log IP addresses for incoming requests using Log4j's ThreadContext.
 * This filter logs the IP address of incoming HTTP requests to facilitate request tracing.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@WebFilter("/*")
public class IpLoggingFilter implements Filter {

    /**
     * Initializes the filter. This method is called by the servlet container when the filter is
     * first loaded into memory.
     *
     * @param filterConfig The configuration object for this filter.
     * @throws ServletException If an error occurs during initialization.
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Implementação do método init se necessário
    }

    /**
     * Filters incoming requests to log the IP address before passing the request along the filter chain.
     *
     * @param request The servlet request object.
     * @param response The servlet response object.
     * @param chain The filter chain to pass the request along.
     * @throws IOException If an I/O error occurs during the filtering process.
     * @throws ServletException If an error occurs while processing the request.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            String ipAddress = request.getRemoteAddr();
            ThreadContext.put("ipAddress", ipAddress);
        }

        try {
            chain.doFilter(request, response);
        } finally {
            ThreadContext.remove("ipAddress");
        }
    }

    /**
     * Cleans up resources used by the filter. This method is called by the servlet container when the filter is about to be removed from memory.
     */
    @Override
    public void destroy() {
        // Implementação do método destroy se necessário
    }
}

