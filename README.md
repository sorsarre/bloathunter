# bloathunter
C++ build performance asessment tool

## Purpose
As of now the tool will, in the course of its implementation, concern itself with two questions:
1. Which headers are contributing the most code to the compilation process? (aka header bloat)
2. Which templates instantiate all around with the worst amount of redundancy in object files? (aka object bloat)

## Roadmap

### Phase 1. Include bloat
- Stage 1: Parse a single preprocessed C++ file, gather an include tree with proper and cumulative line counts for each node;
- Stage 2: Group include trees by header file, merging proper lines from all matching nodes to figure out the header's total contribution, thus forming a mapping from header files to total proper line counts;
- Stage 3: Merge line count mappings from all specified preprocessed source files. The result is a mapping from a header file to total proper line count across all preprocessed sources and also the header's prevalence across the entire file list;

### Phase 2. Object bloat
- Stage 1: Parse ```nm``` output to get a mapping from symbol name to symbol type and data size for a specified object file;
- Stage 2: Extract template instantiations from symbol mapping;
- Stage 3: Merge instantiation info and symbol properties mapping for several files in order to detect rampant templates;
- Further stages: TBD

### Phase 3. Add a simple UI to inspect the analysis results
TBD

### Phase 4. Add capability to launch CMake builds (and measure their timing and memory consumption)
TBD

### Phase 5. Add capability to make suggestions? Who knows, never thought that far.
