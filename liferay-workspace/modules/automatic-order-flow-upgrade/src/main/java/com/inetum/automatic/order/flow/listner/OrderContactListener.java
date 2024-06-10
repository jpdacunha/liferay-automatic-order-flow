package com.inetum.automatic.order.flow.listner;

import com.liferay.asset.kernel.model.AssetEntry;
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
    private static final String FLOWISE_ENDPOIT="http://laof-flowise:3000/api/v1/prediction/2db83ed9-9ad0-4868-a21e-d4454d0f8cae";
    @Override
	public void onAfterUpdate(AssetEntry originalModel, AssetEntry model) throws ModelListenerException {

		LOGGER.info("Updating  Object ["+model.getClassName()+"] ...");
		
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
					LOGGER.info("Desciption ["+valuesMap.get("description")+"] ...");
					
					//Calling flowise
					callFlowise(valuesMap.get("description").toString());
				}
				
			} catch (PortalException e) {
				e.printStackTrace();
			}
		}
		super.onAfterUpdate(originalModel, model);
	}
    
	private void callFlowise(String requestQuestion) {
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
            jsonRequestParam.put("question", requestQuestion);
            
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
