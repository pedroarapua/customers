package br.com.magazineluiza.v1.customers.repository.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import br.com.magazineluiza.v1.customers.util.repository.SearchOperation;
import br.com.magazineluiza.v1.customers.util.repository.SpecSearchCriteria;

public final class GenericSpecificationsBuilder<T> {

    private final List<SpecSearchCriteria> params;

    public GenericSpecificationsBuilder() {
        params = new ArrayList<>();
    }

    // API

    public final GenericSpecificationsBuilder<T> with(final String key, final String operation, final Object value, final String prefix, final String suffix) {
        return with(null, key, operation, value, prefix, suffix);
    }

    public final GenericSpecificationsBuilder<T> with(final String predicate, final String key, final String operation, final Object value, final String prefix, final String suffix) {
        SearchOperation op = SearchOperation.getSimpleOperation(operation.charAt(0));
        if (op != null) {
            if (op == SearchOperation.EQUALITY) { // the operation may be complex operation
                final boolean startWithAsterisk = prefix != null && prefix.contains(SearchOperation.ZERO_OR_MORE_REGEX);
                final boolean endWithAsterisk = suffix != null && suffix.contains(SearchOperation.ZERO_OR_MORE_REGEX);

                if (startWithAsterisk && endWithAsterisk) {
                    op = SearchOperation.CONTAINS;
                } else if (startWithAsterisk) {
                    op = SearchOperation.ENDS_WITH;
                } else if (endWithAsterisk) {
                    op = SearchOperation.STARTS_WITH;
                }
            }
            boolean orPredicate = predicate != null && predicate.equals(SearchOperation.OR_PREDICATE_FLAG);
            params.add(new SpecSearchCriteria(key, op, value, orPredicate));
        }
        return this;
    }

    public Specification<T> build() {
        if (params.isEmpty())
            return null;

        Specification<T> result = new GenericSpecification<T>(params.get(0));
     
        for (int i = 1; i < params.size(); i++) {
            result = params.get(i).isOrPredicate()
              ? Specification.where(result).or(new GenericSpecification<>(params.get(i))) 
              : Specification.where(result).and(new GenericSpecification<>(params.get(i)));
        }
        
        return result;
    }

    public final GenericSpecificationsBuilder<T> with(GenericSpecification<T> spec) {
        params.add(spec.getCriteria());
        return this;
    }

    public final GenericSpecificationsBuilder<T> with(SpecSearchCriteria criteria) {
        params.add(criteria);
        return this;
    }
}