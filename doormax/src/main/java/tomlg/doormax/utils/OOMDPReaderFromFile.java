package tomlg.doormax.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


import tomlg.doormax.oomdpformalism.Action;
import tomlg.doormax.oomdpformalism.OOMDP;
import tomlg.doormax.oomdpformalism.OOMDPState;
import tomlg.doormax.oomdpformalism.ObjectInstance;
import tomlg.doormax.oomdpformalism.ObjectAttribute;
import tomlg.doormax.oomdpformalism.ObjectClass;
import tomlg.doormax.oomdpformalism.domains.BooleanDomain;
import tomlg.doormax.oomdpformalism.domains.IntegerDomain;
import tomlg.doormax.oomdpformalism.domains.StringDomain;

public class OOMDPReaderFromFile {
    private BufferedReader in;
    private String arquivo;
    private String[] auxStringArray;
//    private String[] class_attributes;
    private String[] propositions;
    private String gamma_string;
	private float gamma;
	List<Action> actions_list = new ArrayList<Action>();
	List<ObjectClass> createdClasses = new ArrayList<ObjectClass>();

    Pattern actions_pattern = Pattern.compile("((?!\\s*Actions\\s*\\{)\\s*(\\w*[\\,,\\s])+(?=\\}\\s+))"); //
    Pattern classes_pattern = Pattern.compile("(Classes\\s*\\{(\\s*(\\w+\\s*\\(\\s*(\\s*\\w+\\s*:\\s*\\w+[\\,,\\s])+\\s*\\))\\s)*\\s*\\})"); //
    Pattern class_pattern = Pattern.compile("(((\\w)+\\s*\\(\\s*)(\\s*\\w+\\s*:\\s*\\w+[\\,,\\s])+\\s*(?!\\)))");
    Pattern propositions_pattern = Pattern.compile("(Propositions\\s*\\{\\s*(\\w*[\\,,\\s])*\\})"); //
    Pattern gamma_pattern = Pattern.compile("(gamma\\s*:\\s*\\d.\\d*)"); //
	
	
	public OOMDPReaderFromFile() {
		arquivo = "";
	}   
	
    public OOMDP leitura() throws FileNotFoundException, IOException {

        String line;

//        Pattern class_name_pattern= Pattern.compile("((\\w)+(?!\\)))");
        in = new BufferedReader(new FileReader("/home/drones/git/TOMLG/doormax/src/Untitled1"));
        line = in.readLine();
        
        while (line != null) {
        	arquivo=arquivo.concat(line);
           line = in.readLine();
        }
        in.close();
        
        //TODO Remover Actions{}
        Matcher actions_matcher = actions_pattern.matcher(arquivo);
        if (actions_matcher.find()) {
        	auxStringArray = actions_matcher.group().split(",");
        	
        	for (String strTemp : auxStringArray){
            	actions_list.add(new Action(strTemp.trim()));
       		}     	
            System.out.println(actions_matcher.group());
        }
        
        //TODO separar Classes
        //TODO Remover ClassName{}
        Matcher classes_matcher = classes_pattern.matcher(arquivo);
        if (classes_matcher.find()) {
        	Matcher class_matcher = class_pattern.matcher(classes_matcher.group());
        	while (class_matcher.find()) {
        		
        		String className=getclassName(class_matcher.group());
        		auxStringArray = class_matcher.group().split(",");
        		auxStringArray[0] = auxStringArray[0].split("\\(")[1];
        		List<ObjectAttribute> attributes = new ArrayList<ObjectAttribute>();
        		for (String strTemp : auxStringArray){
        			attributes.add(makeAttribute(strTemp));
           		}   
        		createdClasses.add(new ObjectClass(attributes, className));
        	}
        	
        	
            System.out.println(classes_matcher.group());
        }
        
        //TODO Remover propositions{}        
        Matcher propositions_matcher = propositions_pattern.matcher(arquivo);
        if (propositions_matcher.find()) {
        	propositions = propositions_matcher.group().split(",");
 //       	List<Action> proposition_list = new ArrayList<Proposition>(4);
        	for (String strTemp : propositions){
        	// propositions_list.add(new Action("strTemp"));
        	
    //    	propositions_matcher.group().split(",");
            System.out.println(strTemp);
        	}
        }
       
        Matcher gamma_matcher = gamma_pattern.matcher(arquivo);
       	if (gamma_matcher.find()) {
        	gamma_string=gamma_matcher.group();
        	gamma= Float.parseFloat(gamma_string.split(":")[1]);
        	System.out.println(gamma);
        }
       	
       	OOMDP oomdp = new OOMDP(actions_list, createdClasses);	
       	System.out.println(oomdp);
       	return oomdp;
    }
    
    
    
    public String getclassName (String str){
    	Pattern class_name_pattern= Pattern.compile("((\\w)+(?!\\)))");
    	
    	Matcher class_name_matcher = class_name_pattern.matcher(str);
    	if (class_name_matcher.find()) {
    		return(class_name_matcher.group());
    	}       	
    	return "erro";
    	
    	
    }
    
    
    public ObjectAttribute makeAttribute(String str) {
    	
    	ObjectAttribute attribute = null;
    	String[] temp = str.split(":");
    	String name=temp[0].trim();
    	String type=temp[1].trim();
		if(type.matches("IntegerDomain")) {
			attribute = new ObjectAttribute(name, new IntegerDomain());
		}
		if(type.matches("StringDomain")) {//
			attribute = new ObjectAttribute(name, new StringDomain());
		}
		if(type.matches("BooleanDomain")) {
			attribute = new ObjectAttribute(name, new BooleanDomain());
		}
		return attribute;
		
	}
    
    
    
    public OOMDPState stateReader(OOMDP oomdp) throws FileNotFoundException, IOException {
        String line;
        OOMDPState oomdpState = new OOMDPState();  
        in = new BufferedReader(new FileReader("/home/drones/git/TOMLG/doormax/src/State0"));
        										
        line = in.readLine();
        arquivo = "";
        
        while (line != null) {
        	arquivo=arquivo.concat(line);
            line = in.readLine();
        }
        in.close();
            
        	List<ObjectClass> objectClasses = oomdp.getObjectClasses();
        	
        	Matcher class_matcher = class_pattern.matcher(arquivo);
        	while (class_matcher.find()) {
        		String className=getclassName(class_matcher.group());
        		auxStringArray = class_matcher.group().split(",");
        		auxStringArray[0] = auxStringArray[0].split("\\(")[1];
        		
        		for (ObjectClass obj : objectClasses){
        			if (obj.name.equals(className)) {
        				ObjectInstance newObjInst = new ObjectInstance(obj);
        				List<ObjectAttribute> attributes = obj.attributes;
        				Iterator<ObjectAttribute> e = attributes.iterator();
        				
                		for (String strTemp : auxStringArray){	
                			String[] temp = strTemp.split(":");
                	    	String type=temp[1].trim();
                	    	ObjectAttribute tmp =e.next();
                			newObjInst.attributesVal.put(tmp, type);
                			System.out.println(tmp);
                			System.out.println(newObjInst.toString());
                			
                   		}
                		oomdpState.objects.put(newObjInst.objectClass.name, newObjInst);
        			}
        		}
        		
        	}
        	return oomdpState;
        	
     }
    
}    

    