import numpy as np
from numpy import power as pow
import matplotlib.pyplot as plt
from random import uniform as uni


def brute(fun, a, b, e):
    x = np.arange(a, b, e)
    return np.min(fun(x)), x.size


def bitwise_search(fun, a, b, e, bn, n):
    d = (b - a) / bn
    x0 = a
    i = 0
    for _ in range(0, n):
        f0 = fun(x0)
        x1 = x0 + d
        f1 = fun(x1)
        if f0 > f1:
            x0 += d
            if (x0 > b):
                raise Exception("Most likely your function is not unimodal")
        else:
            a = x1 - 2 * d
            b = x1
            d /= bn
            x0 = a
        i += 1
        if np.abs(f0 - f1) < e:
            break
    x_opt = (a + b) / 2
    return x_opt, i


def bisection(fun_prime, a, b, e):
    i = 0
    c = (a + b) / 2
    while (b - a) / 2 > e:
        if fun_prime(c) == 0:
            i += 1
            return c, i
        elif fun_prime(a) * fun_prime(c) < 0:
            i += 1
            b = c
        else:
            a = c
        c = (a + b) / 2
    return c, i


def gss(fun, a, b, e=0.001):
    i = 0
    gr = (np.sqrt(5) + 1) / 2  # equals 1.618....
    c = b - (b - a) / gr
    d = a + (b - a) / gr
    while abs(c - d) > e:
        if fun(c) < fun(d):
            i += 1
            b = d
        else:
            a = c
        c = b - (b - a) / gr
        d = a + (b - a) / gr
    return (b + a) / 2, i


def parabolic_select_helper(fun, a, b, n):
    for _ in range(0, n):
        x = sorted([uni(a, b) for _ in range(0, 3)])
        if fun(x[0]) >= fun(x[1]) <= fun(x[2]):
            return x
    raise Exception("Most likely your function is not unimodal")


def parabolic_interp(fun, a, b, e, n):
    x0, x1, x2 = parabolic_select_helper(fun, a, b, n)
    i = 0
    f0 = fun(x0)
    f1 = fun(x1)
    f2 = fun(x2)

    for _ in range(0, n):
        a1 = (f1 - f0) / (x1 - x0)
        a2 = (1 / (x2 - x1)) * (((f2 - f0) / (x2 - x0)) - ((f1 - f0) / (x1 - x0)))
        if i == 0:
            X = 0.5 * (x0 + x1 - a1 / a2)
        else:
            X2 = 0.5 * (x0 + x1 - a1 / a2)
            if np.abs(X2 - X) < e:
                i += 1
                return X2, i
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


def middle_point(fun_prime, a, b, e, n):
    i = 0
    for _ in range(0, n):
        i += 1
        x = (a + b) / 2
        f_p = fun_prime(x)
        if np.abs(f_p) < e:
            return x, i
        else:
            if f_p > 0:
                b = x
            else:
                a = x


def chords(fun_prime, a, b, e, n):
    i = 0
    for _ in range(0, n):
        i += 1
        X = a - (fun_prime(a) / (fun_prime(a) - fun_prime(b))) * (a - b)
        f_p = fun_prime(X)
        if np.abs(f_p) < e:
            return X, i
        else:
            if f_p > 0:
                b = X
            else:
                a = X


def plot(x, y, label_x="", label_y="", title=""):
    plt.plot(x, y)
    plt.grid(True)
    plt.xlabel(label_x)
    plt.ylabel(label_y)
    plt.title(title)
    plt.show()


def newton(fun_prime, fun_second, x0, e, n):
    x = x0
    i = 0
    for _ in range(0, n):
        df = fun_prime(x)
        ddf = fun_second(x)
        i += 1
        if np.abs(df) < e:
            i += 1
            return x, i
        else:
            x = x - df / ddf
        if np.abs(x) < e:
            return None


def myfunc(x):
    return pow(x, 4) + pow(x, 2) + x + 1


def myfunc_prime(x):
    return 4 * pow(x, 3) + 2 * x + 1


def myfunc_second(x):
    return 12 * pow(x, 2) + 2


def massive_test(fun, fun_prime, fun_second, a, b, n, x0, e_start, e_end, e_step):
    test = {}
    test["bitwise_search"] = [[bitwise_search(fun, a, b, e, 4, n)[1], e] for e in np.arange(e_start, e_end, -e_step)]
    test["brute"] = [[brute(fun, a, b, e, )[1], e] for e in np.arange(e_start, e_end, -e_step)]
    test["bisection"] = [[bisection(fun_prime, a, b, e)[1], e] for e in np.arange(e_start, e_end, -e_step)]
    test["gss"] = [[gss(fun, a, b, e)[1], e] for e in np.arange(e_start, e_end, -e_step)]
    test["parabolic_interp"] = [[parabolic_interp(fun, a, b, e, n)[1], e] for e in np.arange(e_start, e_end, -e_step)]
    test["middle_point"] = [[middle_point(fun_prime, a, b, e, n)[1], e] for e in np.arange(e_start, e_end, -e_step)]
    test["chords"] = [[chords(fun_prime, a, b, e, n)[1], e] for e in np.arange(e_start, e_end, -e_step)]
    test["newton"] = [[newton(fun_prime, fun_second, x0, e, n)[1], e] for e in np.arange(e_start, e_end, -e_step)]
    return test


data = massive_test(myfunc, myfunc_prime, myfunc_second, -1, 0, 100, -0.3, 0.001, 0.000001, 0.00001)
vals = data.get("bitwise_search")[:]
y = [d[0] for d in vals]
x = [d[1] for d in vals]
print(x,y)

plt.plot(x,y)
plt.show()