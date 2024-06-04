package com.inetum.automatic.order.flow.listner;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectEntryLocalServiceUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import java.io.Serializable;
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
				}
				
			} catch (PortalException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		super.onAfterUpdate(originalModel, model);
	}
}
