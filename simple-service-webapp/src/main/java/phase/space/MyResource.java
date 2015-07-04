package phase.space;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.PrintWriter;

import javax.naming.InitialContext;
import javax.naming.Context;
import javax.sql.DataSource;
import javax.naming.NamingException;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;

// needed for library
import java.util.*;

// JAXB library for XML 
//Import javax.xml.bind.JAXBElement;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getSuburb() throws Exception{


         //List<suburb> suburbList = new LinkedList<suburb>();

         // grab data
         Context context = new InitialContext();
                Context envCtx = (Context) context.lookup("java:comp/env");
                DataSource   ds =  (DataSource)envCtx.lookup("jdbc/govhack");
                Connection c=ds.getConnection();
                Statement st=c.createStatement();
                ResultSet rs=st.executeQuery("select suburb,postcode from suburbs;");
		String res=new String();
                while(rs.next()){
                        String name=rs.getString(1);
                        String postcode=rs.getString(2);
			Suburb newSuburb = new Suburb(name, postcode);
                        
                        res.concat("<name>"+name+"</name>\n");
                        res.concat("<postcode>"+postcode+"</postcode>\n");
                 
                }

                rs.close();
                st.close();

       return res;
    }

 
   public class Suburb {
      public String name;
      public String postcode;

      Suburb(String name, String postcode){
         this.name = name; 
         this.postcode = postcode; 
      }
   }

}
