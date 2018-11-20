package com.bytemates.demo.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;


@Service
public class ToPDFService {

    public static String[] imageFormats = new String[]{".png", ".jpg", ".jpeg", ".svg"};
    public static String[] textFormats = new String[]{".txt"};

    public static byte[] getByteArray(MultipartFile multipartFile) throws IOException {
        byte[] content = null;

        String fullName = multipartFile.getOriginalFilename();
        String extension = StringUtils.substringAfter(fullName, ".");

        content = multipartFile.getBytes();
        System.out.println("multipart file original name : " + multipartFile.getOriginalFilename());

        return content;
    }

    public static void generatePDFFromImage(byte[] inputByteArray, String filename) throws IOException {
        Document document = new Document();
        String output = filename + ".pdf";
        File file = new File(output);

        try {

            FileOutputStream fos = new FileOutputStream(file);
            PdfWriter writer = PdfWriter.getInstance(document, fos);
            writer.open();
            document.open();
            Image img = Image.getInstance(inputByteArray);
            img.scalePercent(25, 25);
            document.add(img);
            document.close();
            writer.close();
        }
        catch (Exception e){
            System.out.println(e);
        }

        byte[] arr = FileUtils.readFileToByteArray(file);
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
