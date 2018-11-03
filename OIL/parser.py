import numpy as np

class data:
    def __init__(self, raw_data):
        self.raw_data = raw_data
        self.data_type = raw_data[0]
        self.cell_count_xy = list(map(int, raw_data[1].split()))
        self.cell_area_xy = list(map(int, raw_data[2].split()))
        self.cell_rate_xy = np.array(list(map(float, raw_data[3].split()))).reshape((self.cell_count_xy[0], -1))

    def __str__(self):
        return "\nData type: " + str(self.data_type) + "\ncount x,y: " + str(self.cell_count_xy) + "\narea x,y: " +\
               str(self.cell_area_xy) + "\nrate x,y: " + str(self.cell_rate_xy)


def one_intersact():



with open('data.txt') as file:
    raw_data = file.read().splitlines()


mydata = data(raw_data)

print(mydata)

# print(raw_data)


