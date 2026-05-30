import os
import pandas as pd

DATA_PATH = "ml/recommendation/data/recommendation_train_data.csv"
OUTPUT_DIR = "ml/recommendation/output"


def load_train_data():
    data = pd.read_csv(DATA_PATH)

    print("Du lieu train:")
    print(data.head())
    print("So dong du lieu:", len(data))

    return data


def main():
    os.makedirs(OUTPUT_DIR, exist_ok=True)

    data = load_train_data()

    print("Cac cot trong file:", list(data.columns))


if __name__ == "__main__":
    main()