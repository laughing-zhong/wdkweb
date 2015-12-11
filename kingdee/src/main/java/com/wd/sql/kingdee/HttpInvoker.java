package com.wd.sql.kingdee;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class HttpInvoker {

	public static final String POST_URL_Login = "http://120.27.54.208/RRCCSI/json/syncreply/Auth ";
	public static final String POST_URL_Invoke = "http://120.27.54.208/RRCCSI/json/syncreply/SAL_SaleOrder_Save";
    //public static final String POST_URL_Invoke = "http://120.27.54.208/RRCCSI/json/syncreply/SAL_SaleOrder_Submit";
//	/public static final String POST_URL_Invoke = "http://120.27.54.208/RRCCSI/json/syncreply/SAL_SaleOrder_Audit";

	public static void readContentFromPost(String jsonContent)
			throws IOException {

		URL postUrl = new URL(POST_URL_Login);
		HttpURLConnection connection = (HttpURLConnection) postUrl
				.openConnection();
		if (!connection.getDoOutput()) {
			connection.setDoOutput(true);
		}
		connection.setRequestMethod("POST");
		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(true);
		connection.setRequestProperty("Content-Type", "application/json");
		DataOutputStream out = new DataOutputStream(
				connection.getOutputStream());
		// 登陆验证字串
		String content = "{\"provider\":\"credentials\",\"UserName\":\"RRCC\",\"Password\":\"654123\",\"RememberMe\":false\"PasswordIsEncrypted\":false}";
		out.writeBytes(content);
		out.flush();
		out.close();

		// 获取Cookie
		String key = null;
		String cookieVal = null;
		for (int i = 1; (key = connection.getHeaderFieldKey(i)) != null; i++) {
			if (key.equalsIgnoreCase("Set-Cookie")) {
				cookieVal = connection.getHeaderField(i);
				break;
			}
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));
		String line;
		System.out.println(" ============================= ");
		System.out.println(" Contents of post request ");
		System.out.println(" ============================= ");
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
		}
		System.out.println(" ============================= ");
		System.out.println(" Contents of post request ends ");
		System.out.println(" ============================= ");
		reader.close();

		connection.disconnect();

		URL postUrlInvoke = new URL(POST_URL_Invoke);
		HttpURLConnection connectionInvoke = (HttpURLConnection) postUrlInvoke
				.openConnection();
		connectionInvoke.setRequestProperty("Cookie", cookieVal);
		if (!connectionInvoke.getDoOutput()) {
			connectionInvoke.setDoOutput(true);
		}
		connectionInvoke.setRequestMethod("POST");
		connectionInvoke.setUseCaches(false);
		connectionInvoke.setInstanceFollowRedirects(true);
		connectionInvoke.setRequestProperty("Content-Type", "application/json");
		out = new DataOutputStream(connectionInvoke.getOutputStream());
		// 用户新增字串 启用此字串会向Cloud SEC_User用户 增加一条登陆账号为java的记录
		// content =
		// "{\"Creator\":\"Java\",\"NeedUpDateFields\":[\"\"],\"Model\":{\"FUserID\":0,\"FUserAccount\":\"Java\",\"FName\":\"Java\"}}";
		// 用户查询字串 Id 16394 为Cloud Administrator管理员主键Id
		// content = "{\"CreateOrgId\":0,\"Number\":\"\",\"Id\":\"16394\"}";
		out.writeBytes(jsonContent);
		out.flush();
		out.close();

		reader = new BufferedReader(new InputStreamReader(
				connectionInvoke.getInputStream()));
		System.out.println(" ============================= ");
		System.out.println(" Contents of post request ");
		System.out.println(" ============================= ");
		while ((line = reader.readLine()) != null) {
			System.out.println(decodeUnicode(line));
		}
		System.out.println(" ============================= ");
		System.out.println(" Contents of post request ends ");
		System.out.println(" ============================= ");
		reader.close();

		connectionInvoke.disconnect();
	}
	
	public static String convert(String utfString){
		StringBuilder sb = new StringBuilder();
		int i = -1;
		int pos = 0;
		
		while((i=utfString.indexOf("\\u", pos)) != -1){
			sb.append(utfString.substring(pos, i));
			if(i+5 < utfString.length()){
				pos = i+6;
				sb.append((char)Integer.parseInt(utfString.substring(i+2, i+6), 16));
			}
		}
		
		return sb.toString();
	}
	
	 public static String decodeUnicode(String theString) {    
		  
	     char aChar;    
	  
	      int len = theString.length();    
	  
	     StringBuffer outBuffer = new StringBuffer(len);    
	  
	     for (int x = 0; x < len;) {    
	  
	      aChar = theString.charAt(x++);    
	  
	      if (aChar == '\\') {    
	  
	       aChar = theString.charAt(x++);    
	  
	       if (aChar == 'u') {    
	  
	        // Read the xxxx    
	  
	        int value = 0;    
	  
	        for (int i = 0; i < 4; i++) {    
	  
	         aChar = theString.charAt(x++);    
	  
	         switch (aChar) {    
	  
	         case '0':    
	  
	         case '1':    
	  
	         case '2':    
	  
	         case '3':    
	  
	        case '4':    
	  
	         case '5':    
	  
	          case '6':    
	           case '7':    
	           case '8':    
	           case '9':    
	            value = (value << 4) + aChar - '0';    
	            break;    
	           case 'a':    
	           case 'b':    
	           case 'c':    
	           case 'd':    
	           case 'e':    
	           case 'f':    
	            value = (value << 4) + 10 + aChar - 'a';    
	           break;    
	           case 'A':    
	           case 'B':    
	           case 'C':    
	           case 'D':    
	           case 'E':    
	           case 'F':    
	            value = (value << 4) + 10 + aChar - 'A';    
	            break;    
	           default:    
	            throw new IllegalArgumentException(    
	              "Malformed   \\uxxxx   encoding.");    
	           }    
	  
	         }    
	          outBuffer.append((char) value);    
	         } else {    
	          if (aChar == 't')    
	           aChar = '\t';    
	          else if (aChar == 'r')    
	           aChar = '\r';    
	  
	          else if (aChar == 'n')    
	  
	           aChar = '\n';    
	  
	          else if (aChar == 'f')    
	  
	           aChar = '\f';    
	  
	          outBuffer.append(aChar);    
	  
	         }    
	  
	        } else   
	  
	        outBuffer.append(aChar);    
	  
	       }    
	  
	       return outBuffer.toString();    
	  
	      }   


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			String jsonContent = readFile("error24");
			String filterContent = StringFilter(jsonContent);
			System.out.println("content= "+filterContent);
			readContentFromPost(filterContent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    public   static   String StringFilter(String   str)   throws   PatternSyntaxException   {     
        // 只允许字母和数字       
         String   regEx  =  "[^a-zA-Z0-9,\"\"'':{}\\[\\]_]";                     
           // 清除掉所有特殊字符  
 // String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";  
  Pattern   p   =   Pattern.compile(regEx);     
  Matcher   m   =   p.matcher(str);     
  return   m.replaceAll("").trim();     
  }     
       

	public static String readFile(String fileName){
		//data for save
	//	File file = new File("/Users/zhongwq/PycharmProjects/wkjsonhttp/json_test");
		
		URL url  = Thread.currentThread().getContextClassLoader().getResource(fileName);
		//data for submit and audit
		//File file = new File("/Users/zhongwq/PycharmProjects/wkjsonhttp/submit");
		try {
			//byte
			InputStreamReader reader = new InputStreamReader(url.openStream(),"utf-8");
			BufferedReader  bufferedReader = new BufferedReader(reader); 
			String totalContent="";
			String lineContent;
			while((lineContent= bufferedReader.readLine())!= null){
				totalContent+=lineContent;
			}
			return totalContent;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;		
	}
}