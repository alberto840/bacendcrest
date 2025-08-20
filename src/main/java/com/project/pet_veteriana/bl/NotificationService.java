package com.project.pet_veteriana.bl;

import com.project.pet_veteriana.dto.SupportNotificationRequest;
import com.project.pet_veteriana.dto.TransactionNotificationRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotificationService {

    @Autowired
    private JavaMailSender mailSender;

    // Notificación de bienvenida a un nuevo vendedor
    @Transactional
    public void sendSubscriptionNotification(String toEmail, String sellerName) throws MessagingException {
        String subject = "¡Bienvenido como Vendedor en PetVeterinaria!";
        String htmlContent = buildHtmlContent(sellerName);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);

        mailSender.send(mimeMessage);
    }

    private String buildHtmlContent(String sellerName) {
        return """
        <html>
        <head>
            <style>
                body {
                    font-family: Arial, sans-serif;
                    background-color: #f4f4f4;
                    padding: 20px;
                }
                .container {
                    max-width: 600px;
                    margin: auto;
                    background-color: #ffffff;
                    padding: 20px;
                    border-radius: 8px;
                    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
                }
                h1, h2 {
                    color: #3c7f99;
                }
                .button {
                    display: inline-block;
                    margin-top: 20px;
                    padding: 10px 20px;
                    color: #ffffff;
                    background: linear-gradient(to right, #3c7f99, #00bfa6);
                    text-decoration: none;
                    border-radius: 5px;
                }
                .footer {
                    margin-top: 20px;
                    text-align: center;
                    font-size: 12px;
                    color: #888888;
                }
            </style>
        </head>
        <body>
            <div class="container">
                <h1>¡Bienvenido a PetVeterinaria!</h1>
                <h2>Hola, %s</h2>
                <p>
                    Gracias por unirte como vendedor. Ahora puedes publicar tus productos y servicios para ayudar a las mascotas.
                </p>
                <a href="https://petveterinaria.com/login" class="button">Ir al Panel de Vendedor</a>
                <div class="footer">
                    <p>&copy; 2025 PetVeterinaria. Todos los derechos reservados.</p>
                </div>
            </div>
        </body>
        </html>
        """.formatted(sellerName);
    }

    // Notificación de transacción
    @Transactional
    public void sendTransactionNotification(TransactionNotificationRequest request) throws MessagingException {
        String subject = "Confirmación de Transacción - PetVeterinaria";
        String htmlContent = buildTransactionHtml(request);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setTo(request.getEmail());
        helper.setSubject(subject);
        helper.setText(htmlContent, true);

        mailSender.send(mimeMessage);
    }

    private String buildTransactionHtml(TransactionNotificationRequest req) {
        return """
        <html>
        <head>
            <style>
                body {
                    font-family: Arial, sans-serif;
                    background-color: #f9f9f9;
                    padding: 20px;
                }
                .container {
                    max-width: 600px;
                    margin: auto;
                    background-color: white;
                    padding: 20px;
                    border-radius: 8px;
                    box-shadow: 0 2px 10px rgba(0,0,0,0.1);
                }
                h2 {
                    color: #3c7f99;
                }
                ul {
                    padding-left: 20px;
                }
                .footer {
                    margin-top: 20px;
                    text-align: center;
                    font-size: 12px;
                    color: #888;
                }
            </style>
        </head>
        <body>
            <div class="container">
                <h2>¡Gracias por tu compra, %s!</h2>
                <p>Detalles de tu transacción:</p>
                <ul>
                    <li><b>Detalle:</b> %s</li>
                    <li><b>Cantidad:</b> %.2f</li>
                    <li><b>Precio Unitario:</b> %.2f Bs.</li>
                    <li><b>Total Pagado:</b> %.2f Bs.</li>
                </ul>
                <p>Si tienes dudas, contáctanos. ¡Gracias por confiar en PetVeterinaria!</p>
                <div class="footer">
                    <p>&copy; 2025 PetVeterinaria</p>
                </div>
            </div>
        </body>
        </html>
        """.formatted(
                req.getBuyerName(),
                req.getDetail(),
                req.getQuantity(),
                req.getAmountPerUnit(),
                req.getTotalAmount()
        );
    }

    // Notificación de soporte
    @Transactional
    public void sendSupportNotification(SupportNotificationRequest request) throws MessagingException {
        String subject = "Soporte - PetVeterinaria: " + request.getIssue();
        String htmlContent = buildSupportHtml(request);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setTo(request.getEmail());
        helper.setSubject(subject);
        helper.setText(htmlContent, true);

        mailSender.send(mimeMessage);
    }

    private String buildSupportHtml(SupportNotificationRequest req) {
        return """
            <html>
            <head>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        background-color: #f9f9f9;
                        padding: 20px;
                    }
                    .container {
                        max-width: 600px;
                        margin: auto;
                        background-color: white;
                        padding: 20px;
                        border-radius: 8px;
                        box-shadow: 0 2px 10px rgba(0,0,0,0.1);
                    }
                    h2 {
                        color: #3c7f99;
                    }
                    .footer {
                        margin-top: 20px;
                        text-align: center;
                        font-size: 12px;
                        color: #888;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <h2>Respuesta a tu solicitud de soporte</h2>
                    <p><strong>Asunto:</strong> %s</p>
                    <p>%s</p>
                    <p>Gracias por confiar en PetVeterinaria. Estamos para ayudarte.</p>
                    <div class="footer">
                        <p>&copy; 2025 PetVeterinaria</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(req.getIssue(), req.getDetail());
    }

}
