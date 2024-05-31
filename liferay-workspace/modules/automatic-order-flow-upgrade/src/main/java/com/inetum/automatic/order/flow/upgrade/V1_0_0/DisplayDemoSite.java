package com.inetum.automatic.order.flow.upgrade.V1_0_0;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.site.initializer.SiteInitializer;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;


public class DisplayDemoSite extends UpgradeProcess{
	
	private static final String SITE_INITIALIZER_KEY = "site.initializer.key";
	private static final Log LOGGER = LogFactoryUtil.getLog(DisplayDemoSite.class);
	
	@Override
	protected void doUpgrade() throws Exception {
		
		LOGGER.info("Starting DisplayDemoSite");
		
		/*Bundle bundle = FrameworkUtil.getBundle(SiteInitializer.class);
		BundleContext bundleContext = bundle.getBundleContext();
		
		try (ServiceTrackerMap<String, SiteInitializer> serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap( bundleContext, SiteInitializer.class, SITE_INITIALIZER_KEY);) {
			
		}*/
		
		/*
		
		String demoSiteName = "demo-automatic-order-flow";
		boolean isSiteAlreadyExist = GroupServiceUtil.getGroup(GroupConstants.DEFAULT_LIVE_GROUP_ID).getFriendlyURL().equals("/"+demoSiteName);
		
		if(isSiteAlreadyExist) {
			System.out.println("site already created..");
			LOGGER.info("site already created..");
			
		}else {
			try {
				ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
				Locale locale=PortalUtil.getLocale(serviceContext.getRequest());
				
				
				Map<Locale, String> siteNameMap = new HashMap<Locale, String>();
				siteNameMap.put(locale, demoSiteName);
				
				//Création du groupe (type site)
				Group grp = GroupServiceUtil.addGroup(GroupConstants.DEFAULT_PARENT_GROUP_ID, GroupConstants.DEFAULT_LIVE_GROUP_ID,  siteNameMap, siteNameMap, GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION,true, GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, "/"+demoSiteName, true, true, serviceContext);
				System.out.println("site created..");
				LOGGER.info("site created..");

				//Appliquer le site template minium via site initializer
				String siteInitializerKey ="minium-initializer";
				
				Bundle bundle = FrameworkUtil.getBundle(SiteInitializer.class);
				BundleContext bundleContext = bundle.getBundleContext();
				ServiceTrackerMap<String, SiteInitializer> serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
						bundleContext, SiteInitializer.class, "site.initializer.key");
				SiteInitializer siteInitializer = serviceTrackerMap.getService(siteInitializerKey);
				siteInitializer.initialize(grp.getGroupId());
				
				System.out.println("Template site Minium applicated with succes!!");
				LOGGER.info("Template site Minium applicated with succes!!");

				serviceTrackerMap.close();
			} catch(Exception e) {
				System.out.println( e.getClass().getName() + " - " + e.getMessage());
				LOGGER.error(e.getMessage, e);
				}	
		}

*/
	}
}
