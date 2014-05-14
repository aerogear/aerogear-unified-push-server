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
package org.jboss.aerogear.unifiedpush.service;

import org.jboss.aerogear.unifiedpush.api.ProxyServer;

public interface ProxyServerService {
	
	/**
     * Store a new ProxyServer object on the database.
     */
	void addProxy(ProxyServer proxy);
	
	/**
     * Performs an update/merge on the given entity.
     */
	void updateProxy(ProxyServer proxy);
	
	/**
     * Returns the ProxyServer entity, matching the given ID.
     */
	ProxyServer findByProxyID(String proxyID);
	
	/**
     * Returns the Proxy entity (single-row table) or null if empty
     */
	ProxyServer findProxy();
	
	/**
     * Removes the given Proxy entity.
     */
	void removeProxy(ProxyServer proxy);
}
