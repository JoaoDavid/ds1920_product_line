package fileReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class FileCreator {
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException{
		List<String> as = new ArrayList<>(Aspects.aspects);
		
		System.out.println("Inserir nome do ficheiro a ler: ");
		Scanner in = new Scanner(System.in);
		String xmlFileName = in.nextLine(); 
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = null;
        try{
        	document = builder.parse(new File(xmlFileName+".xml"));
        }catch(FileNotFoundException e){
        	System.out.println("Ficheiro nao existe.");
        	return;
        }
        
        NodeList featureList = document.getElementsByTagName("feature");
        for(int i = 0; i < featureList.getLength(); i++){
        	Node f = featureList.item(i);
    		Element feature = (Element) f;
        	if(feature.getAttribute("automatic").equals("selected")){
        		removeName(feature,as);
        	}
        	
        	if(feature.getAttribute("manual").equals("selected")){
        		removeName(feature,as);
        	}
        }
        System.out.println("Inserir nome de ficheiro a criar:");
        String fileName = in.nextLine();
        File file = new File(fileName + ".ajproperties");
        FileWriter writer = new FileWriter(file);
        writer.write("src.includes = src/\n");
        writer.write("src.excludes = ");
        for (String string : as) {
			writer.write("src/rules/"+string+ ",\\\n");
		}
        writer.close();
        in.close();

	}

	private static void removeName(Element feature, List<String> aspects) {
		String fName = feature.getAttribute("name");
		if(fName.equals("Português")){
			aspects.remove("pt_PT.aj");
		}else if(fName.equals("Inglês")){
			aspects.remove("en_US.aj");
		}else if(fName.equals("VozSintetizada")){
			aspects.remove("SynthetizedVoiceAspect.aj");
		}else if(fName.equals("SinaisDeLuzes")){
			aspects.remove("NotificationLEDAspect.aj");
		}else if(fName.equals("Movimento")){
			aspects.remove("MovementDetectedEventAspect.aj");
			aspects.remove("MovementAlertAspect.aj");
			aspects.remove("ReadExtraInputAspect.aj");
			aspects.remove("ChangeContactAspect.aj");
			aspects.remove("AlertSelectorAspect.aj");
		}else if(fName.equals("Inatividade")){
			aspects.remove("InactivityAlertAspect.aj");
			aspects.remove("ActivityUpdateEventAspect.aj");
			aspects.remove("ReadExtraInputAspect.aj");
			aspects.remove("ChangeContactAspect.aj");
			aspects.remove("AlertSelectorAspect.aj");
		}else if(fName.equals("BotaoPressionado")){
			aspects.remove("ButtonPressEventAspect.aj");
			aspects.remove("ChangeContactAspect.aj");
		}else if(fName.equals("MonitorDeAtividade")){
			aspects.remove("ActivityTrackerAspect.aj");
		}else if(fName.equals("Botao")){
			aspects.remove("ButtonAspect.aj");
		}else if(fName.equals("IndicadorLuminoso")){
			aspects.remove("NotificationLEDAspect.aj");
			aspects.remove("LightSignalEventAspect.aj");
		}else if(fName.equals("LampadasInteligentes")){
			aspects.remove("LightSignalEventAspect.aj");
		}else if(fName.equals("DetetorMovimento")){
			aspects.remove("MovementDetectedEventAspect.aj");
		}
	}
}
