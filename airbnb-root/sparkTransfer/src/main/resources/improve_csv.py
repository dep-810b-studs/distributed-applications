import pandas as pd

if __name__ == "__main__":
    print(chr(3000))
    cereal_df = pd.read_csv("D:/GIT/distributed-applications/airbnb-root/server/src/main/resources/listings.csv",low_memory=False)
    temp = cereal_df.replace()
    print(temp)
    #cereal_df = cereal_df.fillna("<null>")
    #ar = cereal_df.head(2)
    #print(ar)
    cereal_df.to_csv("D:/GIT/distributed-applications/airbnb-root/server/src/main/resources/listings2.csv",
    sep = chr(3000),
                     na_rep = "<null>",
                     chunksize = 10,
                     header = True,
                     mode="w",
                     index=False,


                     )