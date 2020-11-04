# PCR: Achieving High MAP-Coverage through Pattern Constraint Reduction

[![Build Status](https://travis-ci.org/travis-ci/travis-web.svg?branch=master)](https://travis-ci.org/travis-ci/travis-web)

### Table of Contents

- [Introduction](#introduction)
- [Getting Started](#getting-started)
- [Result Analysis](#result-analysis)
- [Reference Tools](#reference-tools)

### Introduction

PCR is a new approach to generate constraints based on memory-access patterns, adopts constraint solving to generate new schedules, with the purpose of achieving higher MAP-coverage. Different from the previous studies, PCR generates new schedules to increase the coverage of a single test case. 

### Getting Started

#### Environment

* OS：developed on ***macOS Catalina*** and tested on ***Ubuntu 18.04***

* JDK: `JDK 1.8.0`

* SMT-Sovler: `z3 4.8.6`

  * Follow https://github.com/Z3Prover/z3

  * Add `z3` to your PATH

#### Directory description

* benchmarks :   test projects
* lib : dependency libraries
* results：test case execution results
* src: source code

#### Run

***Step 1: Build Project***

PCR was developed as an ***IDEA*** Java project, so to configure and run PCR, you can import it directly into your ***IDEA*** workspace as a normal Java project. 

***Step 2: Import Test Module***

Before executing the test case, you first need to import the corresponding test project into the workspace as a module, and add it as a dependency of ***PCR***. 

***Step 3: Executing Test Case***

There are two alternative ways to execute test cases. You can execute the corresponding test cases directly to facilitate debugging during the development process, or you can encapsulate the testing code through JunitCore for batch testing. 

***

***RUN METHOD 1:***

* go to `src/test/example`, uncomment the test case. Here we commented the other test cases because of conflicts between different dependencies.

* set `VM options`: `-ea -javaagent:lib/nagent.jar`.
* run as a junit test case.

****

***RUN METHOD 2:***

* go to `src/test/example`, uncomment the test case. 
* go to src/test, open `PCRTester` and set `className`.

* set `VM options`: `-ea -javaagent:lib/nagent.jar`.
* run `PCRTester` as a Java program.

***

If PCR finds a concurrency bug in a test case, it will throw corresponding exception information and corresponding thread switching information. For example, the following is the final output error message of the ***AppenderAttachableImplTester*** test case.

```java
java.lang.RuntimeException: java.lang.NullPointerException
	at test.examples.AppenderAttachableImplTester$2.run(AppenderAttachableImplTester.java:50)
	at java.lang.Thread.run(Thread.java:748)
Caused by: java.lang.NullPointerException
	at org.apache.log4j.helpers.AppenderAttachableImpl.addAppender(AppenderAttachableImpl.java:50)
	at test.examples.AppenderAttachableImplTester$2.run(AppenderAttachableImplTester.java:47)
	... 1 more
============================== CONTEXT SWITCH INFO ==============================
To NewExploration
To Thread-4
To Thread-5
To NewExploration
To Thread-4
To NewExploration
```

### Result Analysis

***PCR*** records the number of thread scheduling when the first bug is found, the running time and the memory-access mode that triggered the bug, etc. The test results can be found in the `results/pcr-results` directory.  An independent run will produce the following run-time information.

```Java
2 2 1 0 111 1 1 375 7 WR
```

The detailed explanation of the fields is shown below.

```powershell
  (1) 2    :  the number of thread scheduling before the first bug is found
  (2) 2    :  the number of generated constraints 
  (3) 1    :  the number of unsat constraints
  (4) 0    :  the number of constraints filtered by unsat constarints
  (5) 111  :  constraint solving time
  (6) 1    :  extract unsat constarints time
  (7) 1    :  the number of successfully generated pattern constraints
  (8) 375  :  running time before the first bug is found
  (9) 7    :  Map-coverage
 (10) WR   :  the type of memory-access pattern that triggers the bug
```

### Reference Tools

* [ASM](<https://asm.ow2.io/>)
* [MAPTest](<https://github.com/sail-repos/Map-Coverage>)

