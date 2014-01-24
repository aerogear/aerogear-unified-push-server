/**
 * JBoss, Home of Professional Open Source
 * Copyright Red Hat, Inc., and individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.aerogear.unifiedpush.rest.util;

import org.jboss.aerogear.security.auth.LoggedUser;
import org.jboss.aerogear.security.authz.Secure;
import org.jboss.aerogear.unifiedpush.service.UserService;
import org.jboss.aerogear.unifiedpush.users.Developer;

import javax.ejb.Stateless;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * A class to test the Authorization status of a user
 */
@Stateless
@Path("/ping")
@Secure( { "developer", "admin", "viewer" })
public class Ping {


    @Inject
    private UserService userService;

    /**
     * an endpoint for testing the authorization status of a user
     * @return 200 if user is logged in, 401( not authorized )
     */
    @GET
    public Response ping() {
        if(userService.getLoginName() != null){
            return Response.ok(userService.findUserByLoginName(userService.getLoginName())).build();
        }
        return Response.noContent().build();
    }

}
