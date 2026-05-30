import os
import pandas as pd
import numpy as np
from sklearn.decomposition import TruncatedSVD

DATA_PATH = "ml/recommendation/data/recommendation_train_data.csv"
OUTPUT_DIR = "ml/recommendation/output"


def load_train_data():
    data = pd.read_csv(DATA_PATH)

    print("Du lieu train:")
    print(data.head())
    print("So dong du lieu:", len(data))

    return data
def create_user_product_matrix(data):
    matrix = data.pivot_table(
        index="userID",
        columns="productID",
        values="score",
        fill_value=0
    )

    print("Ma tran user-product:")
    print(matrix)

    matrix_path = os.path.join(OUTPUT_DIR, "user_product_matrix.csv")
    matrix.to_csv(matrix_path)

    print("Da luu ma tran:", matrix_path)

    return matrix

def train_model(matrix):
    rows, cols = matrix.shape

    if rows < 2 or cols < 2:
        print("Du lieu chua du de train")
        return None

    n_components = min(2, rows - 1, cols - 1)

    model = TruncatedSVD(n_components=n_components, random_state=42)
    user_features = model.fit_transform(matrix)
    predicted_scores = np.dot(user_features, model.components_)

    predicted_matrix = pd.DataFrame(
        predicted_scores,
        index=matrix.index,
        columns=matrix.columns
    )

    return predicted_matrix


def main():
    os.makedirs(OUTPUT_DIR, exist_ok=True)

    data = load_train_data()
    matrix = create_user_product_matrix(data)

    print("So user:", matrix.shape[0])
    print("So san pham:", matrix.shape[1])

if __name__ == "__main__":
    main()