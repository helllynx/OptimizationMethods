file=open('MAIN_MAP.txt','r')

strings = file.readlines()


f= " ".join(s.strip() for s in strings)


file2 = open('OUT_SHIT.txt', "w")

file2.write(f)