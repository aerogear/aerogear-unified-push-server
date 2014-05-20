/**
 * JBoss, Home of Professional Open Source
 * Copyright Red Hat, Inc., and individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.aerogear.unifiedpush.service.dashboard;

import org.jboss.aerogear.unifiedpush.dao.InstallationDao;
import org.jboss.aerogear.unifiedpush.dao.PushApplicationDao;
import org.jboss.aerogear.unifiedpush.dao.VariantDao;

import javax.inject.Inject;
import java.util.List;

public class DashboardService {

    @Inject
    private PushApplicationDao pushApplicationDao;
    @Inject
    private VariantDao variantDao;
    @Inject
    private InstallationDao installationDao;


    public DashboardData loadDashboardData(String principalName) {

        long totalApps = totalApplicationNumber(principalName);
        long totalDevices = totalDeviceNumber(principalName);
        long totalMessages = totalMessages(principalName);


        final DashboardData data = new DashboardData();
        data.setApplications(totalApps);
        data.setDevices(totalDevices);
        data.setMessages(totalMessages);

        return data;
    }

    private long totalMessages(String principalName) {
        return 0;   // TODO
    }

    private long totalDeviceNumber(String principalName) {

        List<String> variantIDs = variantDao.findVariantIDsForDeveloper(principalName);

        return installationDao.getNumberOfDevicesForVariantIDs(variantIDs);
    }

    private long totalApplicationNumber(String principalName) {
        return  pushApplicationDao.getNumberOfPushApplicationsForDeveloper(principalName);
    }

}
