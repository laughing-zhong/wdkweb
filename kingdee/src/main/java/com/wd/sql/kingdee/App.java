package com.wd.sql.kingdee;

import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;

/**
 * Hello world!
 *
 */
public class App 
 {
	public static void main(String[] args) throws Exception {
		//InvokeHelper.POST_K3CloudURL = "http://192.168.19.113/K3Cloud/";
		InvokeHelper.POST_K3CloudURL ="http://139.129.15.198/K3Cloud/";
		//InvokeHelper.POST_K3CloudURL ="http://120.27.54.208/rrcc/";
		//InvokeHelper.POST_K3CloudURL ="http://camellia243.oicp.net:33788/k3cloud/";
		
		//String dbId = "55bb19192ebcde"
	    String dbId = 	 "566057718cf9b1";
		//String dbId = "5625fbdb3d1ef6";
		//String dbId ="5634288082907b";
		String uid = "admin";
		String pwd = "123456";
		int lang = 2052;

		if (InvokeHelper.Login(dbId, uid, pwd, lang)) {

			// 销售订单保存测试
			// 业务对象Id
			String sFormId = "SAL_SaleOrder";
			// 需要保存的数据
			// 如下字段可能需要根据自己实际值做修改
			// FCustId FSalerId FMaterialId FUnitID
			//String sContent = "{\"Creator\":\"String\",\"NeedUpDateFields\":[\"FBillTypeID\",\"FDate\",\"FBusinessType\",\"FSaleOrgId\",\"FCustId\",\"FSettleCurrId\",\"FSalerId\",\"SAL_SaleOrder__FSaleOrderEntry\",\"FMaterialId\",\"FSettleOrgIds\",\"FUnitID\",\"FQty\",\"SAL_SaleOrder__FSaleOrderFinance\",\"FSettleCurrId\",\"FLocalCurrId\",\"FIsIncludedTax\",\"FBillTaxAmount\",\"FBillAmount\",\"FBillAllAmount\",\"FExchangeTypeId\",\"FExchangeRate\"],\"Model\":{\"FID\":0,\"FBillTypeID\":{\"FNumber\":\"XSDD01_SYS\"},\"FBusinessType\":\"NORMAL\",\"FSaleOrgId\":{\"FNUMBER\":\"100\"},\"FCustId\":{\"FNUMBER\":\"CUST0001\"},\"FSettleCurrId\":{\"FNUMBER\":\"PRE001\"},\"FSalerId\":{\"FNUMBER\":\"0002\"},\"SAL_SaleOrder__FSaleOrderFinance\":{\"FExchangeRate\":6.8123},\"SAL_SaleOrder__FSaleOrderEntry\":[{\"FMaterialId\":{\"FNUMBER\":\"001\"},\"FSettleOrgIds\":{\"FNUMBER\":\"100\"},\"FUnitID\":{\"FNumber\":\"个\"},\"FQty\":10}]}}";
			System.out.println("==================save  begin");
			
			//read save data ---------
			String saveContent = App.readFile("json_test_web");
			InvokeHelper.Save(sFormId, saveContent);
			
			
			//-----------------------submit----- but faild---------
	/*		String submitContent = App.readFile("submit_web");
			InvokeHelper.Submit(sFormId, submitContent);
			InvokeHelper.Audit(sFormId, submitContent);
			*/

			System.out.println("hola success");
		}
		
	}
	
	public static String readFile(String fileName){
		URL url  = Thread.currentThread().getContextClassLoader().getResource(fileName);
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
