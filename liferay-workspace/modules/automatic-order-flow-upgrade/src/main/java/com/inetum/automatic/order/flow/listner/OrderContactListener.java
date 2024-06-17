package com.inetum.automatic.order.flow.listner;

import com.liferay.account.service.AccountEntryLocalServiceUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.commerce.product.service.CommerceChannelLocalServiceUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectEntryLocalServiceUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.osgi.service.component.annotations.Component;


/**
 * @author dev
 */

@Component(
	    immediate = true,
	    service = ModelListener.class
	)
public class OrderContactListener extends BaseModelListener<AssetEntry>{
	private static final Log LOGGER = LogFactoryUtil.getLog(OrderContactListener.class);
    private static final String FLOWISE_ENDPOIT="http://laof-flowise:3000/api/v1/prediction/65d366ad-9d26-479d-8674-bdcb9d99a474";
   
	@Override
	public void onAfterCreate(AssetEntry model) throws ModelListenerException {
	
		//If ObjectDefinition
		if(model.getClassName().startsWith(ObjectDefinition.class.getName())) {
			
			LOGGER.info("Updating  Object ["+model.getClassName()+"] ...");
			
			ObjectEntry objectEntry;
			
			try {
				objectEntry = ObjectEntryLocalServiceUtil.getObjectEntry(model.getClassPK());
				ObjectDefinition objectDefinition=ObjectDefinitionLocalServiceUtil.getObjectDefinition(objectEntry.getObjectDefinitionId());
				
				LOGGER.info("objectDefinition.getName() = ["+objectDefinition.getName()+"] ");
				
				//If OrderContact Object
				if(objectDefinition.getName().equals("C_OrderContact")) {					
					Map<String, Serializable> valuesMap=objectEntry.getValues();
					LOGGER.info("Channel : "+CommerceChannelLocalServiceUtil.getCommerceChannels(-1, -1).get(0).getCommerceChannelId());
					
					//Calling flowise
					StringBuilder description = new StringBuilder(valuesMap.get("description").toString());
					long contactOrderId=Long.parseLong(valuesMap.get("c_orderContactId").toString());
					long accountId = AccountEntryLocalServiceUtil.getAccountEntries(-1, -1).get(0).getAccountEntryId();
					long channelId = CommerceChannelLocalServiceUtil.getCommerceChannels(-1, -1).get(0).getCommerceChannelId();
					description.append("\n\n Envois la commande a liferay.");
					
					//Calling Flowise in new Thread
					Thread newThread = new Thread() {
					    public void run() {
					        	callFlowise(description.toString(),contactOrderId,accountId,channelId);					      
					    }  
					};
					newThread.start();
					
				}
				
			} catch (PortalException e) {
				e.printStackTrace();
			}
		}
		super.onAfterCreate(model);
	}
    
	private void callFlowise(String requestQuestion,long contactOrderId,long accountId,long channelId) {
		try {			
            // Creating a URL object
            URL url = new URL(FLOWISE_ENDPOIT);
            
            // Opening a connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            // Setting the request method to POST
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            
            //Send parameter in
            JSONObject jsonRequestParam=JSONFactoryUtil.createJSONObject();
            JSONObject overrideConfig=JSONFactoryUtil.createJSONObject();
            JSONObject vars=JSONFactoryUtil.createJSONObject();
            
            vars.put("accountId", accountId);
            vars.put("channelId", channelId);
            vars.put("contactOrderId", contactOrderId);
            
            overrideConfig.put("vars", vars);
            
            jsonRequestParam.put("question", requestQuestion);
            jsonRequestParam.put("overrideConfig", overrideConfig);
            
            LOGGER.info("jsonRequestParam : "+jsonRequestParam.toJSONString());
            
            connection.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.writeBytes(jsonRequestParam.toJSONString());
            out.flush();
            out.close();
            
            // Retrieving the response code
            int responseCode = connection.getResponseCode();
            
            // Processing the response
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                LOGGER.info("API Response: " + response.toString());
            		
            } else {
            	LOGGER.warn("API Call Failed. Response Code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
