import pandas as pd
import sys
import os

if __name__ == "__main__":

    if len(sys.argv) < 2:
        print('There is not enough command line arguments. Program cant work')
        sys.exit()

    original_csv_name = sys.argv[1]

    if not os.path.exists(original_csv_name):
        print(f'File {original_csv_name} doesnt exist. Program cant work')
        sys.exit()

    cereal_df = pd.read_csv(original_csv_name, low_memory=False)
    temp = cereal_df.replace()
    cereal_df.to_csv("updated.csv",sep = chr(3000), na_rep = "<null>", chunksize = 10, header = True, mode="w", index=False)
