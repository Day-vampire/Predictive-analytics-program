import sys
import pandas as pd
import numpy as np
import base64
import io
from statsmodels.tsa.arima.model import ARIMA
from sklearn.metrics import mean_squared_error
from statsmodels.tsa.stattools import arma_order_select_ic
import plotly.graph_objs as go
from plotly.subplots import make_subplots

# ------------ Чтение аргументов ------------
csv_path = sys.argv[1]
column_index = int(sys.argv[2])
n_preds = int(sys.argv[3])

# ------------ Загрузка данных ------------
df = pd.read_csv(csv_path)
if column_index < 0 or column_index >= df.shape[1]:
    raise ValueError(f"Invalid column index: {column_index}")

data = df.iloc[:, column_index].dropna().reset_index(drop=True)

# ------------ Подбор параметров ARIMA ------------
order = arma_order_select_ic(data, ic=['aic'], trend='c')['aic_min_order']
p, q = order
d = 1  # Можно улучшить, но оставим фиксированным

model = ARIMA(data, order=(p, d, q))
fitted_model = model.fit()

forecast = fitted_model.forecast(steps=n_preds)
forecast_index = np.arange(len(data), len(data) + n_preds)

train_preds = fitted_model.predict(start=d, end=len(data)-1, dynamic=False)
mse = mean_squared_error(data[d:], train_preds)

# ------------ Визуализация с plotly ------------
fig = make_subplots(rows=1, cols=1, subplot_titles=["ARIMA Модель"])

fig.add_trace(go.Scatter(x=np.arange(len(data)), y=data, mode='lines', name='Исходные данные'), row=1, col=1)
fig.add_trace(go.Scatter(x=np.arange(len(train_preds)) + d, y=train_preds, mode='lines', name='Предсказания (train)', line=dict(color='orange')), row=1, col=1)
fig.add_trace(go.Scatter(x=forecast_index, y=forecast, mode='lines', name='Прогноз'), row=1, col=1)

fig.update_layout(
    title=f'ARIMA Прогноз (p={p}, d={d}, q={q}), MSE: {mse:.4f}',
    xaxis_title='Индекс',
    yaxis_title='Значение',
    height=600, width=1000
)

# ------------ Сохранение в буфер и кодировка ------------
buffer = io.BytesIO()
fig.write_image(buffer, format='png')
buffer.seek(0)

# ------------ Печать результатов ------------
print(f"ARIMA параметры: (p={p}, d={d}, q={q})")
print("BASE64_IMAGE_START")
print(base64.b64encode(buffer.read()).decode('utf-8'))
