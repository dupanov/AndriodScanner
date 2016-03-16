
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileReader;


/**
 * Created by admin on 16.03.2016.
 */
public class Runner {
    Thread connection;

    public Runner(String fileUrl) {
        //open resources with Try-With-Resources statement
        try {
            File file = new File(fileUrl);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();


            Document conf = db.parse(file);
            conf.getDocumentElement().normalize();
            String AdminHost = conf.getElementsByTagName("AdminHost").item(0).getChildNodes().item(0).getNodeValue();
            String tm1serverName = conf.getElementsByTagName("tm1server").item(0).getChildNodes().item(0).getNodeValue();
            NodeList userLogins = conf.getElementsByTagName("user_login");
            String[] users = new String[userLogins.getLength()];
            String[] passwords = new String[userLogins.getLength()];
            for (int i = 0; i < userLogins.getLength(); i++)
            {
                Node curr = userLogins.item(i);
                if (curr.getNodeType() ==  Node.ELEMENT_NODE)
                {
                    Element fstElmnt = (Element) curr;
                    users[i] = fstElmnt.getElementsByTagName("login").item(0).getChildNodes().item(0).getNodeValue();
                    if (fstElmnt.getElementsByTagName("password").item(0).getChildNodes().item(0) == null)
                    {passwords[i] = "";}
                    else
                    {passwords[i] = fstElmnt.getElementsByTagName("password").item(0).getChildNodes().item(0).getNodeValue();}
                    //System.out.println (users[i] + passwords[i]);
                }

                Tm1Test server = new Tm1Test(AdminHost, tm1serverName, users[0], passwords[0]);
            }

        } catch (Exception ex){System.out.println("IO Exeption");


        }


    }


    public static void main(String[] args) {
        Runner run = new Runner("F:\\Programming\\Tm1Test\\.idea\\data\\user_config.xml");
    }
}
