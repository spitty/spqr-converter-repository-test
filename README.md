### About
This benchmark should be used with io.leangen:spqr:0.9.9-SNAPSHOT
build from https://github.com/spitty/graphql-spqr/tree/introducing-converter-registry-factory

That patch allows users set custom `ConverterRegistry`.
For example using `CachingConverterRegistry` from this repository
can significantly decrease execution time for simple GraphQL query.

Benchmark results:
```
Benchmark                                         (mode)  (objCount)  Mode  Cnt    Score   Error  Units
SpqrConverterRegistryBenchmark.testMethod       baseline         100  avgt    5    2.947 ± 0.195  ms/op
SpqrConverterRegistryBenchmark.testMethod       baseline        1000  avgt    5   27.017 ± 2.518  ms/op
SpqrConverterRegistryBenchmark.testMethod       baseline       10000  avgt    5  275.344 ± 5.253  ms/op
SpqrConverterRegistryBenchmark.testMethod          cache         100  avgt    5    0.374 ± 0.031  ms/op
SpqrConverterRegistryBenchmark.testMethod          cache        1000  avgt    5    3.265 ± 0.535  ms/op
SpqrConverterRegistryBenchmark.testMethod          cache       10000  avgt    5   36.878 ± 8.855  ms/op
SpqrConverterRegistryBenchmark.testMethod  no_converters         100  avgt    5    0.297 ± 0.032  ms/op
SpqrConverterRegistryBenchmark.testMethod  no_converters        1000  avgt    5    2.993 ± 0.369  ms/op
SpqrConverterRegistryBenchmark.testMethod  no_converters       10000  avgt    5   27.468 ± 1.800  ms/op
```

### Run benchmark
1. Build `0.9.9-SNAPSHOT` from https://github.com/spitty/graphql-spqr/tree/introducing-converter-registry-factory
2. Build benchmark `mvn clean install`
3. Run it `java -jar target/benchmarks.jar`
