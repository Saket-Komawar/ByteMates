package com.bytemates.demo.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Arrays;


@Service
public class ToPDFService {

    public static String[] imageFormats = new String[]{".png", ".jpg", ".jpeg", ".svg"};
    public static String[] textFormats = new String[]{".txt"};

    public static byte[] getByteArray(MultipartFile multipartFile) throws IOException, DocumentException {
        byte[] content = null;

        String[] fileExt = multipartFile.getOriginalFilename().split(".");
        String fileName = fileExt[0];
        String extension = fileExt[1];

        content = multipartFile.getBytes();
        if(Arrays.asList(imageFormats).contains(extension)){
            return generatePDFFromImage(content, fileName);
        }
        else if(Arrays.asList(textFormats).contains(extension)){
            return generatePDFFromText(content, fileName);
        }
        else if(extension.equals("pdf")){
            return content;
        }
        return content;
    }

    public static byte[] generatePDFFromImage(byte[] inputByteArray, String filename) throws IOException {
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

        return FileUtils.readFileToByteArray(file);
    }

    public static byte[] generatePDFFromText(byte[] inputByteArray, String filename) throws IOException, DocumentException {
        Document pdfDoc = new Document();
        String output = filename + ".pdf";
        File file = new File(output);
        PdfWriter.getInstance(pdfDoc, new FileOutputStream(file))
                .setPdfVersion(PdfWriter.PDF_VERSION_1_7);
        pdfDoc.open();

        Font myFont = new Font();
        myFont.setStyle(Font.NORMAL);
        myFont.setSize(11);
        pdfDoc.add(new Paragraph("\n"));

        ByteArrayInputStream bai = new ByteArrayInputStream(inputByteArray);
        BufferedReader br = new BufferedReader(new InputStreamReader(bai));
        String strLine;
        while ((strLine = br.readLine()) != null) {
            Paragraph para = new Paragraph(strLine + "\n", myFont);
            para.setAlignment(Element.ALIGN_JUSTIFIED);
            pdfDoc.add(para);
        }
        pdfDoc.close();
        br.close();
        return FileUtils.readFileToByteArray(file);
    }
}
