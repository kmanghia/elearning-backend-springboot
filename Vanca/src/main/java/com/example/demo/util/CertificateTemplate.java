package com.example.demo.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CertificateTemplate {

    private static final float PAGE_WIDTH = PDRectangle.A4.getWidth();
    private static final float PAGE_HEIGHT = PDRectangle.A4.getHeight();
    private static final float MARGIN = 50;

    /**
     * Generate a certificate PDF
     */
    public static byte[] generateCertificatePDF(
            String certificateId,
            String studentName,
            String courseName,
            String completionDate,
            String verificationUrl) throws IOException, WriterException {

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Draw border
                drawBorder(contentStream);

                // Title
                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 36);
                contentStream.newLineAtOffset(centerX("CERTIFICATE OF COMPLETION", 36), PAGE_HEIGHT - 100);
                contentStream.showText("CERTIFICATE OF COMPLETION");
                contentStream.endText();

                // Subtitle
                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 14);
                contentStream.newLineAtOffset(centerX("This is to certify that", 14), PAGE_HEIGHT - 150);
                contentStream.showText("This is to certify that");
                contentStream.endText();

                // Student Name
                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 24);
                contentStream.newLineAtOffset(centerX(studentName, 24), PAGE_HEIGHT - 200);
                contentStream.showText(studentName);
                contentStream.endText();

                // Achievement text
                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 14);
                contentStream.newLineAtOffset(centerX("has successfully completed the course", 14), PAGE_HEIGHT - 250);
                contentStream.showText("has successfully completed the course");
                contentStream.endText();

                // Course Name
                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 18);
                contentStream.newLineAtOffset(centerX(courseName, 18), PAGE_HEIGHT - 290);
                contentStream.showText(courseName);
                contentStream.endText();

                // Completion Date
                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                contentStream.newLineAtOffset(centerX("Completed on: " + completionDate, 12), PAGE_HEIGHT - 350);
                contentStream.showText("Completed on: " + completionDate);
                contentStream.endText();

                // Certificate ID
                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 10);
                contentStream.newLineAtOffset(MARGIN, PAGE_HEIGHT - 450);
                contentStream.showText("Certificate ID: " + certificateId);
                contentStream.endText();

                // Generate QR Code
                BufferedImage qrCodeImage = generateQRCode(verificationUrl, 150);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(qrCodeImage, "PNG", baos);
                PDImageXObject pdImage = PDImageXObject.createFromByteArray(
                        document, baos.toByteArray(), "QR Code");

                // Draw QR Code
                float qrX = PAGE_WIDTH - MARGIN - 150;
                float qrY = MARGIN + 50;
                contentStream.drawImage(pdImage, qrX, qrY, 150, 150);

                // QR Code label
                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 9);
                contentStream.newLineAtOffset(qrX + 30, qrY - 15);
                contentStream.showText("Scan to verify");
                contentStream.endText();

                // Footer
                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_OBLIQUE), 10);
                contentStream.newLineAtOffset(centerX("E-Learning Platform", 10), MARGIN);
                contentStream.showText("E-Learning Platform");
                contentStream.endText();
            }

            // Save to byte array
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            document.save(output);
            return output.toByteArray();
        }
    }

    /**
     * Draw decorative border for certificate
     */
    private static void drawBorder(PDPageContentStream contentStream) throws IOException {
        float borderMargin = MARGIN - 10;
        contentStream.setLineWidth(2);
        contentStream.addRect(borderMargin, borderMargin,
                PAGE_WIDTH - (2 * borderMargin),
                PAGE_HEIGHT - (2 * borderMargin));
        contentStream.stroke();

        // Inner border
        float innerMargin = MARGIN - 5;
        contentStream.setLineWidth(0.5f);
        contentStream.addRect(innerMargin, innerMargin,
                PAGE_WIDTH - (2 * innerMargin),
                PAGE_HEIGHT - (2 * innerMargin));
        contentStream.stroke();
    }

    /**
     * Calculate X position to center text
     */
    private static float centerX(String text, float fontSize) throws IOException {
        PDType1Font font = new PDType1Font(
                fontSize > 20 ? Standard14Fonts.FontName.HELVETICA_BOLD : Standard14Fonts.FontName.HELVETICA);
        float textWidth = font.getStringWidth(text) / 1000 * fontSize;
        return (PAGE_WIDTH - textWidth) / 2;
    }

    /**
     * Generate QR Code image
     */
    private static BufferedImage generateQRCode(String text, int size) throws WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, size, size);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }
}
