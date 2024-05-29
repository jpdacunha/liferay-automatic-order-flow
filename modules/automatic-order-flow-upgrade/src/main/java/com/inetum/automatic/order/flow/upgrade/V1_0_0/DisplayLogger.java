package com.inetum.automatic.order.flow.upgrade.V1_0_0;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;


public class DisplayLogger extends UpgradeProcess{
	private static final Log LOGGER = LogFactoryUtil.getLog(DisplayLogger.class);
	
	@Override
	protected void doUpgrade() throws Exception {
		LOGGER.info("Starting automatic KHOULOUD");
	}
}
