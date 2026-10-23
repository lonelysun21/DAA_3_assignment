# DAA Assignment 3 — Graph Algorithms

**Student:** Sanzhar Zhakanov  
**Group:** SE-2526

**Repository:** https://github.com/lonelysun21/DAA_3_assignment

## Description

This is a Java Maven project implementing graph algorithms:

- Disjoint Set Union with path compression and union by size
- Kruskal and Prim minimum spanning tree algorithms
- Kosaraju strongly connected components
- Kahn topological sorting
- Shortest paths in directed acyclic graphs

The project includes JUnit 5 tests, graph generators, operation counters, benchmarks and performance plots.

## Requirements

- Java 17 or newer
- Maven
- Python 3 with matplotlib for generating plots

## Build

```bash
mvn clean package
```

## Run Tests

```bash
mvn test
```

## Run Benchmark

```bash
java -cp target/classes benchmark.Benchmark
```

The benchmark generates `results/results.csv` and checks the reference values.

## Validate Results

```bash
java -cp target/classes benchmark.ResultsValidator
```

## Generate Plots

```bash
python3 results/plots/plot_results.py
```

## Generate Analysis

```bash
python3 results/analyze.py
python3 results/report_stats.py
```

## Project Structure

| Directory | Description |
|---|---|
| `src/main/java/graph` | Graph representation and generators |
| `src/main/java/algorithms` | Graph algorithms |
| `src/main/java/metrics` | Operation counters |
| `src/main/java/benchmark` | Benchmark and CSV validation |
| `src/test/java` | JUnit tests |
| `data` | Sample input graphs |
| `results` | Benchmark data and analysis |
| `results/plots` | Performance charts |
| `REPORT.md` | Complexity and correctness report |

## Git

The main branch contains the completed project. The release version is tagged `v1.0`.

## AI Use Acknowledgement

AI assistance was used for drafting parts of the implementation, tests and documentation. The project includes tests and reproducible benchmark checks for evaluating correctness.