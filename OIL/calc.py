import numpy as np

R = 200
G = 300
repeat = 10

S = 0

for _ in range(repeat):
    S += np.pi * R*R
    R+=G

print(S) 

