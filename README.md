### About
This benchmark should be used with io.leangen:spqr:0.9.9-SNAPSHOT
build from https://github.com/spitty/graphql-spqr/tree/introducing-converter-registry-factory

That patch allows users set custom `ConverterRegistry`.
For example using `CachingConverterRegistry` from this repository
can significantly decrease execution time for simple GraphQL query.

Benchmark results on my personal laptop:
```
Benchmark                                  (cacheEnabled)  (objCount)  Mode  Cnt    Score    Error  Units
SpqrConverterRegistryBenchmark.testMethod            true         100  avgt    5    0.495 ±  0.042  ms/op
SpqrConverterRegistryBenchmark.testMethod            true        1000  avgt    5    4.413 ±  0.376  ms/op
SpqrConverterRegistryBenchmark.testMethod            true       10000  avgt    5   44.583 ±  6.199  ms/op
SpqrConverterRegistryBenchmark.testMethod           false         100  avgt    5    4.241 ±  0.333  ms/op
SpqrConverterRegistryBenchmark.testMethod           false        1000  avgt    5   44.428 ±  4.937  ms/op
SpqrConverterRegistryBenchmark.testMethod           false       10000  avgt    5  400.516 ± 13.463  ms/op
```

### Run benchmark
1. Build `0.9.9-SNAPSHOT` from https://github.com/spitty/graphql-spqr/tree/introducing-converter-registry-factory
2. Build benchmark `mvn clean install`
3. Run it `java -jar target/benchmarks.jar`
