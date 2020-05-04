cmake_minimum_required(VERSION 3.16)
project(combinatorical_optimizational C)

set(CMAKE_C_STANDARD 99)

add_executable(combinatorical_optimizational main.c src/Digraph.c src/Bag.c headers/MinHeap.h src/MinHeap.c src/DijkstraSP.c headers/IndexMinPriorityQueue.h src/IndexMinPriorityQueue.c)
add_executable(res res.c)
