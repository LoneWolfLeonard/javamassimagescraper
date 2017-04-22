package mass.image.scraper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import javax.swing.JOptionPane;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
 
public class MassImageScraper {
   public static String currentUsersHomeDir = System.getProperty("user.home")+ File.separator + "Downloads";;
   

         public static String IMAGE_DESTINATION_FOLDER = (currentUsersHomeDir + "//downloads");
         public static String URL = JOptionPane.showInputDialog("Please input a URL");
      
  public static void main(String[] args) throws IOException {
                        int statusCode;
                         System.out.println(currentUsersHomeDir);
      try
{
   Document document = Jsoup.connect(URL).get();
    Elements images = document.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
    for (Element image : images) 
    {
                    Connection.Response response = Jsoup.connect(URL)
                        .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                        .timeout(10000)
                        .execute();
                    statusCode = response.statusCode();
                    System.out.println(statusCode);
if(statusCode == 200) {
            downloadImage(image.attr("src"));  
                        System.out.println(image.attr("src"));
}
    }
} 
catch (IOException e) 
{
    e.printStackTrace();
}
  }
  private static void downloadImage(String strImageURL){
        String strImageName = 
                strImageURL.substring( strImageURL.lastIndexOf("/") + 1 );    
        try {    
            URL urlImage = new URL(URL + "/" +strImageURL);
            InputStream in = urlImage.openStream();        
            byte[] buffer = new byte[4096];
            int n = -1;
            OutputStream os =  new FileOutputStream( currentUsersHomeDir + "/" + strImageName );
            while ( (n = in.read(buffer)) != -1 ){
                os.write(buffer, 0, n);
            }
            os.close();
       } catch (NullPointerException e) {
        e.printStackTrace();
    }   catch (FileNotFoundException e) {
			e.printStackTrace(System.err);
		}
        catch (HttpStatusException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
        }     
    }
}