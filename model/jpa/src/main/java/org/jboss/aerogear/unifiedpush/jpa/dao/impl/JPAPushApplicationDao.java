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
package org.jboss.aerogear.unifiedpush.jpa.dao.impl;

import org.jboss.aerogear.unifiedpush.api.PushApplication;
import org.jboss.aerogear.unifiedpush.dao.PushApplicationDao;

import javax.persistence.Query;
import java.util.List;

public class JPAPushApplicationDao extends JPABaseDao implements PushApplicationDao {

    @Override
    public void create(PushApplication pushApplication) {
        persist(pushApplication);
    }

    @Override
    public void update(PushApplication pushApplication) {
        merge(pushApplication);
    }

    @Override
    public void delete(PushApplication pushApplication) {
        PushApplication entity = entityManager.find(PushApplication.class, pushApplication.getId());
        remove(entity);
    }

    @Override
    public List<PushApplication> findAllForDeveloper(String loginName) {

        List<PushApplication> entities = createQuery("select pa from PushApplication pa where pa.developer = :developer")
                .setParameter("developer", loginName).getResultList();

        return entities;
    }

    @Override
    public PushApplication findByPushApplicationIDForDeveloper(String pushApplicationID, String loginName) {

        PushApplication entity = getSingleResultForQuery(createQuery(
                "select pa from PushApplication pa where pa.pushApplicationID = :pushApplicationID and pa.developer = :developer")
                .setParameter("pushApplicationID", pushApplicationID)
                .setParameter("developer", loginName));

        return entity;
    }

    @Override
    public PushApplication findByPushApplicationID(String pushApplicationID) {

        PushApplication entity = getSingleResultForQuery(createQuery("select pa from PushApplication pa where pa.pushApplicationID = :pushApplicationID")
                .setParameter("pushApplicationID", pushApplicationID));

        return entity;
    }

    @Override
    public long getNumberOfPushApplicationsForDeveloper(String name) {
        return (Long) createQuery("select count(pa) from PushApplication pa where pa.developer = :developer")
                .setParameter("developer", name).getSingleResult();
    }

    @Override
    public PushApplication find(String id) {
        PushApplication entity = entityManager.find(PushApplication.class, id);
        return  entity;
    }

    private PushApplication getSingleResultForQuery(Query query) {
        List<PushApplication> result = query.getResultList();

        if (!result.isEmpty()) {
            return result.get(0);
        } else {
            return null;
        }
    }
}
