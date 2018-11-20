package com.bytemates.demo.service;


import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;

public class AddSignToPDF {
    public static byte[] addSign(byte[] signature, byte[] pdfBytes) throws Exception {
        PdfReader reader = new PdfReader(pdfBytes);//src
        Rectangle box = reader.getPageSize(reader.getNumberOfPages());
        File output = new File("output.pdf");
        File temp = new File("temp.png");
        FileUtils.writeByteArrayToFile(temp, signature);

        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(output));//des

        Image image = Image.getInstance(signature);//sign
        image.scalePercent(30, 30);
        PdfImage stream = new PdfImage(image, "", null);
        stream.put(new PdfName("ITXT_SpecialId"), new PdfName("123456789"));
        PdfIndirectObject ref = stamper.getWriter().addToBody(stream);
        image.setDirectReference(ref.getIndirectReference());

        System.out.println(box.getWidth() + "," + box.getHeight());
        image.setAbsolutePosition(box.getRight() - 200, 50);
        PdfContentByte over = stamper.getOverContent(1);
        over.addImage(image);
        stamper.close();
        reader.close();
        return FileUtils.readFileToByteArray(output);
    }

    public static void main(String argv[]) {
        //AddSignToPDF.addSign();
    }
}
