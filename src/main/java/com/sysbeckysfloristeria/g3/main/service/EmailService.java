package com.sysbeckysfloristeria.g3.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendErrorEmail(String errorMessage, String stackTrace) throws MessagingException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        
        helper.setFrom("jeremy.hernandez2890@gmail.com");
        helper.setTo("jeremy.rivera409@gmail.com");
        helper.setSubject("Error Crítico en Becky's Floristeria");

        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        
        String htmlContent = """
            <!DOCTYPE html>
            <html>
            <head>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        line-height: 1.6;
                        margin: 0;
                        padding: 0;
                    }
                    .header {
                        background: linear-gradient(45deg, #a41671, #d31178);
                        color: white;
                        padding: 20px;
                        text-align: center;
                    }
                    .content {
                        padding: 20px;
                        background-color: #fff;
                    }
                    .error-box {
                        background-color: #fff;
                        border-left: 5px solid #ff90c5;
                        padding: 15px;
                        margin: 20px 0;
                    }
                    .stack-trace {
                        background-color: #f8f8f8;
                        border: 1px solid #ddd;
                        padding: 15px;
                        margin: 20px 0;
                        overflow-x: auto;
                        font-family: monospace;
                    }
                    .timestamp {
                        color: #fe499b;
                        font-size: 0.9em;
                        text-align: right;
                    }
                    .footer {
                        background-color: #ffcd01;
                        color: #a41671;
                        text-align: center;
                        padding: 10px;
                        font-size: 0.8em;
                    }
                    .logo {
                        font-size: 24px;
                        color: white;
                        margin-bottom: 10px;
                    }
                    .alert-icon {
                        font-size: 48px;
                        margin-bottom: 20px;
                    }
                </style>
            </head>
            <body>
                <div class="header">
                    <div class="logo">🌹 Becky's Floristeria 🌹</div>
                    <div class="alert-icon">⚠️</div>
                    <h1>Alerta de Error Crítico</h1>
                </div>
                
                <div class="content">
                    <div class="timestamp">
                        Fecha y hora: %s
                    </div>
                    
                    <div class="error-box">
                        <h2 style="color: #a41671;">Mensaje de Error:</h2>
                        <p style="color: #d31178;">%s</p>
                    </div>
                    
                    <h3 style="color: #a41671;">Detalles Técnicos:</h3>
                    <div class="stack-trace">
                        <pre>%s</pre>
                    </div>
                </div>
                
                <div class="footer">
                    <p>Este es un mensaje automático del sistema de monitoreo de Becky's Floristeria</p>
                    <p>Por favor, no responder a este correo</p>
                </div>
            </body>
            </html>
            """.formatted(currentTime, errorMessage, stackTrace);
        
        helper.setText(htmlContent, true);
        emailSender.send(mimeMessage);
    }
} 