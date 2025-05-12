import pandas as pd
import plotly.graph_objects as go
from plotly.offline import plot
import sys

# ------------ Аргументы из Java ------------
csv_path = sys.argv[1]            # Путь к CSV
column_index = int(sys.argv[2])   # Номер столбца (0-based)
window_size = int(sys.argv[3])    # Окно сглаживания

print(f"Файл: {csv_path}, Колонка №: {column_index}, Окно: {window_size}")

# ------------ Загрузка данных ------------
dataset = pd.read_csv(csv_path)
dataset['timestamp'] = pd.to_datetime(dataset['timestamp'])
dataset.set_index('timestamp', inplace=True)

# ------------ Получение серии по индексу столбца ------------
if column_index < 0 or column_index >= dataset.shape[1]:
    raise ValueError(f"Столбец с индексом {column_index} вне диапазона")

series = dataset.iloc[:, column_index]

# ------------ Построение графика ------------
def plotMovingAverage(series, n):
    rolling_mean = series.rolling(window=n).mean()
    rolling_std = series.rolling(window=n).std()
    upper_bound = rolling_mean + 1.96 * rolling_std
    lower_bound = rolling_mean - 1.96 * rolling_std

    data = [
        go.Scatter(x=series.index, y=rolling_mean, mode='lines', name='Скользящее среднее', line=dict(color='green')),
        go.Scatter(x=series.index, y=upper_bound, mode='lines', name='Верхняя граница', line=dict(color='red', dash='dash')),
        go.Scatter(x=series.index, y=lower_bound, mode='lines', name='Нижняя граница', line=dict(color='red', dash='dash')),
        go.Scatter(x=series.index, y=series, mode='lines', name='Фактические значения', line=dict(color='blue')),
    ]

    layout = go.Layout(title='Сглаживание', xaxis=dict(title='Дата'), yaxis=dict(title=series.name), showlegend=True)
    fig = go.Figure(data=data, layout=layout)

    plot(fig, filename='smoothing_graph.html', auto_open=True)

# ------------ Вызов ------------
plotMovingAverage(series, n=window_size)
