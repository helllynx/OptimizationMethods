import numpy as np
from numpy import power as pow
import matplotlib.pyplot as plt
from random import uniform as uni


def brute(fun, a, b, e):
    x = np.arange(a, b, e)
    return x[np.argmin(fun(x))], x.size


#
# def bitwise_search(fun, a, b, e, bn, n, need_plot=False):
#     d = (b - a) / bn
#     x0 = a
#     x0_p = x0
#     i = 0
#     f0_p = fun(x0)
#     for _ in range(0, n):
#         f0 = fun(x0)
#         x1 = x0 + d
#         f1 = fun(x1)
#         i += 2
#         if f0 > f1:
#             x0 += d
#             if x0 > b:
#                 raise Exception("Most likely your function is not unimodal")
#         else:
#             a = x1 - 2 * d
#             b = x1
#             d /= bn
#             x0 = a
#         if np.abs(f0 - f1) < e:
#             break
#         if need_plot:
#             plt.plot([x0_p, x0], [f0_p, f1])
#     x_opt = (a + b) / 2
#     return x_opt, i


def bitwise_search(fun, a, b, e, d, eta, need_plot=False):
    x = a
    f2 = fun(x)
    n = 1
    old_x = x
    old_f = f2

    while not np.abs(d * eta) < e:
        x += d
        f3 = fun(x)
        n += 1

        while f3 < f2:
            f2 = f3
            x += d
            f3 = fun(x)
            n += 1
        f2 = f3
        if need_plot:
            plt.plot([old_x, x], [old_f, f2])
            old_x = x
            old_f = f2
        d /= eta

    return x - d * eta, f2, n


def bisection(fun, fun_prime, a, b, e, need_plot=False):
    i = 0
    c = (a + b) / 2
    while (b - a) / 2 > e:
        if need_plot:
            plt.plot([a, b], [fun(a), fun(b)])
        if fun_prime(c) == 0:
            i += 1
            return c, i
        elif fun_prime(a) * fun_prime(c) < 0:
            i += 2
            b = c
        else:
            a = c
        c = (a + b) / 2
    return c, i


def bisection_numeric(fun, diff, a, b, e, need_plot=False):
    i = 0
    c = (a + b) / 2
    while (b - a) / 2 > e:
        if need_plot:
            plt.plot([a, b], [fun(a), fun(b)])
        if diff(fun, e, c) == 0:
            i += 1
            return c, i
        elif diff(fun, e, a) * diff(fun, e, c) < 0:
            i += 2
            b = c
        else:
            a = c
        c = (a + b) / 2
    return c, i


def gss(fun, a, b, e, need_plot=False):
    i = 0
    gr = (np.sqrt(5) + 1) / 2  # equals 1.618....
    c = b - (b - a) / gr
    d = a + (b - a) / gr
    while abs(c - d) > e:
        if fun(c) < fun(d):
            i += 2
            b = d
        else:
            a = c
        c = b - (b - a) / gr
        d = a + (b - a) / gr
        if need_plot:
            plt.plot([a, b], [fun(a), fun(b)])
    return (b + a) / 2, i


def parabolic_select_helper(fun, a, b, n):
    for _ in range(0, n):
        x = sorted([uni(a, b) for _ in range(0, 3)])
        if fun(x[0]) >= fun(x[1]) <= fun(x[2]):
            return x
    raise Exception("Most likely your function is not unimodal")


def parabolic_interp(fun, a, b, e, n, need_plot=False):
    x0, x1, x2 = parabolic_select_helper(fun, a, b, n)
    i = 0
    f0 = fun(x0)
    f1 = fun(x1)
    f2 = fun(x2)
    while True:
        a1 = (f1 - f0) / (x1 - x0)
        a2 = (1 / (x2 - x1)) * (((f2 - f0) / (x2 - x0)) - ((f1 - f0) / (x1 - x0)))

        if need_plot:
            x_p = np.linspace(a, b, 200)
            y_p = lambda x: f0 + a1 * (x - x0) + a2 * (x - x0) * (x - x1)
            plt.plot(x_p, y_p(x_p))

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


def middle_point(fun, fun_prime, a, b, e, n, need_plot=False):
    i = 0
    tang_len = (b - a) / 8
    for _ in range(0, n):
        i += 1
        x = (a + b) / 2
        f_p = fun_prime(x)

        if need_plot:
            x_p = np.linspace(x - tang_len, x + tang_len, 100)
            plt.plot(x_p, fun_prime(x) * (x_p - x) + fun(x))

        if np.abs(f_p) < e:
            return x, i
        else:
            if f_p > 0:
                b = x
            else:
                a = x



def middle_point_numeric(fun, diff, a, b, e, n, need_plot=False):
    i = 0
    tang_len = (b - a) / 8
    for _ in range(0, n):
        i += 1
        x = (a + b) / 2
        f_p = diff(fun, e, x)

        if need_plot:
            x_p = np.linspace(x - tang_len, x + tang_len, 100)
            plt.plot(x_p, diff(fun, e, x) * (x_p - x) + fun(x))

        if np.abs(f_p) < e:
            return x, i
        else:
            if f_p > 0:
                b = x
            else:
                a = x



def chords(fun, fun_prime, a, b, e, need_plot=False):
    i = 0
    while np.fabs(b - a) > e:
        tmp = b
        b = b - (b - a) * fun_prime(b) / (fun_prime(b) - fun_prime(a))
        a = tmp
        i += 1
        if need_plot:
            plt.plot([a, b], [fun(a), fun(b)])
    return b, i


def plot(x, y, label_x="", label_y="", title=""):
    plt.plot(x, y)
    plt.grid(True)
    plt.xlabel(label_x)
    plt.ylabel(label_y)
    plt.title(title)
    plt.show()


def newton(fun, fun_prime, fun_second, x0, e, n, need_plot=False):
    x = x0
    i = 0
    for _ in range(0, n):
        if need_plot:
            plt.scatter(x, fun(x))
        df = fun_prime(x)
        ddf = fun_second(x)
        i += 2
        if np.abs(df) < e:
            return x, i
        else:
            x = x - df / ddf


def newton_numeric(fun, diff, diff2, x0, e, n, need_plot=False):
    x = x0
    i = 0
    for _ in range(0, n):
        df = diff(fun, e, x)
        ddf = diff2(fun, e, x)
        i += 1
        if np.abs(df) < e:
            i += 1
            return x, i
        else:
            x = x - df / ddf
            i += 1


def newton_numeric_diff(fun, diff, diff2, x0, e, n, need_plot=False):
    x = x0
    i = 0
    for _ in range(0, n):
        df = diff(fun, e, x)
        ddf = diff2(fun, e, x)
        i += 2
        if np.abs(df) < e:
            return x, i
        else:
            x = x - df / ddf


def newton_rafson(fun_prime, fun_second, x0, e, n, need_plot=False):
    x = x0
    i = 0
    for _ in range(0, n):
        df = fun_prime(x)
        ddf = fun_second(x)
        if np.abs(df) < e:
            i += 1
            return x, i
        else:
            xl = x - df / ddf
            dfxl = fun_prime(xl)
            t = pow(df, 2) / (pow(df, 2) + pow(dfxl, 2))
            x = x - t * df / ddf
            i += 1


def newton_rafson_numeric(fun, diff, diff2, x0, e, n, need_plot=False):
    x = x0
    i = 0
    for _ in range(0, n):
        df = diff(fun, e, x)
        ddf = diff2(fun, e, x)
        if np.abs(df) < e:
            i += 1
            return x, i
        else:
            xl = x - df / ddf
            dfxl = diff(fun, e, xl)
            t = pow(df, 2) / (pow(df, 2) + pow(dfxl, 2))
            x = x - t * df / ddf
            i += 1


def newton_markvardt(fun, fun_prime, fun_second, x0, e, n, need_plot=False):
    x = x0
    i = 0
    f = fun(x)
    mu = fun_second(x)
    for _ in range(0, n):
        df = fun_prime(x)
        ddf = fun_second(x)
        if np.abs(df) < e:
            i += 1
            return x, i
        else:
            x = x - df / (ddf + mu)
            nf = fun(x)
            i += 1
        if nf < f:
            mu = mu / 2
        else:
            mu = mu * 2


def newton_markvardt_numeric(fun, diff, diff2, x0, e, n, need_plot=False):
    x = x0
    i = 0
    f = fun(x)
    mu = diff2(fun, e, x)
    for _ in range(0, n):
        df = diff(fun, e, x)
        ddf = diff2(fun, e, x)
        if np.abs(df) < e:
            i += 1
            return x, i
        else:
            x = x - df / (ddf + mu)
            nf = fun(x)
            i += 1
        if nf < f:
            mu = mu / 2
        else:
            mu = mu * 2


def massive_test(fun, fun_prime, fun_second, a, b, n, x0, e_start, e_end, e_step, need_plot=False):
    test = {}
    test["bitwise_search"] = [[bitwise_search(fun, a, b, e, 0.25, -4, need_plot)[1], e] for e in np.arange(e_start, e_end, -e_step)]
    test["brute"] = [[brute(fun, a, b, e, )[1], e] for e in np.arange(e_start, e_end, -e_step)]
    test["bisection"] = [[bisection(fun, fun_prime, a, b, e, need_plot)[1], e] for e in np.arange(e_start, e_end, -e_step)]
    test["gss"] = [[gss(fun, a, b, e, need_plot)[1], e] for e in np.arange(e_start, e_end, -e_step)]
    test["parabolic_interp"] = [[parabolic_interp(fun, a, b, e, n, need_plot)[1], e] for e in np.arange(e_start, e_end, -e_step)]
    test["middle_point"] = [[middle_point(fun, fun_prime, a, b, e, n, need_plot)[1], e] for e in np.arange(e_start, e_end, -e_step)]
    test["chords"] = [[chords(fun, fun_prime, a, b, e, need_plot)[1], e] for e in np.arange(e_start, e_end, -e_step)]
    test["newton"] = [[newton(fun, fun_prime, fun_second, x0, e, n, need_plot)[1], e] for e in np.arange(e_start, e_end, -e_step)]
    return test


def massive_test_diff(fun, diff, diff2, a, b, n, x0, e_start, e_end, e_step):
    test = {}
    test["bisection"] = [[bisection_numeric(fun, diff, a, b, e)[1], e] for e in np.arange(e_start, e_end, -e_step)]
    test["middle_point"] = [[middle_point_numeric(fun, diff, a, b, e, n)[1], e] for e in
                            np.arange(e_start, e_end, -e_step)]
    test["chords"] = [[chords(diff, a, b, e)[1], e] for e in np.arange(e_start, e_end, -e_step)]
    test["newton"] = [[newton_numeric_diff(fun, diff, diff2, x0, e, n)[1], e] for e in
                      np.arange(e_start, e_end, -e_step)]
    return test


def unpack(data):
    return [d[1] for d in data], [d[0] for d in data]


def plot_test(data):
    plt.style.use('ggplot')
    x, y = unpack(data.get("bitwise_search")[:])
    plt.plot(x, y, label="bitwise_search")
    x, y = unpack(data.get("bisection")[:])
    plt.plot(x, y, label="bisection")
    x, y = unpack(data.get("gss")[:])
    plt.plot(x, y, label="gss")
    x, y = unpack(data.get("parabolic_interp")[:])
    plt.plot(x, y, label="parabolic_interp")
    x, y = unpack(data.get("middle_point")[:])
    plt.plot(x, y, label="middle_point")
    x, y = unpack(data.get("chords")[:])
    plt.plot(x, y, label="chords")
    x, y = unpack(data.get("newton")[:])
    plt.plot(x, y, label="newton")
    plt.legend(bbox_to_anchor=(1, 1), loc=1, borderaxespad=0.)
    plt.show()


def plot_test_diff(data):
    plt.style.use('ggplot')
    x, y = unpack(data.get("bisection")[:])
    plt.plot(x, y, label="bisection")
    x, y = unpack(data.get("middle_point")[:])
    plt.plot(x, y, label="middle_point")
    x, y = unpack(data.get("chords")[:])
    plt.plot(x, y, label="chords")
    x, y = unpack(data.get("newton")[:])
    plt.plot(x, y, label="newton")
    plt.legend(bbox_to_anchor=(1, 1), loc=1, borderaxespad=0.)
    plt.show()


def myfunc(x):
    return pow(x, 4) + pow(x, 2) + x + 1


def myfunc_prime(x):
    return 4 * pow(x, 3) + 2 * x + 1


def myfunc_second(x):
    return 12 * pow(x, 2) + 2


def center(fun, h, x):
    return (fun(x + h) - fun(x - h)) / (2 * h)


def center_second(fun, h, x):
    return (fun(x + h) - 2 * fun(x) + fun(x - h)) / (h ** 2)


def left(fun, h, x):
    return (fun(x) - fun(x - h)) / h


def right(fun, h, x):
    return (fun(x + h) - fun(x)) / h


def newton_test_diff_type(fun, fun_prime, fun_second, diff, diff2, a, b, e, n):
    newton_num = {}
    newton_analit = {}
    for x0 in np.arange(a, b, 0.01):
        newton_num[x0] = newton_numeric_diff(fun, diff, diff2, x0, e, n)[1]
        newton_analit[x0] = newton(fun_prime, fun_second, x0, e, n)[1]
    return [newton_num, newton_analit]


def newton_test_rafson_diff_type(fun, fun_prime, fun_second, diff, diff2, a, b, e, n):
    newton_num = {}
    newton_analit = {}
    for x0 in np.arange(a, b, 0.01):
        newton_num[x0] = newton_rafson_numeric(fun, diff, diff2, x0, e, n)[1]
        newton_analit[x0] = newton_rafson(fun_prime, fun_second, x0, e, n)[1]
    return [newton_num, newton_analit]


def newton_test_markvardt_diff_type(fun, fun_prime, fun_second, diff, diff2, a, b, e, n):
    newton_num = {}
    newton_analit = {}
    for x0 in np.arange(a, b, 0.01):
        newton_num[x0] = newton_markvardt_numeric(fun, diff, diff2, x0, e, n)[1]
        newton_analit[x0] = newton_markvardt(fun, fun_prime, fun_second, x0, e, n)[1]
    return [newton_num, newton_analit]


def fun6_1(x):
    return np.cos(x) / pow(x, 2)


def fun6_2(x):
    return 1 / 10 * x + 2 * np.sin(4 * x)


def plot_main_func(a, b, e=0.001):
    x = np.arange(a, b, e)
    y = myfunc(x)
    plt.plot(x, y)
    plt.grid(True)
    plt.show()


a = -1
b = 0
e = 0.001
