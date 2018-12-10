from math import sin, cos, acos, asin

r1 = 5
r2 = 5
d = 3


F1 = (2*acos(r1**2-r2**2+d**2)) / (2*r1*d)
F2 = (2*acos(r2**2-r1**2+d**2)) / (2*r2*d)

S1 = (r1**2*(F1-sin(F1)))/2
S2 = (r2**2*(F2-sin(F2)))/2

print(S1+S2)