package com.bytemates.demo.service;


import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;

public class AddSignToPDF {
    public static void addSign() throws Exception{
        PdfReader reader = new PdfReader("sample.pdf");//src
        Rectangle box = reader.getPageSize(1);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("output.pdf"));//des
        Image image = Image.getInstance("img.jpg");//sign
        image.scalePercent(10, 10);
        PdfImage stream = new PdfImage(image, "", null);
        stream.put(new PdfName("ITXT_SpecialId"), new PdfName("123456789"));
        PdfIndirectObject ref = stamper.getWriter().addToBody(stream);
        image.setDirectReference(ref.getIndirectReference());
        System.out.println(box.getWidth() + "," + box.getHeight());
        image.setAbsolutePosition(box.getWidth() - 50, box.getHeight() - 800);
        PdfContentByte over = stamper.getOverContent(1);
        over.addImage(image);
        stamper.close();
        reader.close();
    }

    public static void main(String argv[]) throws Exception{
        AddSignToPDF.addSign();
    }
}
