package com.inetum.automatic.order.flow.upgrade;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.security.permission.PermissionCheckerUtil;
import com.liferay.site.initializer.SiteInitializer;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;


/**
 * @author dev
 */

@Component(
		immediate = true,
		service = CreateDefaultSite.class
)
public class CreateDefaultSiteImpl implements CreateDefaultSite {
	
	private static final Log LOGGER = LogFactoryUtil.getLog(CreateDefaultSite.class);
	
    @Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED)
    private ModuleServiceLifecycle _portalInitialized;
    
    @Reference
    private GroupLocalService groupLocalService;
    
    @Reference
	private RoleLocalService roleLocalService;
    
    @Reference
	private UserLocalService userLocalService;
    
    @Reference
	private AccountEntryLocalService accountEntryLocalService;
    
    private static final String SITE_NAME = "Automatic Order Demo";
    private static final String SITE_FRIENDLY_URL = "/automatic-order-demo";
    private static final String SITE_MINIUM_INITIALIZER_KEY = "minium-initializer";
    private static final String SITE_INITIALIZER_KEY = "site.initializer.key";
    
    @Activate
    @Modified
    protected void activate(Map<String, Object> properties) {

		LOGGER.info("Creating and initializing default site ...");
		
		long companyId = PortalUtil.getDefaultCompanyId();
		long userId =0;
		ServiceContext serviceContext =null;
		Group group = groupLocalService.fetchFriendlyURLGroup(companyId, SITE_FRIENDLY_URL);
		int accountCount=accountEntryLocalService.getAccountEntriesCount();
		
		try {
			
			if (group == null || accountCount == 0) {
					
				User user = getFirstLiferayAdmin(companyId);
				userId = user.getUserId();
				
				PermissionCheckerUtil.setThreadValues(user);
				
				serviceContext = new ServiceContext();
				serviceContext.setUserId(userId);
				serviceContext.setCompanyId(companyId);
			}
			
			if (group == null) {	
				Locale locale = LocaleUtil.getDefault();
				
				Map<Locale, String> siteNameMap = new HashMap<Locale, String>();
				siteNameMap.put(locale, SITE_NAME);
				
				Group grp = groupLocalService.addGroup(userId, GroupConstants.DEFAULT_PARENT_GROUP_ID, null, 0, GroupConstants.DEFAULT_LIVE_GROUP_ID, siteNameMap, siteNameMap, GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION,true, GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, SITE_FRIENDLY_URL, true, false, true, serviceContext);
				
				Bundle bundle = FrameworkUtil.getBundle(SiteInitializer.class);
				BundleContext bundleContext = bundle.getBundleContext();
				
				try (ServiceTrackerMap<String, SiteInitializer> serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap( bundleContext, SiteInitializer.class, SITE_INITIALIZER_KEY);) {
					
					SiteInitializer siteInitializer = serviceTrackerMap.getService(SITE_MINIUM_INITIALIZER_KEY);
					siteInitializer.initialize(grp.getGroupId());
					
				}
				
				LOGGER.info("Site successfully created.");
					
			} else {
				LOGGER.info("Skipping default site creation because it already exists.");
			}
		 
			if (accountCount == 0) {
				LOGGER.info("Creating and initializing default Account ...");
				
				//Create Flowise Account
				accountEntryLocalService.addAccountEntry(userId, 0, "Flowise Account", "", null, "", null, "", "business", 0, serviceContext);
				
				LOGGER.info("Account successfully created.");
			}
		
		LOGGER.info("Done.");
		} catch (SystemException | PortalException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
    
	private User getFirstLiferayAdmin(long companyId) throws SystemException, PortalException {
		
		Role adminRole = roleLocalService.getRole(companyId, RoleConstants.ADMINISTRATOR);
		
		if (adminRole == null) {
			throw new SystemException("Missing required role Administrator");
		}
		
		List<User> admins = userLocalService.getRoleUsers(adminRole.getRoleId());
		
		if (admins != null && admins.size() > 0) {
			
			return admins.get(0);
			
		} else {
			throw new SystemException("Default user with Administrator role required");
		}
		
	}
	
}