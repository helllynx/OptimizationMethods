outfile = open("out.txt", "w")

with open("OIL.txt") as file:
    text = file.read()
    str2 = text.replace("\n", " ")
    outfile.write(str2)

outfile.close()