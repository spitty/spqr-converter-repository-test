package ru.yandex.direct;

import java.lang.reflect.AnnotatedType;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import io.leangen.graphql.generator.mapping.ConverterRegistry;
import io.leangen.graphql.generator.mapping.InputConverter;
import io.leangen.graphql.generator.mapping.OutputConverter;

/**
 * Simple Caching extension of {@link ConverterRegistry}.
 * <p>
 * Use hardcoded caching options.
 */
public class CachingConverterRegistry extends ConverterRegistry {

    private final Cache<AnnotatedType, Optional<OutputConverter>> cache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterAccess(10, TimeUnit.SECONDS)
            .build();

    public CachingConverterRegistry(List<InputConverter> inputConverters, List<OutputConverter> outputConverters) {
        super(inputConverters, outputConverters);
    }

    @SuppressWarnings("unchecked")
    public <T, S> OutputConverter<T, S> getOutputConverter(AnnotatedType outputType) {
        Optional<OutputConverter> cachedValue = cache.getIfPresent(outputType);
        //noinspection OptionalAssignedToNull
        if (cachedValue != null) {
            return (OutputConverter<T, S>) cachedValue.orElse(null);
        }

        Optional<OutputConverter> calculatedValueOptional =
                Optional.ofNullable(super.getOutputConverter(outputType));
        cache.put(outputType, calculatedValueOptional);

        return (OutputConverter<T, S>) calculatedValueOptional.orElse(null);
    }

}
