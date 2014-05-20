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
package org.jboss.aerogear.unifiedpush.jpa;

import org.jboss.aerogear.unifiedpush.api.AndroidVariant;
import org.jboss.aerogear.unifiedpush.api.Installation;
import org.jboss.aerogear.unifiedpush.api.iOSVariant;
import org.jboss.aerogear.unifiedpush.jpa.dao.impl.JPAInstallationDao;
import org.jboss.aerogear.unifiedpush.jpa.dao.impl.JPAVariantDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.assertj.core.api.Assertions.assertThat;

public class VariantDaoTest {


    private EntityManager entityManager;
    private JPAVariantDao variantDao;
    private JPAInstallationDao installationDao;


    @Before
    public void setUp() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("UnifiedPush");
        entityManager = emf.createEntityManager();

        // start the shindig
        entityManager.getTransaction().begin();

        variantDao = new JPAVariantDao();
        variantDao.setEntityManager(entityManager);
        installationDao = new JPAInstallationDao();
        installationDao.setEntityManager(entityManager);
    }

    @After
    public void tearDown() {
        entityManager.getTransaction().commit();

        entityManager.close();
    }

    @Test
    public void findVariantByIdForDeveloper() {

        final AndroidVariant av = new AndroidVariant();
        av.setGoogleKey("KEY");
        av.setDeveloper("admin");
        final String uuid  = av.getVariantID();

        variantDao.create(av);

        assertThat(variantDao.findByVariantIDForDeveloper(uuid, "admin")).isNotNull();
        assertThat(variantDao.findByVariantIDForDeveloper(null, "admin")).isNull();
        assertThat(variantDao.findByVariantIDForDeveloper(uuid, "mr x")).isNull();
    }

    @Test
    public void findVariantIDsForDeveloper() {

        final AndroidVariant av = new AndroidVariant();
        av.setGoogleKey("KEY");
        av.setDeveloper("admin");
        final String uuid  = av.getVariantID();

        variantDao.create(av);

        assertThat(variantDao.findVariantIDsForDeveloper("admin")).isNotNull();
        assertThat(variantDao.findVariantIDsForDeveloper("admin")).containsOnly(uuid);
    }

    @Test
    public void findVariantById() {

        final AndroidVariant av = new AndroidVariant();
        av.setGoogleKey("KEY");
        av.setDeveloper("admin");
        final String uuid  = av.getVariantID();

        variantDao.create(av);



        assertThat(variantDao.findByVariantID(uuid)).isNotNull();
        assertThat(variantDao.findByVariantID(null)).isNull();
    }

    @Test
    public void updateVariant() {

        final AndroidVariant av = new AndroidVariant();
        av.setGoogleKey("KEY");
        av.setDeveloper("admin");
        final String uuid  = av.getVariantID();

        variantDao.create(av);

        AndroidVariant queriedVariant = (AndroidVariant) variantDao.findByVariantID(uuid);
        final String primaryKey = queriedVariant.getId();
        assertThat(queriedVariant).isNotNull();
        assertThat(queriedVariant.getGoogleKey()).isEqualTo("KEY");

        queriedVariant.setGoogleKey("NEW_KEY");
        variantDao.update(queriedVariant);

        queriedVariant = (AndroidVariant) variantDao.findByVariantID(uuid);
        assertThat(queriedVariant).isNotNull();
        assertThat(queriedVariant.getGoogleKey()).isEqualTo("NEW_KEY");
        assertThat(queriedVariant.getId()).isEqualTo(primaryKey);
    }

    @Test
    public void updateAndDeleteVariant() {

        final AndroidVariant av = new AndroidVariant();
        av.setGoogleKey("KEY");
        av.setDeveloper("admin");
        final String uuid  = av.getVariantID();

        variantDao.create(av);

        AndroidVariant queriedVariant = (AndroidVariant) variantDao.findByVariantID(uuid);
        final String primaryKey = queriedVariant.getId();
        assertThat(queriedVariant).isNotNull();
        assertThat(queriedVariant.getGoogleKey()).isEqualTo("KEY");

        queriedVariant.setGoogleKey("NEW_KEY");
        variantDao.update(queriedVariant);

        queriedVariant = (AndroidVariant) variantDao.findByVariantID(uuid);
        assertThat(queriedVariant).isNotNull();
        assertThat(queriedVariant.getGoogleKey()).isEqualTo("NEW_KEY");
        assertThat(queriedVariant.getId()).isEqualTo(primaryKey);

        variantDao.delete(queriedVariant);
        assertThat(variantDao.findByVariantID(uuid)).isNull();
    }

    @Test
    public void lookupNonExistingVariant() {
        AndroidVariant variant = (AndroidVariant) variantDao.findByVariantIDForDeveloper("NOT-IN-DATABASE", "admin");
        assertThat(variant).isNull();
    }

    @Test
    public void variantIDUnmodifiedAfterUpdate() {

        final AndroidVariant av = new AndroidVariant();
        av.setGoogleKey("KEY");
        av.setDeveloper("admin");
        final String uuid  = av.getVariantID();

        variantDao.create(av);

        AndroidVariant queriedVariant = (AndroidVariant) variantDao.findByVariantID(uuid);
        final String primaryKey = queriedVariant.getId();
        assertThat(queriedVariant.getVariantID()).isEqualTo(uuid);
        assertThat(queriedVariant).isNotNull();

        queriedVariant.setGoogleKey("NEW_KEY");
        variantDao.update(queriedVariant);

        queriedVariant = (AndroidVariant) variantDao.findByVariantID(uuid);
        assertThat(queriedVariant).isNotNull();
        assertThat(queriedVariant.getVariantID()).isEqualTo(uuid);
        assertThat(queriedVariant.getId()).isEqualTo(primaryKey);
    }

    @Test
    public void primaryKeyUnmodifiedAfterUpdate() {
        AndroidVariant av = new AndroidVariant();
        av.setGoogleKey("KEY");
        av.setDeveloper("admin");
        final String id  = av.getId();

        variantDao.create(av);

        // flush to be sure that it's in the database
        entityManager.flush();
        // clear the cache otherwise finding the entity will not perform a select but get the entity from cache
        entityManager.clear();

        AndroidVariant variant = (AndroidVariant) variantDao.find(id);

        assertThat(variant.getId()).isEqualTo(id);

        av.setGoogleKey("NEW_KEY");
        variantDao.update(av);

        entityManager.flush();
        entityManager.clear();

        variant = (AndroidVariant) variantDao.find(id);

        assertThat(variant.getGoogleKey()).isEqualTo("NEW_KEY");

        assertThat(av.getId()).isEqualTo(id);
    }

    @Test
    public void deleteVariantIncludingInstallations() {

        final AndroidVariant av = new AndroidVariant();
        av.setGoogleKey("KEY");
        av.setDeveloper("admin");
        final String uuid  = av.getVariantID();

        variantDao.create(av);

        AndroidVariant queriedVariant = (AndroidVariant) variantDao.findByVariantID(uuid);
        assertThat(queriedVariant).isNotNull();
        assertThat(queriedVariant.getGoogleKey()).isEqualTo("KEY");

        Installation androidInstallation1 = new Installation();
        androidInstallation1.setDeviceToken("12345432122323");
        installationDao.create(androidInstallation1);

        queriedVariant.getInstallations().add(androidInstallation1);
        variantDao.update(queriedVariant);

        Installation storedInstallation =  installationDao.find(androidInstallation1.getId());
        assertThat(storedInstallation.getId()).isEqualTo(androidInstallation1.getId());

        variantDao.delete(queriedVariant);
        assertThat(variantDao.findByVariantID(uuid)).isNull();

        // Installation should be gone...
        assertThat(installationDao.find(androidInstallation1.getId())).isNull();
    }


    @Test
    public void createDifferentVariantTypes() {
        AndroidVariant av = new AndroidVariant();
        av.setGoogleKey("KEY");
        av.setDeveloper("admin");
        final String androidId  = av.getVariantID();

        variantDao.create(av);

        // flush to be sure that it's in the database
        entityManager.flush();
        // clear the cache otherwise finding the entity will not perform a select but get the entity from cache
        entityManager.clear();


        iOSVariant iOS = new iOSVariant();
        iOS.setCertificate("test".getBytes());
        iOS.setPassphrase("secret");
        final String iOSid = iOS.getVariantID();

        variantDao.create(iOS);
        // flush to be sure that it's in the database
        entityManager.flush();
        // clear the cache otherwise finding the entity will not perform a select but get the entity from cache
        entityManager.clear();

    }
}
