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
package org.jboss.aerogear.unifiedpush.users;

import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.PartitionManager;
import org.picketlink.idm.RelationshipManager;
import org.picketlink.idm.credential.Password;
import org.picketlink.idm.model.basic.Role;
import org.picketlink.idm.model.basic.BasicModel;
import org.picketlink.idm.model.basic.User;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.util.Calendar;
import java.util.Date;

@Singleton
@Startup
/**
 * Preload a default user into the database
 */
public class PicketLinkDefaultUsers {

    @Inject
    private PartitionManager partitionManager;

    private IdentityManager identityManager;
    private RelationshipManager relationshipManager;

    /**
     * <p>Loads some users during the <b>first</b> construction.</p>
     */
    @PostConstruct
    public void create() {

        this.identityManager = partitionManager.createIdentityManager();
        this.relationshipManager = partitionManager.createRelationshipManager();

        final String DEFAULT_PASSWORD = "123";
        final String DEFAULT_USER = "admin";

        User adminUser = BasicModel.getUser(identityManager, DEFAULT_USER);
        
        // We only create the Admin, if there is none;
        // if present, there is also no need to apply the same 'Developer' role again.
        if (adminUser == null) {
            adminUser = new User(DEFAULT_USER);
            identityManager.add(adminUser);

            Calendar calendar = expirationDate();
            Password password = new Password(DEFAULT_PASSWORD.toCharArray());

            identityManager.updateCredential(adminUser, password, new Date(), calendar.getTime());

            Role roleDeveloper = new Role(UserRoles.DEVELOPER);

            identityManager.add(roleDeveloper);

            grantRoles(adminUser, roleDeveloper);
        }

    }

    private void grantRoles(User user, Role role) {
        BasicModel.grantRole(relationshipManager, user, role);
    }

    //Expiration date of the password
    private Calendar expirationDate() {
        int EXPIRATION_TIME = -5;
        Calendar expirationDate = Calendar.getInstance();
        expirationDate.add(Calendar.MINUTE, EXPIRATION_TIME);
        return expirationDate;
    }
}
