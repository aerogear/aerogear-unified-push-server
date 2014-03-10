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
package org.jboss.aerogear.unifiedpush.model.jpa;

import org.jboss.aerogear.unifiedpush.api.AndroidVariant;
import org.jboss.aerogear.unifiedpush.api.ChromePackagedAppVariant;
import org.jboss.aerogear.unifiedpush.api.PushApplication;
import org.jboss.aerogear.unifiedpush.api.SimplePushVariant;
import org.jboss.aerogear.unifiedpush.api.iOSVariant;
import org.jboss.aerogear.unifiedpush.jpa.PersistentObject;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class PushApplicationEntity extends PersistentObject {
    private static final long serialVersionUID = 6507691362454032282L;

    @Column
    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    @Column
    @Size(min = 1, max = 255)
    private String description;

    @Column
    private String pushApplicationID = UUID.randomUUID().toString();
    @Column
    private String masterSecret = UUID.randomUUID().toString();

    @Column
    @Size(min = 1, max = 255)
    private String developer;

    // TODO: let's do LAZY
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn
    private Set<iOSVariantEntity> iOSVariants = new HashSet<iOSVariantEntity>();

    // TODO: let's do LAZY
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn
    private Set<AndroidVariantEntity> androidVariants = new HashSet<AndroidVariantEntity>();

    // TODO: let's do LAZY
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn
    private Set<SimplePushVariantEntity> simplePushVariants = new HashSet<SimplePushVariantEntity>();

    // TODO: let's do LAZY
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn
    private Set<ChromePackagedAppVariantEntity> chromePackagedAppVariants = new HashSet<ChromePackagedAppVariantEntity>();

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Set<iOSVariantEntity> getIOSVariants() {
        return this.iOSVariants;
    }

    public void setIOSVariants(final Set<iOSVariantEntity> iOSVariants) {
        this.iOSVariants = iOSVariants;
    }

    public Set<AndroidVariantEntity> getAndroidVariants() {
        return this.androidVariants;
    }

    public void setAndroidVariants(final Set<AndroidVariantEntity> androidVariants) {
        this.androidVariants = androidVariants;
    }

    public Set<SimplePushVariantEntity> getSimplePushVariants() {
        return simplePushVariants;
    }

    public void setSimplePushVariants(final Set<SimplePushVariantEntity> simplePushVariants) {
        this.simplePushVariants = simplePushVariants;
    }

    public Set<ChromePackagedAppVariantEntity> getChromePackagedAppVariants() {
        return chromePackagedAppVariants;
    }

    public void setChromePackagedAppVariants(final Set<ChromePackagedAppVariantEntity> chromePackagedAppVariants) {
        this.chromePackagedAppVariants = chromePackagedAppVariants;
    }

    public String getPushApplicationID() {
        return pushApplicationID;
    }

    public void setPushApplicationID(String pushApplicationID) {
        this.pushApplicationID = pushApplicationID;
    }

    public void setMasterSecret(String secret) {
        this.masterSecret = secret;
    }

    public String getMasterSecret() {
        return masterSecret;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }
}
