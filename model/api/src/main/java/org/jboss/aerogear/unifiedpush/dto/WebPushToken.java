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
package org.jboss.aerogear.unifiedpush.dto;

import org.jboss.aerogear.unifiedpush.api.VariantType;

/**
 * Holds additional information for {@link VariantType#WEB_PUSH} for push message encryption.
 */
public final class WebPushToken extends Token {

    private static final long serialVersionUID = 58015315605339296L;

    private final String publicKey;
    private final String authSercret;

    public WebPushToken(String endpoint, String publicKey, String authSercret) {
        super(endpoint);
        this.publicKey = publicKey;
        this.authSercret = authSercret;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getAuthSercret() {
        return authSercret;
    }
}
