package aor.paj.projetofinalbackend.utils;

import jakarta.ejb.Singleton;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Singleton class responsible for sending emails using SMTP(Simple Mail Transfer Protocol) server configuration.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@Singleton
public class EmailSender {

    /**
     * Sends a confirmation email to the specified recipient with a token for account confirmation.
     *
     * @param recipientEmail The recipient's email address.
     * @param token The token to include in the confirmation email.
     */
    public void sendConfirmationEmail(String recipientEmail, String token) {

        // Configurações do servidor SMTP do Outlook
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.office365.com");
        properties.put("mail.smtp.port", "587");

        // Credenciais da conta do Outlook
        String username = "testeAor@hotmail.com"; // Insira seu endereço de e-mail do Outlook
        String password = "#Elias123"; // Insira sua senha do Outlook

        // Criando uma sessão de e-mail com autenticação
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Criando a mensagem de e-mail
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setSubject("Account Confirmation");
            String emailBody = "Please click the link below to confirm your account:\n\n"
                    + "https://localhost:3000/RegistrationStatusPage/" + token;
            message.setText(emailBody);

            // Send email
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Error sending email to " + recipientEmail);
        }
    }

    /**
     * Sends a recovery email to the specified recipient with a token for password recovery.
     *
     * @param recipientEmail The recipient's email address.
     * @param token The token to include in the recovery email.
     */
    public void sendRecoveryPassword(String recipientEmail, String token) {

        recipientEmail= "testeAor@hotmail.com";
        // Configurações do servidor SMTP do Outlook
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.office365.com");
        properties.put("mail.smtp.port", "587");

        // Credenciais da conta do Outlook
        String username = "testeAor@hotmail.com"; // Insira seu endereço de e-mail do Outlook
        String password = "#Elias123"; // Insira sua senha do Outlook

        // Criando uma sessão de e-mail com autenticação
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Criando a mensagem de e-mail
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setSubject("Recovery Password");
            String emailBody = "Please click the link below to recovery your password:\n\n"
                    + "https://localhost:3000/forgotPassword/" + token;
            message.setText(emailBody);

            // Send email
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Error sending email to " + recipientEmail);
        }
    }


}
