package test.serverframe.armc.server.util;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.springframework.util.ResourceUtils;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.List;

/**
 * Created by 79870 on 2018/10/9.
 */
public class Word2Html_doc {



    public static void main(String argv[]) {
        try {
           //convert2Html("D:/uplodFile/docx.docx","D:/uplodFile/docx.html","D:/uplodFile","D:/uplodFile");
           // String result = Html2String.readfile("D:\\test\\ac\\doc.html");

            System.out.println( ResourceUtils.getURL("classpath:").getPath());

            System.out.println(System.getProperty("user.dir"));

           // System.out.println("result:  "+result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeFile(String content, String path) {
        FileOutputStream fos = null;
        BufferedWriter bw = null;
        try {
            File file = new File(path);
            fos = new FileOutputStream(file);
            bw = new BufferedWriter(new OutputStreamWriter(fos,"utf-8"));
            bw.write(content);
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();
                if (fos != null)
                    fos.close();
            } catch (IOException ie) {
            }
        }
    }

    public static String convert2Html(String fileName, String outPutFile,String imgPath,String httpImagePath,String ftpImgPath)
            throws TransformerException, IOException,
            ParserConfigurationException {
        //文件夹
        FileUtils.mkdirs(imgPath);
        //先连接ftp
      //  FTPUtil.initFtpClient();
        //在创建
        //FTPUtil.CreateDirecroty(ftpImgPath);
        HWPFDocument wordDocument = new HWPFDocument(new FileInputStream(fileName));//WordToHtmlUtils.loadDoc(new FileInputStream(inputFile));
        WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
                DocumentBuilderFactory.newInstance().newDocumentBuilder()
                        .newDocument());
        wordToHtmlConverter.setPicturesManager( new PicturesManager() {
            public String savePicture(byte[] content,
                                      PictureType pictureType, String suggestedName,
                                      float widthInches, float heightInches )
            {
                return imgPath+suggestedName;
            }
        } );
        wordToHtmlConverter.processDocument(wordDocument);
        //save pictures
        List pics=wordDocument.getPicturesTable().getAllPictures();
        Picture pic = null;
        if(pics!=null){
            for(int i=0;i<pics.size();i++){
                pic = (Picture)pics.get(i);
                System.out.println();
                try {
                    pic.writeImageContent(new FileOutputStream( imgPath+File.separator + pic.suggestFullFileName()));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        Document htmlDocument = wordToHtmlConverter.getDocument();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DOMSource domSource = new DOMSource(htmlDocument);
        StreamResult streamResult = new StreamResult(out);

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer serializer = tf.newTransformer();
        serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        serializer.setOutputProperty(OutputKeys.METHOD, "html");
        serializer.transform(domSource, streamResult);
        out.close();
        writeFile(new String(out.toByteArray()), outPutFile);
        return pic.suggestFullFileName();
    }

    public  static  String getFileData(String path) {
        return  Html2String.readfile(path);
    }
}
