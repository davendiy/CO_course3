#!/usr/bin/env python3
# -*- coding: utf-8 -*-


def algo1(N):

    if N == 1:
        yield [1]
        return

    for c in range(N):
        for el in algo1(N-1):
            el.append(N)
            x = N - c if (N & 1 == 0 and c > 2) else N - 1
            el[c], el[x] = el[x], el[c]
            yield el


def algo2(N):
    if N == 1:
        yield [1]
        return

    c = [1] * (N+1)
    p = list(range(N+1))
    yield p[1:]
    i = 1
    while i <= N:
        if c[i] < i:
            if i & 1:
                k = 1
            else:
                k = c[i]
            p[i], p[k] = p[k], p[i]
            c[i] = c[i] + 1
            i = 2
            yield p[1:]
        else:
            c[i] = 1
            i += 1


def algo3(N):
    c = [1] * (N+1)
    d = [True] * (N+1)
    c[1] = 0
    p = list(range(N+1))
    yield p[1:]

    while True:
        i = N
        x = 0
        while c[i] == i:
            if not d[i]:
                x += 1
            d[i] = not d[i]
            c[i] = 1
            i -= 1

        if i <= 1:
            break

        if d[i]:
            k = c[i] + x
        else:
            k = i - c[i] + x
        p[k], p[k+1] = p[k+1], p[k]
        yield p[1:]
        c[i] += 1
