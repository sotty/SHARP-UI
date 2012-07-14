package edu.asu.bmi.greenes

import org.apache.commons.lang.builder.HashCodeBuilder

class RuleConcept implements Serializable {

	Rule rule
	Concept concept

	boolean equals(other) {
		if (!(other instanceof RuleConcept)) {
			return false
		}

		other.rule?.id == rule?.id &&
			other.concept?.id == concept?.id
	}

	int hashCode() {
		def builder = new HashCodeBuilder()
		if (rule) builder.append(rule.id)
		if (concept) builder.append(concept.id)
		builder.toHashCode()
	}

	static RuleConcept get(long ruleId, long conceptId) {
		find 'from RuleConcept where rule.id=:ruleId and concept.id=:conceptId',
			[ruleId: ruleId, conceptId: conceptId]
	}

	static RuleConcept create(Rule rule, Concept concept, boolean flush = false) {
		new RuleConcept(rule: rule, concept: concept).save(flush: flush, insert: true)
	}

	static boolean remove(Rule rule, Concept concept, boolean flush = false) {
		RuleConcept instance = RuleConcept.findByRuleAndConcept(rule, concept)
		if (!instance) {
			return false
		}

		instance.delete(flush: flush)
		true
	}

	static void removeAll(Rule rule) {
		executeUpdate 'DELETE FROM RuleConcept WHERE rule=:rule', [rule: rule]
	}

	static void removeAll(Concept concept) {
		executeUpdate 'DELETE FROM RuleConcept WHERE concept=:concept', [concept: concept]
	}

	static mapping = {
		id composite: ['concept', 'rule']
		version false
	}
}
