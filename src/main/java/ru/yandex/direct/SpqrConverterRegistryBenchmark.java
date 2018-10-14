package ru.yandex.direct;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import io.leangen.graphql.GraphQLSchemaGenerator;
import io.leangen.graphql.metadata.strategy.query.AnnotatedResolverBuilder;
import io.leangen.graphql.metadata.strategy.value.jackson.JacksonValueMapperFactory;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import ru.yandex.direct.graphql.Person;
import ru.yandex.direct.graphql.PersonQuery;

@Warmup(iterations = 5, time = 5)
@Measurement(iterations = 5, time = 5)
@BenchmarkMode(Mode.AverageTime)
@Fork(1)
@Threads(1)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class SpqrConverterRegistryBenchmark {

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(SpqrConverterRegistryBenchmark.class.getSimpleName())
                .build();
        new Runner(opt).run();
    }

    @State(Scope.Thread)
    public static class BenchmarkParam {

        @Param({"true", "false"})
        boolean cacheEnabled;

        @Param({"100", "1000", "10000"})
        long objCount;

        private GraphQL graphQL;

        @Setup
        public void setup() {
            List<Person> people = LongStream.range(0L, objCount)
                    .mapToObj(id -> new Person(id, "person #" + id))
                    .collect(Collectors.toList());
            PersonQuery personQuery = new PersonQuery(people);

            GraphQLSchemaGenerator graphQLSchemaGenerator = new GraphQLSchemaGenerator()
                    .withResolverBuilders(new AnnotatedResolverBuilder())
                    .withOperationsFromSingleton(personQuery)
                    .withValueMapperFactory(new JacksonValueMapperFactory());
            if (cacheEnabled) {
                graphQLSchemaGenerator
                        .withConverterRegistryFactory(CachingConverterRegistry::new);
            }
            GraphQLSchema schema = graphQLSchemaGenerator
                    .generate();
            graphQL = GraphQL.newGraphQL(schema).build();
        }
    }

    @Benchmark
    public ExecutionResult testMethod(BenchmarkParam benchmark) {
        return benchmark.graphQL.execute("{persons{id name}}");
    }

}
