package com.inetum.automatic.order.flow.upgrade;
import com.inetum.automatic.order.flow.upgrade.V1_0_0.DisplayDemoSite;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author dev
 */

@Component(service = UpgradeStepRegistrator.class)
/**
 * 
 * @author jpdacunha
 * 
 * NICE TIPS : :-)
 * 
 * see https://git-dxp.gfi.fr/root/BNPPhenix/tree/master/sources/phenix-liferay/phenix-liferay-workspace/modules/phenix-common/phenix-common-upgrade/src/main/java/com/bnpparibas/phenix/common/upgrade
 * 
 * To restart an already executed chain of upgrade processes
 *  1 - use following SQL :
 *    select * from release_ where servletcontextname like '%phenix%';
 *    delete from release_ where releaseid='<ID>';
 *    
 *  2 - Restart Liferay
 *  
 * To check phenix upgrade process registration
 *  1 - Connect to gogo shell
 *  2 - Execute upgrade:list | grep phenix
 *
 */

public class UpgradeProcess implements UpgradeStepRegistrator {
	

	@Override
	public void register(Registry registry) {
		
		registry.register("0.0.0", "1.0.0", new DisplayDemoSite());
	}
	
	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(ModuleServiceLifecycle moduleServiceLifecycle) {
		
		
	}
	
}