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

import org.jboss.aerogear.unifiedpush.api.AbstractVariant;
import org.jboss.aerogear.unifiedpush.api.Variant;
import org.jboss.aerogear.unifiedpush.dao.VariantDao;
import org.jboss.aerogear.unifiedpush.jpa.dao.impl.helper.JPATransformHelper;
import org.jboss.aerogear.unifiedpush.model.jpa.AbstractVariantEntity;

import javax.persistence.Query;
import java.util.List;

public class JPAVariantDao extends JPABaseDao implements VariantDao {


    @Override
    public void create(Variant variant) {
        AbstractVariantEntity entity = JPATransformHelper.toEntity(variant);

        persist(entity);
    }

    @Override
    public void update(Variant variant) {
        AbstractVariantEntity entity = JPATransformHelper.toEntity(variant);

        merge(entity);
    }

    @Override
    public void delete(Variant variant) {
        AbstractVariantEntity entity = entityManager.find(AbstractVariantEntity.class, variant.getId());
        remove(entity);
    }


    @Override
    public Variant findByVariantID(String variantID) {

        AbstractVariantEntity entity = getSingleResultForQuery(createQuery("select t from " + AbstractVariantEntity.class.getSimpleName() + " t where t.variantID = :variantID")
                .setParameter("variantID", variantID));

        return JPATransformHelper.fromEntity(entity);
    }

    @Override
    public Variant findByVariantIDForDeveloper(String variantID, String loginName) {

        AbstractVariantEntity entity = getSingleResultForQuery(createQuery("select t from " + AbstractVariantEntity.class.getSimpleName() + " t where t.variantID = :variantID and t.developer = :developer")
                .setParameter("variantID", variantID)
                .setParameter("developer", loginName));


        return JPATransformHelper.fromEntity(entity);
    }

    @Override
    public Variant find(String id) {
        AbstractVariantEntity entity = entityManager.find(AbstractVariantEntity.class, id);
        return JPATransformHelper.fromEntity(entity);
    }

    private AbstractVariantEntity getSingleResultForQuery(Query query) {
        List<AbstractVariantEntity> result = query.getResultList();

        if (!result.isEmpty()) {
            return result.get(0);
        } else {
            return null;
        }
    }


}
