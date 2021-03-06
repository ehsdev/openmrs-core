/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.api.db.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.*;

public class PersonSearchCriteria {
	
	Criterion prepareCriterionForAttribute(String value) {
		return Restrictions.conjunction().add(Restrictions.eq("attributeType.searchable", true)).add(
		    Restrictions.eq("attribute.voided", false))
		        .add(Restrictions.ilike("attribute.value", value, MatchMode.ANYWHERE));
	}
	
	Criterion prepareCriterionForName(String value) {
		return Restrictions.conjunction().add(Restrictions.eq("name.voided", false)).add(
		    Restrictions.disjunction().add(Restrictions.ilike("name.givenName", value, MatchMode.START)).add(
		        Restrictions.ilike("name.middleName", value, MatchMode.START)).add(
		        Restrictions.ilike("name.familyName", value, MatchMode.START)).add(
		        Restrictions.ilike("name.familyName2", value, MatchMode.START)));
	}
	
	void addAliasForName(Criteria criteria) {
		criteria.createAlias("names", "name");
	}
	
	void addAliasForAttribute(Criteria criteria) {
		criteria.createAlias("attributes", "attribute", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("attribute.attributeType", "attributeType", CriteriaSpecification.LEFT_JOIN);
	}
}
