import math


size = 1_000_00


s = " ".join(map(str, range(1, size+1)))

size = math.sqrt(size)

with open("generated_data.txt", "w+") as file:
    file.write("OIL\n")
    file.write(str(int(size)) + " " + str(int(size))+"\n")
    file.write("20 20\n")
    file.write(s+"\n")
    file.write("/\n")
