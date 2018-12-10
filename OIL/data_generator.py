import math
from random import uniform

# size = 10890000
size = 100_00


# s = " ".join(map(str, [round(uniform(0.1, 1.0), 6) for _ in range(size)]))
s = " ".join(map(str, [1 for _ in range(size)]))


size = math.sqrt(size)

with open("generated_data.txt", "w+") as file:
    file.write("OIL\n")
    file.write(str(int(size)) + " " + str(int(size))+"\n")
    file.write("20 20\n")
    file.write(s+"\n")
    file.write("/\n")
