import os
import pandas as pd
import numpy as np
from sklearn.decomposition import TruncatedSVD

DATA_PATH = "ml/recommendation/data/recommendation_train_data.csv"
OUTPUT_DIR = "ml/recommendation/output"
WEB_OUTPUT_DIR = "src/main/webapp/WEB-INF/ml/recommendation"


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


def export_recommendations(matrix, predicted_matrix, top_n=5):
    results = []

    for user_id in predicted_matrix.index:
        user_scores = predicted_matrix.loc[user_id].sort_values(ascending=False)

        bought_products = matrix.loc[user_id]
        bought_products = bought_products[bought_products > 0].index

        user_scores = user_scores.drop(bought_products, errors="ignore")
        user_scores =user_scores[user_scores > 0]

        if user_scores.empty:
            user_scores = predicted_matrix.loc[user_id].sort_values(ascending=False)

        top_products = user_scores.head(top_n)

        for product_id, score in top_products.items():
            results.append({
                "userID": user_id,
                "productID": product_id,
                "predictedScore": round(float(score), 4)
            })

    result_data = pd.DataFrame(results)

    output_path = os.path.join(WEB_OUTPUT_DIR, "recommendation_results.csv")
    os.makedirs(WEB_OUTPUT_DIR, exist_ok=True)
    result_data.to_csv(output_path, index=False)

    print("Da xuat ket qua goi y:", output_path)
    print(result_data.head())

def main():
    os.makedirs(OUTPUT_DIR, exist_ok=True)

    data = load_train_data()
    matrix = create_user_product_matrix(data)

    print("So user:", matrix.shape[0])
    print("So san pham:", matrix.shape[1])
    predicted_matrix = train_model(matrix)
    export_recommendations(matrix, predicted_matrix)

if __name__ == "__main__":
    main()