package com.bytemates.demo.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import org.springframework.stereotype.Service;

import java.io.*;


@Service
public class ToPDFService {

    public static void generatePDFFromImage(String filename, String extension){
        Document document = new Document();
        String input = filename + "." + extension;
        String output = filename + ".pdf";
        try {
            File file = new File(output);
            FileOutputStream fos = new FileOutputStream(file);
            PdfWriter writer = PdfWriter.getInstance(document, fos);
            writer.open();
            document.open();
            Image img = Image.getInstance(input);
            img.scalePercent(25, 25);
            document.add(img);
            document.close();
            writer.close();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }


    public void generatePDFFromText(String filename, String extension) throws IOException, DocumentException {
        Document pdfDoc = new Document();
        String input = filename + "." + extension;
        String output = filename + ".pdf";
        File file = new File(output);
        PdfWriter.getInstance(pdfDoc, new FileOutputStream(file))
                .setPdfVersion(PdfWriter.PDF_VERSION_1_7);
        pdfDoc.open();

        Font myFont = new Font();
        myFont.setStyle(Font.NORMAL);
        myFont.setSize(11);
        pdfDoc.add(new Paragraph("\n"));

        BufferedReader br = new BufferedReader(new FileReader(input));
        String strLine;
        while ((strLine = br.readLine()) != null) {
            Paragraph para = new Paragraph(strLine + "\n", myFont);
            para.setAlignment(Element.ALIGN_JUSTIFIED);
            pdfDoc.add(para);
        }
        pdfDoc.close();
        br.close();
    }
}
