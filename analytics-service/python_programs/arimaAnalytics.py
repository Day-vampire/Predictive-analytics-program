import sys
import pandas as pd
import numpy as np
import plotly.graph_objs as go
from plotly.subplots import make_subplots
from statsmodels.tsa.arima.model import ARIMA
from sklearn.metrics import mean_squared_error
from statsmodels.tsa.stattools import arma_order_select_ic

# ------------ Аргументы командной строки ------------
if len(sys.argv) < 4:
    print("Usage: python arimaForecast.py <csv_file> <column_index> <n_preds>")
    sys.exit(1)

csv_path = sys.argv[1]
column_index = int(sys.argv[2])
n_preds = int(sys.argv[3])

# ------------ Загрузка данных ------------
df = pd.read_csv(csv_path)
if column_index < 0 or column_index >= df.shape[1]:
    raise ValueError(f"Invalid column index: {column_index}")

data = df.iloc[:, column_index].dropna().reset_index(drop=True)

# ------------ Автоматический подбор порядка ARIMA ------------
order = arma_order_select_ic(data, ic=['aic'], trend='c')['aic_min_order']
p, q = order
d = 1  # фиксированная интеграция, можно улучшить через тесты

# ------------ Построение модели ARIMA ------------
model = ARIMA(data, order=(p, d, q))
fitted_model = model.fit()

forecast = fitted_model.forecast(steps=n_preds)
forecast_index = np.arange(len(data), len(data) + n_preds)

# ------------ Ошибка прогноза ------------
train_preds = fitted_model.predict(start=d, end=len(data)-1, dynamic=False)
mse = mean_squared_error(data[d:], train_preds)

# ------------ Визуализация ------------
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

fig.show()
# import sys
# import pandas as pd
# import matplotlib.pyplot as plt
# import warnings
# from statsmodels.tsa.arima.model import ARIMA
# from sklearn.metrics import mean_squared_error
# import itertools
#
# warnings.filterwarnings("ignore")
#
# # ========== Чтение аргументов командной строки ==========
# if len(sys.argv) < 4:
#     print("Usage: python arimaAnalytics.py <csv_file_path> <index_column> <value_column>")
#     sys.exit(1)
#
# csv_file_path = sys.argv[1]
# index_column = int(sys.argv[2])
# value_column = int(sys.argv[3])
#
# # ========== Загрузка данных ==========
# df = pd.read_csv(csv_file_path)
#
# # Преобразуем столбец индекса во временной ряд
# df.iloc[:, index_column] = pd.to_datetime(df.iloc[:, index_column])
# df.set_index(df.columns[index_column], inplace=True)
#
# # Извлекаем временной ряд
# series = df.iloc[:, value_column]
#
# # ========== Подбор параметров ARIMA вручную ==========
# print("Selecting best ARIMA(p,d,q) parameters...")
# p = d = q = range(0, 3)
# pdq = list(itertools.product(p, d, q))
#
# best_score = float("inf")
# best_cfg = None
# for param in pdq:
#     try:
#         model = ARIMA(series, order=param)
#         model_fit = model.fit()
#         aic = model_fit.aic
#         if aic < best_score:
#             best_score = aic
#             best_cfg = param
#     except:
#         continue
#
# print(f"Best ARIMA parameters: {best_cfg}, AIC: {best_score:.2f}")
#
# # ========== Обучение модели с лучшими параметрами ==========
# model = ARIMA(series, order=best_cfg)
# model_fit = model.fit()
#
# # Прогноз на будущее (например, на 12 шагов)
# forecast_steps = min(12, len(series) // 5)
# forecast = model_fit.forecast(steps=forecast_steps)
#
# # ========== Визуализация ==========
# plt.figure(figsize=(12, 6))
# plt.plot(series, label="Original")
# plt.plot(pd.date_range(start=series.index[-1], periods=forecast_steps + 1, freq=series.index.inferred_freq)[1:], forecast, label="Forecast", color='red')
# plt.title("ARIMA Forecast")
# plt.xlabel("Time")
# plt.ylabel("Value")
# plt.legend()
# plt.grid(True)
# plt.tight_layout()
# plt.savefig("arima_forecast.png")
# plt.close()
#
# # ========== Вывод метрик ==========
# print("\nModel Summary:")
# print(model_fit.summary())
