package com.edsys.framework.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.edsys.framework.log.DiagnosticLogger;

public class XMLUtil
{
  private static DiagnosticLogger logger = DiagnosticLogger.getLogger(XMLUtil.class);
  private static final String TAG_NAME_PARAMETER = "Parameter";
  private static final String TAG_NAME_JOB = "Job";
  
  public static final Map<String, String> readConfig(String url)
    throws Exception
  {
	
    Map<String, String> nameValueMap = new HashMap<String, String>();
    
    Document document = createDocument(url);
    
    NodeList nodeList = document.getElementsByTagName("Parameter");
    if ((nodeList != null) && (nodeList.getLength() > 0)) {
      for (int index = 0; index < nodeList.getLength(); index++)
      {
        Node node = nodeList.item(index);
        NamedNodeMap namedNodeMap = node.getAttributes();
        String name = namedNodeMap.getNamedItem("name").getNodeValue();
        String value = namedNodeMap.getNamedItem("value").getNodeValue();
        
        nameValueMap.put(name, value);
      }
    }
    return nameValueMap;
  }
  
  private static final Document createDocument(String filePath)
    throws SAXException, IOException
  {
    DOMParser parser = new DOMParser();
    parser.parse(filePath);
    return parser.getDocument();
  }
}
