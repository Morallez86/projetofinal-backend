package aor.paj.projetofinalbackend.utils;

import jakarta.ejb.Singleton;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

@Singleton
public class EmailSender {

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
                    + "http://localhost:3000/RegistrationStatusPage/" + token;
            message.setText(emailBody);

            // Send email
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Error sending email to " + recipientEmail);
        }
    }

    public void sendRecoveryPassword(String recipientEmail, String token) {
        System.out.println(recipientEmail);
        System.out.println(token);
        recipientEmail= "testeAor@hotmail.com";
        System.out.println(recipientEmail);

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
                    + "http://localhost:3000/forgotPassword/" + token;
            message.setText(emailBody);

            // Send email
            Transport.send(message);
            System.out.println("Email send to " + recipientEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Error sending email to " + recipientEmail);
        }
    }


}
