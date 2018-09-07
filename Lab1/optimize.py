import numpy as np
from numpy import power as pow
import matplotlib.pyplot as plt
from random import uniform as uni


def brute(fun, a, b, e):
    x = np.arange(a, b, e)
    return np.min(fun(x)), x.size


def bitwise_search(fun, a, b, e, n):
    d = (b - a) / n
    x0 = a
    i = 0
    while True:
        f0 = fun(x0)
        x1 = x0 + d
        f1 = fun(x1)
        if f0 > f1:
            x0 += d
            if (x0 > b):
                print("There is no extrema\n")
                return
        else:
            a = x1 - 2 * d
            b = x1
            d /= n
            x0 = a
        i += 1
        if np.abs(f0 - f1) < e:
            break
    x_opt = (a + b) / 2
    return x_opt, fun(x_opt), e, i


def bisection(fun, a, b, e):
    c = (a + b) / 2
    while (b - a) / 2 > e:
        if fun(c) == 0:
            return c
        elif fun(a) * fun(c) < 0:
            b = c
        else:
            a = c
        c = (a + b) / 2

    return c


def nt(a, b, fun, fun_d, e):
    x0 = (a + b) / 2
    x1 = x0 - (fun(x0) / fun_d(x0))
    while True:
        if np.fabs(x1 - x0) < e:
            return x1
        x0 = x1
        x1 = x0 - (fun(x0) / fun_d(x0))


def gss(f, a, b, tol=1e-5):
    gr = (np.sqrt(5) + 1) / 2
    c = b - (b - a) / gr
    d = a + (b - a) / gr
    while abs(c - d) > tol:
        if f(c) < f(d):
            b = d
        else:
            a = c
        c = b - (b - a) / gr
        d = a + (b - a) / gr
    return (b + a) / 2


def parabolic_select_helper(fun, a, b):
    while True:
        x = sorted([uni(a, b) for _ in range(0, 3)])
        if fun(x[0]) >= fun(x[1]) <= fun(x[2]):
            return x


def parabolic_interp(fun, a, b, e):
    x0, x1, x2 = parabolic_select_helper(fun, a, b)
    i = 0
    f0 = fun(x0)
    f1 = fun(x1)
    f2 = fun(x2)

    while True:
        a1 = (f1 - f0) / (x1 - x0)
        a2 = (1 / (x2 - x1)) * (((f2 - f0) / (x2 - x0)) - ((f1 - f0) / (x1 - x0)))
        if i == 0:
            X = 0.5 * (x0 + x1 - a1 / a2)
        else:
            X2 = 0.5 * (x0 + x1 - a1 / a2)
            if np.abs(X2 - X) < e:
                x_min = X2
                i += 1
                return x_min, i
            else:
                X = X2
        f_min = fun(X)
        i += 1
        if X < x2:
            x2 = x1
            f2 = f1
            x1 = X
            f1 = f_min
        else:
            x0 = x1
            f0 = f1
            x1 = X
            f1 = f_min



def plot(x, y, label_x="", label_y="", title=""):
    plt.plot(x, y)
    plt.grid(True)
    plt.xlabel(label_x)
    plt.ylabel(label_y)
    plt.title(title)
    plt.show()


def myfunc(x):
    return pow(x, 4) + pow(x, 2) + x + 1


def myfunc_prime(x):
    return 4 * pow(x, 3) + 2 * x + 1


print(parabolic_interp(myfunc, -1, 0, 0.0001))
