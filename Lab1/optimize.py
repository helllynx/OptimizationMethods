import numpy as np
from numpy import power as pow
import matplotlib.pyplot as plt


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




def nt(a, b, fun, fun_d, e):
    x0 = (a + b) / 2
    x1 = x0 - (fun(x0) / fun_d(x0))
    while True:
        if np.fabs(x1 - x0) < e:
            return x1
        x0 = x1
        x1 = x0 - (fun(x0) / fun_d(x0))


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
    return 4*pow(x, 3) + 2*x + 1

f = np.vectorize(myfunc)

f_prime = np.vectorize(myfunc_prime)


print(bis(f, -1, 0))
