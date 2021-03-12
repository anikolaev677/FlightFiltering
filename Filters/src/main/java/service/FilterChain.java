package service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FilterChain {
    private final List<Object> filters = new ArrayList<>();
    private List<?> listToFiltering;

    public FilterChain(List<?> listToFiltering) {
        this.listToFiltering = listToFiltering;
    }

    public List<?> execute(List<Integer> targets) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ListIterator<Integer> targetsIterator = targets.listIterator();
        if (targets.size() != 0) {
            while (targetsIterator.hasNext()) {
                final Integer nextTarget = targetsIterator.next();
                listToFiltering = listToFiltering
                        .stream()
                        .filter((Predicate<Object>) filters.get(nextTarget)
                                .getClass()
                                .getDeclaredMethod("getFilter")
                                .invoke(filters.get(nextTarget)))
                        .collect(Collectors.toList());
            }
        }
        return listToFiltering;
    }

    public void setFilters(Object... filter) {
        filters.addAll(Arrays.asList(filter));
    }

    public List<?> getFilters() {
        return filters;
    }

    public boolean isFiltersListEmpty() {
        return filters.isEmpty();
    }
}
