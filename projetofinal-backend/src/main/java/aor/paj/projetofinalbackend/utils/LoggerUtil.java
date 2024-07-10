package aor.paj.projetofinalbackend.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Utility class for logging messages using Log4j.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
public class LoggerUtil {

    /**
     * The logger instance for logging messages.
     */
    private static final Logger logger = LogManager.getLogger(LoggerUtil.class);

    /**
     * Logs an INFO level message with structured details.
     *
     * @param prefix A prefix to include in the log message.
     * @param message The main message to log.
     * @param user The user associated with the log message.
     * @param token The token associated with the log message.
     */
    public static void logInfo(String prefix, String message, String user, String token) {
        logger.info(prefix + " - " + message + " - User: " + user + " - Token: " + token);
    }

    /**
     * Logs an ERROR level message with structured details.
     *
     * @param prefix A prefix to include in the log message.
     * @param message The main message to log.
     * @param user The user associated with the log message.
     * @param token The token associated with the log message.
     */
    public static void logError(String prefix, String message, String user, String token) {
        logger.error(prefix + " - " + message + " - User: " + user + " - Token: " + token);
    }

    /**
     * Logs a WARN level message with structured details.
     *
     * @param prefix A prefix to include in the log message.
     * @param message The main message to log.
     * @param user The user associated with the log message.
     * @param token The token associated with the log message.
     */
    public static void logWarn(String prefix, String message, String user, String token) {
        logger.warn(prefix + " - " + message + " - User: " + user + " - Token: " + token);
    }

}
