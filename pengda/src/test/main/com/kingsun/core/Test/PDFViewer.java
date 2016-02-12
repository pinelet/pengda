package com.kingsun.core.Test;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import com.sun.pdfview.PagePanel;

public class PDFViewer {

	/**浏览页面，生成页面的图片字节数据 
     *  
     * @param fileName 文档名称及路径 
     * @param page 
     * @return 
     */  
    public void ViewPage(String fileName, int page) {  
//      if(!fileName.endsWith("pdf")||!fileName.endsWith("PDF"))  
//          return null;  
        try  
        {          
            PDFFile pdfFile = this.getPdfFile(fileName);  
            PDFPage pdfPage = pdfFile.getPage(page);  

              
            //get the width and height for the doc at the default zoom   
            Rectangle rect = new Rectangle(0,0,  
                    (int)pdfPage.getBBox().getWidth(),  
                    (int)pdfPage.getBBox().getHeight());  
              
            //generate the image  
            Image img = pdfPage.getImage(  
                    rect.width/2, rect.height/2, //width & height  
                    rect, // clip rect  
                    null, // null for the ImageObserver  
                    true, // fill background with white  
                    true  // block until drawing is done  
                    );  
           // ByteArrayOutputStream baos=new ByteArrayOutputStream();           
            BufferedImage bImg =(BufferedImage)img;           
            ImageIO.write(bImg, "JPG", new File("g:\\temp\\sign.jpg"));  
              
            //return baos.toByteArray();   
        }  
        catch(Exception ex)  
        {  
            ex.printStackTrace();  
        }         
        //return null;  
    }  
/**建立PDF文档读取类 
     *  
     * @param filePath PDF文件的路径 
     * @return null 或者PDFFile instance 
     */  
    private PDFFile getPdfFile(String filePath)  
    {  
        try  
        {  
            File file = new File(filePath);  
            RandomAccessFile raf = new RandomAccessFile(file, "r");  
            FileChannel channel = raf.getChannel();  
            ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());  
            PDFFile pdffile = new PDFFile(buf);  
            return pdffile;  
        }  
        catch(Exception ex)  
        {  
            ex.printStackTrace();  
        }  
        return null;  
    } 
    
    
	 public static void setup() throws IOException {  
	      
	        //set up the frame and panel  
	        JFrame frame = new JFrame("PDF Test");  
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
	        PagePanel panel = new PagePanel();  
	        frame.add(panel);  
	        frame.pack();  
	        frame.setVisible(true);  
	  
	        //load a pdf from a byte buffer  
	        String urlPath = "G:\\temp\\14235_22919_2012-01-10_2_sign.pdf";  
	        File file = new File(urlPath);  
	        RandomAccessFile raf = new RandomAccessFile(file, "r");  
	        FileChannel channel = raf.getChannel();  
	        ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY,  
	            0, channel.size());  
	        PDFFile pdffile = new PDFFile(buf);  
	  
	        // show the first page  
	        PDFPage page = pdffile.getPage(0);  
	        panel.showPage(page);  
	    }  
	  
	    public static void main(final String[] args) {  
	    	new PDFViewer().ViewPage("G:\\temp\\14235_22919_2012-01-10_2_sign.pdf", 1);
	    }
}
