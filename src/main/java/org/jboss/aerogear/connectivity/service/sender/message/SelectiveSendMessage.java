/**
 * JBoss, Home of Professional Open Source
 * Copyright Red Hat, Inc., and individual contributors
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

package org.jboss.aerogear.connectivity.service.sender.message;

import java.util.List;
import java.util.Map;

public class SelectiveSendMessage implements UnifiedPushMessage {

    private final List<String> aliases;
    private final List<String> deviceTypes;
    private final String staging;


    private final Map<String, String> simplePush;
    private final String alert;
    private final String sound;
    private final int badge;

    private final Map<String, Object> data;

    /**
     * Selective Send messages are submitted as flexible JSON maps, like:
     * <pre>
     *   {
     *     "alias" : ["someUsername"],
     *     "deviceType" : ["someDevice"],
     *     "staging":"production",
     *     "message":
     *     {
     *       "key":"value",
     *       "key2":"other value",
     *       "alert":"HELLO!",
     *       "sound":"default",
     *       "badge":2
     *     },
     *     "simple-push":
     *     {
     *       "SomeCategory":"version=123",
     *       "anotherCategory":"version=456"
     *     }
     *   }
     * </pre>
     * This class give some convenient methods to access the query components (<code>alias</code> or <code>deviceType</code>),
     * the <code>simple-push</code> value or some <i>highlighted</i> keywords.
     */
    @SuppressWarnings("unchecked")
    public SelectiveSendMessage(Map<String, Object> data) {
        this.aliases = (List<String>) data.remove("alias");
        this.deviceTypes = (List<String>) data.remove("deviceType");
        this.staging = (String) this.data.remove("staging");
        
        // ======= Payload ====
        // the Android/iOS payload of the actual message:
        this.data = (Map<String, Object>) data.remove("message");
        // remove the desired keywords:
        // special key words (for APNs)
        this.alert = (String) this.data.remove("alert");  // used in AGDROID as well
        this.sound = (String) this.data.remove("sound");

        Integer badgeVal = (Integer) this.data.remove("badge");
        if (badgeVal == null) {
            this.badge = -1;
        } else {
            this.badge = badgeVal;
        }

        
        // SimplePush values: 
        this.simplePush = (Map<String, String>) data.remove("simple-push");

    }

    public List<String> getAliases() {
        return aliases;
    }
    
    public List<String> getDeviceTypes() {
        return deviceTypes;
    }
    
    
    public Map<String, String> getSimplePush() {
        return simplePush;
    }

    @Override
    public String getAlert() {
        return alert;
    }

    @Override
    public String getSound() {
        return sound;
    }

    @Override
    public int getBadge() {
        return badge;
    }

    @Override
    public Map<String, Object> getData() {
        return data;
    }

    @Override
    public String getStaging() {
        return staging;
    }
}
